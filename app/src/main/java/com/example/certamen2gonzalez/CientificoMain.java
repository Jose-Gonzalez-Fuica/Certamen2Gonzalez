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
import BD.BDGonzalez;
import Models.CientificoModel;

public class CientificoMain extends AppCompatActivity {

    ListView ListCientificos;

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
        listarCientificos();
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


}

