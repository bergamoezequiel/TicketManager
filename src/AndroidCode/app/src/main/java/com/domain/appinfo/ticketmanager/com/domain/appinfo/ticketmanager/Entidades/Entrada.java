package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

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
}
