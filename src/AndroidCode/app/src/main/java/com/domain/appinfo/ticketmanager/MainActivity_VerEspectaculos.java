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

public class MainActivity_VerEspectaculos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__ver_espectaculos);
        ConsultarEmpresasEmisoras();
    }

    private void ConsultarEmpresasEmisoras(){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        String url =UrlBackend.URL+"/Empresas";
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
        TextView text= (TextView) findViewById(R.id.textView);
        JSONArray jsonArr;
        String[] RazonesSociales=new String[0];
        String[] Cuits=new String[0];
        try {
            jsonArr = response.getJSONArray("Empresas");
            RazonesSociales=new String[jsonArr.length()];
            Cuits=new String[jsonArr.length()];
            for(int i=0;i<jsonArr.length();i++){
                JSONObject jsonEmpresa = jsonArr.getJSONObject(i);
                RazonesSociales[i]=jsonEmpresa.getString("RazonSocial");
                Cuits[i]=jsonEmpresa.getString("CUIT");

            }
        }catch(Exception e){}

        try {
            text.setText(String.valueOf(response.getString("Empresas")));

        }catch(Exception e){}

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, RazonesSociales,Cuits);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // TextView text= (TextView) findViewById(R.id.textView);
               // text.setText(parent.getItemAtPosition(position).toString());
                iniciarVerListadoDeEspectaculos(parent.getItemAtPosition(position).toString());
            }
        });
    }

    public void iniciarVerListadoDeEspectaculos(String cuit){
        Intent intent2 = new Intent(this, VerEspectaculos_VerListadoDeEspectaculos.class);
        intent2.putExtra("cuitEmpresa",cuit);

        startActivity(intent2);
    }





    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] razonesSociales;
        private final String[] cuits;

        public MySimpleArrayAdapter(Context context, String[] razonesSociales, String[] cuits) {
            super(context, R.layout.rowlayout_empresas_emisoras, cuits);
            this.context = context;
            this.razonesSociales = razonesSociales;
            this.cuits=cuits;
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

            textView.setText(razonesSociales[position]);
            textView2.setText(cuits[position]);
            textView3.setText("modific esto");

            // Change the icon for Windows and iPhone
            String s = razonesSociales[position];

            imageView.setImageResource(R.drawable.superman);


            return rowView;
        }
    }

}
