package com.example.quickaccessandroid.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.Adapters.ManageAdminsAdapter;
import com.example.quickaccessandroid.Adapters.ManageResidentsAdapter;
import com.example.quickaccessandroid.Adapters.ManageSiteManagersAdapter;
import com.example.quickaccessandroid.DTO.ManageAdminDTO;
import com.example.quickaccessandroid.DTO.ManageResidentDTO;
import com.example.quickaccessandroid.DTO.ManageSiteManagerDTO;
import com.example.quickaccessandroid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageResidentsActivity extends AppCompatActivity {

    private RecyclerView residentsRecycleView;
    private ManageResidentsAdapter residentsAdapter;
    private List<ManageResidentDTO> residentsList;
    private ApiService API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_residents);

        residentsRecycleView = findViewById(R.id.residentsRecycleView);
        residentsRecycleView.setLayoutManager(new LinearLayoutManager(this));

        API = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        fetchAllResidents();
    }

    private void fetchAllResidents() {
        // API çağrısı yap
        API.getResidentsForSiteManager().enqueue(new Callback<List<ManageResidentDTO>>() {
            @Override
            public void onResponse(Call<List<ManageResidentDTO>> call, Response<List<ManageResidentDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ManageResidentDTO> residentsDTO = response.body();

                    // listeye ekle
                    residentsList = new ArrayList<>(residentsDTO);

                    // Adapter'ı oluşturup RecyclerView'a set et
                    residentsAdapter = new ManageResidentsAdapter(ManageResidentsActivity.this, residentsList);
                    residentsRecycleView.setAdapter(residentsAdapter);
                } else {
                    Toast.makeText(ManageResidentsActivity.this, "Could not retrieve residents", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Response failed: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ManageResidentDTO>> call, Throwable t) {
                Toast.makeText(ManageResidentsActivity.this, "Could not connect to server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Network error: " + t.getMessage());
            }
        });
    }

}
