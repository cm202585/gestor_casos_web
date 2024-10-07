package sv.edu.udb.www.models;

import sv.edu.udb.www.beans.CasoBeans;
import sv.edu.udb.www.beans.EstadoBeans;
import sv.edu.udb.www.beans.UsuarioBeans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;

public class JefeDesarrolloModel extends Conexion{

    public int totalCasosPendientes(int areaFuncional) throws SQLException{
        int result = 0;
        String sql = "SELECT COUNT(c.id_casos) as total_casos \n" +
                        "FROM casos c\n" +
                        "INNER JOIN Usuario u ON c.id_usuario_caso = u.id_usuario\n" +
                        "WHERE c.id_estado_caso = 1 \n" +
                        "AND u.id_area_funcional = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, areaFuncional);
            rs = st.executeQuery();
            while (rs.next()){
                result = rs.getInt("total_casos");
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }finally{
            this.desconectar();
        }
    }

    public int totalCasosProbadores(int areaFuncional) throws SQLException{
        int result = 0;
        String sql = "SELECT COUNT(c.id_casos) as total_casos \n" +
                "FROM casos c\n" +
                "INNER JOIN Usuario u ON c.id_usuario_caso = u.id_usuario\n" +
                "WHERE c.id_estado_caso = 4 \n" +
                "AND u.id_area_funcional = ?;";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, areaFuncional);
            rs = st.executeQuery();
            while (rs.next()){
                result = rs.getInt("total_casos");
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }finally{
            this.desconectar();
        }
    }
    public int totalCasos(int areaFuncional) throws SQLException{
        int result = 0;
        String sql = "SELECT COUNT(c.id_casos) as total_casos \n" +
                        "FROM casos c\n" +
                        "INNER JOIN Usuario u ON c.id_usuario_caso = u.id_usuario\n" +
                        "AND u.id_area_funcional = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, areaFuncional);
            rs = st.executeQuery();
            while(rs.next()){
                result = rs.getInt("total_casos");
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }finally{
            this.desconectar();
        }
    }

    public List<CasoBeans> obtenerRegistroCasos(String areaUsuario) throws SQLException{
        List<CasoBeans> list = new ArrayList<>();
        String sql = "SELECT \n" +
                "    c.id_casos, \n" +
                "    c.fecha_registro, \n" +
                "    c.fecha_inicio, \n" +
                "    c.fecha_vencimiento, \n" +
                "    c.fecha_produccion, \n" +
                "    c.detalles, \n" +
                "    et.estado, \n" +
                "    c.info_rechazo,\n" +
                "    c.progreso, \n" +
                "    CONCAT(u.nombre, ' ', u.apellido) AS usuario_creador,\n" +
                "    CONCAT(up.nombre, ' ', up.apellido) AS programador_asignado,\n" +
                "    CONCAT(ut.nombre, ' ', ut.apellido) AS probador_asignado,\n" +
                "    c.pdf\n" +
                "FROM casos c\n" +
                "INNER JOIN estado et ON c.id_estado_caso = et.id_estado\n" +
                "INNER JOIN usuario u ON c.id_usuario_caso = u.id_usuario\n" +
                "LEFT JOIN Usuario up ON c.id_usuario_programador = up.id_usuario\n" +
                "LEFT JOIN Usuario ut ON c.id_usuario_tester = ut.id_usuario\n" +
                "INNER JOIN areafuncional af ON u.id_area_funcional = af.id_area\n" +
                "WHERE af.area = ?;";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, areaUsuario);
            rs = st.executeQuery();
            while(rs.next()){
                CasoBeans casoBeans = new CasoBeans();
                casoBeans.setIdCasos(rs.getString("id_casos"));
                casoBeans.setFechaRegistro(rs.getDate("fecha_registro"));
                casoBeans.setFechaInicio(rs.getDate("fecha_inicio"));
                casoBeans.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                casoBeans.setFechaProduccion(rs.getDate("fecha_produccion"));
                casoBeans.setDetallesCaso(rs.getString("detalles"));
                casoBeans.setEstadoBeans(new EstadoBeans(rs.getString("estado")));
                casoBeans.setInfoRechazo(rs.getString("info_rechazo"));
                casoBeans.setProgresoCaso(rs.getInt("progreso"));
                casoBeans.setIdUsuarioCaso(rs.getString("usuario_creador"));
                casoBeans.setIdUsuarioProgramador(rs.getString("programador_asignado"));
                casoBeans.setIdUsuarioTester(rs.getString("probador_asignado"));
                casoBeans.setPdfCaso(rs.getString("pdf"));
                list.add(casoBeans);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }

    public List<CasoBeans> obtenerCasosPendientes(String areaUsuario) throws SQLException{
        List<CasoBeans> list = new ArrayList<>();
        String sql = "SELECT c.id_casos, c.fecha_registro, c.detalles, CONCAT(uj.nombre, ' ', uj.apellido) AS usuario\n" +
                        "FROM casos c\n" +
                        "INNER JOIN estado et ON c.id_estado_caso = et.id_estado\n" +
                        "INNER JOIN usuario u ON c.id_usuario_caso = u.id_usuario\n" +
                        "INNER JOIN areafuncional af ON u.id_area_funcional = af.id_area\n" +
                        "LEFT JOIN Usuario uj ON u.id_usuario = uj.id_usuario\n" +
                        "WHERE af.area = ? and c.id_estado_caso = 1";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, areaUsuario);
            rs = st.executeQuery();
            while(rs.next()){
                CasoBeans casoBeans = new CasoBeans();
                casoBeans.setIdCasos(rs.getString("id_casos"));
                casoBeans.setFechaRegistro(rs.getDate("fecha_registro"));
                casoBeans.setDetallesCaso(rs.getString("detalles"));
                casoBeans.setUsuarioBeans(new UsuarioBeans(rs.getString("usuario")));
                list.add(casoBeans);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }


    public CasoBeans obtenerCaso(String codigo) throws SQLException{
        String sql = "SELECT id_casos, detalles FROM `casos` WHERE id_casos = ?";
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
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return null;
    }

    public List<UsuarioBeans> obtenerProgramadoresAsignados(String codigo) throws SQLException{
        List<UsuarioBeans> list = new ArrayList<>();
        String sql = "SELECT id_usuario, CONCAT(nombre, ' ' ,apellido) as nombre FROM usuario WHERE id_jefe_usuario = ? AND id_tipo_usuario = 4;";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            while(rs.next()){
                UsuarioBeans usuariosBeans = new UsuarioBeans();
                usuariosBeans.setIdUsuarioString(rs.getString("id_usuario"));
                usuariosBeans.setNombreUsuario(rs.getString("nombre"));
                list.add(usuariosBeans);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }

    public List<UsuarioBeans> obtenerTesterAsignados(String codigo) throws SQLException{
        List<UsuarioBeans> list = new ArrayList<>();
        String sql = "SELECT id_usuario, CONCAT(nombre, ' ' ,apellido) as nombre FROM usuario WHERE id_jefe_usuario = ? AND id_tipo_usuario = 5;";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigo);
            rs = st.executeQuery();
            while(rs.next()){
                UsuarioBeans usuariosBeans = new UsuarioBeans();
                usuariosBeans.setIdUsuarioString(rs.getString("id_usuario"));
                usuariosBeans.setNombreUsuario(rs.getString("nombre"));
                list.add(usuariosBeans);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }

    public int aprobarCaso(CasoBeans casoBeans) throws SQLException{
        int result = 0;
        String sql = "UPDATE casos SET fecha_inicio = ?, fecha_vencimiento = ?, id_estado_caso = ?, id_usuario_programador = ?, pdf = ? WHERE id_casos = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setDate(1, casoBeans.getFechaInicio());
            st.setDate(2, casoBeans.getFechaVencimiento());
            st.setInt(3, 3);
            st.setString(4, casoBeans.getIdUsuarioProgramador());
            st.setString(5, casoBeans.getPdfCaso());
            st.setString(6, casoBeans.getIdCasos());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }

    public int rechazarCaso(String codigo, String informacion) throws SQLException{
        int result = 0;
        String sql = "UPDATE casos SET id_estado_caso = ?, info_rechazo = ? WHERE id_casos = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, 2);
            st.setString(2, informacion);
            st.setString(3, codigo);
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }

    public List<CasoBeans> obtenerCasosProbadores(String areaUsuario) throws SQLException{
        List<CasoBeans> list = new ArrayList<>();
        String sql = "SELECT c.id_casos, c.fecha_registro, c.detalles, CONCAT(uj.nombre, ' ', uj.apellido) AS usuario, CONCAT(ut.nombre, ' ', ut.apellido) AS tester\n" +
                "FROM casos c\n" +
                "INNER JOIN estado et ON c.id_estado_caso = et.id_estado\n" +
                "INNER JOIN usuario u ON c.id_usuario_caso = u.id_usuario\n" +
                "INNER JOIN areafuncional af ON u.id_area_funcional = af.id_area\n" +
                "LEFT JOIN Usuario uj ON u.id_usuario = uj.id_usuario\n" +
                "LEFT JOIN Usuario ut ON c.id_usuario_tester = ut.id_usuario\n" +
                "WHERE af.area = ? and c.id_estado_caso = 4;";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, areaUsuario);
            rs = st.executeQuery();
            while(rs.next()){
                CasoBeans casoBeans = new CasoBeans();
                casoBeans.setIdCasos(rs.getString("id_casos"));
                casoBeans.setFechaRegistro(rs.getDate("fecha_registro"));
                casoBeans.setDetallesCaso(rs.getString("detalles"));
                casoBeans.setUsuarioBeans(new UsuarioBeans(rs.getString("usuario")));
                casoBeans.setIdUsuarioTester(rs.getString("tester"));
                list.add(casoBeans);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }

    public int asignarProbador(String codigoCaso, String codigoUsuario) throws SQLException{
        int rows = 0;
        String sql = "UPDATE casos SET id_usuario_tester = ? WHERE id_casos = ?;";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigoUsuario);
            st.setString(2, codigoCaso);
            rows = st.executeUpdate();
        } catch (SQLException ex){
            Logger.getLogger(JefeDesarrolloModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return rows;
    }
}
