package sv.edu.udb.www.beans;

public class AreaFuncionalBeans {
    private int idAreaFuncional;
    private String areaFuncional;

    public AreaFuncionalBeans(){
        this.idAreaFuncional = 0;
        this.areaFuncional = "";
    }
    public AreaFuncionalBeans(String areaFuncional){
        this.areaFuncional = areaFuncional;
    }

    public int getIdAreaFuncional() {
        return idAreaFuncional;
    }

    public void setIdAreaFuncional(int idAreaFuncional) {
        this.idAreaFuncional = idAreaFuncional;
    }

    public String getAreaFuncional() {
        return areaFuncional;
    }

    public void setAreaFuncional(String areaFuncional) {
        this.areaFuncional = areaFuncional;
    }
}
