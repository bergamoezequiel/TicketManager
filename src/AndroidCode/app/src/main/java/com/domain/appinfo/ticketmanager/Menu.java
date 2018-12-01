package com.domain.appinfo.ticketmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Cliente;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Interes;

import org.json.JSONObject;

public class Menu extends AppCompatActivity {

    public Cliente cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        TextView TextV = (TextView) findViewById(R.id.textView);
        this.cliente=this.CrearCliente();
        TextV.setText("Bienvenido "+ cliente.GetNombre()+" "+cliente.GetApellido());

        Interes[] intereses=cliente.getIntereses();
        for(int i =0;i<intereses.length;i++){
            if (Integer.valueOf(intereses[i].getCantidadDeEntradas())>0){
                NotificationHelper not= new NotificationHelper(this);
                String mensaje="Hay entradas disponible para la funcion de "+intereses[i].getNombresEspectaculo()+"el dia"+intereses[i].getDia();
                not.createNotification("Entradas Disponibles!",mensaje,intereses[i].getIdFunciones(),cliente);
            }
        }

    }

    public void VerEspectaculosBottonAction(View view) {
        Intent intent = new Intent(this, MainActivity_VerEspectaculos.class);
        intent.putExtra("IdCliente",this.cliente.GetJsonString() );
        startActivity(intent);
    }
    public void MisEntradasBottonAction(View view) {
        Intent intent = new Intent(this, MainActivity_MisEntradas.class);
        intent.putExtra("IdCliente",this.cliente.GetJsonString() );
        startActivity(intent);
    }
    public void CodigosPromocionalesBottonAction(View view) {
        Intent intent = new Intent(this, MainActivity_MisCodigosPromocionales.class);
        intent.putExtra("IdCliente",this.cliente.GetJsonString() );
        startActivity(intent);

    }
    public void AlertasBottonAction(View view) {
        Intent intent = new Intent(this, MainActivity_Alertas.class);
        intent.putExtra("IdCliente",this.cliente.GetJsonString() );
        startActivity(intent);
    }

    public Cliente CrearCliente(){
        String clienteString=getIntent().getStringExtra("Cliente");
        JSONObject CienteJSON = new JSONObject();
        try {
            CienteJSON = new JSONObject(clienteString);
        }catch (Exception e){}
        return new Cliente(CienteJSON);
    }
}
