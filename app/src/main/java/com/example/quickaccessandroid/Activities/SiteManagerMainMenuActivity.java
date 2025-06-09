package com.example.quickaccessandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickaccessandroid.DTO.CreateNotificationDTO;
import com.example.quickaccessandroid.R;

public class SiteManagerMainMenuActivity extends AppCompatActivity {

    private Button addNewResidentBtn;
    private Button addNewSecurityBtn;
    private Button manageResidentsBtn;
    private Button manageSecuritiesBtn;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_manager_main_menu);

        addNewResidentBtn = findViewById(R.id.addNewResidentBtn);
        addNewSecurityBtn = findViewById(R.id.addNewSecurityBtn);
        manageResidentsBtn = findViewById(R.id.manageResidentsBtn);
        manageSecuritiesBtn = findViewById(R.id.manageSecuritiesBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        addNewResidentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SiteManagerMainMenuActivity.this, AddResidentActivity.class);
                startActivity(intent);
            }
        });

        addNewSecurityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SiteManagerMainMenuActivity.this, AddSecurityActivity.class);
                startActivity(intent);
            }
        });

        manageResidentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SiteManagerMainMenuActivity.this, ManageResidentsActivity.class);
                startActivity(intent);
            }
        });


        manageSecuritiesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SiteManagerMainMenuActivity.this, ManageSecuritiesActivity.class);
                startActivity(intent);
            }
        });

        // Logout Button
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Token'ı temizle
                SharedPreferences preferences = getSharedPreferences("auth", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("token");
                editor.remove("role");
                editor.apply();

                // 2. Login'e yönlendir, geçmişi temizle
                Intent intent = new Intent(SiteManagerMainMenuActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // backstack'i sil
                startActivity(intent);
                finish();
            }
        });

    }
}
