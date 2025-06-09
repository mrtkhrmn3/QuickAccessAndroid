package com.example.quickaccessandroid.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.API.JwtUtils;
import com.example.quickaccessandroid.Adapters.ManageAdminsAdapter;
import com.example.quickaccessandroid.Adapters.ResidentNotificationAdapter;
import com.example.quickaccessandroid.DTO.ManageAdminDTO;
import com.example.quickaccessandroid.DTO.ManageSiteManagerDTO;
import com.example.quickaccessandroid.DTO.NotificationDTO;
import com.example.quickaccessandroid.Models.Notification;
import com.example.quickaccessandroid.R;
import com.example.quickaccessandroid.Utils.TimeUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAdminsActivity extends AppCompatActivity {

    private RecyclerView adminsRecycleView;
    private ManageAdminsAdapter adminsAdapter;
    private List<ManageAdminDTO> adminsList;
    private ApiService API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admins);

        adminsRecycleView = findViewById(R.id.adminsRecycleView);
        adminsRecycleView.setLayoutManager(new LinearLayoutManager(this));

        API = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        fetchAllAdmins();
    }

    private void fetchAllAdmins() {
        // Adminleri çekmek için API çağrısı yap
        API.getAllAdmins().enqueue(new Callback<List<ManageAdminDTO>>() {
            @Override
            public void onResponse(Call<List<ManageAdminDTO>> call, Response<List<ManageAdminDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ManageAdminDTO> adminsDTO = response.body();

                    // Adminleri listeye ekle
                    adminsList = new ArrayList<>(adminsDTO);

                    // Sort admins by name and then surname
                    adminsList.sort(Comparator
                            .comparing(ManageAdminDTO::getName)
                            .thenComparing(ManageAdminDTO::getSurname));

                    // Adapter'ı oluşturup RecyclerView'a set et
                    adminsAdapter = new ManageAdminsAdapter(ManageAdminsActivity.this, adminsList);
                    adminsRecycleView.setAdapter(adminsAdapter);
                } else {
                    Toast.makeText(ManageAdminsActivity.this, "Could not retrieve admins.", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ManageAdminDTO>> call, Throwable t) {
                Toast.makeText(ManageAdminsActivity.this, "Could not connect to server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Network error: " + t.getMessage());
            }
        });
    }

}
