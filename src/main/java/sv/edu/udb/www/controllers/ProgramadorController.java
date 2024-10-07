package sv.edu.udb.www.controllers;

import org.json.simple.JSONObject;
import sv.edu.udb.www.beans.BitacoraBeans;
import sv.edu.udb.www.beans.CasoBeans;
import sv.edu.udb.www.models.ProgramadorModel;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import sv.edu.udb.www.models.LoginModel;
import sv.edu.udb.www.models.UtilsModel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ProgramadorController", value = "/ProgramadorController")
public class ProgramadorController extends HttpServlet {

    ProgramadorModel programadorModel = new ProgramadorModel();

    ArrayList<String> listaErrores = new ArrayList<>();
    LoginModel loginModel = new LoginModel();
    UtilsModel utilsModel = new UtilsModel();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try (PrintWriter out = response.getWriter()) {
            //Colocar el caso como vencido
            utilsModel.actualizarCasoVencido();

            HttpSession session = request.getSession();
            String codigoUsuario = (String) session.getAttribute("programador");

            if(request.getParameter("op")==null) {
                listarCasos(request, response, codigoUsuario);
                return;
            }
            String operacion = request.getParameter("op");
            switch (operacion){
                case "menuprogramador":
                    listarCasos(request, response, codigoUsuario);
                    break;
                case "registrocasos":
                    casosPendientes(request, response, codigoUsuario);
                    break;
                case "finalizarcaso":
                    finalizarCaso(request, response);
                    break;
                case "verpdf":
                    verPdfCaso(request, response);
                    break;
                case "obtenercaso":
                    obtenerCaso(request, response);
                    break;
                case "crearbitacora":
                    crearBitacora(request, response, codigoUsuario);
                    break;
                case "casosvencidos":
                    casosVencidos(request, response, codigoUsuario);
                    break;
                case "casosobservaciones":
                    casosObservaciones(request, response, codigoUsuario);
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
            request.setAttribute("totalCasos", programadorModel.totalCasos(codigoUsuario));
            request.setAttribute("totalCasosVencidos", programadorModel.totalCasosVencidos(codigoUsuario));
            request.setAttribute("totalCasosObservaciones", programadorModel.totalCasosObservaciones(codigoUsuario));
            request.getRequestDispatcher("/programador/menuProgramador.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(ProgramadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void finalizarCaso(HttpServletRequest request, HttpServletResponse response){
        try {
            if(programadorModel.finalizarCasoTrabajado(request.getParameter("id")) > 0){
                request.getSession().setAttribute("exito", "El caso fue terminado de manera correcta.");
            } else {
                request.getSession().setAttribute("error", "Hubo un error al momento marcar el caso como terminado.");
            }
            response.sendRedirect(request.getContextPath() + "/ProgramadorController?op=registrocasos");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ProgramadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void casosPendientes(HttpServletRequest request, HttpServletResponse response, String codigoUsuario){
        try {
            request.setAttribute("casosPendientes", programadorModel.obtenerCasosPendientes(codigoUsuario));
            request.getRequestDispatcher("/programador/desarrolloCasos.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(ProgramadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void casosVencidos(HttpServletRequest request, HttpServletResponse response, String codigoUsuario){
        try {
            request.setAttribute("casosVencidos", programadorModel.obtenerCasosVencidos(codigoUsuario));
            request.getRequestDispatcher("/programador/casosVencidos.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(ProgramadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void casosObservaciones(HttpServletRequest request, HttpServletResponse response, String codigoUsuario){
        try {
            request.setAttribute("casosVencidos", programadorModel.obtenerCasosObservaciones(codigoUsuario));
            request.getRequestDispatcher("/programador/casosObservaciones.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(ProgramadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void verPdfCaso(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Obtener el nombre del caso
            String pdfName = request.getParameter("pdfName");
            // Obtener la ubicación completa del archivo desde la base de datos
            String fileLocation = utilsModel.obtenerPdfBase64(pdfName);

            // Decodificar el archivo PDF
            byte[] decodedPdf = Base64.getDecoder().decode(fileLocation);

            // Configurar la respuesta
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Length", String.valueOf(decodedPdf.length));

            // Escribir el PDF decodificado en la respuesta
            try (OutputStream output = response.getOutputStream()) {
                output.write(decodedPdf);
                output.flush();
            }
        } catch (SQLException ex) {
            Logger.getLogger(JefeDesarrolloController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JefeDesarrolloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerCaso(HttpServletRequest request, HttpServletResponse response){
        try {
            PrintWriter out = response.getWriter();
            String codigo = request.getParameter("id");
            CasoBeans casoBeans = programadorModel.obtenerCaso(codigo);
            JSONObject json = new JSONObject();
            json.put("codigoCaso", casoBeans.getIdCasos());
            json.put("progresoCaso", casoBeans.getProgresoCaso());
            out.println(json);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(JefeDesarrolloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearBitacora(HttpServletRequest request, HttpServletResponse response, String codigoUsuario){
        try {
            listaErrores.clear();
            String codigoCaso = request.getParameter("codigo");
            int progreso = Integer.parseInt(request.getParameter("progreso"));
            if(programadorModel.actualizarProgreso(codigoCaso, progreso) < 0){
                listaErrores.add("Hubo un error al momento de actualizar el progreso.");
            }

            BitacoraBeans bitacoraBeans = new BitacoraBeans();
            bitacoraBeans.setIdCasoBitacora(codigoCaso);
            bitacoraBeans.setDescripcionBitacora(request.getParameter("descripcion"));
            bitacoraBeans.setIdUsuarioBitacora(codigoUsuario);
            bitacoraBeans.setFechaBitacora(new java.sql.Date(new java.util.Date().getTime()));
            if(programadorModel.crearBitacora(bitacoraBeans) < 0){
                listaErrores.add("Hubo un error al momento de crear la bitacora.");
            }

            if (listaErrores.size() > 0) {
                request.getSession().setAttribute("error",listaErrores);
                response.sendRedirect(request.getContextPath() + "/ProgramadorController?op=registrocasos");
                return;
            }
            request.getSession().setAttribute("exito", "Se ha creado la bitacora de manera correcta.");
            response.sendRedirect(request.getContextPath() + "/ProgramadorController?op=registrocasos");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ProgramadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void verBitacoraCaso(HttpServletRequest request, HttpServletResponse response){
        try{
            request.setAttribute("bitacoras", utilsModel.obtenerBitacorasCaso(request.getParameter("id")));
            request.getRequestDispatcher("/programador/bitacoras.jsp").forward(request, response);
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
