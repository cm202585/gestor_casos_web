<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Verificar si el jefe de desarrollo está logeado
    if (session.getAttribute("programador") == null) {
        // Si no está logeado, redirigir al inicio de sesión con un error
        response.sendRedirect("LoginController?op=error");
        return;
    }
%>

<html>
<head lang="es">
    <title>Casos vencidos</title>
    <jsp:include page="../cabecera.jsp"/>
</head>
<body>
<header>
    <jsp:include page="menu.jsp"/>
</header>

<div class="container">
    <div class="row">
        <h3 class="text-center">Lista de casos pendientes.</h3>
    </div>

    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped table-bordered table-hover" id="tabla">
                <thead class="thead-dark">
                <tr>
                    <th class="text-center">Código del caso</th>
                    <th class="text-center">Fecha de registro</th>
                    <th class="text-center">Fecha de vencimiento</th>
                    <th class="text-center">Estado</th>
                    <th class="text-center">Usuario creador</th>
                    <th class="text-center">Detalles</th>
                    <th class="text-center">PDF</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.casosVencidos}" var="casos">
                    <tr>
                        <td class="text-center">${casos.idCasos}</td>
                        <td class="text-center">${casos.fechaRegistro}</td>
                        <td class="text-center">${casos.fechaVencimiento}</td>
                        <td class="text-center">${casos.estadoBeans.estadoCaso}</td>
                        <td class="text-center">${casos.usuarioBeans.nombreUsuario}</td>
                        <td class="text-center">${casos.detallesCaso}</td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${casos.pdfCaso != null}">
                                    <a title="verPdf" class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/ProgramadorController?op=verpdf&pdfName=${casos.idCasos}" target="_blank">
                                        <i class="glyphicon glyphicon-repeat"></i> Ver pdf
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <p>No disponible</p>
                                </c:otherwise>
                            </c:choose>
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