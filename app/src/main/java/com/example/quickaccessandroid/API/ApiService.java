package com.example.quickaccessandroid.API;

import com.example.quickaccessandroid.DTO.ManageAdminDTO;
import com.example.quickaccessandroid.DTO.AdminRegisterDTO;
import com.example.quickaccessandroid.DTO.CreateNotificationDTO;
import com.example.quickaccessandroid.DTO.LoginRequestDTO;
import com.example.quickaccessandroid.DTO.LoginResponseDTO;
import com.example.quickaccessandroid.DTO.ManageResidentDTO;
import com.example.quickaccessandroid.DTO.ManageSiteManagerDTO;
import com.example.quickaccessandroid.DTO.NotificationCompleteDTO;
import com.example.quickaccessandroid.DTO.NotificationDTO;
import com.example.quickaccessandroid.DTO.NotificationUpdateDTO;
import com.example.quickaccessandroid.DTO.ResidentRegisterDTO;
import com.example.quickaccessandroid.DTO.ManageSecurityDTO;
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

    @GET("api/Notification/ActiveNotificationsForSecurity")
    Call<List<NotificationDTO>> getNotifications(@Query("userId") String userId);

    @GET("api/Notification/ActiveNotificationsForResident")
    Call<List<NotificationDTO>> getActiveNotificationsForResident(@Query("userId") String userId);

    @PUT("api/Notification/UpdateNotification")
    Call<ResponseBody> updateNotification(@Body NotificationUpdateDTO dto);

    @PUT("api/Notification/CompleteNotification")
    Call<ResponseBody> completeNotification(@Body NotificationCompleteDTO dto);

    @DELETE("api/Notification/DeleteNotification/{id}")
    Call<ResponseBody> deleteNotification(@Path("id") String id);

    @GET("api/Notification/GetNotification/{id}")
    Call<ResponseBody> getNotificationById(@Path("id") String id);

    //REGISTER
    @POST("api/Register/register")
    Call<Void> registerAdmin(@Body AdminRegisterDTO dto);

    @POST("api/Register/sitemanager")
    Call<Void> registerSiteManager(@Body SiteManagerRegisterDTO dto);

    @POST("api/Register/resident")
    Call<Void> registerResident(@Body ResidentRegisterDTO dto);

    @POST("api/Register/security")
    Call<Void> registerSecurity(@Body SecurityRegisterDTO dto);


    //USER MANAGEMENT
    @GET("api/UserManagement/Admins")
    Call<List<ManageAdminDTO>> getAllAdmins();

    @GET("api/UserManagement/SiteManagers")
    Call<List<ManageSiteManagerDTO>> getAllSiteManagers();

    @GET("api/UserManagement/Securities")
    Call<List<ManageSecurityDTO>> getAllSecurities();

    @GET("api/UserManagement/Residents")
    Call<List<ManageResidentDTO>> getAllResidents();

    @DELETE("api/UserManagement/Admin/{id}")
    Call<Void> deleteAdmin(@Path("id") String adminId);

    @DELETE("api/UserManagement/SiteManager/{id}")
    Call<Void> deleteSiteManager(@Path("id") String siteManagerId);

    @DELETE("api/UserManagement/Security/{id}")
    Call<Void> deleteSecurity(@Path("id") String securityId);

    @DELETE("api/UserManagement/Resident/{id}")
    Call<Void> deleteResident(@Path("id") String residentId);

    @GET("api/UserManagement/ResidentsForSiteManager")
    Call<List<ManageResidentDTO>> getResidentsForSiteManager();

    @GET("api/UserManagement/SecuritiesForSiteManager")
    Call<List<ManageSecurityDTO>> getSecuritiesForSiteManager();
}

