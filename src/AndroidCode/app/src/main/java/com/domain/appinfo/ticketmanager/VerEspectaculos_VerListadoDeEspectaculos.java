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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.EmpresaEmisora;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Espectaculo;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.GetRestAPIDAO;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;

import org.json.JSONArray;
import org.json.JSONObject;

public class VerEspectaculos_VerListadoDeEspectaculos extends AppCompatActivity {
    Espectaculo[] espectaculos;
    EmpresaEmisora emp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_espectaculos__ver_listado_de_espectaculos);
        //ConsultarEspectaculosPorEmpresa(getIntent().getStringExtra("cuitEmpresa"));
        JSONObject js= new JSONObject();
        try{
            js=new JSONObject(getIntent().getStringExtra("empresa"));
        }
        catch(Exception e){}
        emp = new EmpresaEmisora(js);

        espectaculos= emp.GetEspectaculosDisponibles();
        String[] IdsDeEspectaculos = new String[espectaculos.length];


        for (int i = 0; i < espectaculos.length; i++) {
            IdsDeEspectaculos[i] = espectaculos[i].getId();


        }



        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, IdsDeEspectaculos ,espectaculos);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TextView text= (TextView) findViewById(R.id.textView);
                // text.setText(parent.getItemAtPosition(position).toString());
                iniciarVerFunciones(getIntent().getStringExtra("cuitEmpresa"),position);
            }
        });

    }

    public void iniciarVerFunciones(String cuit,int position){
        Intent intent2 = new Intent(this, VerEspectaculos_VerFunciones.class);
        intent2.putExtra("cuitEmpresa",emp.CUIT);
        intent2.putExtra("espectaculo",espectaculos[position].jsonString);
        intent2.putExtra("IdCliente",getIntent().getStringExtra("IdCliente"));

        startActivity(intent2);
    }


    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] IdsDeEspectaculos;
        private final Espectaculo[] espectaculos;


        public MySimpleArrayAdapter(Context context,String[] IdsDeEspectaculos,Espectaculo[] espectaculos) {
            super(context, R.layout.rowlayout_espectaculos, IdsDeEspectaculos);
            this.context = context;
            this.IdsDeEspectaculos= IdsDeEspectaculos;
            this.espectaculos=espectaculos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout_espectaculos, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            TextView textView2 = (TextView) rowView.findViewById(R.id.label2);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

            textView.setText(espectaculos[position].getNombre());
            textView2.setText(espectaculos[position].getDescripcion());

            // Change the icon for Windows and iPhone
            //String s = NombresEspectaculos[position];

            imageView.setImageResource(R.drawable.popcorn);


            return rowView;
        }
    }




}
