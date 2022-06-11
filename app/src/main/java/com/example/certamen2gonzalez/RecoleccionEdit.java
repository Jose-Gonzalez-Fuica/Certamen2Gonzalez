package com.example.certamen2gonzalez;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
    RecoleccionModel recolecccion;

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
            this.recolecccion = (RecoleccionModel) getIntent().getSerializableExtra("recoleccion");
            etFechaRecoleccion.setText(recolecccion.getFecha());
            txtComentarioRecoleccion.setText(recolecccion.getComentario());
            Bitmap image = BitmapFactory.decodeByteArray(recolecccion.getFotoLugar(), 0, recolecccion.getFotoLugar().length);
            ivFotoRecoleccion.setImageBitmap(image);
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
            int positionc = adapter2.getPosition(this.recolecccion.getRutCientifico());
            int positionp = adapter.getPosition(this.recolecccion.getCodigoPlanta());
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
}
