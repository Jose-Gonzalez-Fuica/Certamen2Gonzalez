package BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class BDGonzalez extends SQLiteOpenHelper {
    public BDGonzalez( Context context)
    {
        super(context, "BDGonzalez", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Rut, nombres, apellidos, sexo
        sqLiteDatabase.execSQL("CREATE TABLE CientificoGonzalez" +
                "(id INT PRIMARY KEY AUTOINCREMENT, rut TEXT unique, apelllidoPaterno TEXT," +
                "apellidoMaterno TEXT, sexo TEXT)");
        //código planta, nombre de la planta, nombre científico de la planta, foto de la planta y
        // para qué sirvecódigo planta, nombre de la planta, nombre científico de la planta,
        // foto de la planta y para qué sirve
        sqLiteDatabase.execSQL("CREATE TABLE PlantaGonzalez" +
        "(id INT PRIMARY KEY AUTOINCREMENT, codigoPlanta TEXT, nombrePlanta TEXT, nombreCientifico TEXT, " +
                "fotoPlanta BLOB, uso TEXT )");

        //identificador, fecha registro, código de la planta, rut del científico,
        // comentario, foto del lugar, localización (latitud y longitud) del lugar.
        sqLiteDatabase.execSQL("CREATE TABLE RecolecciónGonzalez" +
                "(id INT PRIMARY KEY AUTOINCREMENT, fecha TEXT, codigoPlanta TEXT, " +
                "rutCientifico TEXT, comentario TEXT, fotoLugar BLOB,latitud REAL,longitud REAL )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  CientificoGonzalez");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  PlantaGonzalez");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  RecolecciónGonzalez");
        onCreate(sqLiteDatabase);
    }
    //insert
    //.........................................
    public boolean insertarCientificoSql(String rut, String apellidoPaterno, String apellidoMaterno, String sexo) {

        boolean sw1=true;
        SQLiteDatabase db = getWritableDatabase(); //ABRE BD EN MODO ESCRITURA
        if(db != null){//PREGUNTO SI LA BASE EXISTE

            try{

                db.execSQL("INSERT INTO CientificoGonzalez VALUES("+"'" + rut + "','" + apellidoPaterno + "','" + apellidoMaterno + "','"+sexo+"')") ;//INSERTA REGISTROS
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

            try{

                db.execSQL("INSERT INTO PlantaGonzalez VALUES("+"'" + codigoPlanta + "','" + nombrePlanta + "','" + nombreCientifico + "',+fotoPlanta+,'"+uso+"')") ;//INSERTA REGISTROS
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
    public boolean insertarRecoleccionSql(String fecha,String codigoPlanta,String rutCientifico,String comentario,byte[] fotoLugar,double latitud,double longitud)
    {
        boolean sw1=true;
        SQLiteDatabase db = getWritableDatabase(); //ABRE BD EN MODO ESCRITURA
        if(db != null){//PREGUNTO SI LA BASE EXISTE

            try{

                db.execSQL("INSERT INTO RecolecciónGonzalez VALUES("+"'" + codigoPlanta + "','" + nombrePlanta + "','" + nombreCientifico + "',+fotoPlanta+,'"+uso+"')") ;//INSERTA REGISTROS
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
    //..................................................
}

        /*publicclassClaseBDextendsSQLiteOpenHelper {
        publicClaseBD(@Nullable Contextcontext, @Nullable Stringname, @Nullable SQLiteDatabase.CursorFactoryfactory, intversion) {
        super(context, name, factory, version);
        }

        publicClaseBD(@Nullable Contextcontext, @Nullable Stringname, @Nullable SQLiteDatabase.CursorFactoryfactory, intversion, @Nullable DatabaseErrorHandlererrorHandler) {
        super(context, name, factory, version, errorHandler);
        }

        publicClaseBD(@Nullable Contextcontext, @Nullable Stringname, intversion, @NonNull SQLiteDatabase.OpenParamsopenParams) {
        super(context, name, version, openParams);
        }


        publicClaseBD( Contextcontext)
        {
        super(context, "basecontacto", null, 1);
        }


@Override
publicvoidonCreate(SQLiteDatabasesqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE contactos" +
        "(_id INT PRIMARY KEY, nombre TEXT, telefono INT, email TEXT)");
        //sqLiteDatabase.execSQL("CREATE TABLE Otra" +
        "(id_otra INT PRIMARY KEY, nombre_otra TEXT)"); // se crea otra tabla

        }

@Override
publicvoidonUpgrade(SQLiteDatabasedb, inti, inti1) {
        db.execSQL("DROP TABLE IF EXISTS  contactos");
        onCreate(db);  // llama a método que permite crear de nuevo BD y tablas

        }


//.........................................
        publicbooleaninsertarCONTACTOsql(intid, Stringnom, inttlf, String email) {

        booleansw1=true;
        SQLiteDatabasedb = getWritableDatabase(); //ABRE BD EN MODO ESCRITURA
        if(db != null){//PREGUNTO SI LA BASE EXISTE

        try{

        db.execSQL("INSERT INTO contactos VALUES(" + id + ",'" + nom + "'," + tlf + ",'" + email + "')");//INSERTA REGISTROS
        db.close();

        }catch(Exception e){
        db.close();
        sw1=false;
        }
        }
        else{
        sw1=false;
        }
        returnsw1;

        }
//..................................................


        publicbooleanmodificarCONTACTOsql(intid, Stringnom, inttlf, String email) {

        booleansw1=true;
        SQLiteDatabasedb = getWritableDatabase(); //ABRE BD EN MODO ESCRITURA
        if(db != null){//PREGUNTO SI LA BASE EXISTE

        try{
        db.execSQL("UPDATE Contactos SET nombre='"+nom+"', telefono="+tlf+", email='"+email+"' WHERE _id="+ id);
        db.close();
        }catch(Exception e){
        db.close();
        sw1=false;
        }
        }
        else{
        sw1=false;
        }
        returnsw1;

        }
//........................................................

        publicbooleaneliminarCONTACTOsql(intid) {
// se debe preguntar si existe el registro primero,sino da error en al devoluciÃƒÂ³n
        booleansw1 = true;
        SQLiteDatabasedb = getWritableDatabase(); //ABRE BD EN MODO ESCRITURA
        if(db != null) {//PREGUNTO SI LA BASE EXISTE
        try {
        db.execSQL("DELETE FROM Contactos WHERE _id=" + id);
        db.close();
        } catch (Exception e) {
        db.close();
        sw1 = false;
        }
        }
        else{
        sw1=false;
        }
        returnsw1;
        }
//...................................................

        publicContacto recuperarCONTACTOsql(intid) {

        SQLiteDatabasedb = getReadableDatabase();//ABRIR BASE DE DATOS EN MODO LECTURA

        Cursor c = db.rawQuery("SELECT * FROM Contactos WHERE _id="+id, null);

        if(c.moveToFirst()) {
        Contacto contactos = new Contacto(c.getInt(0), c.getString(1), c.getInt(2), c.getString(3));
        db.close();
        c.close();
        returncontactos;
        }
        else{
        db.close();
        c.close();
        returnnull;
        }
        }

//................................................



        publicList<Contacto>listarContactossql() {
//METODO QUE DEVUELVE UNA LISTA CUYOS ELEMENTOS SON OBJETOS DE LA CLASE Contacto

        SQLiteDatabasedb = getReadableDatabase();//abrir modo lectura
        if(db != null) {//PREGUNTO SI LA BASE EXISTE
        List<Contacto>lista_contactos = new ArrayList<Contacto>();

        Cursor c = db.rawQuery("SELECT * FROM Contactos", null);

        if(c.moveToFirst()) {
// c.moveToFirst();   //mueve puntero del cursor al primer registro

        do {
        Contacto objContacto = new Contacto(c.getInt(0), c.getString(1), c.getInt(2), c.getString(3));

        lista_contactos.add(objContacto);

        } while(c.moveToNext());  //itera mientras hay registro
        db.close();
        c.close();
        returnlista_contactos;
        } else
        returnnull;
        }
        else
        returnnull;
        }

        }

