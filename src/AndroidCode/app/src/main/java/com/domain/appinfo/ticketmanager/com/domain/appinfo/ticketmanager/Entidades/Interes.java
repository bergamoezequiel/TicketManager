package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import org.json.JSONObject;

public class Interes {
    private String NombresEspectaculo;
    private String NombreEmpresa;
    private String Hora;
    private String Dia;
    private String IdFunciones;
    private String CantidadDeEntradas;
    private boolean fueNotificado;

    public Interes(JSONObject json) {
        try {
            this.NombresEspectaculo = json.getString("NombreEspectaculo");
            this.NombreEmpresa = json.getString("NombreEmpresaEmisora");
            this.Hora = json.getString("Hora");
            this.Dia = json.getString("Dia");
            this.IdFunciones = json.getString("IdFuncion");
            this.CantidadDeEntradas = json.getString("CantidadDeEntradas");
            this.fueNotificado = json.getInt("FueNotificado") == 1 ? true : false;
        } catch (Exception e) {
        }
    }

    public String getHora() {
        return Hora;
    }

    public String getDia() {
        return Dia;
    }

    public String getCantidadDeEntradas() {
        return CantidadDeEntradas;
    }

    public String getIdFunciones() {
        return IdFunciones;
    }

    public String getNombreEmpresa() {
        return NombreEmpresa;
    }

    public String getNombresEspectaculo() {
        return NombresEspectaculo;
    }

    public void setFueNotificado(boolean value) {
        this.fueNotificado = value;
    }
    public boolean getFueNotificado(){return fueNotificado;}
}
