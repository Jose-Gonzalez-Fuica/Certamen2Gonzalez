package com.example.certamen2gonzalez;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
//  Jose Esteban Gonzalez Fuica 18800804-6
public class PlantaEdit extends AppCompatActivity {

    final static int cons=0;
    ImageView ivFotoPlanta;
    Bitmap bmp1;
    EditText txtCodigoPlanta;
    EditText txtNombrePlanta;
    EditText txtNombreCientificoPlanta;
    EditText txtUsoPlanta;
    BDGonzalez bd;
    PlantaModel planta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta_edit);
        ivFotoPlanta= (ImageView) findViewById(R.id.ivFotoPlantaEdit);
        txtCodigoPlanta = (EditText) findViewById(R.id.txtCodigoPlantaEdit);
        txtNombrePlanta = (EditText) findViewById(R.id.txtNombrePlantaEdit);
        txtNombreCientificoPlanta = (EditText) findViewById(R.id.txtNombreCientificoPlantaEdit);
        txtUsoPlanta = (EditText) findViewById(R.id.txtUsoPlantaEdit);
        this.bd = new BDGonzalez(this);
        if(getIntent().getExtras() != null) {
            this.planta = (PlantaModel) getIntent().getSerializableExtra("planta");
            txtCodigoPlanta.setText(planta.getCodigoPlanta());
            txtNombrePlanta.setText(planta.getNombrePlanta());
            txtNombreCientificoPlanta.setText(planta.getNombreCientifico());
            txtUsoPlanta.setText(planta.getUso());
            Bitmap image = BitmapFactory.decodeByteArray(planta.getFotoPlanta(), 0, planta.getFotoPlanta().length);
            ivFotoPlanta.setImageBitmap(image);
            this.bmp1=image;
        }

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
    public void EditarPlanta(View v){
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
            PlantaModel planta = new PlantaModel(this.planta.getId(),codigo,nombre,nombrecientificoplanta,byteArray,uso);
            this.bd.updatePantaSql(planta);
            finish();
        }

    }
    public void Borrar(){
        String codigo = this.planta.getCodigoPlanta();
        int id = this.planta.getId();
        try {
            boolean check=this.bd.checkRecoleccionPlanta(codigo);
            if(!check)
            {
                this.bd.deletePlantasSql(id);
                lanzarToast("planta borrado codigo: " + this.planta.getCodigoPlanta());
                finish();
            }
            else{
                lanzarToast("planta tiene asociado recolecciones");
                finish();
            }

        }catch (Exception e)
        {
            lanzarToast("error en borrado");
        }
    }
    public void dialogo_doble(View view){
        new AlertDialog.Builder(this)
                .setTitle("Estas seguro de eliminar?")
                .setMessage("Usted desea eliminar a " + this.planta.getCodigoPlanta())

                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                lanzarToast("Se tratara de eliminar");
                                Borrar();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        lanzarToast("Se arrepintió de eliminar");
                        dialog.cancel();
                    }
                }).show();

    }

    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
}