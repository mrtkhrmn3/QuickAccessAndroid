package com.example.quickaccessandroid.API;

import com.example.quickaccessandroid.DTO.AdminRegisterDTO;
import com.example.quickaccessandroid.DTO.CreateNotificationDTO;
import com.example.quickaccessandroid.DTO.LoginRequestDTO;
import com.example.quickaccessandroid.DTO.LoginResponseDTO;
import com.example.quickaccessandroid.DTO.NotificationCompleteDTO;
import com.example.quickaccessandroid.DTO.NotificationDTO;
import com.example.quickaccessandroid.DTO.NotificationUpdateDTO;
import com.example.quickaccessandroid.DTO.ResidentRegisterDTO;
import com.example.quickaccessandroid.DTO.SecurityRegisterDTO;
import com.example.quickaccessandroid.DTO.SiteManagerRegisterDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //LOGIN
    @POST("api/Login/Login")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO request);


    //NOTIFICATION
    @POST("api/Notification")
    Call<Void> sendNotification(@Body CreateNotificationDTO dto);

    @GET("api/Notification/GetActiveNotifications/{siteName}")
    Call<List<NotificationDTO>> getNotifications(@Path("siteName") String siteName);

    @GET("api/Notification/ActiveNotificationsForResident")
    Call<List<NotificationDTO>> getActiveNotificationsForResident(@Query("userId") String userId);

    @PUT("api/Notification/UpdateNotification")
    Call<ResponseBody> updateNotification(@Body NotificationUpdateDTO dto);

    @PUT("api/Notification/CompleteNotification")
    Call<ResponseBody> completeNotification(@Body NotificationCompleteDTO dto);

    @DELETE("api/Notification/DeleteNotification{id}")
    Call<ResponseBody> deleteNotification(@Path("id") String id);


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

