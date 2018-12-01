package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Entrada {

    public String Ubicacion;
    public String NombreEmpresa;
    public String NombreEspectaculo;
    public String Hora;
    public String Fecha;
    public String IdFuncion;
    public String JsonFullInfo;
    public Entrada(JSONObject json){
        try {
            this.Ubicacion = json.getString("Ubicacion");
        }catch(Exception e){this.Ubicacion="ERROR";}
    }
    public Entrada(){

    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setFullInfo(JSONObject json){
        try {
            this.NombreEspectaculo = json.getString("NombreEspectaculo");
            this.NombreEmpresa = json.getString("NombreEmpresaEmisora");
            this.Hora = json.getString("Hora");
            this.Fecha = json.getString("Dia");
            this.Ubicacion= json.getString("Ubicacion");
            this.IdFuncion = json.getString("IdFuncion") ;
        }
        catch(Exception e){}
        this.JsonFullInfo=json.toString();

    }

    public String getJsonFullInfo() {
        return JsonFullInfo;
    }
    //Desasociar de un cliente
    public void liberar() {
        String URL=UrlBackend.URL+"/Entradas?Ubicacion="+this.Ubicacion+"&IdFuncion="+this.IdFuncion;
        try {
            DeleteRestAPIDAO deleteEntrada = new DeleteRestAPIDAO();
            String respuestaLiberacion = deleteEntrada.execute(URL).get();
        }catch(Exception e){}

    }

    public boolean EstaVencida(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date getCurrentDateTime = new Date();
        String getMyTime=this.Fecha+" "+this.Hora;
        Date dateEntrada=new Date();
        try {
             dateEntrada = sdf.parse(getMyTime);
        }catch(Exception e){}
        return dateEntrada.before(getCurrentDateTime);
    }
}
