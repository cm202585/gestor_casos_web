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
    <title>Lista de bitacoras</title>
    <jsp:include page="../cabecera.jsp"/>
</head>
<body>
<header>
    <jsp:include page="menu.jsp"/>
</header>

<div class="container">
    <div class="row">
        <h3 class="text-center">Lista de bitacoras creadas.</h3>
    </div>

    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped table-bordered table-hover" id="tabla">
                <thead class="thead-dark">
                <tr>
                    <th class="text-center">Descripción</th>
                    <th class="text-center">Fecha</th>
                    <th class="text-center">Programador asignado</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.bitacoras}" var="bitacoras">
                    <tr>
                        <td class="text-center">${bitacoras.descripcionBitacora}</td>
                        <td class="text-center">${bitacoras.fechaBitacora}</td>
                        <td class="text-center">${bitacoras.idUsuarioBitacora}</td>
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