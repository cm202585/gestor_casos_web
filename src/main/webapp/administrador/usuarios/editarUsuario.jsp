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
    <title>Editar usuario</title>
    <jsp:include page="../../cabecera.jsp"/>
</head>
<body>
<jsp:include page="../menu.jsp"/>

<div class="container">

    <div class="row">
        <h3 class="mb-4">Editar usuario <i class="glyphicon glyphicon-user"></i></h3>
    </div>

    <c:if test="${not empty listaErrores}">
        <div class="alert alert-danger">
            <ul>
                <c:forEach var="errores"  items="${listaErrores}">
                    <li>${errores}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <div class="col-md-7">
        <form role="form" action="${pageContext.request.contextPath}/AdministradorController?op=actualizarusuario" method="POST">
            <div class="form-group">
                <label for="codigoUsuario">Código del usuario <i class="glyphicon glyphicon-barcode"></i>:</label>
                <input type="text" class="form-control" name="codigoUsuario"  id="codigoUsuario" value="${usuario.idUsuarioString}" readonly>
            </div>

            <div class="form-group">
                <label for="nombreUsuario">Nombre del usuario <i class="glyphicon glyphicon-user"></i>:</label>
                <input type="text" class="form-control" name="nombreUsuario"  id="nombreUsuario" value="${usuario.nombreUsuario}" placeholder="Ingresa el nombre del usuario">
            </div>

            <div class="form-group">
                <label for="apellidoUsuario">Apellido del usuario <i class="glyphicon glyphicon-user"></i>:</label>
                <input type="text" class="form-control" name="apellidoUsuario"  id="apellidoUsuario" value="${usuario.apellidoUsuario}" placeholder="Ingresa el apellido del usuario">
            </div>

            <div class="form-group">
                <label for="telefonoUsuario">Teléfono del usuario <i class="glyphicon glyphicon-phone"></i>:</label>
                <input type="text" class="form-control" name="telefonoUsuario"  id="telefonoUsuario" value="${usuario.telefonoUsuario}" placeholder="Ingresa el teléfono del usuario">
            </div>

            <div class="form-group">
                <label for="correoUsuario">Correo del usuario <i class="glyphicon glyphicon-envelope"></i>:</label>
                <input type="email" class="form-control" name="correoUsuario"  id="correoUsuario" value="${usuario.correoUsuario}" placeholder="Ingresa el correo del usuario">
            </div>

            <div class="form-group">
                <label for="duiUsuario">DUI <i class="glyphicon glyphicon-credit-card"></i>:</label>
                <input type="text" class="form-control" name="duiUsuario"  id="duiUsuario" value="${usuario.duiUsuario}" placeholder="Ingresa el DUI del usuario">
            </div>

            <div class="form-group">
                <label for="contraseniaUsuario">Contraseña del usuario <i class="glyphicon glyphicon-lock"></i>:</label>
                <input type="password" class="form-control" name="contraseniaUsuario"  id="contraseniaUsuario" value="${usuario.contraseniaUsuario}" placeholder="Ingresa la contraseña del usuario">
            </div>

            <div class="form-group">
                <label for="areaFuncional">Área funcional <i class="glyphicon glyphicon-briefcase"></i>:</label>
                <select name="areaFuncional" id="areaFuncional" class="form-control" onchange="obtenerJefeAreas()">
                    <c:forEach items="${requestScope.areaFuncional}" var="area">
                        <option value="${area.idAreaFuncional}">${area.areaFuncional}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="tipoUsuario">Tipo de usuario <i class="glyphicon glyphicon-user"></i>:</label>
                <select name="tipoUsuario" id="tipoUsuario" class="form-control" onchange="obtenerJefeAreas()">
                    <c:forEach items="${requestScope.tipoUsuario}" var="tipoUsuario">
                        <option value="${tipoUsuario.idTipoUsuario}">${tipoUsuario.nombreTipoUsuario}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="jefeUsuario">Jefe <i class="glyphicon glyphicon-king"></i>:</label>
                <select name="jefeUsuario" id="jefeUsuario" class="form-control"></select>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-info mr-2">Guardar <i class="glyphicon glyphicon-floppy-disk"></i></button>
                <a class="btn btn-danger" href="${pageContext.request.contextPath}/AdministradorController?op=listarusuarios">Cancelar <i class="glyphicon glyphicon-remove"></i></a>
            </div>
        </form>
    </div>
</div>

</body>

<script>
    function obtenerJefeAreas() {
        var tipo = $("#tipoUsuario").val();
        console.log(tipo);

        if (tipo != 4 && tipo != 5) {
            $("#jefeUsuario").prop("disabled", true); //Desabilitamos el select para no enviarlo en nuestro formulario
            $('#jefeUsuario').empty(); // Limpiar select
        } else {
            var area = $("#areaFuncional").val();

            $("#jefeUsuario").prop("disabled", false);
            $.ajax({
                url: "${pageContext.request.contextPath}/AdministradorController?op=listarjefes&area=" + area,
                type: "GET",
                dataType: "JSON",
                success: function (data) {
                    $('#jefeUsuario').empty(); // Limpiar select antes de agregar nuevas opciones

                    // Iterar sobre los datos recibidos
                    $.each(data, function(index, jefe) {
                        // Agregar una opción para cada jefe
                        $('#jefeUsuario').append($('<option>', {
                            value: jefe.codigoJefe,
                            text: jefe.nombreJefe
                        }));
                    });
                }
            });
        }
    }
</script>

</html>
