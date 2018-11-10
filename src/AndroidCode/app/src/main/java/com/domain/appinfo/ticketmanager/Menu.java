package com.domain.appinfo.ticketmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void VerEspectaculosBottonAction(View view) {
        Intent intent = new Intent(this, MainActivity_VerEspectaculos.class);
        startActivity(intent);
    }
    public void MisEntradasBottonAction(View view) {
        Intent intent = new Intent(this, MainActivity_MisEntradas.class);
        startActivity(intent);
    }
    public void CodigosPromocionalesBottonAction(View view) {
        Intent intent = new Intent(this, MainActivity_MisCodigosPromocionales.class);
        startActivity(intent);
    }
    public void AlertasBottonAction(View view) {
        Intent intent = new Intent(this, MainActivity_Alertas.class);
        startActivity(intent);
    }
}
