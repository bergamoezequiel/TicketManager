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
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Cliente;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.CodigoPromocional;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity_MisCodigosPromocionales extends AppCompatActivity {
    public CodigoPromocional[] codigos;
    public String[] cod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mis_codigos_promocionales);
        JSONObject clienteJSON= new JSONObject();
        try {
            clienteJSON = new JSONObject(getIntent().getStringExtra("IdCliente"));
        }catch (Exception e){}
        Cliente cliente= new Cliente(clienteJSON);
        codigos=cliente.getCodigosPromocionales();
        cod=new String[codigos.length];
        for(int i=0;i<codigos.length;i++) {
            cod[i]=codigos[i].getCodigo();
        }
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, codigos ,cod);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);

        //ConsultarCodigos();
    }





    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final CodigoPromocional[] CodigosPromocionales;
        private final String[] codigos;



        public MySimpleArrayAdapter(Context context, CodigoPromocional[] CodigosProm, String[] cod) {
            super(context, R.layout.rowlayout_mis_codigos_promocionales, cod);
            this.context = context;
            this.codigos=cod;
            this.CodigosPromocionales=CodigosProm;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout_mis_codigos_promocionales, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.Codigo);
            TextView textView2 = (TextView) rowView.findViewById(R.id.Descripcion);



            textView.setText(cod[position]);
            textView2.setText(CodigosPromocionales[position].getDescripcion());

            // Change the icon for Windows and iPhone
           // String s = NombresEspectaculos[position];




            return rowView;
        }
    }





}
