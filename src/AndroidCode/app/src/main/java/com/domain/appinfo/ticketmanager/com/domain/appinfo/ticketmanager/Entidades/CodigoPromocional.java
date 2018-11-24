package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import org.json.JSONObject;

public class CodigoPromocional {
    private String Codigo;
    private String Descripcion;
    private String IdCliente;
    public CodigoPromocional(JSONObject js,String idCliente){
        try{
            this.Codigo = js.getString("CodigoPromocional");
            this.Descripcion = js.getString("Descripcion");
            this.IdCliente=idCliente;

        }catch(Exception e){}

    }
    public CodigoPromocional(JSONObject js){
        try{
            this.Codigo = js.getString("CodigoPromocional");
            this.Descripcion = js.getString("Descripcion");
            this.IdCliente="NO_ASIGNADA";

        }catch(Exception e){}

    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getCodigo() {
        return Codigo;
    }
}
