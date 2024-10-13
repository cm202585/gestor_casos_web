package sv.edu.udb.www.models;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    protected static Connection conexion = null;
    protected PreparedStatement st;
    protected CallableStatement cs;
    protected ResultSet rs;
    private Statement s = null;


    public Conexion() {
        this.st = null;
        this.rs = null;
    }

    public Connection conectar(){
        try {
            if(conexion==null || conexion.isClosed()){
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection("jdbc:mysql://root:WwvVKCpMaOcQjsdHWlqPHMgewNRJDmGS@mysql.railway.internal:3306/bdd_telecomunicaciones_sv?useSSL=false", "root", "WwvVKCpMaOcQjsdHWlqPHMgewNRJDmGS");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void desconectar() throws SQLException {
        //Cierro los objetos en el orden inverso del que se crean
        // es decir: primero el resulset, luego el statement
        if (rs != null) {
            rs.close();
        }
        if (st != null) {
            st.close();
        }
    }

    //Metodo que permite obtener los valores del resulset
    public ResultSet getRs() {
        return rs;
    }
    //Metodo que permite fijar la tabla resultado de la pregunta
    //SQL realizada
    public void setRs(String consulta) {
        try {
            this.rs = s.executeQuery(consulta);
        } catch (SQLException e2) {
            System.out.println("ERROR:Fallo en SQL: " + e2.getMessage());
        }
    }
    //Metodo que recibe un sql como parametro que sea un update,insert.delete
    public void setQuery(String query) throws SQLException {
        this.s.executeUpdate(query);
    }
}
