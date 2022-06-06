package com.example.certamen2gonzalez;
//Jose Esteban Gonzalez Fuica 18.800.804-6
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import Models.CientificoModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void test(View v)
    {
        Intent prueba = new Intent(this,CientificoMain.class);
        startActivity(prueba);

    }
}