package com.example.quickaccessandroid.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.Activities.ManageSiteManagersActivity;
import com.example.quickaccessandroid.DTO.ManageAdminDTO;
import com.example.quickaccessandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAdminsAdapter extends RecyclerView.Adapter<ManageAdminsAdapter.AdminsViewHolder> {

    private List<ManageAdminDTO> adminsList;
    private Context context;

    // Constructor
    public ManageAdminsAdapter(Context context, List<ManageAdminDTO> adminsList) {
        this.context = context;
        this.adminsList = adminsList;
    }

    @Override
    public AdminsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_admins, parent, false);
        return new AdminsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdminsViewHolder holder, int position) {
        ManageAdminDTO admin = adminsList.get(position);

        String nameSurname = admin.getName() + " " + admin.getSurname();

        holder.nameSurname.setText(nameSurname);
        holder.username.setText(admin.getUsername());
        holder.role.setText(admin.getRole());

        holder.deleteBtn.setOnClickListener(v -> {
            String adminId = admin.getId();  // ID'yi aldık

            // API ile silme işlemi yapılabilir:
            deleteAdminFromServer(context, adminId);

            // Listeden çıkar ve UI'yı güncelle
            adminsList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, adminsList.size());
        });
    }

    @Override
    public int getItemCount() {
        return adminsList.size();
    }

    // ViewHolder class
    public static class AdminsViewHolder extends RecyclerView.ViewHolder {
        TextView nameSurname;
        TextView username;
        TextView role;
        ImageView deleteBtn;

        public AdminsViewHolder(View itemView) {
            super(itemView);
            nameSurname = itemView.findViewById(R.id.nameSurnameTxt);
            username = itemView.findViewById(R.id.usernameTxt);
            role = itemView.findViewById(R.id.roleTxt);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    private void deleteAdminFromServer(Context context, String id) {
        ApiService api = ApiClient.getClient(context).create(ApiService.class);
        // Admin silmek için DELETE isteği
        Call<Void> call = api.deleteAdmin(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Admin deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error deleting admin", Toast.LENGTH_SHORT).show();
                    Log.e("DELETE", "Server returned error: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error deleting admin", Toast.LENGTH_SHORT).show();
                Log.e("DELETE", "Error deleting admin: " + t.getMessage());
            }
        });
    }

}

