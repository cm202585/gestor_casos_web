package sv.edu.udb.www.models;

import sv.edu.udb.www.beans.CasoBeans;
import sv.edu.udb.www.beans.EstadoBeans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JefaturaModel extends Conexion{

    public List<CasoBeans> obtenerCasos(String codigoUsuario) throws SQLException{
        List<CasoBeans> list = new ArrayList<>();
        String sql = "SELECT c.id_casos, c.fecha_registro, c.detalles , et.estado ,c.info_rechazo\n" +
                                        "FROM casos c\n" +
                                        "INNER JOIN estado et ON c.id_estado_caso = et.id_estado\n" +
                                        "WHERE c.id_usuario_caso = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigoUsuario);
            rs = st.executeQuery();
            while(rs.next()){
                CasoBeans casos = new CasoBeans();
                casos.setIdCasos(rs.getString("id_casos"));
                casos.setFechaRegistro(rs.getDate("fecha_registro"));
                casos.setDetallesCaso(rs.getString("detalles"));
                casos.setEstadoBeans(new EstadoBeans(rs.getString("estado")));
                casos.setInfoRechazo(rs.getString("info_rechazo"));
                list.add(casos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JefaturaModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }

    public int insertarCaso(CasoBeans casoBeans) throws SQLException{
        int rows = 0;
        String sql = "INSERT INTO casos(id_casos, fecha_registro, id_estado_caso, detalles, id_usuario_caso) VALUES (?,?,?,?,?)";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, casoBeans.getIdCasos());
            st.setDate(2, casoBeans.getFechaRegistro());
            //Siempre será 1 ya que al crear un usuario siempre tendrá el estado de : "EN ESPERA DE RESPUESTA"
            st.setInt(3,1);
            st.setString(4, casoBeans.getDetallesCaso());
            st.setString(5, casoBeans.getIdUsuarioCaso());
            rows = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JefaturaModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return rows;
    }

    public CasoBeans obtenerDetallesCaso(String codigo) throws SQLException{
        String sql = "SELECT id_casos, detalles FROM casos WHERE id_casos = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            if(rs.next()){
                CasoBeans casoBeans = new CasoBeans();
                casoBeans.setIdCasos(rs.getString("id_casos"));
                casoBeans.setDetallesCaso(rs.getString("detalles"));
                return casoBeans;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JefaturaModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return null;
    }

    public int actualizarCaso(String codigo, String descripcion) throws SQLException{
        int rows = 0;
        String sql = "UPDATE casos SET  info_rechazo = ?, detalles = ?, id_estado_caso = ? WHERE id_casos = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            //Al reenviar el caso mandamos la info del rechazo como nulo
            st.setString(1, null);
            st.setString(2, descripcion);
            //volvmemos a poner el caso como en espera de respuesta
            st.setInt(3, 1);
            st.setString(4, codigo);
            rows = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JefaturaModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return rows;
    }
}
