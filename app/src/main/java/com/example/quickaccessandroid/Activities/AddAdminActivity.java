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
import com.example.quickaccessandroid.DTO.AdminRegisterDTO;
import com.example.quickaccessandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAdminActivity extends AppCompatActivity {

    private EditText editName, editSurname, editUsername, editPassword;
    private Button buttonAddAdmin;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin); // XML dosyanın adı buysa

        editName = findViewById(R.id.editAdminName);
        editSurname = findViewById(R.id.editAdminSurname);
        editUsername = findViewById(R.id.editAdminUsername);
        editPassword = findViewById(R.id.editAdminPassword);
        buttonAddAdmin = findViewById(R.id.buttonAddAdmin);

        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        buttonAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAdmin();
            }
        });
    }

    private void registerAdmin() {
        String name = editName.getText().toString().trim();
        String surname = editSurname.getText().toString().trim();
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // Password validation with regular expression (min 6 characters and at least 1 uppercase letter)
        if (!password.matches("^(?=.*[A-Z]).{6,}$")) {
            Toast.makeText(this, "Password must be 6+ characters and include 1 uppercase letter", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        AdminRegisterDTO dto = new AdminRegisterDTO(name, surname, username, password);

        Call<Void> call = apiService.registerAdmin(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddAdminActivity.this, "Admin added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(AddAdminActivity.this, "Failed to add admin. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e(null, response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddAdminActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clearFields() {
        editName.setText("");
        editSurname.setText("");
        editUsername.setText("");
        editPassword.setText("");
    }
}
