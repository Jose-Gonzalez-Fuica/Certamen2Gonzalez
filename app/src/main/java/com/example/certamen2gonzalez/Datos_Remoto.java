package com.example.certamen2gonzalez;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapter.CientificoAdapter;
import Adapter.PlantaAdapter;
import BD.BDGonzalez;
import BD.BDRemota;
import Models.CientificoModel;
import Models.PlantaModel;

public class Datos_Remoto extends AppCompatActivity {

    Button RemotoCientifico,RemotoPlanta,RemotoRecoleccion;
    BDRemota bd = new BDRemota();
    BDGonzalez local = new BDGonzalez(this);
    ListView lvRemoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_remoto);
        RemotoCientifico=findViewById(R.id.RemotoCientifico);
        RemotoPlanta=findViewById(R.id.RemotoPlanta);
        RemotoRecoleccion=findViewById(R.id.RemotoRecoleccion);
        lvRemoto=findViewById(R.id.lvRemoto);
    }
    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
    //Cientifico
    public void getCientificosRemotos(View v){

        ArrayList list = new ArrayList();
        ArrayAdapter adapter = new ArrayAdapter(this, 0, list);
        list = bd.recibirdatosCientifico();
        adapter = new CientificoAdapter(this, 0, list);
        SetAdapterListCientifico(list, adapter);
    }
    public void SetAdapterListCientifico(ArrayList list, ArrayAdapter arrayAdapter){
        if (list.size() > 0) {

            ArrayList lstExist = ValidateExistCientifico(list);

            if(lstExist.size() > 0){
                SaveListCientifico(lstExist);
                lvRemoto.setVisibility(View.VISIBLE);
            }
            else {
                lanzarToast(  "Sus bases de datos contienen los mismos datos.");
                lvRemoto.setVisibility(View.INVISIBLE);
                return;
            }
                arrayAdapter = new CientificoAdapter(this, 0, lstExist);

            lvRemoto.setAdapter(arrayAdapter);
        }
        else
            lanzarToast("La Base de Datos Remota no contiene datos en la tabla seleccionada!!");
    }

    private void SaveListCientifico(ArrayList lstExist) {
        ArrayList<CientificoModel> lstEC = lstExist;
        for(CientificoModel itemC : lstEC)
            local.insertarCientificoSql(itemC.getRut(),itemC.getNombre(),itemC.getApellidoPaterno(),itemC.getApellidoMaterno(),itemC.getSexo());
    }

    private ArrayList ValidateExistCientifico(ArrayList list) {
        ArrayList<CientificoModel> listExistC = local.getCientificosSql();
        ArrayList<CientificoModel> lstC = list;

        try{
            for(int i = 0; i < lstC.size(); i++){
                for(int x = 0; x < listExistC.size(); x++){
                    if(lstC.get(i).getRut().equals(listExistC.get(x).getRut()))
                        lstC.remove(i);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        list = lstC;
        return list;
    }

    //Planta
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getPlantaRemotas(View v){

        ArrayList list = new ArrayList();
        ArrayAdapter adapter = new ArrayAdapter(this, 0, list);
        list = bd.recibirdatosPlanta();
        adapter = new PlantaAdapter(this, 0, list);
        SetAdapterListPlanta(list, adapter);
    }
    public void SetAdapterListPlanta(ArrayList list, ArrayAdapter arrayAdapter){
        if (list.size() > 0) {

            ArrayList lstExist = ValidateExistPlanta(list);

            if(lstExist.size() > 0){
                SaveListPlanta(lstExist);
                lvRemoto.setVisibility(View.VISIBLE);
            }
            else {
                lanzarToast(  "Sus bases de datos contienen los mismos datos.");
                lvRemoto.setVisibility(View.INVISIBLE);
                return;
            }
            arrayAdapter = new PlantaAdapter(this, 0, lstExist);

            lvRemoto.setAdapter(arrayAdapter);
        }
        else
            lanzarToast("La Base de Datos Remota no contiene datos en la tabla seleccionada!!");
    }

    private void SaveListPlanta(ArrayList lstExist) {
        ArrayList<PlantaModel> lstEC = lstExist;
        for(PlantaModel itemC : lstEC)
            local.insertarPlantaSql(itemC.getCodigoPlanta(),itemC.getNombrePlanta(),itemC.getNombreCientifico(),itemC.getFotoPlanta(),itemC.getUso());
    }

    private ArrayList ValidateExistPlanta(ArrayList list) {
        ArrayList<PlantaModel> listExistC = local.getPlantasSql();
        ArrayList<PlantaModel> lstC = list;

        try{
            for(int i = 0; i < lstC.size(); i++){
                for(int x = 0; x < listExistC.size(); x++){
                    if(lstC.get(i).getCodigoPlanta().equals(listExistC.get(x).getCodigoPlanta()))
                        lstC.remove(i);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        list = lstC;
        return list;
    }
}