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
import com.example.quickaccessandroid.DTO.ManageSiteManagerDTO;
import com.example.quickaccessandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageSiteManagersAdapter extends RecyclerView.Adapter<ManageSiteManagersAdapter.SiteManagersViewHolder> {

    private List<ManageSiteManagerDTO> siteManagersList;
    private Context context;

    // Constructor
    public ManageSiteManagersAdapter(Context context, List<ManageSiteManagerDTO> siteManagersList) {
        this.context = context;
        this.siteManagersList = siteManagersList;
    }

    @Override
    public SiteManagersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_site_managers, parent, false);
        return new SiteManagersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SiteManagersViewHolder holder, int position) {
        ManageSiteManagerDTO siteManager = siteManagersList.get(position);

        String nameSurname = siteManager.getName() + " " + siteManager.getSurname();

        holder.nameSurname.setText(nameSurname);
        holder.username.setText(siteManager.getUsername());
        holder.siteName.setText(siteManager.getSiteName());

        holder.deleteBtn.setOnClickListener(v -> {
            String siteManagerId = siteManager.getId();  // ID'yi aldık

            // API ile silme işlemi yapılabilir:
            deleteSiteManagerFromServer(context, siteManagerId);

            // Listeden çıkar ve UI'yı güncelle
            siteManagersList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, siteManagersList.size());
        });
    }

    @Override
    public int getItemCount() {
        return siteManagersList.size();
    }

    // ViewHolder class
    public static class SiteManagersViewHolder extends RecyclerView.ViewHolder {
        TextView nameSurname;
        TextView username;
        TextView siteName;
        ImageView deleteBtn;

        public SiteManagersViewHolder(View itemView) {
            super(itemView);
            nameSurname = itemView.findViewById(R.id.nameSurnameTxt);
            username = itemView.findViewById(R.id.usernameTxt);
            siteName = itemView.findViewById(R.id.siteNameTxt);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    private void deleteSiteManagerFromServer(Context context, String siteManagerId) {
        ApiService api = ApiClient.getClient(context).create(ApiService.class);
        // Site Manager silmek için DELETE isteği
        Call<Void> call = api.deleteSiteManager(siteManagerId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Site manager deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("DELETE", "Server returned error: " + response.code());
                    Toast.makeText(context, "Error deleting site manager", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("DELETE", "Error deleting site manager: " + t.getMessage());
                Toast.makeText(context, "Error deleting site manager", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

