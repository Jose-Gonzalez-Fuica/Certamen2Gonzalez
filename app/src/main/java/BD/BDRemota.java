package BD;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

import Models.CientificoModel;
import Models.PlantaModel;
import Models.RecoleccionModel;

public class BDRemota {


    //Planta
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<PlantaModel> recibirdatosPlanta(){
        URL url;
        String linea;
        ArrayList<PlantaModel> r=new ArrayList<>();
        int respuesta;
        StringBuilder resul=null;
        try {
            url=new URL("https://android.gxmundoweb.cl/GetPlanta.php");
            HttpURLConnection conection=(HttpURLConnection) url.openConnection();
            respuesta=conection.getResponseCode();  //codigo respuesta
            resul=new StringBuilder();
            if(respuesta== HttpURLConnection.HTTP_OK){
                InputStream in=new BufferedInputStream(conection.getInputStream());
                // se lee respuesta
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                //llenar variable resul
                while((linea=reader.readLine())!=null) {
                    resul.append(linea);
                }
                r=obtDatosJsonPlanta(resul.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

        // return resul.toString();  //el json que se esta consumiendo del servicio php

        return r;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private  ArrayList<PlantaModel> obtDatosJsonPlanta(String response){
        int res=0;
        ArrayList<PlantaModel> plantas=new ArrayList<>();
        try{
            response = response.replace("][",",");
            JSONArray json=new JSONArray(response);

            // se cuenta datos
            if(json.length()>0){
                res=1;
                for(int i=0;i<json.length();i++) {
                    JSONObject objeto=json.getJSONObject(i);
                    int id=objeto.getInt("id");
                    String codigoPlanta=objeto.getString("codigoPlanta");
                    String nombrePlanta=objeto.getString("nombrePlanta");
                    String nombreCientifico=objeto.getString("nombreCientifico");
                    String fotoPlanta=objeto.getString("fotoPlanta");
                    String uso = objeto.getString("uso");
                    PlantaModel planta=new PlantaModel(id,codigoPlanta,nombrePlanta,nombreCientifico,Base64.getDecoder().decode(new String(fotoPlanta).getBytes("UTF-8")),uso);
                    plantas.add(planta);
                    // se debe separar los campos de Json
                    //..................... se pueden pasar a un List si son varios .
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return plantas;   // devuelve 1: si existe o 0


    }

    //Cientifico
    public ArrayList<CientificoModel> recibirdatosCientifico(){
        URL url;
        String linea;
        ArrayList<CientificoModel> r=new ArrayList<>();
        int respuesta;
        StringBuilder resul=null;
        try {
            url=new URL("https://android.gxmundoweb.cl/GetCientifico.php");
            HttpURLConnection conection=(HttpURLConnection) url.openConnection();
            respuesta=conection.getResponseCode();  //codigo respuesta
            resul=new StringBuilder();
            if(respuesta== HttpURLConnection.HTTP_OK){
                InputStream in=new BufferedInputStream(conection.getInputStream());
                // se lee respuesta
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                //llenar variable resul
                while((linea=reader.readLine())!=null) {
                    resul.append(linea);
                }
                r=obtDatosJsonCientifico(resul.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

        // return resul.toString();  //el json que se esta consumiendo del servicio php

        return r;
    }
    private  ArrayList<CientificoModel> obtDatosJsonCientifico(String response){
        int res=0;
        ArrayList<CientificoModel> cientificos=new ArrayList<>();
        try{
            response = response.replace("][",",");
            JSONArray json=new JSONArray(response);

            // se cuenta datos
            if(json.length()>0){
                res=1;
                for(int i=0;i<json.length();i++) {
                    JSONObject objeto=json.getJSONObject(i);
                    int id=objeto.getInt("id");
                    String rut=objeto.getString("rut");
                    String nombre=objeto.getString("nombre");
                    String apellidoPaterno=objeto.getString("apellidoPaterno");
                    String apellidoMaterno=objeto.getString("apellidoMaterno");
                    String sexo = objeto.getString("sexo");
                    CientificoModel cientifico=new CientificoModel(id,rut,nombre,apellidoPaterno,apellidoMaterno,sexo);
                    cientificos.add(cientifico);
                    // se debe separar los campos de Json
                    //..................... se pueden pasar a un List si son varios .
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return cientificos;   // devuelve 1: si existe o 0


    }

    //Recoleccion
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<RecoleccionModel> recibirdatosRecoleccion(){
        URL url;
        String linea;
        ArrayList<RecoleccionModel> r=new ArrayList<>();
        int respuesta;
        StringBuilder resul=null;
        try {
            url=new URL("https://android.gxmundoweb.cl/GetRecoleccion.php");
            HttpURLConnection conection=(HttpURLConnection) url.openConnection();
            respuesta=conection.getResponseCode();  //codigo respuesta
            resul=new StringBuilder();
            if(respuesta== HttpURLConnection.HTTP_OK){
                InputStream in=new BufferedInputStream(conection.getInputStream());
                // se lee respuesta
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                //llenar variable resul
                while((linea=reader.readLine())!=null) {
                    resul.append(linea);
                }
                r=obtDatosJsonRecoleccion(resul.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

        // return resul.toString();  //el json que se esta consumiendo del servicio php

        return r;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private  ArrayList<RecoleccionModel> obtDatosJsonRecoleccion(String response){
        int res=0;
        ArrayList<RecoleccionModel> recolecciones=new ArrayList<>();
        try{
            response = response.replace("][",",");
            JSONArray json=new JSONArray(response);

            // se cuenta datos
            if(json.length()>0){
                res=1;
                for(int i=0;i<json.length();i++) {
                    JSONObject objeto=json.getJSONObject(i);
                    int id=objeto.getInt("id");
                    String fecha=objeto.getString("fecha");
                    String codigoPlanta=objeto.getString("codigoPlanta");
                    String rutCientifico=objeto.getString("rutCientifico");
                    String comentario=objeto.getString("comentario");
                    String fotoLugar = objeto.getString("fotoLugar");
                    Double latitud = objeto.getDouble("latitud");
                    Double longitud = objeto.getDouble("longitud");
                    RecoleccionModel recoleccion=new RecoleccionModel(id,fecha,codigoPlanta,rutCientifico,comentario,Base64.getDecoder().decode(new String(fotoLugar).getBytes("UTF-8")),latitud,longitud);
                    recolecciones.add(recoleccion);
                    // se debe separar los campos de Json
                    //..................... se pueden pasar a un List si son varios .
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return recolecciones;   // devuelve 1: si existe o 0


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String PostRecoleccion (RecoleccionModel recoleccion){
        String linea,r="nn";
        int respuesta;
        StringBuilder resul=null;
        try {
            String params = new JSONObject()
                    .put("fecha", recoleccion.getFecha())
                    .put("codigoPlanta", recoleccion.getCodigoPlanta())
                    .put("rutCientifico", recoleccion.getRutCientifico())
                    .put("comentario", recoleccion.getComentario())
                    .put("latitud", recoleccion.getLatitud())
                    .put("longitud", recoleccion.getLongitud())
                    .put("fotoLugar", Base64.getEncoder().encodeToString(recoleccion.getFotoLugar())).toString();
            URL _url = new URL("https://android.gxmundoweb.cl/PostRecoleccion.php");
            HttpURLConnection urlConn =(HttpURLConnection)_url.openConnection();
            urlConn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.setDoOutput(true);
            urlConn.setRequestMethod("POST");
            DataOutputStream outputStream = new DataOutputStream(urlConn.getOutputStream());
            outputStream.writeBytes(params);
            outputStream.close();
            urlConn.connect();
            urlConn.getContent();
            respuesta=urlConn.getResponseCode();
            if(respuesta== HttpURLConnection.HTTP_OK){
                r="OK";
            }
        } catch (Exception e) {
            e.printStackTrace();
            String n=e.getMessage().toString();
            return n;
        }
        return r;
    }
}



