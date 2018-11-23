package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import org.json.JSONObject;

public class Entrada {

    public String Ubicacion;
    public Entrada(JSONObject json){
        try {
            this.Ubicacion = json.getString("Ubicacion");
        }catch(Exception e){this.Ubicacion="ERROR";}
    }

    public String getUbicacion() {
        return Ubicacion;
    }
}
