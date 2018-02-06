package com.example.json;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.json.network.RestClient;
import com.example.json.pojo.Contacto;
import com.example.json.pojo.ContactoGSON;
import com.example.json.pojo.ContactosGSON;
import com.example.json.utils.Analisis;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ContactosGsonActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    //public static final String WEB = "http://192.168.3.57/acceso/contactos.json";
    public static final String WEB = "https://portadaalta.mobi/acceso/contacts.json";
    Button boton;
    ListView lista;
    ContactosGSON contactosGSON;
    ArrayList<ContactoGSON> contactos;
    ArrayAdapter<ContactoGSON> adapter;

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
                progreso.setMessage("Conectando a . . ." + "\n" + WEB);
                progreso.setCancelable(true);
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progreso.dismiss();

                //try {
                    contactosGSON = new Gson().fromJson(response.toString(), ContactosGSON.class);
                    contactos = new ArrayList<>(contactosGSON.getContacts());
                    mostrar();
                //} catch (JSONException e) {
                //    e.printStackTrace();
                //}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                progreso.dismiss();

                StringBuilder message = new StringBuilder();
                message.append("Fallo en la descarga");
                if (throwable != null) {
                    message.append("; " + throwable.getMessage());
                    if (errorResponse != null) {
                        message.append("\n" + errorResponse.toString());
                    }
                    message.append("\nCódigo de error: " + statusCode);
                }
                Toast.makeText(ContactosGsonActivity.this, message, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


    }

    private void mostrar() {
        if (contactos != null)
            if (adapter == null) {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactos);
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
        Toast.makeText(this, "Móvil: " + contactos.get(position).getPhone().getMobile(), Toast.LENGTH_SHORT).show();
    }
}
