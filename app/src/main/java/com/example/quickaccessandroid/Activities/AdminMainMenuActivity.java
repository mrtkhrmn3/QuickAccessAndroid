package com.example.quickaccessandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickaccessandroid.R;

public class AdminMainMenuActivity extends AppCompatActivity {

    private Button addNewAdminBtn;
    private Button addNewSiteManagerBtn;
    private Button manageAdminsBtn;
    private Button manageSiteManagersBtn;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);

        addNewAdminBtn = findViewById(R.id.addNewAdminBtn);
        addNewSiteManagerBtn = findViewById(R.id.addNewSiteManagerBtn);
        manageAdminsBtn = findViewById(R.id.manageAdminsBtn);
        manageSiteManagersBtn = findViewById(R.id.manageSiteManagersBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        addNewAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainMenuActivity.this, AddAdminActivity.class);
                startActivity(intent);
            }
        });

        addNewSiteManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainMenuActivity.this, AddSiteManagerActivity.class);
                startActivity(intent);
            }
        });

        manageAdminsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        manageSiteManagersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Intent intent = new Intent(AdminMainMenuActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // backstack'i sil
                startActivity(intent);
                finish();
            }
        });

    }

}
