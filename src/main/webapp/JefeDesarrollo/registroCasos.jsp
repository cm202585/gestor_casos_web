<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Verificar si el jefe de desarrollo está logeado
    if (session.getAttribute("jefedesarrollo") == null) {
        // Si no está logeado, redirigir al inicio de sesión con un error
        response.sendRedirect("LoginController?op=error");
        return;
    }
%>

<html>
<head lang="es">
    <title>Registro de casos</title>
    <jsp:include page="../cabecera.jsp"/>
</head>
<body>
    <header>
        <jsp:include page="menu.jsp"/>
    </header>

    <div class="container">
        <div class="row">
            <h3 class="text-center">Lista de casos por departamento.</h3>
        </div>

        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped table-bordered table-hover" id="tabla">
                    <thead class="thead-dark">
                    <tr>
                        <th class="text-center">Código del caso</th>
                        <th class="text-center">Fecha de registro</th>
                        <th class="text-center">Fecha de inicio</th>
                        <th class="text-center">Fecha de vencimiento</th>
                        <th class="text-center">Fecha de producción</th>
                        <th class="text-center">Detalles</th>
                        <th class="text-center">Estado</th>
                        <th class="text-center">Información de rechazo</th>
                        <th class="text-center">Usuario que ha aperturado</th>
                        <th class="text-center">Programador asignado</th>
                        <th class="text-center">Probador asignado</th>
                        <th class="text-center">PDF</th>
                        <th class="text-center">Ver bitacoras</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.listarCasos}" var="casos">
                        <tr>
                            <td class="text-center">${casos.idCasos}</td>
                            <td class="text-center">${casos.fechaRegistro}</td>
                            <td class="text-center">${casos.fechaInicio != null ? casos.fechaInicio: "El caso no ha sido aprobado."}</td>
                            <td class="text-center">${casos.fechaVencimiento != null ? casos.fechaVencimiento: "El caso no ha sido aprobado."}</td>
                            <td class="text-center">${casos.fechaProduccion != null ? casos.fechaProduccion: "El caso aún no ha finalizado."}</td>
                            <td class="text-center">${casos.detallesCaso}</td>
                            <td class="text-center">${casos.estadoBeans.estadoCaso}</td>
                            <td class="text-center">${casos.infoRechazo != null ? casos.infoRechazo: "No hay información"}</td>
                            <td class="text-center">${casos.idUsuarioCaso}</td>
                            <td class="text-center">${casos.idUsuarioProgramador != null ? casos.idUsuarioProgramador: "No hay un programador asignado."}</td>
                            <td class="text-center">${casos.idUsuarioTester != null ? casos.idUsuarioTester: "No hay un tester asignado."}</td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${casos.pdfCaso != null}">
                                        <a title="verPdf" class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/JefeDesarrolloController?op=verpdf&pdfName=${casos.idCasos}" target="_blank">
                                            <i class="glyphicon glyphicon-repeat"></i> Ver pdf
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <p>No disponible</p>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <a class="btn btn-primary" title="Ver Bitácora" href="${pageContext.request.contextPath}/JefeDesarrolloController?op=verbitacoras&id=${casos.idCasos}">
                                    <i class="glyphicon glyphicon-list-alt"></i> Ver Registro
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

<script>
    $(document).ready(function(){
        $('#tabla').DataTable();
    });
</script>

</body>
</html>
