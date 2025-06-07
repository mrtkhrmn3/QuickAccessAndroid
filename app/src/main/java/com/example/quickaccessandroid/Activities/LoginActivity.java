package com.example.quickaccessandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.Activities.AddResidentActivity;
import com.example.quickaccessandroid.API.JwtUtils;
import com.example.quickaccessandroid.DTO.LoginRequestDTO;
import com.example.quickaccessandroid.DTO.LoginResponseDTO;
import com.example.quickaccessandroid.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private ApiService API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.editUsername);
        edtPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.buttonLogin);

        API = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and password cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(username, password);
        });
    }

    private void loginUser(String username, String password) {
        LoginRequestDTO request = new LoginRequestDTO(username, password);

        API.login(request).enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    String role = response.body().getRole();

                    // Token ve rolü SharedPreferences'a kaydet
                    SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
                    prefs.edit()
                            .putString("token", token)
                            .putString("role", role)
                            .apply();

                    // Token'dan verileri test et
                    JSONObject decoded = JwtUtils.decodeJWT(token);
                    if (decoded != null) {
                        String userId = decoded.optString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier");
                        String username = decoded.optString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name");
                        String roleFromToken = decoded.optString("http://schemas.microsoft.com/ws/2008/06/identity/claims/role");

                        Log.d("JWT_TEST", "UserId: " + userId);
                        Log.d("JWT_TEST", "Username: " + username);
                        Log.d("JWT_TEST", "Role from token: " + roleFromToken);
                    } else {
                        Log.e("JWT_TEST", "Token decode failed.");
                    }

                    // Rolüne göre ilgili sayfaya yönlendir
                    redirectToRoleScreen(role);
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed! Check your information.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed to connect to server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectToRoleScreen(String role) {
        Intent intent;
        switch (role.toLowerCase()) {
            case "admin":
                intent = new Intent(LoginActivity.this, AddAdminActivity.class);
                break;
            case "sitemanager":
                intent = new Intent(LoginActivity.this, AddResidentActivity.class);
                break;
            case "resident":
                intent = new Intent(LoginActivity.this, ResidentMainMenuActivity.class);
                break;
            case "security":
                intent = new Intent(LoginActivity.this, SecurityActiveNotificationsActivity.class);
                break;
            default:
                Toast.makeText(this, "Undefined role: " + role, Toast.LENGTH_SHORT).show();
                return;
        }
        startActivity(intent);
        finish(); // login ekranını kapat
    }
}
