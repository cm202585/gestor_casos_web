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
    <title>Lista de areas</title>
    <jsp:include page="../../cabecera.jsp"/>
</head>
<body>

<jsp:include page="../menu.jsp"/>

<div class="container">
    <div class="row">
        <h3 class="text-center">Lista de áreas funcionales</h3>
    </div>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <form role="form" action="${pageContext.request.contextPath}/AdministradorController?op=insertararea" method="post">
                <div class="form-group">
                    <label for="txtNombreArea">Nombre del área:</label>
                    <div class="input-group">
                        <input type="text" class="form-control" name="txtNombreArea" id="txtNombreArea" placeholder="Ingresa el nombre del área" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                    </div>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-info">Guardar</button>
                </div>
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <table class="table table-bordered table-hover" id="tabla">
                <thead class="thead-dark">
                <tr>
                    <th class="text-center">Código del área</th>
                    <th class="text-center">Nombre del área</th>
                    <th class="text-center">Operaciones</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.listarareas}" var="areas">
                        <tr>
                            <td class="text-center">${areas.idAreaFuncional}</td>
                            <td>${areas.areaFuncional}</td>
                            <td class="text-center">
                                <a title="Modificar" class="btn btn-primary btn-sm" href="javascript:detallesAreas('${areas.idAreaFuncional}')">
                                    <span class="glyphicon glyphicon-search"></span> Modificar
                                </a>
                                <a title="Eliminar" class="btn btn-danger btn-sm" onclick="eliminar('${areas.idAreaFuncional}')">
                                    <span class="glyphicon glyphicon-trash"></span> Eliminar
                                </a>
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
                <h5 class="modal-title">EDITAR ÁREA</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form role="form" action="${pageContext.request.contextPath}/AdministradorController?op=editararea" method="post">
                        <div class="form-group row">
                            <label for="idArea" class="col-sm-4 col-form-label">Id área:</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="idArea" id="idArea" readonly>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="nombreArea" class="col-sm-4 col-form-label">Nombre del área:</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="nombreArea" id="nombreArea" required>
                            </div>
                        </div>

                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-info">Editar</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>



<script>
    $(document).ready(function(){
        $('#tabla').DataTable();
    });

    function eliminar(id) {
        alertify.confirm("¿Realmente desea eliminar a esta area funcional?", function (e) {
            if (e) {
                location.href = "AdministradorController?op=eliminararea&id=" + id;
            }
        })
    }

    function detallesAreas(id){
        $.ajax({
            url: "${pageContext.request.contextPath}/AdministradorController?op=detallesarea&id=" + id,
            type: "GET",
            dataType: "JSON",
            success: function (data){
                $('#idArea').val(data.idArea);
                $('#nombreArea').val(data.nombreArea);
                $('#modal').modal('show');
            }
        });
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
