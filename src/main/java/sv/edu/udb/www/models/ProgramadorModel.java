package sv.edu.udb.www.models;

import sv.edu.udb.www.beans.CasoBeans;
import sv.edu.udb.www.beans.EstadoBeans;
import sv.edu.udb.www.beans.UsuarioBeans;
import sv.edu.udb.www.beans.BitacoraBeans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProgramadorModel extends Conexion{
    public int totalCasos(String codigo) throws SQLException{
        int totalCasos = 0;
        String sql = "SELECT COUNT(id_casos) AS total_casos FROM `casos` WHERE id_usuario_programador = ? and (id_estado_caso = 3 OR id_estado_caso = 4)";
        try{
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            while(rs.next()){
                totalCasos = rs.getInt("total_casos");
            }
            return totalCasos;
        } catch (SQLException ex) {
            Logger.getLogger(ProgramadorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }finally{
            this.desconectar();
        }
    }

    public int totalCasosVencidos(String codigo) throws SQLException{
        int totalCasos = 0;
        String sql = "SELECT COUNT(id_casos) AS total_casos FROM `casos` WHERE id_usuario_programador = ? and id_estado_caso = 5";
        try{
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            while(rs.next()){
                totalCasos = rs.getInt("total_casos");
            }
            return totalCasos;
        } catch (SQLException ex) {
            Logger.getLogger(ProgramadorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }finally{
            this.desconectar();
        }
    }

    public int totalCasosObservaciones(String codigo) throws SQLException{
        int totalCasos = 0;
        String sql = "SELECT COUNT(id_casos) AS total_casos FROM `casos` WHERE id_usuario_programador = ? and id_estado_caso = 6";
        try{
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            while(rs.next()){
                totalCasos = rs.getInt("total_casos");
            }
            return totalCasos;
        } catch (SQLException ex) {
            Logger.getLogger(ProgramadorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }finally{
            this.desconectar();
        }
    }

    public List<CasoBeans> obtenerCasosPendientes(String codigo) throws SQLException{
        List<CasoBeans> list = new ArrayList<>();
        String sql = "SELECT c.id_casos, c.fecha_registro, c.fecha_vencimiento , et.estado, CONCAT(uj.nombre, ' ', uj.apellido) AS usuario, c.detalles, c.pdf\n" +
                        "FROM casos c\n" +
                        "INNER JOIN estado et ON c.id_estado_caso = et.id_estado\n" +
                        "INNER JOIN usuario u ON c.id_usuario_caso = u.id_usuario\n" +
                        "LEFT JOIN Usuario uj ON u.id_usuario = uj.id_usuario\n" +
                        "WHERE ( id_estado_caso = 3 or id_estado_caso = 4) and c.id_usuario_programador = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            while(rs.next()){
                CasoBeans casoBeans = new CasoBeans();
                casoBeans.setIdCasos(rs.getString("id_casos"));
                casoBeans.setFechaRegistro(rs.getDate("fecha_registro"));
                casoBeans.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                casoBeans.setEstadoBeans(new EstadoBeans(rs.getString("estado")));
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

    public CasoBeans obtenerCaso(String codigo) throws SQLException{
        String sql = "SELECT id_casos, progreso FROM `casos` WHERE id_casos = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            if(rs.next()){
                CasoBeans casoBeans = new CasoBeans();
                casoBeans.setIdCasos(rs.getString("id_casos"));
                casoBeans.setProgresoCaso(rs.getInt("progreso"));
                return casoBeans;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProgramadorModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return null;
    }

    public int actualizarProgreso(String codigoCaso, int progreso) throws SQLException{
        int rows = 0;
        String sql = "UPDATE casos SET progreso = ? WHERE id_casos = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, progreso);
            st.setString(2, codigoCaso);
            rows = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProgramadorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return rows;
    }

    public int crearBitacora(BitacoraBeans bitacoraBeans) throws SQLException{
        int result = 0;
        String sql = "INSERT INTO bitacora (id_caso_bitacora, descripcion, fecha, usuario_bitacora) VALUES (?,?,?,?)";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, bitacoraBeans.getIdCasoBitacora());
            st.setString(2, bitacoraBeans.getDescripcionBitacora());
            st.setDate(3, bitacoraBeans.getFechaBitacora());
            st.setString(4, bitacoraBeans.getIdUsuarioBitacora());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProgramadorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }

    public int finalizarCasoTrabajado(String codigoCaso) throws SQLException{
        int result = 0;
        String sql = "UPDATE casos SET observaciones = null, id_estado_caso = 4 WHERE id_casos = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigoCaso);
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProgramadorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }

    public List<CasoBeans> obtenerCasosVencidos(String codigo) throws SQLException{
        List<CasoBeans> list = new ArrayList<>();
        String sql = "SELECT c.id_casos, c.fecha_registro, c.fecha_vencimiento , et.estado, CONCAT(uj.nombre, ' ', uj.apellido) AS usuario, c.detalles, c.pdf\n" +
                "FROM casos c\n" +
                "INNER JOIN estado et ON c.id_estado_caso = et.id_estado\n" +
                "INNER JOIN usuario u ON c.id_usuario_caso = u.id_usuario\n" +
                "LEFT JOIN Usuario uj ON u.id_usuario = uj.id_usuario\n" +
                "WHERE  id_estado_caso = 5 and c.id_usuario_programador = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            while(rs.next()){
                CasoBeans casoBeans = new CasoBeans();
                casoBeans.setIdCasos(rs.getString("id_casos"));
                casoBeans.setFechaRegistro(rs.getDate("fecha_registro"));
                casoBeans.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                casoBeans.setEstadoBeans(new EstadoBeans(rs.getString("estado")));
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

    public List<CasoBeans> obtenerCasosObservaciones(String codigo) throws SQLException{
        List<CasoBeans> list = new ArrayList<>();
        String sql = "SELECT c.id_casos, et.estado, CONCAT(uj.nombre, ' ', uj.apellido) AS usuario, c.detalles, c.observaciones, c.pdf\n" +
                "FROM casos c\n" +
                "INNER JOIN estado et ON c.id_estado_caso = et.id_estado\n" +
                "INNER JOIN usuario u ON c.id_usuario_caso = u.id_usuario\n" +
                "LEFT JOIN Usuario uj ON u.id_usuario = uj.id_usuario\n" +
                "WHERE  id_estado_caso = 6 and c.id_usuario_programador = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            while(rs.next()){
                CasoBeans casoBeans = new CasoBeans();
                casoBeans.setIdCasos(rs.getString("id_casos"));
                casoBeans.setEstadoBeans(new EstadoBeans(rs.getString("estado")));
                casoBeans.setUsuarioBeans(new UsuarioBeans(rs.getString("usuario")));
                casoBeans.setDetallesCaso(rs.getString("detalles"));
                casoBeans.setObservacionesCaso(rs.getString("observaciones"));
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
}
