package com.example.quickaccessandroid.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.DTO.SiteManagerRegisterDTO;
import com.example.quickaccessandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSiteManagerActivity extends AppCompatActivity {

    private EditText editName, editSurname, editSiteName, editUsername, editPassword;
    private Button buttonAddSiteManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site_manager); // XML dosyanın adı buysa

        editName = findViewById(R.id.editSiteManagerName);
        editSurname = findViewById(R.id.editSiteManagerSurname);
        editSiteName = findViewById(R.id.editSiteManagerSiteName);
        editUsername = findViewById(R.id.editSiteManagerUsername);
        editPassword = findViewById(R.id.editSiteManagerPassword);
        buttonAddSiteManager = findViewById(R.id.buttonAddSiteManager);

        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        buttonAddSiteManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerSiteManager();
            }
        });
    }

    private void registerSiteManager() {
        String name = editName.getText().toString().trim();
        String surname = editSurname.getText().toString().trim();
        String siteName = editSiteName.getText().toString().trim();
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (name.isEmpty() || surname.isEmpty() || siteName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SiteManagerRegisterDTO dto = new SiteManagerRegisterDTO(name, surname, siteName, username, password);

        Call<Void> call = apiService.registerSiteManager(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddSiteManagerActivity.this, "Site Manager added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(AddSiteManagerActivity.this, "Failed to add site manager. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddSiteManagerActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
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
