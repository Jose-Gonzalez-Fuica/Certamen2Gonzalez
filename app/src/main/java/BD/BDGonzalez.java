package BD;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.certamen2gonzalez.CientificoEdit;

import java.util.ArrayList;

import Models.CientificoModel;
import Models.PlantaModel;
import Models.RecoleccionModel;


public class BDGonzalez extends SQLiteOpenHelper {
    public BDGonzalez( Context context)
    {
        super(context, "BDGonzalez", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Rut, nombres, apellidos, sexo
        sqLiteDatabase.execSQL("CREATE TABLE CientificoGonzalez" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, rut TEXT unique,nombre TEXT , apellidoPaterno TEXT," +
                "apellidoMaterno TEXT, sexo TEXT)");
        //código planta, nombre de la planta, nombre científico de la planta, foto de la planta y
        // para qué sirvecódigo planta, nombre de la planta, nombre científico de la planta,
        // foto de la planta y para qué sirve
        sqLiteDatabase.execSQL("CREATE TABLE PlantaGonzalez" +
        "(id INTEGER PRIMARY KEY AUTOINCREMENT, codigoPlanta TEXT, nombrePlanta TEXT, nombreCientifico TEXT, " +
                "fotoPlanta BLOB, uso TEXT )");

        //identificador, fecha registro, código de la planta, rut del científico,
        // comentario, foto del lugar, localización (latitud y longitud) del lugar.
        sqLiteDatabase.execSQL("CREATE TABLE RecoleccionGonzalez" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, fecha TEXT, codigoPlanta TEXT, " +
                "rutCientifico TEXT, comentario TEXT, fotoLugar BLOB,latitud REAL,longitud REAL )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  CientificoGonzalez");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  PlantaGonzalez");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  RecoleccionGonzalez");
        onCreate(sqLiteDatabase);
    }
    //insert
    //.........................................
    public boolean insertarCientificoSql(String rut,String nombre, String apellidoPaterno, String apellidoMaterno, String sexo) {

        boolean sw1=true;

        SQLiteDatabase db = getWritableDatabase();//ABRE BD EN MODO ESCRITURA
        if(db != null){//PREGUNTO SI LA BASE EXISTE

            try{

                db.execSQL("INSERT INTO CientificoGonzalez (rut,nombre,apellidoPaterno,apellidoMaterno,sexo) VALUES("+"'" + rut + "','" + nombre + "','"+  apellidoPaterno + "','" + apellidoMaterno + "','"+sexo+"')") ;//INSERTA REGISTROS
                db.close();

            }catch(Exception e){
                db.close();
                sw1=false;
            }
        }
        else{
            sw1=false;
        }
        return sw1;

    }
    public boolean insertarPlantaSql(String codigoPlanta, String nombrePlanta, String nombreCientifico, byte[] fotoPlanta, String uso) {

        boolean sw1=true;
        SQLiteDatabase db = getWritableDatabase(); //ABRE BD EN MODO ESCRITURA
        if(db != null){//PREGUNTO SI LA BASE EXISTE

            ContentValues valores = new ContentValues();
            valores.put("codigoPlanta", codigoPlanta);
            valores.put("nombrePlanta", nombrePlanta);
            valores.put("nombreCientifico", nombreCientifico);
            valores.put("fotoPlanta", fotoPlanta);
            valores.put("uso", uso);
            try {
                db.insert("PlantaGonzalez", "fotoPlanta", valores);
                db.close();
            } catch (Exception e) {
                db.close();
                sw1 = false;
            }

        }
        else{
            sw1=false;
        }
        return sw1;

    }
    public boolean insertarRecoleccionSql(String fecha,String codigoPlanta,String rutCientifico,String comentario,byte[] fotoLugar,double latitud,double longitud){
        boolean sw1=true;
        SQLiteDatabase db = getWritableDatabase(); //ABRE BD EN MODO ESCRITURA
        if(db != null){//PREGUNTO SI LA BASE EXISTE

            ContentValues valores = new ContentValues();
            valores.put("fecha", fecha);
            valores.put("codigoPlanta", codigoPlanta);
            valores.put("rutCientifico", rutCientifico);
            valores.put("comentario", comentario);
            valores.put("fotoLugar", fotoLugar);
            valores.put("latitud", 0);
            valores.put("longitud", 0);
            try {
                db.insert("RecoleccionGonzalez", "fotoLugar", valores);
                db.close();
            } catch (Exception e) {
                db.close();
                sw1 = false;
            }
        }
        else{
            sw1=false;
        }
        return sw1;
    }
    //..................................................

