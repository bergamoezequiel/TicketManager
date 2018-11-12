package com.domain.appinfo.ticketmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_LONG;

public class VerEspectaculos_VerEntradas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_espectaculos__ver_entradas);
        TextView text = (TextView) findViewById(R.id.textView);
        text.setText(getIntent().getStringExtra("IdFuncion")+getIntent().getStringExtra("IdCliente"));
        ConsultarUbicaciones(getIntent().getStringExtra("IdFuncion"));
    }

    private void ConsultarUbicaciones(String IdFuncion){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url =UrlBackend.URL+"/Entradas?IdFuncion="+IdFuncion;
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
       // TextView text = (TextView) findViewById(R.id.textView);
        //text.setText(response.toString());
        JSONArray jsonArr;
        //Se arman dos listados, de nombres y descripciones para pasarselo al adaptador de la lista
        String[] Ubicaciones=new String[0];

        try {
            jsonArr = response.getJSONArray("Entradas");
            Ubicaciones=new String[jsonArr.length()];
            if(jsonArr.length()<1){
                Button boton = (Button) findViewById(R.id.button);
                boton.setBackgroundColor(Color.GREEN);

            }else{
                Button boton = (Button) findViewById(R.id.button);
                boton.setBackgroundColor(Color.GRAY);


            }
            for(int i=0;i<jsonArr.length();i++){
                JSONObject jsonEspectaculo = jsonArr.getJSONObject(i);
                Ubicaciones[i]=jsonEspectaculo.getString("Ubicacion");



            }
        }catch(Exception e){}

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, Ubicaciones);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);


    }




    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] Ubicaciones;


        public MySimpleArrayAdapter(Context context,String[] Ubicaciones) {
            super(context, R.layout.rowlayout_entradas, Ubicaciones);
            this.context = context;
            this.Ubicaciones= Ubicaciones;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout_entradas, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.Ubic);



            textView.setText(Ubicaciones[position]);



            // Change the icon for Windows and iPhone
            String s = Ubicaciones[position];




            return rowView;
        }
    }

    public void GuardarInteresBottonAction(View view){
       JSONObject json= new JSONObject();
       try {
           json.put("IdCliente", getIntent().getStringExtra("IdCliente"));
           json.put("IdFuncion", getIntent().getStringExtra("IdFuncion"));
       }catch(Exception e){}
        GuardarInteres(json);

      

    }

    private void GuardarInteres(JSONObject json){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url =UrlBackend.URL+"/Intereses";
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
