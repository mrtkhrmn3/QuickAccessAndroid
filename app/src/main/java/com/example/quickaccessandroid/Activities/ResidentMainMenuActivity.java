package com.example.quickaccessandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.DTO.CreateNotificationDTO;
import com.example.quickaccessandroid.DTO.NotificationDTO;
import com.example.quickaccessandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        // Food Delivery Button
        foodDeliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Inflate layout
                LayoutInflater inflater = LayoutInflater.from(ResidentMainMenuActivity.this);
                View popupView = inflater.inflate(R.layout.popup_add_notification, null);

                // 2. Access views
                TextView popupTitle = popupView.findViewById(R.id.popupTitle);
                EditText inputField = popupView.findViewById(R.id.popupInputField);
                TextView popupDescription = popupView.findViewById(R.id.popupDescription);
                Button buttonAdd = popupView.findViewById(R.id.buttonAdd);
                Button buttonCancel = popupView.findViewById(R.id.buttonCancel);

                // 3. Set dynamic content
                popupTitle.setText("Add Food Delivery Notification");
                popupDescription.setText("Please enter the name of the restaurant that you ordered from.");


                // 4. Build the dialog
                AlertDialog dialog = new AlertDialog.Builder(ResidentMainMenuActivity.this)
                        .setView(popupView)
                        .setCancelable(false)
                        .create();

                dialog.show();

                // 5. Button actions
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inputText = inputField.getText().toString().trim();
                        if (!inputText.isEmpty()) {
                            String type = "Food Delivery";
                            String description = inputText;

                            CreateNotificationDTO notification = new CreateNotificationDTO(type, description);

                            saveNotificationToDatabase(notification);

                            //Toast.makeText(ResidentMainMenuActivity.this, "Added: " + type, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            inputField.setError("This field cannot be empty");
                        }
                    }
                });
            }
        });


        // Package Button
        packageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Inflate layout
                LayoutInflater inflater = LayoutInflater.from(ResidentMainMenuActivity.this);
                View popupView = inflater.inflate(R.layout.popup_add_notification, null);

                // 2. Access views
                TextView popupTitle = popupView.findViewById(R.id.popupTitle);
                EditText inputField = popupView.findViewById(R.id.popupInputField);
                TextView popupDescription = popupView.findViewById(R.id.popupDescription);
                Button buttonAdd = popupView.findViewById(R.id.buttonAdd);
                Button buttonCancel = popupView.findViewById(R.id.buttonCancel);

                // 3. Set dynamic content
                popupTitle.setText("Add Cargo Delivery Notification");
                popupDescription.setText("Please enter the cargo company and/or the type of your cargo");

                // 4. Build the dialog
                AlertDialog dialog = new AlertDialog.Builder(ResidentMainMenuActivity.this)
                        .setView(popupView)
                        .setCancelable(false)
                        .create();

                dialog.show();

                // 5. Button actions
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inputText = inputField.getText().toString().trim();
                        if (!inputText.isEmpty()) {
                            String type = "Cargo Delivery";
                            String description = inputText;

                            CreateNotificationDTO notification = new CreateNotificationDTO(type, description);

                            saveNotificationToDatabase(notification);

                            //Toast.makeText(ResidentMainMenuActivity.this, "Added: " + type, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            inputField.setError("This field cannot be empty");
                        }
                    }
                });
            }
        });

        // Guest Coming Button
        guestComingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Inflate layout
                LayoutInflater inflater = LayoutInflater.from(ResidentMainMenuActivity.this);
                View popupView = inflater.inflate(R.layout.popup_add_notification, null);

                // 2. Access views
                TextView popupTitle = popupView.findViewById(R.id.popupTitle);
                EditText inputField = popupView.findViewById(R.id.popupInputField);
                TextView popupDescription = popupView.findViewById(R.id.popupDescription);
                Button buttonAdd = popupView.findViewById(R.id.buttonAdd);
                Button buttonCancel = popupView.findViewById(R.id.buttonCancel);

                // 3. Set dynamic content
                popupTitle.setText("Added Guest Arrival Notification");
                popupDescription.setText("Please enter the name and surname of your guest.");


                // 4. Build the dialog
                AlertDialog dialog = new AlertDialog.Builder(ResidentMainMenuActivity.this)
                        .setView(popupView)
                        .setCancelable(false)
                        .create();

                dialog.show();

                // 5. Button actions
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inputText = inputField.getText().toString().trim();
                        if (!inputText.isEmpty()) {
                            String type = "Guest";
                            String description = inputText;

                            CreateNotificationDTO notification = new CreateNotificationDTO(type, description);

                            saveNotificationToDatabase(notification);

                            //Toast.makeText(ResidentMainMenuActivity.this, "Added: " + type, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            inputField.setError("This field cannot be empty");
                        }
                    }
                });
            }
        });


        // Active Notifications Button
        activeNotificationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Active Notifications Activity
                Intent intent = new Intent(ResidentMainMenuActivity.this, ResidentActiveNotificationsActivity.class);
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
                Intent intent = new Intent(ResidentMainMenuActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // backstack'i sil
                startActivity(intent);
                finish();
            }
        });

    }

    private void saveNotificationToDatabase(CreateNotificationDTO notification) {
        ApiService api = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        Call<Void> call = api.sendNotification(notification);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ResidentMainMenuActivity.this, "Added new notification", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResidentMainMenuActivity.this, "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e(null, response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ResidentMainMenuActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
