package sv.edu.udb.www.beans;

import java.sql.Date;

public class BitacoraBeans {
    private int idBitacora;
    private String idCasoBitacora;
    private String descripcionBitacora;
    private Date fechaBitacora;
    private String idUsuarioBitacora;

    public BitacoraBeans() {
        this.idBitacora = 0;
        this.idCasoBitacora = "";
        this.descripcionBitacora = "";
        this.fechaBitacora = null;
        this.idUsuarioBitacora = "";
    }

    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getIdCasoBitacora() {
        return idCasoBitacora;
    }

    public void setIdCasoBitacora(String idCasoBitacora) {
        this.idCasoBitacora = idCasoBitacora;
    }

    public String getDescripcionBitacora() {
        return descripcionBitacora;
    }

    public void setDescripcionBitacora(String descripcionBitacora) {
        this.descripcionBitacora = descripcionBitacora;
    }

    public Date getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Date fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    public String getIdUsuarioBitacora() {
        return idUsuarioBitacora;
    }

    public void setIdUsuarioBitacora(String idUsuarioBitacora) {
        this.idUsuarioBitacora = idUsuarioBitacora;
    }
}
