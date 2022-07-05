package com.example.certamen2gonzalez;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import BD.BDRemota;
import Models.CientificoModel;
import Models.DatePickerFragment;
import Models.PlantaModel;
import Models.RecoleccionModel;
//  Jose Esteban Gonzalez Fuica 18800804-6
public class CrearRecoleccion extends AppCompatActivity {
    final static int cons=0;
    EditText etFechaRecoleccion,txtComentarioRecoleccion;
    BDGonzalez bd;
    Spinner spCodigoPlanta,spRutCientifico;
    Bitmap bmp1;
    ImageView ivFotoRecoleccion;
    Location location;
    LocationManager locationManager;
    Double latitud;
    Double longitud;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_recoleccion);
        etFechaRecoleccion = (EditText) findViewById(R.id.etFechaRecoleccion);
        txtComentarioRecoleccion = (EditText) findViewById(R.id.txtComentarioRecoleccion);
        ivFotoRecoleccion = (ImageView) findViewById(R.id.ivFotoRecoleccion);
        spCodigoPlanta = (Spinner) findViewById(R.id.spCodigoPlanta);
        spRutCientifico = (Spinner) findViewById(R.id.spRutCientifico);
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
        ArrayList<PlantaModel> listaPlantas = this.bd.getPlantasSql();
        ArrayList<String> listaPlantasString = new ArrayList<String>();
        ArrayList<CientificoModel> listaCientificos= this.bd.getCientificosSql();
        ArrayList<String> listaCientificosRuts = new ArrayList<String>();
        if(listaPlantas.size()==0){
            lanzarToast("Error debe ingresar planta primero");
            finish();
        }
        if(listaCientificos.size()==0){
            lanzarToast("Error debe ingresar Cientifico primero");
            finish();
        }
        listaPlantas.forEach(plantaModel -> listaPlantasString.add(plantaModel.getCodigoPlanta()));
        listaCientificos.forEach(CientificoModel -> listaCientificosRuts.add(CientificoModel.getRut()));

        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner,listaPlantasString);
        ArrayAdapter adapter2 = new ArrayAdapter(this,R.layout.simple_spinner,listaCientificosRuts);
        spRutCientifico.setAdapter(adapter2);
        spRutCientifico.setSelection(0);
        spCodigoPlanta.setAdapter(adapter);
        spCodigoPlanta.setSelection(0);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))    // chequea si está activo gps
            AlertGPS();

        if (ContextCompat.checkSelfPermission(CrearRecoleccion.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(CrearRecoleccion.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CrearRecoleccion.this, new String[]
                    {
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        };

        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String latitud1 =String.valueOf(location.getLatitude());
                String longitud1 = String.valueOf(location.getLongitude());
                latitud = Double.parseDouble(latitud1);
                longitud=Double.parseDouble(longitud1);
                Lanzar
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                LocationListener.super.onProviderDisabled(provider);
            }
        });

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


    public void Crear(View v){
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
            RecoleccionModel recoleccion = new RecoleccionModel(15,fecha,codigoPlanta,rutCientifico,comentario,byteArray,0,0);
            this.bd.insertarRecoleccionSql(fecha,codigoPlanta,rutCientifico,comentario,byteArray,this.latitud,this.longitud);
            BDRemota test = new BDRemota();
            test.PostRecoleccion(recoleccion);
            finish();
        }
    }
    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
    public void AlertGPS(){
        new AlertDialog.Builder(this)
                .setTitle("Activar GPS")
                .setMessage("El GPS esta desactivado ¿Desea Activarlo?")
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                //dialog.cancel();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

}