package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import org.json.JSONArray;
import org.json.JSONObject;

public class Funcion {
    private String id;
    private String Hora;
    private String Dia;
    private String jsonString;

    public Funcion(JSONObject json) {
        try {
            this.id = json.getString("IdFuncion");
            this.Hora = json.getString("Hora");
            this.Dia = json.getString("Dia");
        } catch (Exception e) {
            this.id = "UNKNOWN";
            this.Hora = "UNKNOWN";
            this.Dia = "UNKNOWN";
        }
        this.jsonString = json.toString();
    }

    public String getId() {
        return id;
    }

    public String getDia() {
        return Dia;
    }

    public String getHora() {
        return Hora;
    }

    public String getJsonString() {
        return jsonString;
    }

    public Entrada[] GetEntradasDisponibles() {

        Entrada[] entradas=new Entrada[0];
        try {
            GetRestAPIDAO getEntradas = new GetRestAPIDAO();
            String jsongetEntradas = getEntradas.execute(UrlBackend.URL + "/Entradas?IdFuncion=" + this.id).get();


        JSONObject jsonObj = new JSONObject(jsongetEntradas);
        JSONArray jsonArr = jsonObj.getJSONArray("Entradas");
             entradas=new Entrada[jsonArr.length()];
            for(int i=0;i<jsonArr.length();i++){
                entradas[i] =new Entrada(jsonArr.getJSONObject(i));


            }




        } catch (Exception e) {}
        return entradas;
    }


}