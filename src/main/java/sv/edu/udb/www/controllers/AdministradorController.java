package sv.edu.udb.www.controllers;

import sv.edu.udb.www.beans.AreaFuncionalBeans;
import sv.edu.udb.www.beans.UsuarioBeans;
import sv.edu.udb.www.models.AdministradorModel;
import sv.edu.udb.www.utils.Validaciones;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(name = "AdministradorController", value = "/AdministradorController")
public class AdministradorController extends HttpServlet {

    ArrayList<String> listaErrores = new ArrayList<>();
    AdministradorModel administradorModel = new AdministradorModel();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if(request.getParameter("op")==null) {
                menuAdministrador(request, response);
                return;
            }

            String operacion = request.getParameter("op");

            switch (operacion){
                case "menuadministrador":
                    menuAdministrador(request, response);
                    break;
                    //CRUD USUARIOS
                case "listarusuarios":
                    listarUsuarios(request, response);
                    break;
                case "nuevousuario":
                    nuevoUsuario(request, response);
                    break;
                case "insertarusuario":
                    insetarUsuario(request, response);
                    break;
                case "listarjefes":
                    listarJefes(request, response);
                    break;
                case "obtenerusuario":
                    obtenerUsuario(request, response);
                    break;
                case "actualizarusuario":
                    actualizarUsuario(request, response);
                    break;
                case "eliminarusuario":
                    eliminarUsuario(request, response);
                    break;
                    //CRUD DE AREAS FUNCIONALES
                case "listarareas":
                    listarAreas(request, response);
                    break;
                case "insertararea":
                    insertarArea(request, response);
                    break;
                case "detallesarea":
                    obtenerArea(request, response);
                    break;
                case "editararea":
                    editarArea(request, response);
                    break;
                case "eliminararea":
                    eliminarArea(request, response);
                    break;
            }
        }
    }

    private void menuAdministrador(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setAttribute("totalusuarios", administradorModel.totalUsuarios());
            request.setAttribute("totalareas", administradorModel.totalAreasFuncionales());
            request.getRequestDispatcher("/administrador/menuAdministrador.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setAttribute("listarUsuarios", administradorModel.listarUsuarios());
            request.getRequestDispatcher("/administrador/usuarios/listarUsuarios.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void nuevoUsuario(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setAttribute("tipoUsuario", administradorModel.listarTipoUsuario());
            request.setAttribute("areaFuncional", administradorModel.listarAreasFuncionales());
            request.getRequestDispatcher("/administrador/usuarios/nuevoUsuario.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerUsuario(HttpServletRequest request, HttpServletResponse response){
        try {
            String codigo = request.getParameter("id");
            UsuarioBeans usuarioBeans = administradorModel.obtenerUsuario(codigo);
            System.out.print(usuarioBeans);
            if(usuarioBeans != null){
                request.setAttribute("usuario", usuarioBeans);
                request.setAttribute("tipoUsuario", administradorModel.listarTipoUsuario());
                request.setAttribute("areaFuncional", administradorModel.listarAreasFuncionales());
                request.getRequestDispatcher("/administrador/usuarios/editarUsuario.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/error404.jsp");
            }
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarJefes(HttpServletRequest request, HttpServletResponse response){
        try {
            //Esta funcion nos servira para obtener el id del jefe con su codigo para evitar colocarlo de manera manual
            PrintWriter out = response.getWriter();
            String areaFuncional = request.getParameter("area");

            List<UsuarioBeans> listaJefes = administradorModel.obtenerJefeArea(areaFuncional);

            // Convertir la lista de jefes a JSON
            JSONArray jsonArray = new JSONArray();
            for (UsuarioBeans jefe : listaJefes) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codigoJefe", jefe.getIdUsuarioString());
                jsonObject.put("nombreJefe", jefe.getNombreJefeUsuario());
                jsonArray.add(jsonObject);
            }
            out.println(jsonArray);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insetarUsuario(HttpServletRequest request, HttpServletResponse response){
        UsuarioBeans usuarioBeans = new UsuarioBeans();
        usuarioBeans.setIdUsuarioString(request.getParameter("codigoUsuario"));
        usuarioBeans.setNombreUsuario(request.getParameter("nombreUsuario"));
        usuarioBeans.setApellidoUsuario(request.getParameter("apellidoUsuario"));
        usuarioBeans.setTelefonoUsuario(request.getParameter("telefonoUsuario"));
        usuarioBeans.setCorreoUsuario(request.getParameter("correoUsuario"));
        usuarioBeans.setDuiUsuario(request.getParameter("duiUsuario"));
        usuarioBeans.setContraseniaUsuario(request.getParameter("contraseniaUsuario"));
        usuarioBeans.setIdAreaFuncional(Integer.parseInt(request.getParameter("areaFuncional")));
        usuarioBeans.setIdTipoUsuarioInt(Integer.parseInt(request.getParameter("tipoUsuario")));
        usuarioBeans.setIdJefeUsuario(request.getParameter("jefeUsuario"));
        validarYRedirigir(request, response, usuarioBeans, "insertar");
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response){
        UsuarioBeans usuarioBeans = new UsuarioBeans();
        usuarioBeans.setIdUsuarioString(request.getParameter("codigoUsuario"));
        usuarioBeans.setNombreUsuario(request.getParameter("nombreUsuario"));
        usuarioBeans.setApellidoUsuario(request.getParameter("apellidoUsuario"));
        usuarioBeans.setTelefonoUsuario(request.getParameter("telefonoUsuario"));
        usuarioBeans.setCorreoUsuario(request.getParameter("correoUsuario"));
        usuarioBeans.setDuiUsuario(request.getParameter("duiUsuario"));
        usuarioBeans.setContraseniaUsuario(request.getParameter("contraseniaUsuario"));
        usuarioBeans.setIdAreaFuncional(Integer.parseInt(request.getParameter("areaFuncional")));
        usuarioBeans.setIdTipoUsuarioInt(Integer.parseInt(request.getParameter("tipoUsuario")));
        usuarioBeans.setIdJefeUsuario(request.getParameter("jefeUsuario"));

        validarYRedirigir(request, response, usuarioBeans, "editar");
    }

    //Verificar datos ingresados
    private void validarYRedirigir(HttpServletRequest request, HttpServletResponse response, UsuarioBeans usuariosBean ,String operacion) {
        try {
            listaErrores.clear();
            if (!Validaciones.esCodigoUsuario(usuariosBean.getIdUsuarioString())){
                listaErrores.add("El codigo debe tener un formato de 2 letras y 6 números.");
            }
            if (Validaciones.isEmpty(usuariosBean.getNombreUsuario())){
                listaErrores.add("El nombre del usuario es obligatorio.");
            }
            if (Validaciones.isEmpty(usuariosBean.getApellidoUsuario())){
                listaErrores.add("El apellido del usuario es obligatorio.");
            }
            if (!Validaciones.esTelefono(usuariosBean.getTelefonoUsuario())){
                listaErrores.add("Ingrese un número de telefono valido.");
            }
            if(!Validaciones.esDui(usuariosBean.getDuiUsuario())){
                listaErrores.add("Ingrese un número de DUI valido.");
            }
            if (!Validaciones.esCorreo(usuariosBean.getCorreoUsuario())){
                listaErrores.add("Ingrese un correo valido.");
            }
            if (Validaciones.isEmpty(usuariosBean.getContraseniaUsuario())){
                listaErrores.add("La contraseña es obligatoria.");
            }

            if("insertar".equals(operacion)){
                if (listaErrores.size() > 0) {
                    request.setAttribute("listaErrores", listaErrores);
                    request.getRequestDispatcher("/AdministradorController?op=nuevousuario").forward(request, response);
                    return;
                }
                if(administradorModel.insertarUsuario(usuariosBean) > 0){
                    request.getSession().setAttribute("exito","El usuario fue ingresado de manera correcta.");
                } else {
                    request.getSession().setAttribute("error", "Hubo un error al momento de ingresar al usuario");
                }
            } else if ("editar".equals(operacion)) {
                if (listaErrores.size() > 0) {
                    request.setAttribute("listaErrores", listaErrores);
                    request.getRequestDispatcher("/AdministradorController?op=obtenerusuario&id=" + usuariosBean.getIdUsuarioString()).forward(request, response);
                    return;
                }
                if(administradorModel.editarUsuario(usuariosBean) > 0){
                    request.getSession().setAttribute("exito","El usuario fue editado de manera correcta.");
                } else {
                    request.getSession().setAttribute("error", "Hubo un error al momento de editar al usuario");
                }
            }
            response.sendRedirect(request.getContextPath() + "/AdministradorController?op=listarusuarios");
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response){
        try {
            String codigo = request.getParameter("id");
            HttpSession session = request.getSession();
            String codigoUsuario = (String) session.getAttribute("administrador");
            if(codigoUsuario.equals(codigo)){
                request.getSession().setAttribute("error", "¡No puedes eliminarte a ti mismo!");
                response.sendRedirect(request.getContextPath() + "/AdministradorController?op=listarusuarios");
                return;
            }
            if(administradorModel.eliminarUsuario(codigo) > 0){
                request.getSession().setAttribute("exito","Usuario eliminado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Hubo un error al momento de eliminar al usuario");
            }
            response.sendRedirect(request.getContextPath() + "/AdministradorController?op=listarusuarios");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //AREAS FUNCIONALES
    private void listarAreas(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setAttribute("listarareas", administradorModel.listarAreasFuncionales());
            request.getRequestDispatcher("/administrador/areas/listarAreas.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertarArea(HttpServletRequest request, HttpServletResponse response){
        try {
            if(administradorModel.insertAreaFuncional(request.getParameter("txtNombreArea")) > 0){
                request.getSession().setAttribute("exito","El area funcional ha sido ingresada exitosamente");
            } else {
                request.getSession().setAttribute("error", "Hubo un error al momento de insertar el area funcional");
            }
            response.sendRedirect(request.getContextPath() + "/AdministradorController?op=listarareas");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerArea(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            int codigo = Integer.parseInt(request.getParameter("id"));
            AreaFuncionalBeans areaFuncionalBeans = administradorModel.obtenerAreasFuncionales(codigo);
            JSONObject json = new JSONObject();
            json.put("idArea", areaFuncionalBeans.getIdAreaFuncional());
            json.put("nombreArea", areaFuncionalBeans.getAreaFuncional());
            out.println(json);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editarArea(HttpServletRequest request, HttpServletResponse response){
        try {
            int idArea = Integer.parseInt(request.getParameter("idArea"));
            String nombreArea = request.getParameter("nombreArea");
            if(administradorModel.editarAreaFuncional(idArea, nombreArea) > 0){
                request.getSession().setAttribute("exito","El area funcional ha sido editada exitosamente");
            } else {
                request.getSession().setAttribute("error", "Hubo un error al momento de editar el area funcional");
            }
            response.sendRedirect(request.getContextPath() + "/AdministradorController?op=listarareas");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarArea(HttpServletRequest request, HttpServletResponse response){
        try {
            int codigo = Integer.parseInt(request.getParameter("id"));
            if(administradorModel.eliminarAreaFuncional(codigo) > 0){
                request.getSession().setAttribute("exito","El area funcional ha sido eliminado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Hubo un error al momento de eliminar el area funcional");
            }
            response.sendRedirect(request.getContextPath() + "/AdministradorController?op=listarareas");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
