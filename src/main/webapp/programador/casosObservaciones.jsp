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
    <title>Casos con observaciones</title>
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
                    <th class="text-center">Estado</th>
                    <th class="text-center">Usuario creador</th>
                    <th class="text-center">Detalles</th>
                    <th class="text-center">Observaciones</th>
                    <th class="text-center">PDF</th>
                    <th class="text-center">Operaciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.casosVencidos}" var="casos">
                    <tr>
                        <td class="text-center">${casos.idCasos}</td>
                        <td class="text-center">${casos.estadoBeans.estadoCaso}</td>
                        <td class="text-center">${casos.usuarioBeans.nombreUsuario}</td>
                        <td class="text-center">${casos.detallesCaso}</td>
                        <td class="text-center">${casos.observacionesCaso}</td>
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
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${casos.estadoBeans.estadoCaso != 'Esperando aprobación de area solicitante'}">
                                    <button type="button" class="btn btn-primary" onclick="crearBitacora('${casos.idCasos}')">
                                        <i class="glyphicon glyphicon-ok"></i> Crear Bitácora
                                    </button>

                                    <button type="button" class="btn btn-primary" onclick="finalizar('${casos.idCasos}')">
                                        <i class="glyphicon glyphicon-ok"></i> Finalizar
                                    </button>
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

<div class="modal" id="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Crear bitacora</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form role="form" action="${pageContext.request.contextPath}/ProgramadorController?op=crearbitacora" method="post">
                        <div class="form-group row">
                            <label for="codigo" class="col-sm-4 col-form-label">Código:</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="codigo" id="codigo" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="descripcion" class="col-sm-4 col-form-label">Descripción:</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" name="descripcion" id="descripcion" required></textarea>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="progreso" class="col-sm-4 col-form-label">Progreso:</label>
                            <div class="col-sm-8">
                                <input type="range" id="progreso" name="progreso" min="0" max="100" class="form-range" step="1">
                                <output id="value"></output>
                            </div>
                        </div>

                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-info mr-2">Ingresar</button>
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

    function crearBitacora(id){
        $.ajax({
            url: "${pageContext.request.contextPath}/ProgramadorController?op=obtenercaso&id=" + id,
            type: "GET",
            dataType: "JSON",
            success: function (data){
                $('#codigo').val(data.codigoCaso);
                $('#progreso').val(data.progresoCaso);
                $('#modal').modal('show');

                const value = document.querySelector("#value");
                const input = document.querySelector("#progreso");
                value.textContent = input.value;
                input.addEventListener("input", (event) => {
                    value.textContent = event.target.value;
                });
            }
        });
    }

    function finalizar(id) {
        alertify.confirm("¿Está seguro de que desea marcar este trabajo como finalizado? Esta acción no se puede deshacer y el caso se pondrá en espera de aprobación de area solicitante.", function (e) {
            if (e) {
                location.href = "ProgramadorController?op=finalizarcaso&id=" + id;
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