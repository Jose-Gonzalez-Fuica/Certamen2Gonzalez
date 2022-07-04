package com.example.certamen2gonzalez;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

import Adapter.CientificoAdapter;
import BD.BDGonzalez;
import BD.BDRemota;
import Models.CientificoModel;
import Models.PlantaModel;
import Models.RecoleccionModel;

//  Jose Esteban Gonzalez Fuica 18800804-6
public class CientificoMain extends AppCompatActivity {

    ListView ListCientificos;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cientifico_main);
        ListCientificos = findViewById(R.id.ListCientificos);
        ListCientificos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                CientificoModel value = (CientificoModel) adapter.getItemAtPosition(position);
                Intent prueba = new Intent(getBaseContext(),CientificoEdit.class);
                prueba.putExtra("cientifico",value);
                startActivity(prueba);
               // listarCientificos();

            }
        });
       // listarCientificos();
        BDRemota test = new BDRemota();
        ArrayList<PlantaModel> test2 = test.recibirdatosPlanta();
        ArrayList<CientificoModel> test3 = test.recibirdatosCientifico();
        ArrayList<RecoleccionModel> test4= test.recibirdatosRecoleccion();
        System.out.println(test2);
    }

    public void listarCientificos()
    {
        BDGonzalez bd = new BDGonzalez(this);
        ArrayList<CientificoModel> list = new ArrayList<CientificoModel>();
        list = bd.getCientificosSql();
        if(list.isEmpty()){
            lanzarToast("No hay datos");
        }
        else
        {
            CientificoAdapter adapter = new CientificoAdapter(this,0,list);
            ListCientificos.setAdapter(adapter);
        }
    }
    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
    public void Crear(View v)
    {
        Intent prueba = new Intent(this,CrearCientifico.class);
        startActivity(prueba);

    }
    @Override
    public void onResume() {

        listarCientificos();
        super.onResume();

    }


}

