package com.example.quickaccessandroid.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.DTO.SecurityRegisterDTO;
import com.example.quickaccessandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSecurityActivity extends AppCompatActivity {

    private EditText editName, editSurname, editSiteName, editUsername, editPassword;
    private Button buttonAddSecurity;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_security); // XML dosyanın adı buysa

        editName = findViewById(R.id.editSecurityName);
        editSurname = findViewById(R.id.editSecuritySurname);
        editSiteName = findViewById(R.id.editSecuritySiteName);
        editUsername = findViewById(R.id.editSecurityUsername);
        editPassword = findViewById(R.id.editSecurityPassword);
        buttonAddSecurity = findViewById(R.id.buttonAddSecurity);

        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        buttonAddSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSecurity();
            }
        });
    }

    private void registerSecurity() {
        String name = editName.getText().toString().trim();
        String surname = editSurname.getText().toString().trim();
        String siteName = editSiteName.getText().toString().trim();
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // Password validation with regular expression (min 6 characters and at least 1 uppercase letter)
        if (!password.matches("^(?=.*[A-Z]).{6,}$")) {
            Toast.makeText(this, "Password must be 6+ characters and include 1 uppercase letter", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.isEmpty() || surname.isEmpty() || siteName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SecurityRegisterDTO dto = new SecurityRegisterDTO(name, surname, siteName, username, password);

        Call<Void> call = apiService.registerSecurity(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddSecurityActivity.this, "Security added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(AddSecurityActivity.this, "Failed to add security. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e(null, response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddSecurityActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clearFields() {
        editName.setText("");
        editSurname.setText("");
        editSiteName.setText("");
        editUsername.setText("");
        editPassword.setText("");
    }
}
