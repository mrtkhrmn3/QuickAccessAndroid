package com.example.quickaccessandroid.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickaccessandroid.Adapters.ResidentNotificationAdapter;
import com.example.quickaccessandroid.Models.ResidentNotification;
import com.example.quickaccessandroid.R;

import java.util.ArrayList;
import java.util.List;

public class ResidentActiveNotificationsActivity extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private ResidentNotificationAdapter notificationAdapter;
    private List<ResidentNotification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_active_notifications);

        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);

        // Initialize the list of notifications
        notificationList = new ArrayList<>();
        notificationList.add(new ResidentNotification("Package Delivery", "Your package has been delivered.", "10 mins ago"));
        notificationList.add(new ResidentNotification("Guest Arrival", "John Doe is at the gate.", "15 mins ago"));
        notificationList.add(new ResidentNotification("Food Delivery", "Pizza is arriving in 5 minutes.", "20 mins ago"));

        // Set up the RecyclerView
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter = new ResidentNotificationAdapter(notificationList);
        notificationsRecyclerView.setAdapter(notificationAdapter);

    }
}
