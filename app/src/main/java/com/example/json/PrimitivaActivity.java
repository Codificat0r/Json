package com.example.json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.json.network.MySingleton;
import com.example.json.utils.Analisis;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;

public class PrimitivaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MyTag";
    //public static final String WEB = "http://192.168.3.57/acceso/primitiva.json";
    public static final String WEB = "http://10.0.2.2/acceso/primitiva.json";
    Button btnObtener;
    TextView txvResultados;
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primitiva);

        btnObtener = (Button) findViewById(R.id.btnObtener);
        btnObtener.setOnClickListener(this);
        txvResultados = (TextView) findViewById(R.id.txvResultados);


        mRequestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    }

    @Override
    public void onClick(View view) {
        if (view == btnObtener)
            descarga();
    }

    private void descarga() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, WEB, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            txvResultados.setText(Analisis.analizarPrimitiva(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null)
                            Toast.makeText(PrimitivaActivity.this, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(PrimitivaActivity.this, "ERROR: No hay conexión al servidor", Toast.LENGTH_LONG).show();
                    }
                });


        // Set the tag on the request.
        jsObjRequest.setTag(TAG);
        // Set retry policy
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 1, 1));
        // Add the request to the RequestQueue.
        mRequestQueue.add(jsObjRequest);
    }
}
