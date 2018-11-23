package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import org.json.JSONArray;
import org.json.JSONObject;

public class EmpresaEmisora {
    public String RazonSocial;
    public String CUIT;
    public String jsonString;

public EmpresaEmisora(JSONObject json){
    try {
        this.RazonSocial = json.getString("RazonSocial");
        this.CUIT = json.getString("CUIT");
    }catch(Exception e){}
    jsonString=json.toString();
}
public EmpresaEmisora(){}


public Espectaculo[] GetEspectaculosDisponibles(){
    Espectaculo[] espectaculos=new Espectaculo[0];
    GetRestAPIDAO getEspectaculos= new GetRestAPIDAO();
    try {
        String jsonEspectaculos = getEspectaculos.execute(UrlBackend.URL + "/Espectaculos?cuitEmp="+this.CUIT).get();
        JSONObject jsonObj = new JSONObject(jsonEspectaculos);

        JSONArray jsonArr;
        jsonArr = jsonObj.getJSONArray("Espectaculos");
        espectaculos=new Espectaculo[jsonArr.length()];
        for(int i=0;i<jsonArr.length();i++){
            espectaculos[i] =new Espectaculo(jsonArr.getJSONObject(i));


        }
    }catch(Exception e){}
    return espectaculos;
}

    public void setCUIT(String CUIT) {
        this.CUIT = CUIT;
    }

    public void setRazonSocial(String razonSocial) {
        RazonSocial = razonSocial;
    }

    public String getJsonString() {
        return jsonString;
    }
}
