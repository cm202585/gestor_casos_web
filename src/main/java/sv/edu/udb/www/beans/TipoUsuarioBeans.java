package sv.edu.udb.www.beans;

public class TipoUsuarioBeans {
    private int idTipoUsuario;
    private String nombreTipoUsuario;

    public TipoUsuarioBeans(){
        this.idTipoUsuario = 0;
        this.nombreTipoUsuario = "";
    }

    public TipoUsuarioBeans(String nombreTipoUsuario){
        this.nombreTipoUsuario = nombreTipoUsuario;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getNombreTipoUsuario() {
        return nombreTipoUsuario;
    }

    public void setNombreTipoUsuario(String nombreTipoUsuario) {
        this.nombreTipoUsuario = nombreTipoUsuario;
    }
}
