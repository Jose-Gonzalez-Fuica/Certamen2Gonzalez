package com.example.certamen2gonzalez;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import BD.BDGonzalez;
import Models.CientificoModel;

public class CrearCientifico extends AppCompatActivity {
    EditText txtRutCientifico;
    EditText txtNombre;
    EditText txtApellidoPaterno;
    EditText txtApellidoMaterno;
    RadioGroup rgGenero;
    RadioButton rdMasculino;
    RadioButton rdFemenino;
    Button btnCrearCientifico;
    BDGonzalez bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cientifico);
        txtRutCientifico = findViewById(R.id.txtRutCientifico);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellidoPaterno=findViewById(R.id.txtApellidoPaterno);
        txtApellidoMaterno=findViewById(R.id.txtApellidoMaterno);
        rgGenero = findViewById(R.id.rgGenero);
        rdMasculino=findViewById(R.id.rdMasculino);
        rdFemenino=findViewById(R.id.rdFemenino);
        btnCrearCientifico=findViewById(R.id.btnCrearCientifico);
        this.bd = new BDGonzalez(this);
        List<CientificoModel> test = this.bd.getCientificosSql();
        String asd="";
    }

    @SuppressLint("NotConstructor")
    public void CrearCientifico(View v){
        String rut = txtRutCientifico.getText().toString();
        String nombre = txtNombre.getText().toString();
        String apMaterno = txtApellidoMaterno.getText().toString();
        String apPaterno = txtApellidoPaterno.getText().toString();
        String sexo ="";
        if(rdMasculino.isChecked())
            sexo="Masculino";
        if(rdFemenino.isChecked())
            sexo="Femenino";
        String error="";
        if(rut.equals(""))
            error+="Debe ingresar rut\n";
        if(nombre.equals(""))
            error+="Debe ingresar nombre\n";
        if(apMaterno.equals(""))
            error+="Debe ingresar apMaterno\n";
        if(apPaterno.equals(""))
            error+="Debe ingresar apPaterno\n";
        if(sexo.equals(""))
            error+="Debe selecionar sexo\n";
        if(!error.equals(""))
            lanzarToast(error);
        else
        {
            CientificoModel cientifico = new CientificoModel(15,rut,nombre,apPaterno,apMaterno,sexo);

            this.bd.insertarCientificoSql(rut,nombre,apPaterno,apMaterno,sexo);
            finish();
        }
    }

    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
}