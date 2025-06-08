package com.example.quickaccessandroid.Activities;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickaccessandroid.API.JwtUtils;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.Adapters.ResidentNotificationAdapter;
import com.example.quickaccessandroid.DTO.NotificationDTO;
import com.example.quickaccessandroid.Models.Notification;
import com.example.quickaccessandroid.R;
import com.example.quickaccessandroid.Utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResidentActiveNotificationsActivity extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private ResidentNotificationAdapter residentNotificationAdapter;
    private List<Notification> notificationList;
    private ApiService API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_active_notifications);

        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        API = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        // Token'ı alma
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token != null) {
            JSONObject decoded = JwtUtils.decodeJWT(token);
            String userId = decoded.optString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier");

            fetchNotifications(userId);
        } else {
            Toast.makeText(this, "User token not found. Please login again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchNotifications(String userId) {
        API.getActiveNotificationsForResident(userId).enqueue(new Callback<List<NotificationDTO>>() {
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
                    residentNotificationAdapter = new ResidentNotificationAdapter(ResidentActiveNotificationsActivity.this, notificationList);
                    notificationsRecyclerView.setAdapter(residentNotificationAdapter);
                } else {
                    Toast.makeText(ResidentActiveNotificationsActivity.this, "Could not receive notifications.", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<NotificationDTO>> call, Throwable t) {
                Toast.makeText(ResidentActiveNotificationsActivity.this, "Could not connect to server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Network error: " + t.getMessage());
            }
        });
    }
}
