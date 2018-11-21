package com.domain.appinfo.ticketmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void LogInBottonAction(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        ConsultarCliente(editText.getText().toString());
    }

    private void ConsultarCliente(String nombreCliente){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        String url =UrlBackend.URL+"/Cliente/"+nombreCliente;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.hide();
                        RespuestaJSON(response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);


    }

    private void RespuestaJSON(JSONObject response) {
        TextView editText = (TextView) findViewById(R.id.textView);
        if (response.has("NombreDeUsuario")) {
            editText.setText("");
            Intent intent = new Intent(this, Menu.class);
            intent.putExtra("Cliente",response.toString());
            startActivity(intent);
        } else {
            editText.setTextColor(Color.RED);
            editText.setText("usuario invalido");
            editText.setVisibility(View.VISIBLE);
        }
    }


}
