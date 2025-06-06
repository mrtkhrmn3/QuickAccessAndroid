package com.example.quickaccessandroid.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickaccessandroid.Adapters.NotificationAdapter;
import com.example.quickaccessandroid.Models.Notification;
import com.example.quickaccessandroid.R;

import java.util.ArrayList;
import java.util.List;

public class SecurityActiveNotificationsActivity extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_active_notifications);

        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);

        // Initialize the list of notifications
        notificationList = new ArrayList<>();
        notificationList.add(new Notification("B-118", "Food Delivery", "10 mins ago"));
        notificationList.add(new Notification("B-724", "Food Delivery", "15 mins ago"));
        notificationList.add(new Notification("C-087", "Guest Coming", "20 mins ago"));
        notificationList.add(new Notification("A-087", "Guest Coming", "30 mins ago"));
        notificationList.add(new Notification("A-587", "Guest Coming", "40 mins ago"));

        // Set up the RecyclerView
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter = new NotificationAdapter(notificationList);
        notificationsRecyclerView.setAdapter(notificationAdapter);

    }
}
