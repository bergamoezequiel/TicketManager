package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import org.json.JSONObject;

public class Cliente {
    private String id="UNKNOWN";
    private String nombre="UNKNOWN";
    private String apellido="UNKNOWN";
    private String dni="UNKNOWN";
    private String nombreUsuario="UNKNOWN";
    private String jsonString="UNKNOWN";

    public Cliente(JSONObject json){
        try {
           this.id=json.get("IdCliente").toString();
           this.nombre=json.get("Nombre").toString();
           this.apellido=json.get("Apellido").toString();
           this.dni=json.get("DNI").toString();
           this.nombreUsuario=json.get("NombreDeUsuario").toString();

        }catch(Exception e){

        }


    }
    public Cliente(String IdCliente){
        GetRestAPIDAO cliDAO= new GetRestAPIDAO();
        try {
            this.jsonString = cliDAO.execute(UrlBackend.URL + "/Cliente/" + IdCliente).get();
            JSONObject jsonObj=new JSONObject(jsonString);
                this.id=jsonObj.get("IdCliente").toString();
                this.nombre=jsonObj.get("Nombre").toString();
                this.apellido=jsonObj.get("Apellido").toString();
                this.dni=jsonObj.get("DNI").toString();
                this.nombreUsuario=jsonObj.get("NombreDeUsuario").toString();



            }catch (Exception e){}
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
    public String GetId(){
        return this.id;
    }
    public String GetJsonString(){
        return this.jsonString;
    }


}
