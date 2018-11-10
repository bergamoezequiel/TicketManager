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
        try {
            text.setText(String.valueOf(response.getString("Empresas")));

        }catch(Exception e){}
        String[] values = new String[] { "Harry Potter 2", "Superman",
                "Batman inicia", "El conjuro", "Insidious", "Duro de matar", "Harry Potter 3",
                "Linux", "OS/2" };
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);
    }

    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public MySimpleArrayAdapter(Context context, String[] values) {
            super(context, R.layout.rowlayout_empresas_emisoras, values);
            this.context = context;
            this.values = values;
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

            textView.setText(values[position]);
            textView2.setText("cine: village");
            textView3.setText("Sucural: Caballito");

            // Change the icon for Windows and iPhone
            String s = values[position];

            imageView.setImageResource(R.drawable.superman);


            return rowView;
        }
    }

}
