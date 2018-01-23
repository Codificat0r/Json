package com.example.json;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick_lanzarExs(View v)
    {
        Intent unIntent = null;

        switch (v.getId())
        {
            case R.id.btn_Primitiva:
                unIntent = new Intent(MainActivity.this, PrimitivaActivity.class);
                break;
            case R.id.btn_Contactos:
                unIntent = new Intent(MainActivity.this, ContactosActivity.class);
                break;
            case R.id.btn_ContactosGson:
                unIntent = new Intent(MainActivity.this, ContactosGsonActivity.class);
                break;
            case R.id.btn_CreacionJSON:
                unIntent = new Intent(MainActivity.this, CreacionJSONActivity.class);
                break;
            case R.id.btn_Repositorios:
                unIntent = new Intent(MainActivity.this, RepositoriosActivity.class);
                break;
        }

        startActivity(unIntent);
    }
}
