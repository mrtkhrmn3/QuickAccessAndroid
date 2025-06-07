package com.example.quickaccessandroid.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickaccessandroid.API.ApiClient;
import com.example.quickaccessandroid.API.ApiService;
import com.example.quickaccessandroid.DTO.ResidentRegisterDTO;
import com.example.quickaccessandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddResidentActivity extends AppCompatActivity {

    private EditText editName, editSurname, editBlock, editAptNo, editPhoneNo, editSiteName;
    private Button buttonAddResident;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resident);

        // EditText’leri bağla
        editName = findViewById(R.id.editResidentName);
        editSurname = findViewById(R.id.editResidentSurname);
        editBlock = findViewById(R.id.editResidentBlock);
        editAptNo = findViewById(R.id.editResidentAptNo);
        editPhoneNo = findViewById(R.id.editResidentPhoneNo);
        editSiteName = findViewById(R.id.editResidentSiteName);
        buttonAddResident = findViewById(R.id.buttonAddResident);

        // API servisi başlat
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        buttonAddResident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerResident();
            }
        });
    }

    private void registerResident() {
        String name = editName.getText().toString().trim();
        String surname = editSurname.getText().toString().trim();
        String block = editBlock.getText().toString().trim();
        String aptNo = editAptNo.getText().toString().trim();
        String phone = editPhoneNo.getText().toString().trim();
        String siteName = editSiteName.getText().toString().trim();

        if (name.isEmpty() || surname.isEmpty() || block.isEmpty() || aptNo.isEmpty()
                || phone.isEmpty() || siteName.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // DTO oluştur

        // Bu kısım değiştirilebilir.
        String username = block + "-" + aptNo;
        String password = siteName;

        ResidentRegisterDTO dto = new ResidentRegisterDTO(name, surname, block, aptNo, phone, siteName, username, password);

        // API çağrısı yap
        Call<Void> call = apiService.registerResident(dto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddResidentActivity.this, "Resident added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(AddResidentActivity.this, "Failed to add resident. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e(null, response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddResidentActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clearFields() {
        editName.setText("");
        editSurname.setText("");
        editBlock.setText("");
        editAptNo.setText("");
        editPhoneNo.setText("");
        editSiteName.setText("");
    }
}
