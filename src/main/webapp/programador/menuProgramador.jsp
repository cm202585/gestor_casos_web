<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Verificar si el programador está logeado
    if (session.getAttribute("programador") == null) {
        // Si no está logeado, redirigir al inicio de sesión con un error
        response.sendRedirect("LoginController?op=error");
        return;
    }
%>

<html>
<head lang="es">
    <title>Menú programador</title>
    <jsp:include page="../cabecera.jsp"/>
</head>
<body>
    <header>
        <jsp:include page="menu.jsp"/>
    </header>

    <div class="container">
        <div class="row">
            <h2 class="text-center">Menú del programador</h2>
        </div>

        <div class="row">
            <div class="col-lg-4 col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="glyphicon glyphicon-list-alt fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">${requestScope.totalCasos}</div>
                                <div>Registro de casos</div>
                            </div>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/ProgramadorController?op=registrocasos">
                        <div class="panel-footer">
                            <span class="pull-left">Ver casos pendientes</span>
                            <span class="pull-right"><i class="glyphicon glyphicon-chevron-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>

            <div class="col-lg-4 col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="glyphicon glyphicon-list-alt fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">${requestScope.totalCasosVencidos}</div>
                                <div>Registro de casos vencidos</div>
                            </div>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/ProgramadorController?op=casosvencidos">
                        <div class="panel-footer">
                            <span class="pull-left">Ver casos vencidos</span>
                            <span class="pull-right"><i class="glyphicon glyphicon-chevron-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>

            <div class="col-lg-4 col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="glyphicon glyphicon-list-alt fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">${requestScope.totalCasosObservaciones}</div>
                                <div>Registro de casos con observaciones</div>
                            </div>
                        </div>
                    </div>
                    <a href="ProgramadorController?op=casosobservaciones">
                        <div class="panel-footer">
                            <span class="pull-left">Ver casos con observaciones</span>
                            <span class="pull-right"><i class="glyphicon glyphicon-chevron-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>

</body>
</html>