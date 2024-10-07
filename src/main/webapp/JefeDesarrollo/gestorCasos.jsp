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
    <title>Gestor de casos</title>
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
                        <th class="text-center">Detalles</th>
                        <th class="text-center">Usuario que ha aperturado</th>
                        <th class="text-center">Operaciones</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.listarCasos}" var="casos">
                            <tr>
                                <td class="text-center">${casos.idCasos}</td>
                                <td class="text-center">${casos.fechaRegistro}</td>
                                <td class="text-center">${casos.detallesCaso}</td>
                                <td class="text-center">${casos.usuarioBeans.nombreUsuario}</td>
                                <td class="text-center">
                                    <a title="Aprobar" class="btn btn-primary" href="javascript:aprobarCaso('${casos.idCasos}')">
                                        <span class="glyphicon glyphicon-ok"></span>
                                    </a>

                                    <a title="Rechazar" class="btn btn-danger" href="javascript:rechazarCaso('${casos.idCasos}')">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Modal para aprobar el caso -->
    <div class="modal" id="modalAprobarCaso" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Aprobar caso</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <form role="form" action="${pageContext.request.contextPath}/JefeDesarrolloController?op=aprobarcaso" method="post" enctype="multipart/form-data">
                            <div class="form-group row">
                                <label for="codigoA" class="col-sm-4 col-form-label">Código:</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="codigoA" id="codigoA" readonly/>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="descripcionA" class="col-sm-4 col-form-label">Descripción:</label>
                                <div class="col-sm-8">
                                    <textarea class="form-control" name="descripcionA" id="descripcionA" readonly></textarea>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="pdfFile" class="col-sm-4 col-form-label">Adjuntar archivo PDF:</label>
                                <div class="col-sm-8">
                                    <input type="file" class="form-control-file" name="pdfFile" id="pdfFile" accept=".pdf">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="fechaInicio" class="col-sm-4 col-form-label">Fecha de inicio <span class="glyphicon glyphicon-calendar"></span>:</label>
                                <div class="col-sm-8">
                                    <input type="date" class="form-control" name="fechaInicio" id="fechaInicio" value="<%= new java.sql.Date(new java.util.Date().getTime()) %>">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="fechaVencimiento" class="col-sm-4 col-form-label">Fecha de vencimiento <span class="glyphicon glyphicon-calendar"></span>:</label>
                                <div class="col-sm-8">
                                    <input type="date" class="form-control" name="fechaVencimiento" id="fechaVencimiento" value="<%= new java.sql.Date(new java.util.Date().getTime()) %>">
                                </div>
                            </div>


                            <div class="form-group row">
                                <label for="programador" class="col-sm-4 col-form-label">Programadores <span class="glyphicon glyphicon-king"></span>:</label>
                                <div class="col-sm-8">
                                    <select name="programador" id="programador" class="form-control" required></select>
                                </div>
                            </div>

                            <div class="form-group text-center">
                                <button type="submit" class="btn btn-info">Aprobar</button>
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

    <!-- Modal para rechazar el caso -->
    <div class="modal" id="modalRechazarCaso" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Rechazar caso</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <form role="form" action="${pageContext.request.contextPath}/JefeDesarrolloController?op=rechazarcaso" method="post">
                            <div class="form-group row">
                                <label for="codigoR" class="col-sm-4 col-form-label">Código:</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="codigoR" id="codigoR" readonly/>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="descripcionR" class="col-sm-4 col-form-label">Descripción:</label>
                                <div class="col-sm-8">
                                    <textarea class="form-control" name="descripcionR" id="descripcionR" readonly></textarea>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="infoRechazo" class="col-sm-4 col-form-label">Descripción para el rechazo:</label>
                                <div class="col-sm-8">
                                    <textarea class="form-control" name="infoRechazo" id="infoRechazo" required></textarea>
                                </div>
                            </div>

                            <div class="form-group text-center">
                                <button type="submit" class="btn btn-info">Rechazar</button>
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

    function aprobarCaso(id){
        $.ajax({
            url: "${pageContext.request.contextPath}/JefeDesarrolloController?op=obtenercaso&id=" + id,
            type: "GET",
            dataType: "JSON",
            success: function (data){
                $('#codigoA').val(data.codigoCaso);
                $('#descripcionA').val(data.descripcionCaso);
                $('#modalAprobarCaso').modal('show');
            }
        });

        $.ajax({
            url: "${pageContext.request.contextPath}/JefeDesarrolloController?op=obtenerprogramadores",
            type: "GET",
            dataType: "JSON",
            success: function (data) {
                // Limpiar select antes de agregar nuevas opciones
                $('#programador').empty();
                // Iterar sobre los datos recibidos programador
                $.each(data, function(index, programador) {
                    $('#programador').append($('<option>', {
                        value: programador.codigoProgramdor,
                        text: programador.nombreProgramador
                    }));
                });
            }
        });
    }

    function rechazarCaso(id){
        $.ajax({
            url: "${pageContext.request.contextPath}/JefeDesarrolloController?op=obtenercaso&id=" + id,
            type: "GET",
            dataType: "JSON",
            success: function (data){
                $('#codigoR').val(data.codigoCaso);
                $('#descripcionR').val(data.descripcionCaso);
                $('#modalRechazarCaso').modal('show');
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
