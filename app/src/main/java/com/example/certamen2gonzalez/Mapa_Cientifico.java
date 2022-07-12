package com.example.certamen2gonzalez;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import BD.BDGonzalez;
import Models.CientificoModel;

public class Mapa_Cientifico extends AppCompatActivity {
    BDGonzalez bd=new BDGonzalez(this);
    Spinner spCientificoRecoleccion;
    String rut;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_cientifico);
        spCientificoRecoleccion = (Spinner) findViewById(R.id.spCientificoRecoleccion) ;
        ArrayList<CientificoModel> listaCientificos= this.bd.getCientificosSql();
        ArrayList<String> listaCientificosRuts = new ArrayList<String>();
        if(listaCientificos.size()==0){
            lanzarToast("Error debe ingresar Cientifico primero");
            finish();
        }
        listaCientificos.forEach(CientificoModel -> listaCientificosRuts.add(CientificoModel.getRut()));
        ArrayAdapter adapter2 = new ArrayAdapter(this,R.layout.simple_spinner,listaCientificosRuts);
        spCientificoRecoleccion.setAdapter(adapter2);
        spCientificoRecoleccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rut=spCientificoRecoleccion.getSelectedItem().toString();
                // listarCientificos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spCientificoRecoleccion.setSelection(0);
    }
    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
    public void Mapa(View v)
    {
        Intent prueba = new Intent(getBaseContext(),MapsActivity.class);
        prueba.putExtra("cientifico",this.rut);
        startActivity(prueba);
    }
    public void MapaTodos(View v)
    {
        Intent prueba = new Intent(getBaseContext(),MapsActivity.class);
        startActivity(prueba);
    }
}