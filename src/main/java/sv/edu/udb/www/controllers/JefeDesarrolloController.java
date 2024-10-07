package sv.edu.udb.www.controllers;

import org.json.simple.JSONArray;
import sv.edu.udb.www.beans.CasoBeans;
import sv.edu.udb.www.beans.UsuarioBeans;
import sv.edu.udb.www.models.JefeDesarrolloModel;
import sv.edu.udb.www.models.UtilsModel;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import org.json.simple.JSONObject;
import java.io.*;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet(name = "JefeDesarrolloController", value = "/JefeDesarrolloController")
@MultipartConfig
public class JefeDesarrolloController extends HttpServlet {
    JefeDesarrolloModel jefeDesarrolloModel = new JefeDesarrolloModel();
    UtilsModel utilsModel = new UtilsModel();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            //Colocar el caso como vencido
            utilsModel.actualizarCasoVencido();

            HttpSession session = request.getSession();
            String codigoUsuario = (String) session.getAttribute("jefedesarrollo");

            if (request.getParameter("op") == null) {
                menuJefeDesarrollo(request, response, codigoUsuario);
                return;
            }

            String operacion = request.getParameter("op");
            switch (operacion){
                case "menujefedesarrollo":
                    menuJefeDesarrollo(request, response, codigoUsuario);
                    break;
                case "registrocasos":
                    registroCasos(request, response, codigoUsuario);
                    break;
                case "casospendientes":
                    verCasosPendientes(request, response, codigoUsuario);
                    break;
                case "obtenercaso":
                    obtenerCaso(request, response);
                    break;
                case "obtenerprogramadores":
                    obtenerListaProgramadores(response, codigoUsuario);
                    break;
                case "obtenertester":
                    obtenerListaTesters(response, codigoUsuario);
                    break;
                case "aprobarcaso":
                    aprobarCaso(request, response);
                    break;
                case "rechazarcaso":
                    rechazarCaso(request, response);
                    break;
                case "verpdf":
                    verPdfCaso(request, response);
                    break;
                case "verbitacoras":
                    verBitacoraCaso(request, response);
                    break;
                case "casosasignar":
                    casosProbadores(request, response, codigoUsuario);
                    break;
                case "asignartester":
                    asignarTesterCaso(request, response);
                    break;
            }
        } catch (SQLException ex){
            Logger.getLogger(JefaturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void menuJefeDesarrollo(HttpServletRequest request, HttpServletResponse response, String codigoUsuario){
        try {
            //Obtenemos el area del usuario
            int areaUsuario = utilsModel.obtenerAreaUsuarioInt(codigoUsuario);
            request.setAttribute("casosPendientes", jefeDesarrolloModel.totalCasosPendientes(areaUsuario));
            request.setAttribute("totalCasos", jefeDesarrolloModel.totalCasos(areaUsuario));
            request.setAttribute("totalCasosProbadores", jefeDesarrolloModel.totalCasosProbadores(areaUsuario));
            request.getRequestDispatcher("/JefeDesarrollo/menuJefeDesarrollo.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(JefeDesarrolloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void verCasosPendientes(HttpServletRequest request, HttpServletResponse response, String codigoUsuario){
        try {
            String area = utilsModel.obtenerAreaUsuarioSring(codigoUsuario);
            request.setAttribute("listarCasos", jefeDesarrolloModel.obtenerCasosPendientes(area));
            request.getRequestDispatcher("/JefeDesarrollo/gestorCasos.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(JefeDesarrolloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void registroCasos(HttpServletRequest request, HttpServletResponse response, String codigoUsuario){
        try {
            String area = utilsModel.obtenerAreaUsuarioSring(codigoUsuario);
            request.setAttribute("listarCasos", jefeDesarrolloModel.obtenerRegistroCasos(area));
            request.getRequestDispatcher("/JefeDesarrollo/registroCasos.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(JefeDesarrolloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerCaso(HttpServletRequest request, HttpServletResponse response){
        try {
            PrintWriter out = response.getWriter();
            String codigo = request.getParameter("id");
            CasoBeans casoBeans = jefeDesarrolloModel.obtenerCaso(codigo);
            JSONObject json = new JSONObject();
            json.put("codigoCaso", casoBeans.getIdCasos());
            json.put("descripcionCaso", casoBeans.getDetallesCaso());
            out.println(json);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(JefeDesarrolloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerListaProgramadores(HttpServletResponse response, String codigoUsuario){
        try {
            PrintWriter out = response.getWriter();
            List<UsuarioBeans> listarProgramadores = jefeDesarrolloModel.obtenerProgramadoresAsignados(codigoUsuario);
            JSONArray jsonArray = new JSONArray();
            for(UsuarioBeans programadores : listarProgramadores){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codigoProgramdor", programadores.getIdUsuarioString());
                jsonObject.put("nombreProgramador", programadores.getNombreUsuario());
                jsonArray.add(jsonObject);
            }
            out.println(jsonArray);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(JefeDesarrolloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerListaTesters(HttpServletResponse response, String codigoUsuario){
        try {
            PrintWriter out = response.getWriter();
            List<UsuarioBeans> listarTesters = jefeDesarrolloModel.obtenerTesterAsignados(codigoUsuario);
            JSONArray jsonArray = new JSONArray();
            for(UsuarioBeans tester : listarTesters){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codigoTester", tester.getIdUsuarioString());
                jsonObject.put("nombreTester", tester.getNombreUsuario());
                jsonArray.add(jsonObject);
            }
            out.println(jsonArray);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(JefeDesarrolloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void aprobarCaso(HttpServletRequest request, HttpServletResponse response){
        CasoBeans casoBeans = new CasoBeans();
        Date fechaActual =  new java.sql.Date(new java.util.Date().getTime());
        try {
            // Recupera la parte del formulario que contiene el valor del campo "codigoA"
            Part codigoCaso = request.getPart("codigoA");
            // Crea un BufferedReader para leer el contenido de la parte
            BufferedReader readerCodigoCaso = new BufferedReader(new InputStreamReader(codigoCaso.getInputStream(), "UTF-8"));
            // Lee el contenido de la parte, que es el valor del campo "codigoA"
            String codigo = readerCodigoCaso.readLine();

            //Primero verificamos que la fecha de inicio sea mayor a la fecha actual
            Part fechaInicio = request.getPart("fechaInicio");
            BufferedReader readerFechaInicio = new BufferedReader(new InputStreamReader(fechaInicio.getInputStream(), "UTF-8"));
            String fechaInicioStr = readerFechaInicio.readLine();
            Date fechaInicioDate = java.sql.Date.valueOf(fechaInicioStr);

            if (!fechaInicioDate.after(fechaActual)) {
                request.getSession().setAttribute("error", "La fecha de inicio debe ser posterior a la fecha actual");
                response.sendRedirect(request.getContextPath() + "/JefeDesarrolloController?op=casospendientes");
                return;
            }

            Part fechaVencimiento = request.getPart("fechaVencimiento");
            BufferedReader readerFechaVencimiento = new BufferedReader(new InputStreamReader(fechaVencimiento.getInputStream(), "UTF-8"));
            String fechaVencimientoStr = readerFechaVencimiento.readLine();
            Date fechaVencimientoDate = java.sql.Date.valueOf(fechaVencimientoStr);
            if (!fechaVencimientoDate.after(fechaInicioDate)) {
                request.getSession().setAttribute("error", "La fecha de vencimiento debe ser posterior a la fecha de inicio");
                response.sendRedirect(request.getContextPath() + "/JefeDesarrolloController?op=casospendientes");
                return;
            }

            Part programador = request.getPart("programador");
            BufferedReader readerProgramador = new BufferedReader(new InputStreamReader(programador.getInputStream(), "UTF-8"));

            //Creamos el objeto con los datos que ya tenemos
            casoBeans.setIdCasos(codigo);
            casoBeans.setFechaInicio(fechaInicioDate);
            casoBeans.setFechaVencimiento(fechaVencimientoDate);
            casoBeans.setIdUsuarioProgramador(readerProgramador.readLine());

            // Obtiene el archivo subido
            Part pdfFile = request.getPart("pdfFile");
            if (pdfFile != null && pdfFile.getContentType().equals("application/pdf")) {
                // Convertir el archivo PDF a una cadena Base64
                String pdfBase64 = convertirPdfABase64(pdfFile);
                // Guardar la cadena Base64 en la base de datos
                casoBeans.setPdfCaso(pdfBase64);
                if (jefeDesarrolloModel.aprobarCaso(casoBeans) > 0) {
                    request.getSession().setAttribute("exito", "El caso fue aprobado con éxito.");
                } else {
                    request.getSession().setAttribute("error", "Hubo un error al momento de aprobar el caso.");
                }
            } else {
                casoBeans.setPdfCaso(null);
                if(jefeDesarrolloModel.aprobarCaso(casoBeans) > 0){
                    request.getSession().setAttribute("exito", "El caso fue aprobado con exito.");
                } else {
                    request.getSession().setAttribute("error", "Hubo un error al momento de aprobar el caso");
                }
            }
            response.sendRedirect(request.getContextPath() + "/JefeDesarrolloController?op=casospendientes");
        } catch ( SQLException | IOException | ServletException ex) {
            Logger.getLogger(JefeDesarrolloController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private String convertirPdfABase64(Part pdfPart) throws IOException {
        try (InputStream pdfInputStream = pdfPart.getInputStream(); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = pdfInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        }
    }

    private void rechazarCaso(HttpServletRequest request, HttpServletResponse response){
        try {
            String codigo = request.getParameter("codigoR");
            String innformacion = request.getParameter("infoRechazo");
            if(jefeDesarrolloModel.rechazarCaso(codigo, innformacion) > 0){
                request.getSession().setAttribute("exito", "El caso fue rechazado con exito.");
            } else {
                request.getSession().setAttribute("error", "Hubo un error al momento de rechazar el caso");
            }
            response.sendRedirect(request.getContextPath() + "/JefeDesarrolloController?op=casospendientes");
        } catch ( SQLException | IOException ex) {
            Logger.getLogger(JefeDesarrolloController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void verBitacoraCaso(HttpServletRequest request, HttpServletResponse response){
        try{
            request.setAttribute("bitacoras", utilsModel.obtenerBitacorasCaso(request.getParameter("id")));
            request.getRequestDispatcher("/JefeDesarrollo/bitacoras.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException ex) {
            Logger.getLogger(ProgramadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void casosProbadores(HttpServletRequest request, HttpServletResponse response, String codigoUsuario) {
        try {
            String area = utilsModel.obtenerAreaUsuarioSring(codigoUsuario);
            request.setAttribute("listarCasos", jefeDesarrolloModel.obtenerCasosProbadores(area));
            request.getRequestDispatcher("/JefeDesarrollo/casosProbadores.jsp").forward(request, response);
        } catch (ServletException | SQLException | IOException ex) {
            Logger.getLogger(ProgramadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void asignarTesterCaso(HttpServletRequest request, HttpServletResponse response){
        try {
            String codigoCaso = request.getParameter("codigo");
            String codigoUsuario = request.getParameter("tester");
            if(jefeDesarrolloModel.asignarProbador(codigoCaso, codigoUsuario) > 0){
                request.getSession().setAttribute("exito","El caso fue asignado a un tester de manera correcta.");
            } else {
                request.getSession().setAttribute("error", "Hubo un error al momento de asignar el caso a un tester.");
            }
            response.sendRedirect(request.getContextPath() + "/JefeDesarrolloController?op=casosasignar");
        } catch (SQLException | IOException ex) {
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
