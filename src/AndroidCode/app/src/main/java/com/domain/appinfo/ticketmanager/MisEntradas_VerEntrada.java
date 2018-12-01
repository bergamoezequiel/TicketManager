package com.domain.appinfo.ticketmanager;

import android.app.ProgressDialog;
import android.graphics.Color;
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
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Cliente;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.CodigoPromocional;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.EmpresaEmisora;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Entrada;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.GetRestAPIDAO;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MisEntradas_VerEntrada extends AppCompatActivity {
    private String opcion1Codigo;
    private String opcion2Codigo;
    private String opcion3Codigo;
    private boolean opcion1Selected;
    private boolean opcion2Selected;
    private boolean opcion3Selected;
    private String ID_FUNCION;
    private String UBICACION;
    Entrada entrada;
    CodigoPromocional[] codigoPromocionals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setearRadioGroupsIniciales();
        setContentView(R.layout.activity_mis_entradas__ver_entradas);
        JSONObject js;
        entrada = new Entrada();
        try {
             js = new JSONObject(getIntent().getStringExtra("Entrada"));

            entrada.setFullInfo(js);
        }catch (Exception e){}

        TextView textEspectaculo = findViewById(R.id.Espectaculo);
        textEspectaculo.setText(entrada.NombreEspectaculo);

        TextView textEmpresa = findViewById(R.id.EmpEmisora);
        TextView textDiaHoraUbicacion = findViewById(R.id.DiaHoraUbicacion);
        textEmpresa.setText(entrada.NombreEmpresa);
        textDiaHoraUbicacion.setText(entrada.Fecha+"  "+entrada.Hora+"   "+entrada.Ubicacion);

        UBICACION=entrada.Ubicacion;
        ID_FUNCION=entrada.IdFuncion;
        GetRestAPIDAO getCodigosPromocionales= new GetRestAPIDAO();
        try {
            String jsonCodigosPromocionales = getCodigosPromocionales.execute(UrlBackend.URL+"/CodigosPromocionales?IdFuncion=" + entrada.IdFuncion).get();
            JSONObject jsonObj = new JSONObject(jsonCodigosPromocionales );

            JSONArray jsonArr;
            jsonArr = jsonObj.getJSONArray("CodigosPromocionales");

             codigoPromocionals = new CodigoPromocional[jsonArr.length()];

            for (int i = 0; i < codigoPromocionals.length; i++) {
                codigoPromocionals[i] = new CodigoPromocional(jsonArr.getJSONObject(i));
                if(i==0){
                    Button bot1= findViewById(R.id.opcion1);
                    bot1.setText(codigoPromocionals[i].getDescripcion());
                    bot1.setVisibility(View.VISIBLE);
                    opcion1Codigo=codigoPromocionals[i].getCodigo();

                }
                if(i==1){
                    Button bot1= findViewById(R.id.opcion2);
                    bot1.setText(codigoPromocionals[i].getDescripcion());
                    bot1.setVisibility(View.VISIBLE);
                    opcion2Codigo=codigoPromocionals[i].getCodigo();

                }
                if(i==2){
                    Button bot1= findViewById(R.id.opcion3);
                    bot1.setText(codigoPromocionals[i].getDescripcion());
                    bot1.setVisibility(View.VISIBLE);
                    opcion3Codigo=codigoPromocionals[i].getCodigo();
                }
            }
            if (BotonesInvisibles()) {
              this.SetearMensajeFaltaDeCodigos();
            }

        }catch(Exception e){}

    }

    private boolean BotonesInvisibles() {
        return this.findViewById(R.id.opcion1).getVisibility() == View.INVISIBLE && this.findViewById(R.id.opcion2).getVisibility() == View.INVISIBLE && this.findViewById(R.id.opcion3).getVisibility() == View.INVISIBLE;
    }

    private void SetearMensajeFaltaDeCodigos () {
        TextView CodigosTitulo = this.findViewById(R.id.Codig);
        CodigosTitulo.setText("No hay códigos promocionales en este momento");
        CodigosTitulo.setTextColor(Color.RED);
        Button buttonDevolver = this.findViewById(R.id.button6);
        buttonDevolver.setVisibility(View.INVISIBLE);
    }

    private void setearRadioGroupsIniciales() {
        boolean opcion1Selected=true;
        boolean opcion2Selected=false;
        boolean opcion3Selected=false;
    }


    public void opcion1ButtonClicked(View view) {
        opcion1Selected=true;
        opcion2Selected=false;
        opcion3Selected=false;
    }
    public void opcion2ButtonClicked(View view) {
        opcion1Selected=false;
        opcion2Selected=true;
        opcion3Selected=false;
    }
    public void opcion3ButtonClicked(View view) {
        opcion1Selected=false;
        opcion2Selected=false;
        opcion3Selected=true;
    }
    public void devolverEntradaButtonClicked(View view) {

        if(entrada.EstaVencida()){
            Toast.makeText(this,
                    "Entrada vecida, no puede devolverse", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,
                    "Entrada devuelta", Toast.LENGTH_SHORT).show();


            JSONObject json = new JSONObject();

            try {
                JSONObject jsCli = new JSONObject(getIntent().getStringExtra("IdCliente"));
                Cliente cliente = new Cliente(jsCli);
                json.put("IdCliente", cliente.GetId());
                if (opcion1Selected) {
                    //json.put("CodProm",opcion1Codigo );
                     codigoPromocionals[0].asignar(cliente.GetId());

                }
                if (opcion2Selected) {
                    //json.put("CodProm",opcion2Codigo );
                    codigoPromocionals[1].asignar(cliente.GetId());

                }
                if (opcion3Selected) {
                    //json.put("CodProm",opcion3Codigo );
                    codigoPromocionals[2].asignar(cliente.GetId());

                }


            } catch (Exception e) {
            }
            //asignarCodigoPromocional(json);

            entrada.liberar();
            super.onBackPressed();
        }
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



}
