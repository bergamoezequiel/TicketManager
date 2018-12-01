package com.domain.appinfo.ticketmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Cliente;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Interes;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.PutRestAPIDAO;

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
            Interes interesActual = intereses[i];
            if (Integer.valueOf(interesActual.getCantidadDeEntradas())>0 && !interesActual.getFueNotificado()){
                NotificationHelper not= new NotificationHelper(this);
                String mensaje="Hay entradas disponible para la funcion de "+interesActual.getNombresEspectaculo()+"el dia"+interesActual.getDia();
                not.createNotification("Entradas Disponibles!",mensaje,interesActual.getIdFunciones(),cliente);
                intereses[i].setFueNotificado(true);
                cliente.actualizarInteres(interesActual.getIdFunciones(),this);
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
