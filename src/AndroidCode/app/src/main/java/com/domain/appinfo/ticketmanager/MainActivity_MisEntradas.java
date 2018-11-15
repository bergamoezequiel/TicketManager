package com.domain.appinfo.ticketmanager;

import android.app.ProgressDialog;
import android.content.Context;
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

public class MainActivity_MisEntradas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mis_entradas);
        ConsultarMisEntradas(getIntent().getStringExtra("IdCliente"));
    }

    private void ConsultarMisEntradas(String IdCliente){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        String url =UrlBackend.URL+"/InformacionCompleta/Entradas?IdCliente="+IdCliente;
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

    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] NombresEspectaculos;
        private final String[] NombresEmpresa;
        private final String[] Horas;
        private final String[] Dias;
        private final String[] Ubicaciones;


        public MySimpleArrayAdapter(Context context,String[] NombreEspectaculos,String[] NombresEmpresa, String[] Horas, String[] Dias, String[] Ubicaciones) {
            super(context, R.layout.rowlayout_espectaculos, NombreEspectaculos);
            this.context = context;
            this.NombresEspectaculos= NombreEspectaculos;
            this.NombresEmpresa = NombresEmpresa;
            this.Horas=Horas;
            this.Dias=Dias;
            this.Ubicaciones=Ubicaciones;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout_mis_entradas, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.Espectaculo);
            TextView textView2 = (TextView) rowView.findViewById(R.id.EEmisora);
            TextView textView3 = (TextView) rowView.findViewById(R.id.FechaHora);


            textView.setText(NombresEspectaculos[position]);
            textView2.setText(NombresEmpresa[position]);
            textView3.setText(Dias[position]+ "  "+Horas[position]+"  "+Ubicaciones[position]);

            // Change the icon for Windows and iPhone
            String s = NombresEspectaculos[position];




            return rowView;
        }
    }



    private void RespuestaJSON(JSONObject response) {
        TextView text = (TextView) findViewById(R.id.textView);
        text.setText(response.toString());

        JSONArray jsonArr;
        //Se arman dos listados, de nombres y descripciones para pasarselo al adaptador de la lista
        String[] NombresEspectaculos=new String[0];
        String[] NombreEmpresa=new String[0];
        String[] Horas=new String[0];
        String[] Dias= new String[0];
        String[] Ubicaciones = new String[0];
        try {
            jsonArr = response.getJSONArray("Entradas");
            NombresEspectaculos=new String[jsonArr.length()];
            NombreEmpresa=new String[jsonArr.length()];
            Horas=new String[jsonArr.length()];
            Dias=new String[jsonArr.length()];
            Ubicaciones=new String[jsonArr.length()];
            for(int i=0;i<jsonArr.length();i++){
                JSONObject jsonEspectaculo = jsonArr.getJSONObject(i);
                NombresEspectaculos[i]=jsonEspectaculo.getString("NombreEspectaculo");
                NombreEmpresa[i]=jsonEspectaculo.getString("NombreEmpresaEmisora");
                Horas[i]=jsonEspectaculo.getString("Hora");
                Dias[i]=jsonEspectaculo.getString("Dia");
                Ubicaciones[i]=jsonEspectaculo.getString("Ubicacion");


            }
        }catch(Exception e){}
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, NombresEspectaculos ,NombreEmpresa,Horas,Dias,Ubicaciones);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);

    }
}
