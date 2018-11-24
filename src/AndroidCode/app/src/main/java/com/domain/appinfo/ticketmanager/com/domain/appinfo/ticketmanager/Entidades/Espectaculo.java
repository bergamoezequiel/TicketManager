package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import org.json.JSONArray;
import org.json.JSONObject;

public class Espectaculo {
    public String nombre;
    public String descripcion;
    public String id;
    public String jsonString;
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
        jsonString=js.toString();
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
    public Funcion[] GetFuncionesDisponibles(String cuitEmpresa){
        Funcion[] funciones=new Funcion[0];
        GetRestAPIDAO getfunciones= new GetRestAPIDAO();
        try {
            String jsonEspectaculos = getfunciones.execute(UrlBackend.URL+"/Funciones?cuitEmp="+cuitEmpresa+"&"+"IdEspectaculo="+this.id).get();
            JSONObject jsonObj = new JSONObject(jsonEspectaculos);

            JSONArray jsonArr;
            jsonArr = jsonObj.getJSONArray("Funciones");
            funciones=new Funcion[jsonArr.length()];
            for(int i=0;i<jsonArr.length();i++){
                funciones[i] =new Funcion(jsonArr.getJSONObject(i));


            }
        }catch(Exception e){}
        return funciones;
    }
}
