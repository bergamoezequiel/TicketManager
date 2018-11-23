package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import org.json.JSONObject;

public class Funcion {
    private String id;
    private String Hora;
    private String Dia;

    public Funcion(JSONObject json){
        try {
            this.id = json.getString("IdFuncion");
            this.Hora = json.getString("Hora");
            this.Dia = json.getString("Dia");
        }catch(Exception e){
            this.id = "UNKNOWN";
            this.Hora = "UNKNOWN";
            this.Dia = "UNKNOWN";
        }
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
}
