package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import org.json.JSONObject;

public class EmpresaEmisora {
    public String RazonSocial;
    public String CUIT;

public EmpresaEmisora(JSONObject json){
    try {
        this.RazonSocial = json.getString("RazonSocial");
        this.CUIT = json.getString("CUIT");
    }catch(Exception e){}
}
}
