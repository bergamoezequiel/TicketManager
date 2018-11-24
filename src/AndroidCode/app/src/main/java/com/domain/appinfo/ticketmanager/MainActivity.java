package com.domain.appinfo.ticketmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.Cliente;
import com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades.UrlBackend;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void ClearText(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.getText().clear();
    }
    public void LogInBottonAction(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        Cliente cli= new Cliente(editText.getText().toString());
        if (!(cli.NombreUsuario().equals("UNKNOWN"))) {
            editText.setText("");
            Intent intent = new Intent(this, Menu.class);
            intent.putExtra("Cliente",cli.GetJsonString());
            startActivity(intent);
        } else {
            editText.setTextColor(Color.RED);
            editText.setText("usuario invalido");
            editText.setVisibility(View.VISIBLE);
        }
    }



}
