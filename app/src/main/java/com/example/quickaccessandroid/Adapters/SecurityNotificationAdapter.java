package com.example.quickaccessandroid.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quickaccessandroid.Models.Notification;
import com.example.quickaccessandroid.R;

import java.util.List;

public class SecurityNotificationAdapter extends RecyclerView.Adapter<SecurityNotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationsList;

    // Constructor
    public SecurityNotificationAdapter(List<Notification> notificationsList) {
        this.notificationsList = notificationsList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Security için özel item layout'u kullanılacak
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.security_notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = notificationsList.get(position);
        holder.title.setText(notification.getTitle());
        holder.message.setText(notification.getMessage());
        holder.timestamp.setText(notification.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    // ViewHolder class
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView message;
        TextView timestamp;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notificationTitle);
            message = itemView.findViewById(R.id.notificationMessage);
            timestamp = itemView.findViewById(R.id.notificationTime);
        }
    }
}
