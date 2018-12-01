package com.domain.appinfo.ticketmanager.com.domain.appinfo.ticketmanager.Entidades;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
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
           this.jsonString=json.toString();

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

    public void GuardarInteres(String IdFuncion, Context cont){

        JSONObject json= new JSONObject();
        try {
            json.put("IdCliente", this.id);
            json.put("IdFuncion", IdFuncion);
            json.put("FueNotificado",0);
        }catch(Exception e){}
        String URL= UrlBackend.URL+"/Intereses";

        try {
            PostRestAPIDAO post = new PostRestAPIDAO();

            String RespuestaCrearInteres=post.execute(json.toString(),URL).get();

            JSONObject jsonObj = new JSONObject(RespuestaCrearInteres);
            jsonObj.getString("error");

            Toast.makeText(cont,
                    "Alerta generada previamente", Toast.LENGTH_SHORT).show();

        }catch(Exception e){

            Toast.makeText(cont,
                    "Alerta Generada", Toast.LENGTH_SHORT).show();
        }
    }

    public void  actualizarInteres(String idFuncion , Context cont) {
        JSONObject json= new JSONObject();
        try {
            json.put("IdCliente", this.id);
            json.put("IdFuncion", idFuncion);
            json.put("FueNotificado",1);
        }catch(Exception e){}
        String URL= UrlBackend.URL+"/Intereses";

        try {
            PutRestAPIDAO put = new PutRestAPIDAO();

            String RespuestaActualizarInteres=put.execute(json.toString(),URL).get();

            JSONObject jsonObj = new JSONObject(RespuestaActualizarInteres);

        }catch(Exception e){

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
    public String GetId(){
        return this.id;
    }
    public String GetJsonString(){
        return this.jsonString;
    }
    public Entrada[] GetEntradas(){

            Entrada[] entradas=new Entrada[0];
            try {
                GetRestAPIDAO getEntradas = new GetRestAPIDAO();
                String URL= UrlBackend.URL+"/InformacionCompleta/Entradas?IdCliente="+this.id;
                String jsongetEntradas = getEntradas.execute(URL).get();



                JSONObject jsonObj = new JSONObject(jsongetEntradas);
                JSONArray jsonArr = jsonObj.getJSONArray("Entradas");
                entradas=new Entrada[jsonArr.length()];
                for(int i=0;i<jsonArr.length();i++){
                    entradas[i] =new Entrada();
                    entradas[i].setFullInfo(jsonArr.getJSONObject(i));

                }




            } catch (Exception e) {}
            return entradas;
        }


        public Interes[] getIntereses(){
            Interes[] intereses=new Interes[0];
            String URL=UrlBackend.URL+"/Intereses?IdCliente="+this.id;
            GetRestAPIDAO getIntereses= new GetRestAPIDAO();
            try {
                String respIntereses = getIntereses.execute(URL).get();
                JSONObject json= new JSONObject(respIntereses);
                JSONArray jsonArr = json.getJSONArray("Intereses");
                intereses=new Interes[jsonArr.length()];
                for(int i=0;i<jsonArr.length();i++){
                    intereses[i] =new Interes(jsonArr.getJSONObject(i));


                }


            }catch(Exception e){}
            return intereses;
        }

    public CodigoPromocional[] getCodigosPromocionales(){
        CodigoPromocional[] codigos=new CodigoPromocional[0];
        String URL=UrlBackend.URL+"/"+ this.id+"/CodigosPromocionales";
        GetRestAPIDAO getCodigos= new GetRestAPIDAO();
        try {
            String respIntereses = getCodigos.execute(URL).get();
            JSONObject json= new JSONObject(respIntereses);
            JSONArray jsonArr = json.getJSONArray("CodigosPromocionales");
            codigos=new CodigoPromocional[jsonArr.length()];
            for(int i=0;i<jsonArr.length();i++){
                codigos[i] =new CodigoPromocional(jsonArr.getJSONObject(i));


            }


        }catch(Exception e){}
        return codigos;
    }



}
