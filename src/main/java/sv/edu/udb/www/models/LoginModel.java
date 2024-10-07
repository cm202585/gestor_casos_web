package sv.edu.udb.www.models;

import sv.edu.udb.www.beans.UsuarioBeans;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginModel extends Conexion{
    public int verificarUsuario(String codigoUsuario, String contraseniaUsuario) throws SQLException{
        int tipoUsuario = 0;
        String sql = "SELECT contrasenia, id_tipo_usuario from usuario where id_usuario = ?";
        try{
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, codigoUsuario);
            rs = st.executeQuery();

            if(rs.next()){
                UsuarioBeans usuario = new UsuarioBeans();
                usuario.setContraseniaUsuario(rs.getString("contrasenia"));
                usuario.setIdTipoUsuarioInt(rs.getInt("id_tipo_usuario"));

                if (!usuario.getContraseniaUsuario().equals(contraseniaUsuario)) {
                    return 0;
                }
                return usuario.getIdTipoUsuarioInt();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } finally {
            this.desconectar();
        }
        return tipoUsuario;
    }
}
