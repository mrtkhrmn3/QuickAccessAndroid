package com.example.quickaccessandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.API.JwtUtils;
import com.example.quickaccessandroid.Adapters.SecurityNotificationAdapter;
import com.example.quickaccessandroid.DTO.NotificationCompleteDTO;
import com.example.quickaccessandroid.DTO.NotificationDTO;
import com.example.quickaccessandroid.Models.Notification;
import com.example.quickaccessandroid.R;
import com.example.quickaccessandroid.Utils.TimeUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecurityActiveNotificationsActivity extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private Button logoutButton;
    private SecurityNotificationAdapter securityNotificationAdapter;
    private List<Notification> notificationList;
    private ApiService API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_active_notifications);

        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        logoutButton = findViewById(R.id.logoutButton);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        notificationsRecyclerView.setLayoutManager(layoutManager);

        API = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        String userId = getUserIdFromToken();
        fetchNotifications(userId);

        logoutButton.setOnClickListener(v -> {
            // 1. Token'ı temizle
            SharedPreferences preferences = getSharedPreferences("auth", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("token");
            editor.remove("role");
            editor.apply();

            // 2. Login'e yönlendir, geçmişi temizle
            Intent intent = new Intent(SecurityActiveNotificationsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // backstack'i sil
            startActivity(intent);
            finish();
        });
    }

    private void fetchNotifications(String userId) {
        API.getNotifications(userId).enqueue(new Callback<List<NotificationDTO>>() {
            @Override
            public void onResponse(Call<List<NotificationDTO>> call, Response<List<NotificationDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NotificationDTO> notificationDTOs = response.body();

                    // DTO’ları Model’e çevir
                    notificationList = new ArrayList<>();
                    for (NotificationDTO dto : notificationDTOs) {
                        String relativeTime = TimeUtils.getTimeAgo(dto.getCreatedAt());
                        notificationList.add(new Notification(dto.getNotificationId(), dto.getType(), dto.getDescription(), relativeTime));
                    }

                    // Adapter set et
                    securityNotificationAdapter = new SecurityNotificationAdapter(notificationList, clickedNotification -> {
                        fetchNotificationDetail(clickedNotification.getNotificationId());
                    });
                    notificationsRecyclerView.setAdapter(securityNotificationAdapter);
                } else {
                    Toast.makeText(SecurityActiveNotificationsActivity.this, "Could not receive notifications.", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<NotificationDTO>> call, Throwable t) {
                Toast.makeText(SecurityActiveNotificationsActivity.this, "Could not connect to server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Network error: " + t.getMessage());
            }
        });
    }

    private void showNotificationDetailPopup(NotificationDTO notification) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View popupView = getLayoutInflater().inflate(R.layout.popup_notification, null);

        TextView typeText = popupView.findViewById(R.id.textType);
        TextView blockAptText = popupView.findViewById(R.id.textAddress);
        TextView descText = popupView.findViewById(R.id.textDescription);
        TextView timeText = popupView.findViewById(R.id.textTime);
        Button completeBtn = popupView.findViewById(R.id.buttonMarkAsCompleted);
        Button backBtn = popupView.findViewById(R.id.buttonClose);

        // Değerleri set et
        typeText.setText(notification.getType());
        blockAptText.setText("Block: " + notification.getBlock() + " Apartment: " + notification.getAptNo());
        descText.setText(notification.getDescription());
        timeText.setText(notification.getCreatedAt());

        AlertDialog dialog = builder.setView(popupView).create();
        dialog.show();

        completeBtn.setOnClickListener(v -> {
            NotificationCompleteDTO completeDTO = new NotificationCompleteDTO(notification.getNotificationId());

            API.completeNotification(completeDTO).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(SecurityActiveNotificationsActivity.this, "Notification marked as completed.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        fetchNotifications(getUserIdFromToken());
                    } else {
                        Toast.makeText(SecurityActiveNotificationsActivity.this, "Failed to mark as completed.", Toast.LENGTH_SHORT).show();
                        Log.e("API_ERROR", "Complete failed: " + response.toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(SecurityActiveNotificationsActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Network error on complete: " + t.getMessage());
                }
            });
        });

        backBtn.setOnClickListener(v -> dialog.dismiss());
    }

    private void fetchNotificationDetail(String id) {
        API.getNotificationById(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String json = response.body().string();

                        // JSON'u NotificationDTO ya çevir
                        Gson gson = new Gson();
                        NotificationDTO notification = gson.fromJson(json, NotificationDTO.class);

                        notification.setCreatedAt(TimeUtils.getTimeAgo(notification.getCreatedAt()));

                        showNotificationDetailPopup(notification);

                    } catch (Exception e) {
                        Log.e("PARSE_ERROR", "JSON parsing failed: " + e.getMessage());
                        Toast.makeText(SecurityActiveNotificationsActivity.this, "An error occurred.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SecurityActiveNotificationsActivity.this, "Could not load notification.", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Fetch failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SecurityActiveNotificationsActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Fetch failed: " + t.getMessage());
            }
        });
    }

    private String getUserIdFromToken() {
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token != null) {
            JSONObject decoded = JwtUtils.decodeJWT(token);
            return decoded.optString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier");
        }

        return null;
    }


}
