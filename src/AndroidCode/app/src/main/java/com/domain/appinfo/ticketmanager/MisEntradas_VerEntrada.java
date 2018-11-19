package com.domain.appinfo.ticketmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MisEntradas_VerEntrada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_entradas__ver_entradas);
        TextView text = findViewById(R.id.Ubicacion);
        String concatenado=getIntent().getStringExtra("IdEntrada");
        String[] words=concatenado.split("-",5);
        text.setText(words[3]);

    }
}
