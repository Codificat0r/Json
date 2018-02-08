package com.example.json.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by usuario on 6/02/18.
 */

public class ApiAdapter {

        private static ApiService.GitHubClient API_SERVICE;
        public static final String BASE_URL = "https://api.github.com/";
        //public static final String BASE_URL = "http://192.168.3.57/";

        public static synchronized ApiService.GitHubClient getApiService() {
            if (API_SERVICE == null) {
                Gson gson = new GsonBuilder()
                        .setDateFormat("dd-MM-yyyy'T'HH:mm:ss")
                        .create();

                //Para poder poner el timeout debemos especificarle el cliente interno que usará
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .build();

                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(okHttpClient)
                        .build();
                API_SERVICE = retrofit.create(ApiService.GitHubClient.class);
            }
            return API_SERVICE;


        }
}
