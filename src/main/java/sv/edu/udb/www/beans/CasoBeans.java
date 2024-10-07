package sv.edu.udb.www.beans;

import java.sql.Date;

public class CasoBeans {
    private String idCasos;
    private Date fechaRegistro;
    private Date fechaInicio;
    private Date fechaVencimiento;
    private Date fechaProduccion;
    private int idEstadoCaso;
    private EstadoBeans estadoBeans;
    private String infoRechazo;
    private String observacionesCaso;
    private String detallesCaso;
    private String pdfCaso;
    private float progresoCaso;
    private String idUsuarioCaso;
    private String idUsuarioProgramador;
    private String idUsuarioTester;

    private UsuarioBeans usuarioBeans;

    public CasoBeans() {
        this.idCasos = "";
        this.fechaRegistro = null;
        this.fechaInicio = null;
        this.fechaVencimiento = null;
        this.fechaProduccion = null;
        this.idEstadoCaso = 0;
        this.estadoBeans = null;
        this.infoRechazo = "";
        this.observacionesCaso = "";
        this.detallesCaso = "";
        this.pdfCaso = "";
        this.progresoCaso = 0;
        this.idUsuarioCaso = "";
        this.idUsuarioProgramador = "";
        this.idUsuarioTester = "";
        this.usuarioBeans = null;
    }

    public String getIdCasos() {
        return idCasos;
    }

    public void setIdCasos(String idCasos) {
        this.idCasos = idCasos;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaProduccion() {
        return fechaProduccion;
    }

    public void setFechaProduccion(Date fechaProduccion) {
        this.fechaProduccion = fechaProduccion;
    }

    public int getIdEstadoCaso() {
        return idEstadoCaso;
    }

    public void setIdEstadoCaso(int idEstadoCaso) {
        this.idEstadoCaso = idEstadoCaso;
    }

    public EstadoBeans getEstadoBeans() {
        return estadoBeans;
    }

    public void setEstadoBeans(EstadoBeans estadoBeans) {
        this.estadoBeans = estadoBeans;
    }

    public String getInfoRechazo() {
        return infoRechazo;
    }

    public void setInfoRechazo(String infoRechazo) {
        this.infoRechazo = infoRechazo;
    }

    public String getObservacionesCaso() {
        return observacionesCaso;
    }

    public void setObservacionesCaso(String observacionesCaso) {
        this.observacionesCaso = observacionesCaso;
    }

    public String getDetallesCaso() {
        return detallesCaso;
    }

    public void setDetallesCaso(String detallesCaso) {
        this.detallesCaso = detallesCaso;
    }

    public String getPdfCaso() {
        return pdfCaso;
    }

    public void setPdfCaso(String pdfCaso) {
        this.pdfCaso = pdfCaso;
    }

    public float getProgresoCaso() {
        return progresoCaso;
    }

    public void setProgresoCaso(float progresoCaso) {
        this.progresoCaso = progresoCaso;
    }

    public String getIdUsuarioCaso() {
        return idUsuarioCaso;
    }

    public void setIdUsuarioCaso(String idUsuarioCaso) {
        this.idUsuarioCaso = idUsuarioCaso;
    }

    public String getIdUsuarioProgramador() {
        return idUsuarioProgramador;
    }

    public void setIdUsuarioProgramador(String idUsuarioProgramador) {
        this.idUsuarioProgramador = idUsuarioProgramador;
    }

    public String getIdUsuarioTester() {
        return idUsuarioTester;
    }

    public void setIdUsuarioTester(String idUsuarioTester) {
        this.idUsuarioTester = idUsuarioTester;
    }

    public UsuarioBeans getUsuarioBeans() {
        return usuarioBeans;
    }

    public void setUsuarioBeans(UsuarioBeans usuarioBeans) {
        this.usuarioBeans = usuarioBeans;
    }
}
