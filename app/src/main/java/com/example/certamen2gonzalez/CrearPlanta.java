package com.example.certamen2gonzalez;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import BD.BDGonzalez;
import Models.CientificoModel;
import Models.PlantaModel;

public class CrearPlanta extends AppCompatActivity {
    final static int cons=0;
    ImageView ivFotoPlanta;
    Bitmap bmp1;
    EditText txtCodigoPlanta;
    EditText txtNombrePlanta;
    EditText txtNombreCientificoPlanta;
    EditText txtUsoPlanta;
    BDGonzalez bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_planta);
        ivFotoPlanta= (ImageView) findViewById(R.id.ivFotoPlanta);
        txtCodigoPlanta = (EditText) findViewById(R.id.txtCodigoPlanta);
        txtNombrePlanta = (EditText) findViewById(R.id.txtNombrePlanta);
        txtNombreCientificoPlanta = (EditText) findViewById(R.id.txtNombreCientificoPlanta);
        txtUsoPlanta = (EditText) findViewById(R.id.txtUsoPlanta);
        this.bd = new BDGonzalez(this);
    }
    public boolean TomarFoto(View v) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, cons);
        return false;
    }

    protected void onActivityResult(int requesCode, int resultCode, Intent data) {
        super.onActivityResult(requesCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK)
        {
            Bundle ext=data.getExtras();
            bmp1=(Bitmap)ext.get("data");
            ivFotoPlanta.setImageBitmap(bmp1);
        }
    }
    @SuppressLint("NotConstructor")
    public void CrearPlanta(View v){
        String codigo = txtCodigoPlanta.getText().toString();
        String nombre = txtNombrePlanta.getText().toString();
        String nombrecientificoplanta = txtNombreCientificoPlanta.getText().toString();
        String uso = txtUsoPlanta.getText().toString();
        String error="";
        byte [] byteArray = new byte[0];
        if(bmp1==null)
            error="Debe tomar foto primero";
        else {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp1.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
       
        if(codigo.equals(""))
            error+="Debe ingresar codigo\n";
        if(nombre.equals(""))
            error+="Debe ingresar nombre\n";
        if(nombrecientificoplanta.equals(""))
            error+="Debe ingresar nombre cientifico de planta\n";
        if(uso.equals(""))
            error+="Debe ingresar uso\n";
        if(!error.equals(""))
            lanzarToast(error);
        else
        {
            PlantaModel planta = new PlantaModel(15,codigo,nombre,nombrecientificoplanta,byteArray,uso);
            this.bd.insertarPlantaSql(codigo,nombre,nombrecientificoplanta,byteArray,uso);
           finish();
        }

    }
    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
}