package com.example.certamen2gonzalez;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import Adapter.PlantaAdapter;
import Adapter.RecoleccionAdapter;
import BD.BDGonzalez;
import Models.CientificoModel;
import Models.PlantaModel;
import Models.RecoleccionModel;
//  Jose Esteban Gonzalez Fuica 18800804-6
public class RecoleccionMain extends AppCompatActivity {

    ListView lvRecoleccion;
    BDGonzalez bd;
    Spinner spCientificoRecoleccion;
    String rutSeleccionado="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoleccion_main);
        lvRecoleccion= (ListView) findViewById(R.id.lvRecoleccion);
        lvRecoleccion.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                RecoleccionModel value = (RecoleccionModel) adapter.getItemAtPosition(position);
                Intent prueba = new Intent(getBaseContext(),RecoleccionEdit.class);
                prueba.putExtra("recoleccion",value);
                startActivity(prueba);
                // listarCientificos();

            }
        });
        this.bd = new BDGonzalez(this);
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
                String rut=spCientificoRecoleccion.getSelectedItem().toString();
                listarRecolecccion(rut);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spCientificoRecoleccion.setSelection(0);
    }
    public void listarRecolecccion(String rut){
        this.rutSeleccionado=rut;
        BDGonzalez bd = new BDGonzalez(this);
        ArrayList<RecoleccionModel> list = new ArrayList<RecoleccionModel>();
        list = bd.getRecoleccionByCientificoSql(this.rutSeleccionado);
        if(list.isEmpty())
            lanzarToast("No hay datos");
        RecoleccionAdapter adapter = new RecoleccionAdapter(this,0,list);
        lvRecoleccion.setAdapter(adapter);
    }
    public void Crear(View v){
        Intent prueba = new Intent(this,CrearRecoleccion.class);
        startActivity(prueba);

    }
    @Override
    public void onResume() {
        if(!this.rutSeleccionado.isEmpty())
        listarRecolecccion(this.rutSeleccionado);
        super.onResume();

    }
    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }


}