package com.example.certamen2gonzalez;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import BD.BDGonzalez;
import Models.CientificoModel;
import Models.DatePickerFragment;
import Models.PlantaModel;
import Models.RecoleccionModel;

public class RecoleccionEdit extends AppCompatActivity {

    final static int cons = 0;
    EditText etFechaRecoleccion, txtComentarioRecoleccion;
    BDGonzalez bd;
    Spinner spCodigoPlanta, spRutCientifico;
    Bitmap bmp1;
    ImageView ivFotoRecoleccion;
    RecoleccionModel recoleccion;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoleccion_edit);
        etFechaRecoleccion = (EditText) findViewById(R.id.etFechaRecoleccionEdit);
        txtComentarioRecoleccion = (EditText) findViewById(R.id.txtComentarioRecoleccionEdit);
        ivFotoRecoleccion = (ImageView) findViewById(R.id.ivFotoRecoleccionEdit);
        spCodigoPlanta = (Spinner) findViewById(R.id.spCodigoPlantaEdit);
        spRutCientifico = (Spinner) findViewById(R.id.spRutCientificoEdit);
        etFechaRecoleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.etFechaRecoleccion:
                        showDatePickerDialog();
                        break;
                }
            }
        });
        this.bd = new BDGonzalez(this);
        if (getIntent().getExtras() != null) {
            this.recoleccion = (RecoleccionModel) getIntent().getSerializableExtra("recoleccion");
            etFechaRecoleccion.setText(recoleccion.getFecha());
            txtComentarioRecoleccion.setText(recoleccion.getComentario());
            Bitmap image = BitmapFactory.decodeByteArray(recoleccion.getFotoLugar(), 0, recoleccion.getFotoLugar().length);
            ivFotoRecoleccion.setImageBitmap(image);
            this.bmp1=image;
            ArrayList<PlantaModel> listaPlantas = this.bd.getPlantasSql();
            ArrayList<String> listaPlantasString = new ArrayList<String>();
            ArrayList<CientificoModel> listaCientificos = this.bd.getCientificosSql();
            ArrayList<String> listaCientificosRuts = new ArrayList<String>();
            listaPlantas.forEach(plantaModel -> listaPlantasString.add(plantaModel.getCodigoPlanta()));
            listaCientificos.forEach(CientificoModel -> listaCientificosRuts.add(CientificoModel.getRut()));

            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_spinner, listaPlantasString);
            ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.simple_spinner, listaCientificosRuts);
            spRutCientifico.setAdapter(adapter2);
            spCodigoPlanta.setAdapter(adapter);
            int positionc = adapter2.getPosition(this.recoleccion.getRutCientifico());
            int positionp = adapter.getPosition(this.recoleccion.getCodigoPlanta());
            spCodigoPlanta.setSelection(positionp);
            spRutCientifico.setSelection(positionc);
        }
    }
    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etFechaRecoleccion.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
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
            ivFotoRecoleccion.setImageBitmap(bmp1);
        }
    }
    public void Editar(View v){
        String fecha="";
        String comentario="";
        String codigoPlanta;
        String rutCientifico;
        String error="";
        codigoPlanta = spCodigoPlanta.getSelectedItem().toString();
        rutCientifico = spRutCientifico.getSelectedItem().toString();
        byte [] byteArray = new byte[0];
        if(bmp1==null)
            error="Debe tomar foto primero";
        else {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp1.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
        if(etFechaRecoleccion.getText().toString().isEmpty())
            error+="Debe Seleccionar fecha\n";
        else
            fecha = etFechaRecoleccion.getText().toString();
        if(txtComentarioRecoleccion.getText().toString().isEmpty())
            error+="Debe ingresar comentario\n";
        else
            comentario = txtComentarioRecoleccion.getText().toString();
        if(!error.isEmpty())
            lanzarToast(error);
        else
        {
            RecoleccionModel recoleccion = new RecoleccionModel(this.recoleccion.getId(),fecha,codigoPlanta,rutCientifico,comentario,byteArray,0,0);
            this.bd.updateRecoleccionSql(recoleccion);
            finish();
        }
    }

    public void Borrar(){
        int id = this.recoleccion.getId();
        try {
            this.bd.deleteRecoleccionSql(id);
            lanzarToast("recolecccion borrado codigo: " + this.recoleccion.getFecha());
            finish();
        }catch (Exception e)
        {
            lanzarToast("error en borrado");
        }
    }
    public void dialogo_doble(View view){
        new AlertDialog.Builder(this)
                .setTitle("Estas seguro de eliminar?")
                .setMessage("Usted desea eliminar a " + this.recoleccion.getFecha())

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

}
