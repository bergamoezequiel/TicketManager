package com.domain.appinfo.ticketmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

public class VerEspectaculos_VerFunciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_espectaculos__ver_funciones);
        TextView text = (TextView) findViewById(R.id.textView);
        ConsultarFuncionesPorEmpresaYEspectaculo(getIntent().getStringExtra("cuitEmpresa"),getIntent().getStringExtra("IdEspectaculo"));
    }

    private void ConsultarFuncionesPorEmpresaYEspectaculo(String cuitEmpresa,String IdEspectaculo){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url =UrlBackend.URL+"/Funciones?cuitEmp="+cuitEmpresa+"&"+"IdEspectaculo="+IdEspectaculo;
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
        TextView text = (TextView) findViewById(R.id.textView);
        JSONArray jsonArr;
        //Se arman dos listados, de nombres y descripciones para pasarselo al adaptador de la lista
        String[] IdsDeFuncion=new String[0];
        String[] Horas=new String[0];
        String[] Dias=new String[0];
        try {
            jsonArr = response.getJSONArray("Funciones");
            IdsDeFuncion=new String[jsonArr.length()];
            Horas=new String[jsonArr.length()];
            Dias=new String[jsonArr.length()];
            for(int i=0;i<jsonArr.length();i++){
                JSONObject jsonEspectaculo = jsonArr.getJSONObject(i);
                IdsDeFuncion[i]=jsonEspectaculo.getString("IdFuncion");
                Horas[i]=jsonEspectaculo.getString("Hora");
                Dias[i]=jsonEspectaculo.getString("Dia");


            }
        }catch(Exception e){}

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, IdsDeFuncion ,Horas,Dias);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TextView text= (TextView) findViewById(R.id.textView);
                // text.setText(parent.getItemAtPosition(position).toString());
                iniciarVerEntradas(parent.getItemAtPosition(position).toString());
            }
        });

    }


    public void iniciarVerEntradas(String funcion){
        Intent intent2 = new Intent(this, VerEspectaculos_VerEntradas.class);
        intent2.putExtra("IdFuncion",funcion);
        //intent2.putExtra("IdEspectaculo",idEspectaculo);
        intent2.putExtra("IdCliente",getIntent().getStringExtra("IdCliente"));
        startActivity(intent2);


    }

    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] IdsDeFuncion;
        private final String[] Horas;
        private final String[] Dias;

        public MySimpleArrayAdapter(Context context,String[] IdsDeFuncion,String[] Horas, String[] Dias) {
            super(context, R.layout.rowlayout_funciones, IdsDeFuncion);
            this.context = context;
            this.IdsDeFuncion= IdsDeFuncion;
            this.Horas = Horas;
            this.Dias=Dias;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout_funciones, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.Hora);
            TextView textView2 = (TextView) rowView.findViewById(R.id.Dia);


            textView.setText(Horas[position]);
            textView2.setText(Dias[position]);


            // Change the icon for Windows and iPhone
            String s = Horas[position];




            return rowView;
        }
    }
}
