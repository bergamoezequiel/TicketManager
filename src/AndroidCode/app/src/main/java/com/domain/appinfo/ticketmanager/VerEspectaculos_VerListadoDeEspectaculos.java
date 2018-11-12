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

public class VerEspectaculos_VerListadoDeEspectaculos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_espectaculos__ver_listado_de_espectaculos);
        ConsultarEspectaculosPorEmpresa(getIntent().getStringExtra("cuitEmpresa"));

    }
    private void ConsultarEspectaculosPorEmpresa(String cuitEmpresa){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url =UrlBackend.URL+"/Espectaculos?cuitEmp="+cuitEmpresa;
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
        text.setText(response.toString());
        JSONArray jsonArr;
        //Se arman dos listados, de nombres y descripciones para pasarselo al adaptador de la lista
        String[] IdsDeEspectaculos=new String[0];
        String[] NombreEspectaculos=new String[0];
        String[] DescripcionesEspectaculos=new String[0];
        try {
            jsonArr = response.getJSONArray("Espectaculos");
            IdsDeEspectaculos=new String[jsonArr.length()];
            NombreEspectaculos=new String[jsonArr.length()];
            DescripcionesEspectaculos=new String[jsonArr.length()];
            for(int i=0;i<jsonArr.length();i++){
                JSONObject jsonEspectaculo = jsonArr.getJSONObject(i);
                NombreEspectaculos[i]=jsonEspectaculo.getString("Nombre");
                DescripcionesEspectaculos[i]=jsonEspectaculo.getString("Descripcion");
                IdsDeEspectaculos[i]=jsonEspectaculo.getString("IdEspectaculo");


            }
        }catch(Exception e){}

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, IdsDeEspectaculos ,NombreEspectaculos,DescripcionesEspectaculos);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TextView text= (TextView) findViewById(R.id.textView);
                // text.setText(parent.getItemAtPosition(position).toString());
                iniciarVerFunciones(getIntent().getStringExtra("cuitEmpresa"),parent.getItemAtPosition(position).toString());
            }
        });


    }

    public void iniciarVerFunciones(String cuit,String idEspectaculo){
        Intent intent2 = new Intent(this, VerEspectaculos_VerFunciones.class);
        intent2.putExtra("cuitEmpresa",cuit);
        intent2.putExtra("IdEspectaculo",idEspectaculo);
        intent2.putExtra("IdCliente",getIntent().getStringExtra("IdCliente"));

        startActivity(intent2);
    }


    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] IdsDeEspectaculos;
        private final String[] NombresEspectaculos;
        private final String[] DescripcionesEspectaculos;

        public MySimpleArrayAdapter(Context context,String[] IdsDeEspectaculos,String[] NombresEspectaculos, String[] DescripcionesEspectaculos) {
            super(context, R.layout.rowlayout_espectaculos, IdsDeEspectaculos);
            this.context = context;
            this.IdsDeEspectaculos= IdsDeEspectaculos;
            this.NombresEspectaculos = NombresEspectaculos;
            this.DescripcionesEspectaculos=DescripcionesEspectaculos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout_empresas_emisoras, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            TextView textView2 = (TextView) rowView.findViewById(R.id.label2);
            TextView textView3 = (TextView) rowView.findViewById(R.id.label3);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

            textView.setText(NombresEspectaculos[position]);
            textView2.setText(DescripcionesEspectaculos[position]);
            textView3.setText("modific esto");

            // Change the icon for Windows and iPhone
            String s = NombresEspectaculos[position];

            imageView.setImageResource(R.drawable.superman);


            return rowView;
        }
    }




}
