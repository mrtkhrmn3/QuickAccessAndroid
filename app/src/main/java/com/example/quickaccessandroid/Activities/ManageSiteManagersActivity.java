package com.example.quickaccessandroid.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.Adapters.ManageSiteManagersAdapter;
import com.example.quickaccessandroid.DTO.ManageSiteManagerDTO;
import com.example.quickaccessandroid.DTO.NotificationDTO;
import com.example.quickaccessandroid.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageSiteManagersActivity extends AppCompatActivity {

    private RecyclerView siteManagersRecycleView;
    private ManageSiteManagersAdapter siteManagersAdapter;
    private List<ManageSiteManagerDTO> siteManagersList;
    private ApiService API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_site_managers);

        siteManagersRecycleView = findViewById(R.id.siteManagersRecycleView);
        siteManagersRecycleView.setLayoutManager(new LinearLayoutManager(this));

        API = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        fetchAllSiteManagers();
    }

    private void fetchAllSiteManagers() {
        // Site Manager'ları çekmek için API çağrısı yap
        API.getAllSiteManagers().enqueue(new Callback<List<ManageSiteManagerDTO>>() {
            @Override
            public void onResponse(Call<List<ManageSiteManagerDTO>> call, Response<List<ManageSiteManagerDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ManageSiteManagerDTO> siteManagersDTO = response.body();

                    // Site Manager'ları listeye ekle
                    siteManagersList = new ArrayList<>(siteManagersDTO);

                    // Sort site managers by name and then surname
                    siteManagersList.sort(Comparator
                            .comparing(ManageSiteManagerDTO::getName)
                            .thenComparing(ManageSiteManagerDTO::getSurname));

                    // Adapter'ı oluşturup RecyclerView'a set et
                    siteManagersAdapter = new ManageSiteManagersAdapter(ManageSiteManagersActivity.this, siteManagersList);
                    siteManagersRecycleView.setAdapter(siteManagersAdapter);
                } else {
                    Toast.makeText(ManageSiteManagersActivity.this, "Could not retrieve site managers.", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ManageSiteManagerDTO>> call, Throwable t) {
                Toast.makeText(ManageSiteManagersActivity.this, "Could not connect to server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Network error: " + t.getMessage());
            }
        });
    }

}
