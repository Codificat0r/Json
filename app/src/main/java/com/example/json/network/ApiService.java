package com.example.json.network;

import com.example.json.pojo.Repo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit
 */

public class ApiService {
    public interface GitHubClient {
        @GET("/users/{user}/repos")
        Call<ArrayList<Repo>> reposForUser(@Path("user") String user);

        @GET("acceso/repositorios.json")
        Call<ArrayList<Repo>> getLocalRepos();
    }

}
