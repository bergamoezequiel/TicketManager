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
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.EmpresaEmisora;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.GetRestAPIDAO;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity_VerEspectaculos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__ver_espectaculos);
        //ConsultarEmpresasEmisoras();
        GetRestAPIDAO getEmpresasEmisoras= new GetRestAPIDAO();
        try {
            String jsonEmpresasEmisoras = getEmpresasEmisoras.execute(UrlBackend.URL + "/Empresas").get();
            JSONObject jsonObj=new JSONObject(jsonEmpresasEmisoras);
            JSONArray jsonArr;
            jsonArr= jsonObj.getJSONArray("Empresas");
            String[] RazonesSociales=new String[jsonArr.length()];
            String[] Cuits=new String[jsonArr.length()];
            EmpresaEmisora[] empresasEmisoras= new EmpresaEmisora[jsonArr.length()];
            for(int i=0;i<jsonArr.length();i++) {
                empresasEmisoras[i]= new EmpresaEmisora(jsonArr.getJSONObject(i));
                RazonesSociales[i]=empresasEmisoras[i].RazonSocial;
                Cuits[i]=empresasEmisoras[i].CUIT;
            }
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

        }catch(Exception e){}

    }




    public void iniciarVerListadoDeEspectaculos(String cuit){
        Intent intent2 = new Intent(this, VerEspectaculos_VerListadoDeEspectaculos.class);
        intent2.putExtra("cuitEmpresa",cuit);
        intent2.putExtra("IdCliente",getIntent().getStringExtra("IdCliente"));

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
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

            textView.setText(razonesSociales[position]);

            // Change the icon for Windows and iPhone
            String s = razonesSociales[position];

            imageView.setImageResource(R.drawable.popcorn);


            return rowView;
        }
    }

}
