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
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Cliente;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Entrada;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity_MisEntradas extends AppCompatActivity {

    private String[] NombresEspectaculos=new String[0];
    private String[] NombreEmpresa=new String[0];
    private String[] Horas=new String[0];
    private String[] Dias= new String[0];
    private String[] Ubicaciones = new String[0];
    private String[] IdsEntradas=new String[0];
    Entrada[] entradas=new Entrada[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mis_entradas);
        ConsultarMisEntradas();

    }

    public void ConsultarMisEntradas(){
        JSONObject clienteJSON= new JSONObject();
        try {
            clienteJSON = new JSONObject(getIntent().getStringExtra("IdCliente"));
        }catch (Exception e){}
        Cliente cliente= new Cliente(clienteJSON);
       entradas = cliente.GetEntradas();
        String[]  IdsEntradas= new String[entradas.length];
        for(int i=0;i<entradas.length;i++) {
            IdsEntradas[i]=entradas[i].IdFuncion+entradas[i].NombreEmpresa+entradas[i].NombreEspectaculo+entradas[i].Ubicacion+entradas[i].Hora+entradas[i].Fecha;
        }
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this,entradas,IdsEntradas );
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TextView text= (TextView) findViewById(R.id.textView);
                // text.setText(parent.getItemAtPosition(position).toString());
                VerEntrada(position);
            }
        });


    }
    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final Entrada[] entradas;
        private final String[] IdsEntradas;


        public MySimpleArrayAdapter(Context context,Entrada[] entradas, String[] IdsEntradas) {
            super(context, R.layout.rowlayout_espectaculos, IdsEntradas);
            this.context = context;
            this.entradas=entradas;
            this.IdsEntradas=IdsEntradas;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout_mis_entradas, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.Espectaculo);
            TextView textView2 = (TextView) rowView.findViewById(R.id.EEmisora);
            TextView textView3 = (TextView) rowView.findViewById(R.id.FechaHora);


            textView.setText(entradas[position].NombreEspectaculo);
            textView2.setText(entradas[position].NombreEmpresa);
            textView3.setText(entradas[position].Fecha+ "  "+entradas[position].Hora+"  "+entradas[position].Ubicacion);

            // Change the icon for Windows and iPhone
            //String s = NombresEspectaculos[position];




            return rowView;
        }
    }





    public void VerEntrada(int position){
        Intent intent2 = new Intent(this, MisEntradas_VerEntrada.class);
        intent2.putExtra("Entrada",entradas[position].getJsonFullInfo());
        intent2.putExtra("IdCliente",getIntent().getStringExtra("IdCliente"));
        startActivity(intent2);

    }
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        ConsultarMisEntradas();
    }
}
