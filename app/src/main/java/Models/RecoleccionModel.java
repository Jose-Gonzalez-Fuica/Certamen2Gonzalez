package Models;

import java.io.Serializable;

public class RecoleccionModel implements Serializable {

    private int id;
    private String fecha;
    private String codigoPlanta;
    private String rutCientifico;
    private String comentario;
    private byte[] fotoLugar;
    private double latitud;
    private double longitud;

    public RecoleccionModel() {
    }

    public RecoleccionModel(int id, String fecha, String codigoPlanta, String rutCientifico, String comentario, byte[] fotoLugar, double latitud, double longitud) {
        this.id = id;
        this.fecha = fecha;
        this.codigoPlanta = codigoPlanta;
        this.rutCientifico = rutCientifico;
        this.comentario = comentario;
        this.fotoLugar = fotoLugar;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodigoPlanta() {
        return codigoPlanta;
    }

    public void setCodigoPlanta(String codigoPlanta) {
        this.codigoPlanta = codigoPlanta;
    }

    public String getRutCientifico() {
        return rutCientifico;
    }

    public void setRutCientifico(String rutCientifico) {
        this.rutCientifico = rutCientifico;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public byte[] getFotoLugar() {
        return fotoLugar;
    }

    public void setFotoLugar(byte[] fotoLugar) {
        this.fotoLugar = fotoLugar;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
