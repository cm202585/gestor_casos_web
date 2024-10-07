package sv.edu.udb.www.beans;

public class UsuarioBeans {

    private String idUsuarioString;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String telefonoUsuario;
    private String duiUsuario;
    private String correoUsuario;
    private String contraseniaUsuario;
    private int idAreaFuncional;
    private AreaFuncionalBeans areaFuncional;
    private int idTipoUsuarioInt;
    private TipoUsuarioBeans tipoUsuario;
    private String idJefeUsuario;
    private String nombreJefeUsuario;

    public UsuarioBeans(){
        this.idUsuarioString = "";
        this.nombreUsuario = "";
        this.apellidoUsuario = "";
        this.telefonoUsuario = "";
        this.duiUsuario = "";
        this.correoUsuario = "";
        this.contraseniaUsuario = "";
        this.idAreaFuncional = 0;
        this.areaFuncional = null;
        this.idTipoUsuarioInt = 0;
        this.tipoUsuario = null;
        this.idJefeUsuario = "";
        this.nombreJefeUsuario = "";
    }

    public UsuarioBeans(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getIdUsuarioString() {
        return idUsuarioString;
    }

    public void setIdUsuarioString(String idUsuarioString) {
        this.idUsuarioString = idUsuarioString;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }

    public String getDuiUsuario() {
        return duiUsuario;
    }

    public void setDuiUsuario(String duiUsuario) {
        this.duiUsuario = duiUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContraseniaUsuario() {
        return contraseniaUsuario;
    }

    public void setContraseniaUsuario(String contraseniaUsuario) {
        this.contraseniaUsuario = contraseniaUsuario;
    }

    public int getIdAreaFuncional() {
        return idAreaFuncional;
    }

    public void setIdAreaFuncional(int idAreaFuncional) {
        this.idAreaFuncional = idAreaFuncional;
    }

    public AreaFuncionalBeans getAreaFuncional() {
        return areaFuncional;
    }

    public void setAreaFuncional(AreaFuncionalBeans areaFuncional) {
        this.areaFuncional = areaFuncional;
    }

    public int getIdTipoUsuarioInt() {
        return idTipoUsuarioInt;
    }

    public void setIdTipoUsuarioInt(int idTipoUsuarioInt) {
        this.idTipoUsuarioInt = idTipoUsuarioInt;
    }

    public TipoUsuarioBeans getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioBeans tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getIdJefeUsuario() {
        return idJefeUsuario;
    }

    public void setIdJefeUsuario(String idJefeUsuario) {
        this.idJefeUsuario = idJefeUsuario;
    }

    public String getNombreJefeUsuario() {
        return nombreJefeUsuario;
    }

    public void setNombreJefeUsuario(String nombreJefeUsuario) {
        this.nombreJefeUsuario = nombreJefeUsuario;
    }
}
