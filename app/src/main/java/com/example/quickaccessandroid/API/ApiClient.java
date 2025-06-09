package com.example.quickaccessandroid.API;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context)) // Token interceptor
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://myapi2-dot-qa-api-462416.oa.r.appspot.com/") // senin API adresin
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
