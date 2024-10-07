package sv.edu.udb.www.models;

import sv.edu.udb.www.beans.CasoBeans;
import sv.edu.udb.www.beans.EstadoBeans;
import sv.edu.udb.www.beans.UsuarioBeans;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TesterModel extends Conexion{

    public List<CasoBeans> obtenerRegistroCasos(String codigo) throws SQLException{
        List<CasoBeans> list = new ArrayList<>();
        String sql = "SELECT c.id_casos, c.fecha_registro, CONCAT(uj.nombre, ' ', uj.apellido) AS usuario, c.detalles, c.pdf\n" +
                "FROM casos c\n" +
                "INNER JOIN usuario u ON c.id_usuario_caso = u.id_usuario\n" +
                "LEFT JOIN Usuario uj ON u.id_usuario = uj.id_usuario\n" +
                "WHERE id_estado_caso = 4 and c.id_usuario_tester = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            while(rs.next()){
                CasoBeans casoBeans = new CasoBeans();
                casoBeans.setIdCasos(rs.getString("id_casos"));
                casoBeans.setFechaRegistro(rs.getDate("fecha_registro"));
                casoBeans.setUsuarioBeans(new UsuarioBeans(rs.getString("usuario")));
                casoBeans.setDetallesCaso(rs.getString("detalles"));
                casoBeans.setPdfCaso(rs.getString("pdf"));
                list.add(casoBeans);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProgramadorModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }

    public int aprobarCaso(String codigo, Date fecha) throws SQLException{
        int result = 0;
        String sql = "UPDATE casos SET id_estado_caso = ?, fecha_produccion = ? WHERE id_casos = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, 7);
            st.setDate(2, fecha);
            st.setString(3, codigo);
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProgramadorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }

    public int rechazarCaso(String codigo, String observaciones) throws SQLException{
        int result = 0;
        String sql = "UPDATE casos SET id_estado_caso = ?, observaciones = ? WHERE id_casos = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, 6);
            st.setString(2, observaciones);
            st.setString(3, codigo);
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProgramadorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }
}
