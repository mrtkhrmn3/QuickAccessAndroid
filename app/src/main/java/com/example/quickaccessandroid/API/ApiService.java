package com.example.quickaccessandroid.API;

import com.example.quickaccessandroid.DTO.AdminRegisterDTO;
import com.example.quickaccessandroid.DTO.CreateNotificationDTO;
import com.example.quickaccessandroid.DTO.LoginRequestDTO;
import com.example.quickaccessandroid.DTO.LoginResponseDTO;
import com.example.quickaccessandroid.DTO.NotificationDTO;
import com.example.quickaccessandroid.DTO.ResidentRegisterDTO;
import com.example.quickaccessandroid.DTO.SecurityRegisterDTO;
import com.example.quickaccessandroid.DTO.SiteManagerRegisterDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    //LOGIN
    @POST("api/Login/Login")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO request);


    //NOTIFICATION
    @POST("api/Notification")
    Call<Void> sendNotification(@Body CreateNotificationDTO dto);

    @GET("api/Notification/{siteName}")
    Call<List<NotificationDTO>> getNotifications(@Path("siteName") String siteName);


    //REGISTER
    @POST("api/Register/register")
    Call<Void> registerAdmin(@Body AdminRegisterDTO dto);

    @POST("api/Register/sitemanager")
    Call<Void> registerSiteManager(@Body SiteManagerRegisterDTO dto);

    @POST("api/Register/resident")
    Call<Void> registerResident(@Body ResidentRegisterDTO dto);

    @POST("api/Register/security")
    Call<Void> registerSecurity(@Body SecurityRegisterDTO dto);
}

