package com.domain.appinfo.ticketmanager;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;

import org.json.JSONArray;
import org.json.JSONObject;

public class MisEntradas_VerEntrada extends AppCompatActivity {
    private String opcion1Codigo;
    private String opcion2Codigo;
    private String opcion3Codigo;
    private boolean opcion1Selected=true;
    private boolean opcion2Selected=false;
    private boolean opcion3Selected=false;
    private String ID_FUNCION;
    private String UBICACION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_entradas__ver_entradas);
        TextView textEspectaculo = findViewById(R.id.Espectaculo);
        TextView textEmpresa = findViewById(R.id.EmpEmisora);
        TextView textDiaHoraUbicacion = findViewById(R.id.DiaHoraUbicacion);
        String concatenado=getIntent().getStringExtra("IdEntrada");
        String[] words=concatenado.split(",",7);
        textEspectaculo.setText(words[2]);
        textEmpresa.setText(words[1]);
        textDiaHoraUbicacion.setText(words[5]+"  "+words[4]+"   "+words[3]);
        UBICACION= words[3];
        ID_FUNCION=words[0];
        ConsultarCodigos(words[0]);

    }

    private void ConsultarCodigos(String IdFuncion){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url =UrlBackend.URL+"/CodigosPromocionales?IdFuncion="+IdFuncion;
        pDialog.setMessage(url);
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
        TextView textCod = findViewById(R.id.Codig);
        textCod.setText(response.toString());
        JSONArray jsonArr;
       try {
           jsonArr = response.getJSONArray("CodigosPromocionales");

           for (int i = 0; i < jsonArr.length(); i++) {
               JSONObject  jsonCodigo= jsonArr.getJSONObject(i);
               if(i==0){
                   Button bot1= findViewById(R.id.opcion1);
                   bot1.setText(jsonCodigo.getString("Descripcion"));
                   opcion1Codigo=jsonCodigo.getString("CodigoPromocional");

               }
               if(i==1){
                   Button bot1= findViewById(R.id.opcion2);
                   bot1.setText(jsonCodigo.getString("Descripcion"));
                   opcion2Codigo=jsonCodigo.getString("CodigoPromocional");

               }
               if(i==2){
                   Button bot1= findViewById(R.id.opcion3);
                   bot1.setText(jsonCodigo.getString("Descripcion"));
                   opcion3Codigo=jsonCodigo.getString("CodigoPromocional");


               }


           }
       }catch(Exception e){}


    }
    public void opcion1ButtonClicked(View view) {
        // Is the button now checked?
        Toast.makeText(this,
                "Opcion 1 seleccionada", Toast.LENGTH_SHORT).show();
        opcion1Selected=true;
        opcion2Selected=false;
        opcion3Selected=false;

    }
    public void opcion2ButtonClicked(View view) {
        // Is the button now checked?
        Toast.makeText(this,
                "Opcion 2 seleccionada", Toast.LENGTH_SHORT).show();
        opcion1Selected=false;
        opcion2Selected=true;
        opcion3Selected=false;
    }
    public void opcion3ButtonClicked(View view) {
        // Is the button now checked?
        Toast.makeText(this,
                "Opcion 3 seleccionada", Toast.LENGTH_SHORT).show();
        opcion1Selected=false;
        opcion2Selected=false;
        opcion3Selected=true;
    }
    public void devolverEntradaButtonClicked(View view) {
        // Is the button now checked?
        Toast.makeText(this,
                "Entrada devuelta", Toast.LENGTH_SHORT).show();



        JSONObject json= new JSONObject();

            try {
                json.put("IdCliente", getIntent().getStringExtra("IdCliente"));
                if (opcion1Selected){
                    json.put("CodProm",opcion1Codigo );

                }
                if (opcion2Selected){
                    json.put("CodProm",opcion2Codigo );

                }
                if (opcion3Selected){
                    json.put("CodProm",opcion3Codigo );

                }


            } catch (Exception e) {
            }
        asignarCodigoPromocional(json);
            liberarEntrada();
        super.onBackPressed();
    }

    public void asignarCodigoPromocional(JSONObject json){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url =UrlBackend.URL+"/CodigosPromocionales";
        pDialog.setMessage(url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, json,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.hide();
                        RespuestaJSON2(response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);


    }


    private void RespuestaJSON2(JSONObject response) {

    }
    public void liberarEntrada(){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url =UrlBackend.URL+"/Entradas?Ubicacion="+UBICACION+"&IdFuncion="+ID_FUNCION;
        pDialog.setMessage(url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.hide();
                        RespuestaJSON3(response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);


    }


    private void RespuestaJSON3(JSONObject response) {}


}
