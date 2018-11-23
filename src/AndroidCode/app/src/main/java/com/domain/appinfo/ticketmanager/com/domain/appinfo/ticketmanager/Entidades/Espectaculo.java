package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import org.json.JSONObject;

public class Espectaculo {
    public String nombre;
    public String descripcion;
    public String id;
    public Espectaculo(JSONObject js){
        try {
            this.nombre = js.getString("Nombre");
            this.descripcion = js.getString("Descripcion");
            this.id = js.getString("IdEspectaculo");
        }catch(Exception e){
            this.nombre = "UNKNOWN";
            this.descripcion = "UNKNOWN";
            this.id = "UNKNOWN";
        }
    }
    public String getNombre(){
        return this.nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getId() {
        return id;
    }
}
