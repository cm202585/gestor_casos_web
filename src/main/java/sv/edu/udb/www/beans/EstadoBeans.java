package sv.edu.udb.www.beans;

public class EstadoBeans {
    private int idEstado;
    private String estadoCaso;

    public EstadoBeans() {
        this.idEstado = 0;
        this.estadoCaso = "";
    }

    public EstadoBeans(String estadoCaso) {
        this.estadoCaso = estadoCaso;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstadoCaso() {
        return estadoCaso;
    }

    public void setEstadoCaso(String estadoCaso) {
        this.estadoCaso = estadoCaso;
    }
}