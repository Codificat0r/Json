package com.example.json;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.json.network.RestClient;
import com.example.json.pojo.Contacto;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ContactosActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    //public static final String WEB = "http://192.168.1.20/acceso/contactos.json";
    public static final String WEB = "https://www.portadaalta.mobi/acceso/contactos.json";
    Button boton;
    ListView lista;
    ArrayList<Contacto> contactos;
    ArrayAdapter<Contacto> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        boton = (Button) findViewById(R.id.button);
        boton.setOnClickListener(this);
        lista = (ListView) findViewById(R.id.lista);
        lista.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == boton)
            descarga(WEB);
    }

    //usar JsonHttpResponseHandler()
    private void descarga(String web) {
        final ProgressDialog progreso = new ProgressDialog(this);
        RestClient.get(web, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(true);
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void mostrar() {
        if (contactos != null)
            if (adapter == null) {
                adapter = new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_1, contactos);
                lista.setAdapter(adapter);
            } else {
                adapter.clear();
                adapter.addAll(contactos);
            }
        else
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Móvil: " + contactos.get(position).getTelefono().getMovil(), Toast.LENGTH_SHORT).show();
    }
}
