package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import org.json.JSONObject;

public class Cliente {
    private String id;
    private String nombre;
    private String apellido;
    private String dni;
    private String nombreUsuario;

    public Cliente(JSONObject json){
        try {
           this.id=json.get("IdCliente").toString();
           this.nombre=json.get("Nombre").toString();
           this.apellido=json.get("Apellido").toString();
           this.dni=json.get("DNI").toString();
           this.nombreUsuario=json.get("NombreDeUsuario").toString();

        }catch(Exception e){
            this.id="UNKNOWN";
            this.nombre="UNKNOWN";
            this.apellido="UNKNOWN";
            this.dni="UNKNOWN";
            this.nombreUsuario="UNKNOWN";

        }


    }

    public String GetNombre(){
        return this.nombre;
    }
    public String GetApellido(){
        return this.apellido;
    }
    public String GetDNI(){
        return this.dni;
    }
    public String NombreUsuario(){
        return this.nombreUsuario;
    }


}
