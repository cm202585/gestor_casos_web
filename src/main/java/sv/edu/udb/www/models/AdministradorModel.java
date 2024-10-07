package sv.edu.udb.www.models;

import sv.edu.udb.www.beans.TipoUsuarioBeans;
import sv.edu.udb.www.beans.UsuarioBeans;
import sv.edu.udb.www.beans.AreaFuncionalBeans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministradorModel extends Conexion{

    public int totalUsuarios() throws SQLException{
        int totalUsu = 0;
        String sql = "SELECT COUNT(id_usuario) as total_usuarios FROM usuario";
        try{
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next()){
                totalUsu = rs.getInt("total_usuarios");
            }
            return totalUsu;
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }finally{
            this.desconectar();
        }
    }

    public int totalAreasFuncionales() throws SQLException{
        int toralAre = 0;
        String sql = "SELECT COUNT(id_area) as total_areas FROM areafuncional";
        try{
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next()){
                toralAre = rs.getInt("total_areas");
            }
            return toralAre;
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }finally{
            this.desconectar();
        }
    }

    //CRUD DE USUARIOS
    public List<UsuarioBeans> listarUsuarios() throws SQLException{
        List<UsuarioBeans> list = new ArrayList<>();
        String sql = "SELECT u.id_usuario AS codigo, u.nombre, u.apellido, u.telefono, u.dui, u.correo, af.area AS area, tu.nombre_tipo_usuario AS tipo_usuario, CONCAT(uj.nombre, ' ', uj.apellido) AS jefe, u.contrasenia\n" +
                        "FROM Usuario u\n" +
                        "INNER JOIN TipoUsuario tu ON u.id_tipo_usuario = tu.id_tipo_usuario\n" +
                        "INNER JOIN AreaFuncional af ON u.id_area_funcional = af.id_area\n" +
                        "LEFT JOIN Usuario uj ON u.id_jefe_usuario = uj.id_usuario\n" +
                        "ORDER BY u.id_usuario";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next()){
                UsuarioBeans usuariosBean = new UsuarioBeans();
                usuariosBean.setIdUsuarioString(rs.getString("codigo"));
                usuariosBean.setNombreUsuario(rs.getString("nombre"));
                usuariosBean.setApellidoUsuario(rs.getString("apellido"));
                usuariosBean.setTelefonoUsuario(rs.getString("telefono"));
                usuariosBean.setDuiUsuario(rs.getString("dui"));
                usuariosBean.setCorreoUsuario(rs.getString("correo"));
                usuariosBean.setAreaFuncional(new AreaFuncionalBeans(rs.getString("area")));
                usuariosBean.setTipoUsuario(new TipoUsuarioBeans(rs.getString("tipo_usuario")));
                usuariosBean.setIdJefeUsuario(rs.getString("jefe"));
                usuariosBean.setContraseniaUsuario(rs.getString("contrasenia"));
                list.add(usuariosBean);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }

    public int eliminarUsuario(String codigoUsuario) throws SQLException{
        int result = 0;
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigoUsuario);
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }

    public List<TipoUsuarioBeans> listarTipoUsuario() throws SQLException{
        List<TipoUsuarioBeans> list = new ArrayList<>();
        String sql = "SELECT id_tipo_usuario, nombre_tipo_usuario FROM tipousuario ORDER BY id_tipo_usuario";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next()) {
                TipoUsuarioBeans tipoUsuarioBeans = new TipoUsuarioBeans();
                tipoUsuarioBeans.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
                tipoUsuarioBeans.setNombreTipoUsuario(rs.getString("nombre_tipo_usuario"));
                list.add(tipoUsuarioBeans);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }
    //Metodo para obtener el nombre de los jefes de desarrollo
    public List<UsuarioBeans> obtenerJefeArea(String areaFuncional) throws SQLException{
        List<UsuarioBeans> list = new ArrayList<>();
        String sql = "SELECT id_usuario, CONCAT(nombre, ' ' ,apellido) as nombre FROM usuario WHERE id_area_funcional = ? AND id_tipo_usuario = 2";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, areaFuncional);
            rs = st.executeQuery();
            while(rs.next()){
                UsuarioBeans usuariosBeans = new UsuarioBeans();
                usuariosBeans.setIdUsuarioString(rs.getString("id_usuario"));
                usuariosBeans.setNombreJefeUsuario(rs.getString("nombre"));
                list.add(usuariosBeans);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }

    public int insertarUsuario(UsuarioBeans usuarioBeans) throws SQLException{
        int result = 0;
        String sql = "INSERT INTO Usuario (id_usuario, nombre, apellido, telefono, dui, correo, id_area_funcional, id_tipo_usuario, id_jefe_usuario, contrasenia) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, usuarioBeans.getIdUsuarioString());
            st.setString(2, usuarioBeans.getNombreUsuario());
            st.setString(3, usuarioBeans.getApellidoUsuario());
            st.setString(4, usuarioBeans.getTelefonoUsuario());
            st.setString(5, usuarioBeans.getDuiUsuario());
            st.setString(6, usuarioBeans.getCorreoUsuario());
            st.setInt(7, usuarioBeans.getIdAreaFuncional());
            st.setInt(8, usuarioBeans.getIdTipoUsuarioInt());
            st.setString(9, usuarioBeans.getIdJefeUsuario());
            st.setString(10, usuarioBeans.getContraseniaUsuario());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }

    public UsuarioBeans obtenerUsuario(String codigoUsuario) throws SQLException{
        String sql = "SELECT id_usuario, nombre, apellido, telefono, dui, correo, contrasenia FROM usuario WHERE id_usuario = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigoUsuario);
            rs = st.executeQuery();
            if (rs.next()){
                UsuarioBeans usuarioBeans = new UsuarioBeans();
                usuarioBeans.setIdUsuarioString(rs.getString("id_usuario"));
                usuarioBeans.setNombreUsuario(rs.getString("nombre"));
                usuarioBeans.setApellidoUsuario(rs.getString("apellido"));
                usuarioBeans.setTelefonoUsuario(rs.getString("telefono"));
                usuarioBeans.setDuiUsuario(rs.getString("dui"));
                usuarioBeans.setCorreoUsuario(rs.getString("correo"));
                usuarioBeans.setContraseniaUsuario(rs.getString("contrasenia"));
                return usuarioBeans;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return null;
    }

    public int editarUsuario(UsuarioBeans usuarioBeans) throws SQLException{
        int result = 0;
        String sql = "UPDATE Usuario SET nombre = ?, apellido = ?, telefono = ?, dui = ?, correo = ?, id_area_funcional = ?, id_tipo_usuario = ?, id_jefe_usuario = ?, contrasenia = ? WHERE id_usuario = ?";
        try{
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, usuarioBeans.getNombreUsuario());
            st.setString(2, usuarioBeans.getApellidoUsuario());
            st.setString(3, usuarioBeans.getTelefonoUsuario());
            st.setString(4, usuarioBeans.getDuiUsuario());
            st.setString(5, usuarioBeans.getCorreoUsuario());
            st.setInt(6, usuarioBeans.getIdAreaFuncional());
            st.setInt(7, usuarioBeans.getIdTipoUsuarioInt());
            st.setString(8, usuarioBeans.getIdJefeUsuario());
            st.setString(9, usuarioBeans.getContraseniaUsuario());
            st.setString(10, usuarioBeans.getIdUsuarioString());
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }

    //CRUD DE AREAS FUNCIONALES
    public List<AreaFuncionalBeans> listarAreasFuncionales() throws SQLException{
        List<AreaFuncionalBeans> list = new ArrayList<>();
        String sql = "SELECT id_area, area FROM areafuncional ORDER BY id_area";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next()) {
                AreaFuncionalBeans areaFuncionalBeans = new AreaFuncionalBeans();
                areaFuncionalBeans.setIdAreaFuncional(rs.getInt("id_area"));
                areaFuncionalBeans.setAreaFuncional(rs.getString("area"));
                list.add(areaFuncionalBeans);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return list;
    }

    public int insertAreaFuncional(String area) throws SQLException{
        int result = 0;
        String sql = "INSERT INTO areafuncional(area) VALUES (?)";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, area);
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }

    public AreaFuncionalBeans obtenerAreasFuncionales(int codigo) throws SQLException{
        String sql = "SELECT id_area, area FROM `areafuncional` WHERE id_area = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, codigo);
            rs = st.executeQuery();
            if (rs.next()){
                AreaFuncionalBeans areaFuncionalBeans = new AreaFuncionalBeans();
                areaFuncionalBeans.setIdAreaFuncional(rs.getInt("id_area"));
                areaFuncionalBeans.setAreaFuncional(rs.getString("area"));
                return areaFuncionalBeans;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            this.desconectar();
        }
        return null;
    }

    public int editarAreaFuncional(int idArea, String nombreArea) throws SQLException{
        int result = 0;
        String sql = "UPDATE areafuncional SET area = ? WHERE areafuncional.id_area = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, nombreArea);
            st.setInt(2, idArea);
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }

    public int eliminarAreaFuncional(int codigoArea) throws SQLException{
        int result = 0;
        String sql = "DELETE FROM areafuncional WHERE id_area = ?";
        try {
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, codigoArea);
            result = st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return result;
    }
}