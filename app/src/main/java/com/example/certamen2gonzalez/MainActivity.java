package com.example.certamen2gonzalez;
//Jose Esteban Gonzalez Fuica 18.800.804-6
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import Models.CientificoModel;
//  Jose Esteban Gonzalez Fuica 18800804-6
public class MainActivity extends AppCompatActivity {

    Fragment datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datos = new Datos();
        getSupportFragmentManager().beginTransaction().add(R.id.frLayout,datos).commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btSideCientificos:
                Intent intent = new Intent(MainActivity.this, CientificoMain.class);
                startActivity(intent);
                return true;

            case R.id.btSidePlantas:
                intent = new Intent(MainActivity.this, PlantaMain.class);
                startActivity(intent);
                return true;
            case R.id.btSideRecolecciones:
                intent = new Intent(MainActivity.this, RecoleccionMain.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}