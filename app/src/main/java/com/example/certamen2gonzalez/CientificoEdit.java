package com.example.certamen2gonzalez;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import BD.BDGonzalez;
import Models.CientificoModel;

//  Jose Esteban Gonzalez Fuica 18800804-6

public class CientificoEdit extends AppCompatActivity {
    EditText txtRutCientifico;
    EditText txtNombre;
    EditText txtApellidoPaterno;
    EditText txtApellidoMaterno;
    RadioGroup rgGenero;
    RadioButton rdMasculino;
    RadioButton rdFemenino;
    Button btnEditarCientifico;
    BDGonzalez bd;
    CientificoModel cient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cientifico_edit);
        txtRutCientifico = findViewById(R.id.txtRutCientificoEdit);
        txtNombre = findViewById(R.id.txtNombreEdit);
        txtApellidoPaterno=findViewById(R.id.txtApellidoPaternoEdit);
        txtApellidoMaterno=findViewById(R.id.txtApellidoMaternoEdit);
        rgGenero = findViewById(R.id.rgGeneroEdit);
        rdMasculino=findViewById(R.id.rdMasculinoEdit);
        rdFemenino=findViewById(R.id.rdFemeninoEdit);
        btnEditarCientifico=findViewById(R.id.btnEditarCientifico);
        this.bd = new BDGonzalez(this);
        if(getIntent().getExtras() != null) {
            this.cient = (CientificoModel) getIntent().getSerializableExtra("cientifico");
            txtRutCientifico.setText(cient.getRut());
            txtNombre.setText(cient.getNombre());
            txtApellidoMaterno.setText(cient.getApellidoMaterno());
            txtApellidoPaterno.setText(cient.getApellidoPaterno());
            if(cient.getSexo().equals("Masculino"))
                rdMasculino.setChecked(true);
            else
                if(cient.getSexo().equals("Femenino"))
                rdFemenino.setChecked(true);
        }

    }
    public void modificar(View v) {
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
            CientificoModel cientifico = new CientificoModel(this.cient.getId(),rut,nombre,apPaterno,apMaterno,sexo);

            this.bd.updateCientificoSql(cientifico);
            lanzarToast("cientifico actualizado rut :" + this.cient.getRut());
            finish();

        }

    }
    public void lanzarToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }
    public void Borrar(){
        String rut = this.cient.getRut();

        try {
            boolean check=this.bd.checkRecoleccionCientifico(rut);
            if(!check)
            {
                this.bd.deleteCientificoSql(rut);
                lanzarToast("cientifico borrado rut: " + this.cient.getRut());
                finish();
            }
            else{
                lanzarToast("cientifico tiene asociado recolecciones");
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
                .setMessage("Usted desea eliminar a " + this.cient.getRut())

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