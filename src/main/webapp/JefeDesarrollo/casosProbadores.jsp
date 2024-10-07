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
    <title>Registro de casos para asignar a probadores</title>
    <jsp:include page="../cabecera.jsp"/>
</head>
<body>
<header>
    <jsp:include page="menu.jsp"/>
</header>

<div class="container">
    <div class="row">
        <h3 class="text-center">Lista de casos probadores.</h3>
    </div>

    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped table-bordered table-hover" id="tabla">
                <thead class="thead-dark">
                <tr>
                    <th class="text-center">Código del caso</th>
                    <th class="text-center">Fecha de registro</th>
                    <th class="text-center">Detalles</th>
                    <th class="text-center">Usuario que aperturo</th>
                    <th class="text-center">Tester asignado</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.listarCasos}" var="casos">
                    <tr>
                        <td class="text-center">${casos.idCasos}</td>
                        <td class="text-center">${casos.fechaRegistro}</td>
                        <td>${casos.detallesCaso}</td>
                        <td class="text-center">${casos.usuarioBeans.nombreUsuario}</td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${casos.idUsuarioTester != null}">
                                    ${casos.idUsuarioTester}
                                </c:when>
                                <c:otherwise>
                                    <span class="text-danger">Por asignar</span>
                                    <button title="Asignar Probador" class="btn btn-primary btn-sm" onclick="asignarProbador('${casos.idCasos}')">
                                        <i class="glyphicon glyphicon-user"></i> Asignar
                                    </button>
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

<div class="modal" id="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Asignar probador</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form role="form" action="${pageContext.request.contextPath}/JefeDesarrolloController?op=asignartester" method="post">
                        <div class="form-group row">
                            <label for="codigo" class="col-sm-4 col-form-label">Codigo del caso:</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="codigo" id="codigo" readonly>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="tester" class="col-sm-4 col-form-label">Probadores <span class="glyphicon glyphicon-king"></span>:</label>
                            <div class="col-sm-8">
                                <select name="tester" id="tester" class="form-control" required></select>
                            </div>
                        </div>

                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-info">Asignar</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function(){
        $('#tabla').DataTable();
    });


    function asignarProbador(id){
        $('#codigo').val(id);
        $.ajax({
            url: "${pageContext.request.contextPath}/JefeDesarrolloController?op=obtenertester",
            type: "GET",
            dataType: "JSON",
            success: function (data) {
                // Limpiar select antes de agregar nuevas opciones
                $('#tester').empty();
                // Iterar sobre los datos recibidos programador
                $.each(data, function(index, tester) {
                    $('#tester').append($('<option>', {
                        value: tester.codigoTester,
                        text: tester.nombreTester
                    }));
                });
                $('#modal').modal('show');
            }
        });
    }

    <c:if test="${not empty error}">
        alertify.error('${error}');
        <c:set var="error" value="" scope="session" />
    </c:if>

    <c:if test="${not empty exito}">
        alertify.success('${exito}');
        <c:set var="exito" value="" scope="session" />
    </c:if>

</script>



</body>
</html>
