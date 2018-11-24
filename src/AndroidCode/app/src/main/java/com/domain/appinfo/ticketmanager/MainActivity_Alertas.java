package com.domain.appinfo.ticketmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
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
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Cliente;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Interes;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity_Alertas extends AppCompatActivity {
    public Interes[] intereses;
    public String[] nombreEspectaculo;
    public Cliente cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__alertas);

        TextView text = (TextView) findViewById(R.id.textView);
        text.setText(getIntent().getStringExtra("IdCliente"));
        JSONObject js= new JSONObject();
        try{
            js=new JSONObject(getIntent().getStringExtra("IdCliente"));
        }
        catch(Exception e){}
        cliente=new Cliente(js);
        ConsultarAlertas();
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this,nombreEspectaculo , intereses);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);

    }

    public void ConsultarAlertas(){
            intereses=cliente.getIntereses();
            nombreEspectaculo=new String[intereses.length];
        for(int i=0;i<intereses.length;i++) {
            nombreEspectaculo[i]=intereses[i].getNombresEspectaculo();
        }

    }

    private void RespuestaJSON(JSONObject response) {



    }

    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final Interes[] intereses;
        private final String[] nombresEspectaculos;



        public MySimpleArrayAdapter(Context context,String[] nombresEspectaculos,Interes[] intereses) {
            super(context, R.layout.rowlayout_espectaculos,nombresEspectaculos);
            this.context = context;
            this.nombresEspectaculos= nombresEspectaculos;
            this.intereses = intereses;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout_intereses, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.Espectaculo);
            TextView textView2 = (TextView) rowView.findViewById(R.id.EEmisora);
            TextView textView3 = (TextView) rowView.findViewById(R.id.FechaHora);
            TextView textView4 = (TextView) rowView.findViewById(R.id.Disponibilidad);

            ImageView FaceImage = (ImageView) rowView.findViewById(R.id.faceImage);
            if (Integer.valueOf(intereses[position].getCantidadDeEntradas())>0){
                textView4.setTextColor(Color.GREEN);
                textView4.setText("Hay "+intereses[position].getCantidadDeEntradas()+" entrada/s disponible/s!" );
                FaceImage.setImageResource(R.drawable.happyface);
            }
            else{
                textView4.setText("No hay entradas disponibles!" );
                textView4.setTextColor(Color.RED);
                FaceImage.setImageResource(R.drawable.sadface);

            }


            textView.setText(nombresEspectaculos[position]);
            textView2.setText(intereses[position].getNombreEmpresa());
            textView3.setText(intereses[position].getDia()+ "  "+intereses[position].getHora());

            // Change the icon for Windows and iPhone
            //String s = NombresEspectaculos[position];





            return rowView;
        }
    }







}
