package sv.edu.udb.www.controllers;

import sv.edu.udb.www.models.JefaturaModel;
import sv.edu.udb.www.beans.CasoBeans;
import sv.edu.udb.www.models.UtilsModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONObject;

@WebServlet(name = "JefaturaController", value = "/JefaturaController")
public class JefaturaController extends HttpServlet {

    ArrayList<String> listaErrores = new ArrayList<>();
    JefaturaModel jefaturaModel = new JefaturaModel();
    UtilsModel utilsModel = new UtilsModel();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //Colocar el caso como vencido
            utilsModel.actualizarCasoVencido();

            //Esto nos servira para obtener el codigo del usuario que se ha logueado.
            HttpSession session = request.getSession();
            String codigoUsuario = (String) session.getAttribute("jefatura");

            if (request.getParameter("op") == null) {
                listarCasos(request, response, codigoUsuario);
                return;
            }

            String operacion = request.getParameter("op");
            switch (operacion){
                case "listarcasos":
                    listarCasos(request, response, codigoUsuario);
                    break;
                case "insertarcaso":
                    insertarCaso(request, response, codigoUsuario);
                    break;
                case "detallescaso":
                    obtenerCaso(request, response);
                    break;
                case "reenviocaso":
                    reenvioCaso(request, response);
                    break;
                case "verbitacoras":
                    verBitacoraCaso(request, response);
                    break;
            }
        } catch (SQLException ex){
            Logger.getLogger(JefaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarCasos(HttpServletRequest request, HttpServletResponse response, String codigoUsuario){
        try {
            request.setAttribute("listarCasos", jefaturaModel.obtenerCasos(codigoUsuario));
            request.getRequestDispatcher("/jefatura/listarCasos.jsp").forward(request, response);
        } catch ( SQLException | ServletException | IOException ex) {
            Logger.getLogger(JefaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertarCaso(HttpServletRequest request, HttpServletResponse response, String codigoUsuario){
        try {
            //Obtenemos la fecha actual
            java.sql.Date fechaIngreso = new java.sql.Date(new java.util.Date().getTime());
            //Obtenemos el area del usuario
            String area = utilsModel.obtenerAreaUsuarioSring(codigoUsuario);
            String a = area.substring(0, Math.min(area.length(), 3)).toUpperCase();
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyMMdd");
            String fechaFormato = fechaActual.format(formato);

            //Obtenemos un numero aleatorio
            int numeroAleatorio = (int) (Math.random() * 1000);
            //Le damos el formato de 3 digitos
            String numeroFormateado = String.format("%03d", numeroAleatorio);

            String idCaso = a + fechaFormato + numeroFormateado;

            CasoBeans casoBeans = new CasoBeans();
            casoBeans.setIdCasos(idCaso);
            casoBeans.setFechaRegistro(fechaIngreso);
            casoBeans.setDetallesCaso(request.getParameter("descripcion"));
            casoBeans.setIdUsuarioCaso(codigoUsuario);

            if(jefaturaModel.insertarCaso(casoBeans) > 0){
                request.getSession().setAttribute("exito","El caso fue ingresado de manera correcta.");
            } else {
                request.getSession().setAttribute("error", "Hubo un error al momento de ingresar el caso.");
            }
            response.sendRedirect(request.getContextPath() + "/JefaturaController?op=listarcasos");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerCaso(HttpServletRequest request, HttpServletResponse response){
        try {
            PrintWriter out = response.getWriter();
            String codigo = request.getParameter("id");
            CasoBeans casoBeans = jefaturaModel.obtenerDetallesCaso(codigo);
            JSONObject json = new JSONObject();
            json.put("idCaso", casoBeans.getIdCasos());
            json.put("descripcion", casoBeans.getDetallesCaso());
            out.println(json);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(JefaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reenvioCaso(HttpServletRequest request, HttpServletResponse response){
        try {
            String codigo = request.getParameter("idCaso");
            String descripcion = request.getParameter("descripcionCaso");
            if (jefaturaModel.actualizarCaso(codigo, descripcion) > 0){
                request.getSession().setAttribute("exito","El caso fue enviado a su revision de manera correcta.");
            } else {
                request.getSession().setAttribute("error", "Hubo un error al momento de reenviar el caso el caso.");
            }
            response.sendRedirect(request.getContextPath() + "/JefaturaController?op=listarcasos");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(JefaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void verBitacoraCaso(HttpServletRequest request, HttpServletResponse response){
        try{
            request.setAttribute("bitacoras", utilsModel.obtenerBitacorasCaso(request.getParameter("id")));
            request.getRequestDispatcher("/jefatura/bitacoras.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException ex) {
            Logger.getLogger(ProgramadorController.class.getName()).log(Level.SEVERE, null, ex);
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
