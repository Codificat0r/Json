package com.example.json;

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
import com.example.json.pojo.Repo;

import java.util.ArrayList;

public class RepositoriosActivity extends AppCompatActivity implements RepoAdapter.OnClickListener {

    private RecyclerView rclrRepos;
    private Button btnObtenerRepo;
    private EditText edtRepo;

    private RepoAdapter adapter;
    private ArrayList<Repo> repos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositorios);
        edtRepo = (EditText) findViewById(R.id.edtRepo);
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
        adapter.addRepos(repos);
        rclrRepos.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void OnItemClicked(Repo clicked) {
        Toast.makeText(getApplicationContext(), "Repositorio " + clicked.getName() + " fue pulsado... Intentando abrir el repositorio", Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(clicked.getUrl());
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