    //Delete
    //.........................................
    public boolean deleteCientificoSql(String rut){
        boolean delete = true;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            String query = "DELETE FROM CientificoGonzalez WHERE rut = '" + rut + "'";
            try {
                db.execSQL(query);
                db.close();
            } catch (Exception e) {
                db.close();
                delete = false;
            }
        }
        else {
            onCreate(db);
        }
        return delete;
    }
    public boolean deletePlantasSql(int id){
        boolean delete = true;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            String query = "DELETE FROM PlantaGonzalez WHERE id = " + id + "";
            try {
                db.execSQL(query);
                db.close();
            } catch (Exception e) {
                db.close();
                delete = false;
            }
        }
        else {
            onCreate(db);
        }
        return delete;
    }
    public boolean deleteRecoleccionSql(int id){
        boolean delete = true;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            String query = "DELETE FROM RecoleccionGonzalez WHERE id = " + id + "";
            try {
                db.execSQL(query);
                db.close();
            } catch (Exception e) {
                db.close();
                delete = false;
            }
        }
        else {
            onCreate(db);
        }
        return delete;
    }
    //..................................................
    //update
    //..................................................
    public boolean updateCientificoSql(CientificoModel cientifico){
        boolean update = true;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {

            String query = "UPDATE CientificoGonzalez " +
                    "SET rut = '" + cientifico.getRut() + "', nombre = '" + cientifico.getNombre() + "'," +
                    "apellidoPaterno = '" + cientifico.getApellidoPaterno() + "', apellidoMaterno = '" + cientifico.getApellidoMaterno() +
                    "',  sexo = '" + cientifico.getSexo() + "' " +
                    "WHERE id = " +  cientifico.getId() + "";
            try {
                db.execSQL(query);
                db.close();

            } catch (Exception e) {
                db.close();
                update = false;

            }
        } else {
            onCreate(db);
        }
        return update;
    }
    public boolean updatePantaSql(PlantaModel planta) {
        boolean update = true;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            valores.put("codigoPlanta", planta.getCodigoPlanta());
            valores.put("nombrePlanta", planta.getNombrePlanta());
            valores.put("nombreCientifico", planta.getNombreCientifico());
            valores.put("fotoPlanta", planta.getFotoPlanta());
            valores.put("uso",  planta.getUso());
            try {
                db.update("PlantaGonzalez",valores,"id = "+planta.getId(),null);
                db.close();
            } catch (Exception e) {
                db.close();
                update = false;
            }
        } else {
            onCreate(db);
        }
        return update;
    }
    public boolean updateRecoleccionSql(RecoleccionModel recoleccion) {
        boolean update = true;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {

            String query = "UPDATE RecoleccionGonzalez " +
                    "SET fecha = '" + recoleccion.getFecha() + "', codigoPlanta = '" + recoleccion.getCodigoPlanta() + "', " +
                    "rutCientifico = '" + recoleccion.getRutCientifico() + "', comentario = '" + recoleccion.getComentario() + "',  fotoLugar = '" + recoleccion.getFotoLugar() + "', " +
                    "latitud = " + recoleccion.getLatitud() + ", longitud = " + recoleccion.getLongitud() + " " +
                    "WHERE id = " +  recoleccion.getId() + "";
            try {
                db.execSQL(query);
                db.close();
            } catch (Exception e) {
                db.close();
                update = false;
            }
        } else {
            onCreate(db);
        }
        return update;
    }
    //..................................................
    //list
    //..................................................
    public ArrayList<CientificoModel> getCientificosSql(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CientificoModel> cientificos = new ArrayList<CientificoModel>();
        CientificoModel cientifico;
        try {
            Cursor c = db.rawQuery("SELECT * FROM CientificoGonzalez", null);

            if (c.moveToFirst()) {
                do {
                    cientifico = new CientificoModel(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4), c.getString(5));
                    cientificos.add(cientifico);
                }
                while(c.moveToNext());
                this.close();
                c.close();

                return cientificos;
            } else {
                this.close();
                c.close();

                return cientificos;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public ArrayList<PlantaModel> getPlantasSql(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<PlantaModel> plantas = new ArrayList<PlantaModel>();
        PlantaModel planta;
        try {
            Cursor c = db.rawQuery("SELECT * FROM PlantaGonzalez", null);

            if (c.moveToFirst()) {
                do {
                    planta = new PlantaModel(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getBlob(4), c.getString(5));
                    plantas.add(planta);
                }
                while(c.moveToNext());
                this.close();
                c.close();

                return plantas;
            } else {
                this.close();
                c.close();

                return plantas;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public ArrayList<RecoleccionModel> getRecoleccionSql(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecoleccionModel> recolecciones = new ArrayList<RecoleccionModel>();
        RecoleccionModel recoleccion;
        try {
            Cursor c = db.rawQuery("SELECT * FROM RecoleccionGonzalez", null);

            if (c.moveToFirst()) {
                do {
                    recoleccion = new RecoleccionModel(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4),c.getBlob(5),c.getInt(6),c.getInt(7));
                    recolecciones.add(recoleccion);
                }
                while(c.moveToNext());
                this.close();
                c.close();

                return recolecciones;
            } else {
                this.close();
                c.close();

                return recolecciones;
            }
        } catch (Exception e) {
            return null;
        }
    }
    //..................................................
    //extras
    //..................................................
    public boolean checkRecoleccionCientifico(String rut) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CientificoModel> cientificos = new ArrayList<CientificoModel>();
        CientificoModel cientifico;
        try {
            Cursor c = db.rawQuery("SELECT * FROM RecoleccionGonzalez WHERE rutCientifico = '"+rut+"'", null);

            if (c.moveToFirst()) {
                this.close();
                c.close();
                return true;
            } else {
                this.close();
                c.close();

                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public boolean checkRecoleccionPlanta(int codigo) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<PlantaModel> plantas = new ArrayList<PlantaModel>();
        PlantaModel planta;
        try {
            Cursor c = db.rawQuery("SELECT * FROM RecoleccionGonzalez WHERE id = '"+codigo+"'", null);

            if (c.moveToFirst()) {
                this.close();
                c.close();
                return true;
            } else {
                this.close();
                c.close();

                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}

