package com.domain.appinfo.ticketmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class MainActivity_MisCodigosPromocionales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mis_codigos_promocionales);
        ConsultarCodigos();
    }

    private void ConsultarCodigos(){
        String tag_json_obj = "json_obj_req";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url =UrlBackend.URL+"/"+ getIntent().getStringExtra("IdCliente")+"/CodigosPromocionales";
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
        TextView text = findViewById(R.id.textView);
        text.setText(response.toString());
        JSONArray jsonArr;
        //Se arman dos listados, de nombres y descripciones para pasarselo al adaptador de la lista
        String[] Codigos=new String[0];
        String[] Descripciones=new String[0];
        String[] Dias=new String[0];
        try {
            jsonArr = response.getJSONArray("CodigosPromocionales");
            Codigos=new String[jsonArr.length()];
            Descripciones=new String[jsonArr.length()];
            for(int i=0;i<jsonArr.length();i++){
                JSONObject jsonEspectaculo = jsonArr.getJSONObject(i);
                Codigos[i]=jsonEspectaculo.getString("CodigoPromocional");
                Descripciones[i]=jsonEspectaculo.getString("Descripcion");
            }
        }catch(Exception e){}

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, Codigos ,Descripciones);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);

    }

    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] Codigos;
        private final String[] Descripciones;



        public MySimpleArrayAdapter(Context context, String[] Codigos, String[] Descripciones) {
            super(context, R.layout.rowlayout_mis_codigos_promocionales, Codigos);
            this.context = context;
            this.Codigos=Codigos;
            this.Descripciones=Descripciones;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout_mis_codigos_promocionales, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.Codigo);
            TextView textView2 = (TextView) rowView.findViewById(R.id.Descripcion);



            textView.setText(Codigos[position]);
            textView2.setText(Descripciones[position]);


            // Change the icon for Windows and iPhone
           // String s = NombresEspectaculos[position];




            return rowView;
        }
    }





}
