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
import com.example.quickaccessandroid.DTO.ManageSecurityDTO;
import com.example.quickaccessandroid.DTO.ManageSiteManagerDTO;
import com.example.quickaccessandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageSecuritiesAdapter extends RecyclerView.Adapter<ManageSecuritiesAdapter.SecuritiesViewHolder> {

    private List<ManageSecurityDTO> securitiesList;
    private Context context;

    // Constructor
    public ManageSecuritiesAdapter(Context context, List<ManageSecurityDTO> securitiesList) {
        this.context = context;
        this.securitiesList = securitiesList;
    }

    @Override
    public SecuritiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_securities, parent, false);
        return new SecuritiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SecuritiesViewHolder holder, int position) {
        ManageSecurityDTO security = securitiesList.get(position);

        String nameSurname = security.getName() + " " + security.getSurname();

        holder.nameSurname.setText(nameSurname);
        holder.username.setText(security.getUsername());
        holder.siteName.setText(security.getSiteName());

        holder.deleteBtn.setOnClickListener(v -> {
            String siteManagerId = security.getId();  // ID'yi aldık

            // API ile silme işlemi yapılabilir:
            deleteSecurityFromServer(context, siteManagerId);

            // Listeden çıkar ve UI'yı güncelle
            securitiesList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, securitiesList.size());
        });
    }

    @Override
    public int getItemCount() {
        return securitiesList.size();
    }

    // ViewHolder class
    public static class SecuritiesViewHolder extends RecyclerView.ViewHolder {
        TextView nameSurname;
        TextView username;
        TextView siteName;
        ImageView deleteBtn;

        public SecuritiesViewHolder(View itemView) {
            super(itemView);
            nameSurname = itemView.findViewById(R.id.nameSurnameTxt);
            username = itemView.findViewById(R.id.usernameTxt);
            siteName = itemView.findViewById(R.id.siteNameTxt);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    private void deleteSecurityFromServer(Context context, String securityId) {
        ApiService api = ApiClient.getClient(context).create(ApiService.class);
        // Security silmek için DELETE isteği
        Call<Void> call = api.deleteSecurity(securityId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Security deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("DELETE", "Server returned error: " + response.code());
                    Toast.makeText(context, "Error deleting security", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("DELETE", "Error deleting security: " + t.getMessage());
                Toast.makeText(context, "Error deleting security", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

