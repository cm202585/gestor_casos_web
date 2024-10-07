package sv.edu.udb.www.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import sv.edu.udb.www.models.TesterModel;
import sv.edu.udb.www.models.UtilsModel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "TesterController", value = "/TesterController")
public class TesterController extends HttpServlet {

    TesterModel testerModel = new TesterModel();
    UtilsModel utilsModel = new UtilsModel();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            String codigoUsuario = (String) session.getAttribute("tester");
            if (request.getParameter("op") == null) {
                listarCasosPendientes(request, response, codigoUsuario);
                return;
            }

            String operacion = request.getParameter("op");
            switch (operacion){
                case "listacasos":
                    listarCasosPendientes(request, response, codigoUsuario);
                    break;
                case "verpdf":
                    verPdfCaso(request, response);
                    break;
                case "aprobarcaso":
                    aprobarCaso(request, response);
                    break;
                case "rechazarcaso":
                    rechazarCaso(request, response);
                    break;
            }
        }
    }

    private void listarCasosPendientes(HttpServletRequest request, HttpServletResponse response, String codigoUsuario){
        try {
            request.setAttribute("listarCasos", testerModel.obtenerRegistroCasos(codigoUsuario));
            request.getRequestDispatcher("/tester/listarCasos.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(TesterController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TesterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TesterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void aprobarCaso(HttpServletRequest request, HttpServletResponse response){
        try {
            Date fechaActual =  new java.sql.Date(new java.util.Date().getTime());

            String codigo = request.getParameter("codigoA");
            Date fechaProduccion = Date.valueOf(request.getParameter("fecha"));
            if(!fechaProduccion.after(fechaActual)){
                request.getSession().setAttribute("error", "La fecha de produccion debe ser posterior a la fecha actual.");
                response.sendRedirect(request.getContextPath() + "/TesterController?op=listacasos");
                return;
            }

            if(testerModel.aprobarCaso(codigo, fechaProduccion) > 0){
                request.getSession().setAttribute("exito", "El caso fue aprobado con éxito.");
            } else {
                request.getSession().setAttribute("error", "Hubo un error al momento de aprobar el caso.");
            }
            response.sendRedirect(request.getContextPath() + "/TesterController?op=listacasos");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(TesterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void rechazarCaso(HttpServletRequest request, HttpServletResponse response){
        try {
            String codigo = request.getParameter("codigoR");
            String observaciones = request.getParameter("observaciones");
            if(testerModel.rechazarCaso(codigo, observaciones) > 0){
                request.getSession().setAttribute("exito", "El caso fue envaido con observaciones.");
            } else {
                request.getSession().setAttribute("error", "Hubo un error al momento de rechazar el caso.");
            }
            response.sendRedirect(request.getContextPath() + "/TesterController?op=listacasos");
        } catch (SQLException | IOException ex) {
            Logger.getLogger(TesterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
