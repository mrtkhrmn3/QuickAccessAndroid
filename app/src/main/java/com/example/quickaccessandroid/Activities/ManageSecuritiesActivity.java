package com.example.quickaccessandroid.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.Adapters.ManageSecuritiesAdapter;
import com.example.quickaccessandroid.Adapters.ManageSiteManagersAdapter;
import com.example.quickaccessandroid.DTO.ManageSecurityDTO;
import com.example.quickaccessandroid.DTO.ManageSiteManagerDTO;
import com.example.quickaccessandroid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageSecuritiesActivity extends AppCompatActivity {

    private RecyclerView securityRecycleView;
    private ManageSecuritiesAdapter securityAdapter;
    private List<ManageSecurityDTO> securitiesList;
    private ApiService API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_securities);

        securityRecycleView = findViewById(R.id.securitiesRecycleView);
        securityRecycleView.setLayoutManager(new LinearLayoutManager(this));

        API = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        fetchAllSecurities();
    }

    private void fetchAllSecurities() {
        // API çağrısı yap
        API.getSecuritiesForSiteManager().enqueue(new Callback<List<ManageSecurityDTO>>() {
            @Override
            public void onResponse(Call<List<ManageSecurityDTO>> call, Response<List<ManageSecurityDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ManageSecurityDTO> securities = response.body();

                    // listeye ekle
                    securitiesList = new ArrayList<>(securities);

                    // Adapter'ı oluşturup RecyclerView'a set et
                    securityAdapter = new ManageSecuritiesAdapter(ManageSecuritiesActivity.this, securitiesList);
                    securityRecycleView.setAdapter(securityAdapter);
                } else {
                    Toast.makeText(ManageSecuritiesActivity.this, "Could not retrieve securities", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Response failed: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ManageSecurityDTO>> call, Throwable t) {
                Toast.makeText(ManageSecuritiesActivity.this, "Could not connect to server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Network error: " + t.getMessage());
            }
        });
    }

}
