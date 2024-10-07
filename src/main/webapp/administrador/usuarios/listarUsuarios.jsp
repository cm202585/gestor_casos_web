<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Verificar si el administrador está logeado
    if (session.getAttribute("administrador") == null) {
        // Si no está logeado, redirigir al inicio de sesión
        response.sendRedirect("LoginController?op=error");
        return;
    }
%>

<html lang="es">
<head>
    <title>Menu administrador</title>
    <jsp:include page="../../cabecera.jsp"/>
</head>
<body>
<jsp:include page="../menu.jsp"/>

<div class="container">
    <div class="row">
        <h3>Lista de usuarios</h3>

        <div class="row">
            <div class="col-md-12">
                <a type="button" class="btn btn-primary btn-md" href="${pageContext.request.contextPath}/AdministradorController?op=nuevousuario"> Nuevo usuario</a>

                <table class="table table-striped table-bordered table-hover" id="tabla">
                    <thead>
                    <tr>
                        <th>Codigo del usuario</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Telefono</th>
                        <th>DUI</th>
                        <th>Correo</th>
                        <th>Area funcional</th>
                        <th>Tipo de usuario</th>
                        <th>Jefe asignado</th>
                        <th>Contraseña</th>
                        <th>Operaciones</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.listarUsuarios}" var="usuario">
                            <tr>
                                <td>${usuario.idUsuarioString}</td>
                                <td>${usuario.nombreUsuario}</td>
                                <td>${usuario.apellidoUsuario}</td>
                                <td>${usuario.telefonoUsuario}</td>
                                <td>${usuario.duiUsuario}</td>
                                <td>${usuario.correoUsuario}</td>
                                <td>${usuario.areaFuncional.areaFuncional}</td>
                                <td>${usuario.tipoUsuario.nombreTipoUsuario}</td>
                                <td class="text-center">${usuario.idJefeUsuario != null ? usuario.idJefeUsuario : "Este usuario no tiene jefe asignado"}</td>
                                <td>${usuario.contraseniaUsuario}</td>
                                <td>
                                    <a title="modificar" class="btn btn-primary" href="${contextPath}/AdministradorController?op=obtenerusuario&id=${usuario.idUsuarioString}"><span class="glyphicon glyphicon-edit"></span></a>
                                    <a title="eliminar" class="btn btn-danger" onclick="eliminar('${usuario.idUsuarioString}')"><span class="glyphicon glyphicon-trash"></span></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function(){
        $('#tabla').DataTable();
    });

    function eliminar(id) {
        alertify.confirm("¿Realmente desea eliminar a este usuario?", function (e) {
            if (e) {
                location.href = "AdministradorController?op=eliminarusuario&id=" + id;
            }
        })
    }

    <c:if test="${not empty error}">
        alertify.error('${error}');
        <c:set var="fracaso" value="" scope="session" />
    </c:if>

    <c:if test="${not empty exito}">
        alertify.success('${exito}');
        <c:set var="exito" value="" scope="session" />
    </c:if>
</script>
</body>
</html>
