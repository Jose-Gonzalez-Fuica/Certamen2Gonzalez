package com.example.certamen2gonzalez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapter.CientificoAdapter;
import Adapter.PlantaAdapter;
import BD.BDGonzalez;
import Models.CientificoModel;
import Models.PlantaModel;
import Models.RecoleccionModel;
//  Jose Esteban Gonzalez Fuica 18800804-6
public class PlantaMain extends AppCompatActivity {

    ListView lvPlanta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta_main);
        lvPlanta = (ListView) findViewById(R.id.lvPlanta);
        lvPlanta.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                PlantaModel value = (PlantaModel) adapter.getItemAtPosition(position);
                Intent prueba = new Intent(getBaseContext(),PlantaEdit.class);
                prueba.putExtra("planta",value);
                startActivity(prueba);
                // listarPlantas();

            }
        });
    }
    public void listarPlantas(){
        BDGonzalez bd = new BDGonzalez(this);
        ArrayList<PlantaModel> list = bd.getPlantasSql();
        if(list.isEmpty()){
            lanzarToast("No hay datos");
        }
        else
        {
            PlantaAdapter adapter = new PlantaAdapter(this,0,list);
            lvPlanta.setAdapter(adapter);
        }
    }
    public void Crear(View v){
        Intent prueba = new Intent(this,CrearPlanta.class);
        startActivity(prueba);

    }
    @Override
    public void onResume() {

        listarPlantas();
        super.onResume();

    }
    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
}