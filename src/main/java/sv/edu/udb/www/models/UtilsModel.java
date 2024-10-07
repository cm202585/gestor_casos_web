package sv.edu.udb.www.models;

import sv.edu.udb.www.beans.BitacoraBeans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtilsModel extends Conexion{
    //clase general que no servira para evitar poner mismos metodos en diferendes modelos
    //Por ejemplo de cada uno necesitaremos saber su area (su nombre no id), por lo que este metodo fuecreado aquí para evitar colocarlo en otros modelos
    public String obtenerAreaUsuarioSring(String codigo) throws SQLException {
        String result = "";
        String sql = "SELECT ar.area\n" +
                        "FROM usuario u\n" +
                        "INNER JOIN areafuncional ar ON u.id_area_funcional = ar.id_area\n" +
                        "WHERE u.id_usuario = ?;";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            if(rs.next()){
                result = (rs.getString("area"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilsModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return result;
    }

    //Obtenemos el id del area funcional del usuario
    public int obtenerAreaUsuarioInt(String codigo) throws SQLException{
        int result = 0;
        String sql = "SELECT id_area_funcional FROM `usuario` WHERE id_usuario = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            if(rs.next()){
                result = (rs.getInt("id_area_funcional"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilsModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }

    //Obtendremos la base 64 del pdf para mostrarlo/descargarlo
    public String obtenerPdfBase64(String codigo) throws SQLException{
        String result = "";
        String sql = "SELECT pdf FROM `casos` WHERE id_casos = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            if(rs.next()){
                result = rs.getString("pdf");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilsModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return result;
    }

    //Codigo que servira para poner los casos en vencimiento
    public void actualizarCasoVencido() throws SQLException{
        String sql = "UPDATE casos c\n" +
                "INNER JOIN estado e ON c.id_estado_caso = e.id_estado\n" +
                "SET c.id_estado_caso = (SELECT id_estado FROM estado WHERE estado = 'Vencido')\n" +
                "WHERE c.fecha_vencimiento < CURDATE() AND c.id_estado_caso = (SELECT id_estado FROM estado WHERE estado = 'En desarrollo')";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UtilsModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.desconectar();
        }
    }

    //Codigo para ver las bitacoras creadas por un caso
    public List<BitacoraBeans> obtenerBitacorasCaso(String codigo) throws SQLException{
        List<BitacoraBeans> list = new ArrayList<>();
        String sql = "SELECT b.id_caso_bitacora, b.descripcion, b.fecha, CONCAT(u.nombre, ' ', u.apellido) AS usuario\n" +
                        "FROM bitacora b\n" +
                        "INNER JOIN usuario u ON b.usuario_bitacora = u.id_usuario\n" +
                        "WHERE b.id_caso_bitacora = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            while (rs.next()){
                BitacoraBeans bitacoraBeans = new BitacoraBeans();
                bitacoraBeans.setIdCasoBitacora(rs.getString("id_caso_bitacora"));
                bitacoraBeans.setDescripcionBitacora(rs.getString("descripcion"));
                bitacoraBeans.setFechaBitacora(rs.getDate("fecha"));
                bitacoraBeans.setIdUsuarioBitacora(rs.getString("usuario"));
                list.add(bitacoraBeans);
            }
        }catch (SQLException ex) {
            Logger.getLogger(UtilsModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }
}
