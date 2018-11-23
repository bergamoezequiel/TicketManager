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
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Funcion;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.GetRestAPIDAO;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;

import org.json.JSONArray;
import org.json.JSONObject;

public class VerEspectaculos_VerFunciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_espectaculos__ver_funciones);
        TextView text = (TextView) findViewById(R.id.textView);
        //ConsultarFuncionesPorEmpresaYEspectaculo(getIntent().getStringExtra("cuitEmpresa"),getIntent().getStringExtra("IdEspectaculo"));
        String cuitEmpresa= getIntent().getStringExtra("cuitEmpresa");
        String IdEspectaculo=getIntent().getStringExtra("IdEspectaculo");
        JSONArray jsonArr;
        //Se arman dos listados, de nombres y descripciones para pasarselo al adaptador de la lista
        String[] IdsDeFuncion=new String[0];
        Funcion[] Funciones=new Funcion[0];

        try {
            GetRestAPIDAO getFunciones= new GetRestAPIDAO();
            String jsonFunciones =  getFunciones.execute(UrlBackend.URL+"/Funciones?cuitEmp="+cuitEmpresa+"&"+"IdEspectaculo="+IdEspectaculo).get();
            JSONObject jsonObj = new JSONObject(jsonFunciones);
            jsonArr = jsonObj.getJSONArray("Funciones");
            IdsDeFuncion=new String[jsonArr.length()];
            Funciones=new Funcion[jsonArr.length()];

            for(int i=0;i<jsonArr.length();i++){
                Funciones[i] = new Funcion(jsonArr.getJSONObject(i));
                IdsDeFuncion[i]=Funciones[i].getId();



            }
        }catch(Exception e){}

        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, IdsDeFuncion ,Funciones);
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
        private final Funcion[] Funciones;

        public MySimpleArrayAdapter(Context context,String[] IdsDeFuncion,Funcion[] funciones) {
            super(context, R.layout.rowlayout_funciones, IdsDeFuncion);
            this.context = context;
            this.IdsDeFuncion= IdsDeFuncion;
            this.Funciones=funciones;}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout_funciones, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.Hora);
            TextView textView2 = (TextView) rowView.findViewById(R.id.Dia);


            textView.setText(Funciones[position].getHora());
            textView2.setText(Funciones[position].getDia());


            // Change the icon for Windows and iPhone
           // String s = Horas[position];




            return rowView;
        }
    }
}
