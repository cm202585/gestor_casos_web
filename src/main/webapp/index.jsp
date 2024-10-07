<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Iniciar sesion</title>
    <jsp:include page="/cabecera.jsp"/>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card mt-5">
                <div class="card-header bg-primary text-white">
                    <h3 class="text-center"><span class="glyphicon glyphicon-log-in"></span> Iniciar sesión</h3>
                </div>
                <div class="card-body">
                    <c:if test="${not empty listaErrores}">
                        <div class="alert alert-danger" role="alert">
                            <ul class="mb-0">
                                <c:forEach var="error" items="${listaErrores}">
                                    <li><span class="glyphicon glyphicon-exclamation-sign"></span> ${error}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/LoginController?op=verificar" method="post">
                        <div class="form-group">
                            <label for="txtCodigoUsuario"><span class="glyphicon glyphicon-user"></span> Usuario:</label>
                            <input type="text" class="form-control" id="txtCodigoUsuario" name="txtCodigoUsuario" placeholder="Ingrese su código" >
                        </div>
                        <div class="form-group">
                            <label for="txtContraseniaUsuario"><span class="glyphicon glyphicon-lock"></span> Contraseña:</label>
                            <input type="password" class="form-control" id="txtContraseniaUsuario" name="txtContraseniaUsuario" placeholder="Ingrese su contraseña" >
                        </div>
                        <button type="submit" class="btn btn-primary btn-block"><span class="glyphicon glyphicon-log-in"></span> Iniciar sesión</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    <c:if test="${not empty exito}">
        alertify.success('${exito}');
        <c:set var="exito" value="" scope="session" />
    </c:if>
</script>


</body>
</html>