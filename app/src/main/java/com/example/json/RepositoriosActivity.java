package com.example.json;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.json.adapter.RepoAdapter;
import com.example.json.network.ApiAdapter;
import com.example.json.network.ApiService;
import com.example.json.pojo.Repo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoriosActivity extends AppCompatActivity implements RepoAdapter.OnClickListener {

    private RecyclerView rclrRepos;
    private Button btnObtenerRepo;
    private EditText edtUser;

    private RepoAdapter adapter;
    private ArrayList<Repo> repos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositorios);
        edtUser = (EditText) findViewById(R.id.edtUser);
        btnObtenerRepo = (Button) findViewById(R.id.btnObtenerRepo);

        btnObtenerRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        rclrRepos = (RecyclerView) findViewById(R.id.rclrRepos);
        adapter = new RepoAdapter(this);
        rclrRepos.setAdapter(adapter);
        //Voy a poner esto aqui para ver si el notificar un cambio en el adapter funciona y actualiza el Recycler... Si no
        //poner antes de la linea de justo arriba.
        rclrRepos.setLayoutManager(new LinearLayoutManager(this));



        btnObtenerRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiService.GitHubClient apiservice = ApiAdapter.getApiService();

                final Call<ArrayList<Repo>> call = apiservice.reposForUser(edtUser.getText().toString());
                //final Call<ArrayList<Repo>> call = apiservice.getLocalRepos();

                final ProgressDialog progreso = new ProgressDialog(RepositoriosActivity.this);
                progreso.setMessage("Obteniendo repositorios de " + edtUser.getText().toString() + " . . .");
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        call.cancel();
                    }
                });
                progreso.show();

                call.enqueue(new Callback<ArrayList<Repo>>() {
                    //Siempre que obtenga respuesta ya sea un 404 o lo que sea entra en onResponse.
                    //Entra en el onFailura si
                    @Override
                    public void onResponse(Call<ArrayList<Repo>> call, Response<ArrayList<Repo>> response) {
                        progreso.dismiss();
                        if (response.isSuccessful()) {
                            adapter.clear();
                            repos = response.body();
                            adapter.addRepos(repos);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Error en la descarga: " + response.code());
                            if (response.body() != null) {
                                sb.append(response.body());
                            }
                            if (response.errorBody() != null) {
                                try {
                                    sb.append(response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(RepositoriosActivity.this, sb, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Repo>> call, Throwable t) {
                        progreso.dismiss();
                        if (t != null)
                            Toast.makeText(RepositoriosActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


    @Override
    public void OnItemClicked(Repo clicked) {
        Toast.makeText(getApplicationContext(), "Repositorio " + clicked.getName() + " fue pulsado... Intentando abrir el repositorio", Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(clicked.getHtmlUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
        else
            Toast.makeText(getApplicationContext(), "No hay un navegador",
                    Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemLongClicked(Repo clicked) {
        Toast.makeText(this, "Se ha hecho CLICK LARGO en el repositorio " + clicked.getName(), Toast.LENGTH_SHORT).show();
    }
}
