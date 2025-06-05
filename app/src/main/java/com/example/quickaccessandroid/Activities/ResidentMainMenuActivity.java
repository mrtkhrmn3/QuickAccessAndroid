package com.example.quickaccessandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickaccessandroid.R;

public class ResidentMainMenuActivity extends AppCompatActivity {

    private Button foodDeliveryBtn;
    private Button packageBtn;
    private Button guestComingBtn;
    private Button activeNotificationsBtn;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_main_menu);

        foodDeliveryBtn = findViewById(R.id.foodDeliveryBtn);
        packageBtn = findViewById(R.id.packageBtn);
        guestComingBtn = findViewById(R.id.guestComingBtn);
        activeNotificationsBtn = findViewById(R.id.activeNotificationsBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        /*
        // Food Delivery Button
        foodDeliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Food Delivery Activity (replace with actual activity)
                Intent intent = new Intent(ResidentMainMenuActivity.this, FoodDeliveryActivity.class);
                startActivity(intent);
            }
        });

        // Package Button
        packageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Package Activity (replace with actual activity)
                Intent intent = new Intent(ResidentMainMenuActivity.this, PackageActivity.class);
                startActivity(intent);
            }
        });

        // Guest Coming Button
        guestComingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Guest Coming Activity (replace with actual activity)
                Intent intent = new Intent(ResidentMainMenuActivity.this, GuestComingActivity.class);
                startActivity(intent);
            }
        });
        */

        // Active Notifications Button
        activeNotificationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Active Notifications Activity
                Intent intent = new Intent(ResidentMainMenuActivity.this, ResidentActiveNotificationsActivity.class);
                startActivity(intent);
            }
        });

        /*
        // Logout Button
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout operation (e.g., clear user session or redirect to login)
                // For now, we'll just finish the activity and go back to the login screen
                Intent intent = new Intent(ResidentMainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the current activity after logging out
            }
        });
         */
    }
}
