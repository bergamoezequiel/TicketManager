package com.domain.appinfo.ticketmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Cliente;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Entrada;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Funcion;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.GetRestAPIDAO;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_LONG;

public class VerEspectaculos_VerEntradas extends AppCompatActivity {
    private boolean hayEntradas=true;
    public Funcion funcion;
    public Entrada[] entradas;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_espectaculos__ver_entradas);
        TextView text = (TextView) findViewById(R.id.textView);
        JSONObject js= new JSONObject();
        try{
            js=new JSONObject(getIntent().getStringExtra("Funcion"));
        }catch(Exception e){}
        funcion=new Funcion(js);
        Entrada[] entradas=funcion.GetEntradasDisponibles();
        String[] Ubicaciones=new String[entradas.length];
       //       getIntent().getStringExtra("funcion"), Toast.LENGTH_SHORT).show();
                Button boton= findViewById(R.id.button);
                if(entradas.length<1){
                    boton.setVisibility(View.VISIBLE);
                    hayEntradas=false;
                }else{
                    boton.setVisibility(View.GONE);
                }
                for(int i=0;i<entradas.length;i++){

                    Ubicaciones[i]=entradas[i].Ubicacion;



                }


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

        if(!hayEntradas) {
          /*  try {
                json.put("IdCliente", getIntent().getStringExtra("IdCliente"));
                json.put("IdFuncion", getIntent().getStringExtra("IdFuncion"));
            } catch (Exception e) {
            }*/
            JSONObject CienteJSON=new JSONObject();

            try {
                CienteJSON= new JSONObject(getIntent().getStringExtra("IdCliente"));
            }catch (Exception e){}
            Cliente cli= new Cliente(CienteJSON);
            cli.GuardarInteres(funcion.getId(),this);
            //GuardarInteres(json);

        }else{
            Toast.makeText(this,
                    "Todavia hay entradas disponibles", Toast.LENGTH_SHORT).show();

        }


    }




}
