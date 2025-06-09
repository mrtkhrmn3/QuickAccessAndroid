package com.example.quickaccessandroid.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.Models.Notification;
import com.example.quickaccessandroid.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResidentNotificationAdapter extends RecyclerView.Adapter<ResidentNotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationsList;
    private Context context;

    // Constructor
    public ResidentNotificationAdapter(Context context, List<Notification> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resident_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = notificationsList.get(position);

        holder.title.setText(notification.getTitle());
        holder.message.setText(notification.getMessage());
        holder.timestamp.setText(notification.getTimestamp());

        holder.deleteBtn.setOnClickListener(v -> {
            String notificationId = notification.getNotificationId();  // ID'yi aldık

            // API ile silme işlemi yapılabilir:
            deleteNotificationFromServer(context, notificationId);

            // Listeden çıkar ve UI'yı güncelle
            notificationsList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, notificationsList.size());
        });
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
        ImageView deleteBtn;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notificationTitle);
            message = itemView.findViewById(R.id.notificationMessage);
            timestamp = itemView.findViewById(R.id.notificationTime);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    private void deleteNotificationFromServer(Context context, String id) {
        ApiService api = ApiClient.getClient(context).create(ApiService.class);
        Call<ResponseBody> call = api.deleteNotification(id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("DELETE", "Notification deleted successfully");
                    // UI'dan da silinmesini buraya ekle
                } else {
                    Log.e("DELETE", "Server returned error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("DELETE", "Error deleting notification: " + t.getMessage());
            }
        });
    }

}

