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
import com.example.quickaccessandroid.DTO.ManageResidentDTO;
import com.example.quickaccessandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageResidentsAdapter extends RecyclerView.Adapter<ManageResidentsAdapter.ResidentsViewHolder> {

    private List<ManageResidentDTO> residentsList;
    private Context context;

    // Constructor
    public ManageResidentsAdapter(Context context, List<ManageResidentDTO> residentsList) {
        this.context = context;
        this.residentsList = residentsList;
    }

    @Override
    public ResidentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_residents, parent, false);
        return new ResidentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResidentsViewHolder holder, int position) {
        ManageResidentDTO resident = residentsList.get(position);

        String nameSurname = resident.getName() + " " + resident.getSurname();
        String aptBlock = resident.getBlock() + "-" + resident.getAptNo();

        holder.nameSurname.setText(nameSurname);
        holder.aptNo.setText(aptBlock);
        holder.phoneNumber.setText(resident.getPhoneNumber());

        holder.deleteBtn.setOnClickListener(v -> {
            String residentId = resident.getId();  // ID'yi aldık

            // API ile silme işlemi yapılabilir:
            deleteResidentFromServer(context, residentId);

            // Listeden çıkar ve UI'yı güncelle
            residentsList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, residentsList.size());
        });
    }

    @Override
    public int getItemCount() {
        return residentsList.size();
    }

    // ViewHolder class
    public static class ResidentsViewHolder extends RecyclerView.ViewHolder {
        TextView nameSurname;
        TextView aptNo;
        TextView phoneNumber;
        ImageView deleteBtn;

        public ResidentsViewHolder(View itemView) {
            super(itemView);
            nameSurname = itemView.findViewById(R.id.nameSurnameTxt);
            aptNo = itemView.findViewById(R.id.aptNoTxt);
            phoneNumber = itemView.findViewById(R.id.phoneNumberTxt);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    private void deleteResidentFromServer(Context context, String id) {
        ApiService api = ApiClient.getClient(context).create(ApiService.class);
        // DELETE isteği
        Call<Void> call = api.deleteResident(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Resident deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error deleting resident", Toast.LENGTH_SHORT).show();
                    Log.e("DELETE", "Server returned error: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error deleting resident", Toast.LENGTH_SHORT).show();
                Log.e("DELETE", "Error deleting resident: " + t.getMessage());
            }
        });
    }

}

