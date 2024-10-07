package sv.edu.udb.www.controllers;

import sv.edu.udb.www.models.LoginModel;
import sv.edu.udb.www.utils.Validaciones;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginController", value = "/LoginController")
public class LoginController extends HttpServlet {

    ArrayList<String> listaErrores = new ArrayList<>();
    LoginModel loginModel = new LoginModel();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if(request.getParameter("op")==null) {
                login(request, response);
                return;
            }

            String operacion = request.getParameter("op");
            switch (operacion){
                case "error":
                    login(request, response);
                    break;
                case "verificar":
                    verificarUsuario(request, response);
                    break;
                case "cerrar":
                    cerrarsession(request, response);
                    break;
            }
        }
    }

    public void verificarUsuario(HttpServletRequest request, HttpServletResponse response){
        try {
            HttpSession session;
            listaErrores.clear();

            String codigoUsuario = request.getParameter("txtCodigoUsuario");
            String contraseniaUsuario = request.getParameter("txtContraseniaUsuario");

            if (Validaciones.isEmpty(codigoUsuario)) {
                listaErrores.add("El campo Código de Usuario está vacío");
            }

            if (Validaciones.isEmpty(contraseniaUsuario)) {
                listaErrores.add("El campo Contraseña está vacío");
            }

            if (!listaErrores.isEmpty()) {
                request.setAttribute("listaErrores", listaErrores);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            int result = loginModel.verificarUsuario(codigoUsuario, contraseniaUsuario);
            if(result > 0){
                switch (result){
                    //Generar la sesion para el administrador
                    case 1:
                        session = request.getSession();
                        session.setAttribute("administrador", codigoUsuario);
                        response.sendRedirect("AdministradorController?op=menuadministrador");
                        break;
                    //Generar la sesion para el jefe de desarrollo
                    case 2:
                        session = request.getSession();
                        session.setAttribute("jefedesarrollo", codigoUsuario);
                        response.sendRedirect("JefeDesarrolloController?op=menujefedesarrollo");
                        break;
                    //Genera la sesion para la jefatura
                    case 3:
                        session = request.getSession();
                        session.setAttribute("jefatura", codigoUsuario);
                        response.sendRedirect("JefaturaController?op=listarcasos");
                        break;
                    //Genera la sesion para los programadores
                    case 4:
                        session = request.getSession();
                        session.setAttribute("programador", codigoUsuario);
                        response.sendRedirect("ProgramadorController?op=menuprogramador");
                        break;
                    case 5:
                        session = request.getSession();
                        session.setAttribute("tester", codigoUsuario);
                        response.sendRedirect("TesterController?op=listacasos");
                        break;
                    default:
                        request.setAttribute("listaErrores", "Hubo un error, porfavor intetnelo nuevamente...");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;
                }
            } else {
                listaErrores.clear();
                listaErrores.add("El usuario o contraseña es incorrecto.");
                request.setAttribute("listaErrores", listaErrores);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch ( SQLException | ServletException | IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cerrarsession(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            session.invalidate();
            request.getSession().setAttribute("exito", "La sesion ha sido cerrada...");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response){
        try {
            HttpSession session = request.getSession();
            session.invalidate();
            request.setAttribute("listaErrores", "Usted ha intentado acceder a una página restringida... Su sesion ha sido cerrada");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch ( ServletException | IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
