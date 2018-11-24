package com.domain.appinfo.ticketmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MainActivity_Alertas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__alertas);
        ConsultarAlertas(getIntent().getStringExtra("IdCliente"));
    }

    public void ConsultarAlertas(String IdCliente){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url =UrlBackend.URL+"/Intereses?IdCliente="+IdCliente;
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
       // text.setText(response.toString());
        JSONArray jsonArr;
        String[] NombresEspectaculos=new String[0];
        String[] NombreEmpresa=new String[0];
        String[] Horas=new String[0];
        String[] Dias= new String[0];
        String[] IdFunciones = new String[0];
        String[] cantidadDeEntradas = new String[0];

        try {
            jsonArr = response.getJSONArray("Intereses");
            NombresEspectaculos=new String[jsonArr.length()];
            NombreEmpresa=new String[jsonArr.length()];
            Horas=new String[jsonArr.length()];
            Dias=new String[jsonArr.length()];
            IdFunciones=new String[jsonArr.length()];
            cantidadDeEntradas=new String[jsonArr.length()];

            for(int i=0;i<jsonArr.length();i++){
                JSONObject jsonEspectaculo = jsonArr.getJSONObject(i);
                NombresEspectaculos[i]=jsonEspectaculo.getString("NombreEspectaculo");
                NombreEmpresa[i]=jsonEspectaculo.getString("NombreEmpresaEmisora");
                Horas[i]=jsonEspectaculo.getString("Hora");
                Dias[i]=jsonEspectaculo.getString("Dia");
                IdFunciones[i]=jsonEspectaculo.getString("IdFuncion");
                cantidadDeEntradas[i]=jsonEspectaculo.getString("CantidadDeEntradas");


            }
        }catch(Exception e){text.setText("error");}
       // text.setText(IdFunciones.toString());
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, NombresEspectaculos ,NombreEmpresa,Horas,Dias,IdFunciones,cantidadDeEntradas);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);
    }

    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] NombresEspectaculos;
        private final String[] NombresEmpresa;
        private final String[] Horas;
        private final String[] Dias;
        private final String[] IdFunciones;
        private final String[] cantidadDeEntradas;


        public MySimpleArrayAdapter(Context context,String[] NombreEspectaculos,String[] NombresEmpresa, String[] Horas, String[] Dias, String[] IdFunciones ,String[] cantidadDeEntradas) {
            super(context, R.layout.rowlayout_espectaculos, NombreEspectaculos);
            this.context = context;
            this.NombresEspectaculos= NombreEspectaculos;
            this.NombresEmpresa = NombresEmpresa;
            this.Horas=Horas;
            this.Dias=Dias;
            this.IdFunciones=IdFunciones;
            this.cantidadDeEntradas=cantidadDeEntradas;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout_intereses, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.Espectaculo);
            TextView textView2 = (TextView) rowView.findViewById(R.id.EEmisora);
            TextView textView3 = (TextView) rowView.findViewById(R.id.FechaHora);
            TextView textView4 = (TextView) rowView.findViewById(R.id.Disponibilidad);
            ImageView FaceImage = (ImageView) rowView.findViewById(R.id.faceImage);
            if (Integer.valueOf(cantidadDeEntradas[position])>0){
                textView4.setTextColor(Color.GREEN);
                textView4.setText("Hay "+cantidadDeEntradas[position]+" entrada/s disponible/s!" );
                FaceImage.setImageResource(R.drawable.happyface);
            }
            else{
                textView4.setText("No hay entradas disponibles!" );
                textView4.setTextColor(Color.RED);
                FaceImage.setImageResource(R.drawable.sadface);
            }


            textView.setText(NombresEspectaculos[position]);
            textView2.setText(NombresEmpresa[position]);
            textView3.setText(Dias[position]+ "  "+Horas[position]);

            // Change the icon for Windows and iPhone
            String s = NombresEspectaculos[position];





            return rowView;
        }
    }







}
