package Models;

public class PlantaModel {
    private int id;
    private String codigoPlanta;
    private String nombrePlanta;
    private String nombreCientifico;
    private byte[] fotoPlanta;
    private String uso;

    public PlantaModel() {

    }

    public PlantaModel(int id, String codigoPlanta, String nombrePlanta, String nombreCientifico, byte[] fotoPlanta, String uso) {
        this.id = id;
        this.codigoPlanta = codigoPlanta;
        this.nombrePlanta = nombrePlanta;
        this.nombreCientifico = nombreCientifico;
        this.fotoPlanta = fotoPlanta;
        this.uso = uso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoPlanta() {
        return codigoPlanta;
    }

    public void setCodigoPlanta(String codigoPlanta) {
        this.codigoPlanta = codigoPlanta;
    }

    public String getNombrePlanta() {
        return nombrePlanta;
    }

    public void setNombrePlanta(String nombrePlanta) {
        this.nombrePlanta = nombrePlanta;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public byte[] getFotoPlanta() {
        return fotoPlanta;
    }

    public void setFotoPlanta(byte[] fotoPlanta) {
        this.fotoPlanta = fotoPlanta;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }
}
