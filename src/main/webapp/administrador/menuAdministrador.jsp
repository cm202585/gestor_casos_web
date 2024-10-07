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

<html>
<head lang="es">
    <title>Menu administrador</title>
    <jsp:include page="../cabecera.jsp"/>
</head>
<body>
    <header>
        <jsp:include page="menu.jsp"/>
    </header>

    <div class="container">
        <div class="row">
            <h2 class="text-center">Menu Administrativo</h2>
        </div>

        <div class="row">
            <div class="col-lg-3 col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="glyphicon glyphicon-user glyphicon-size"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">${requestScope.totalusuarios}</div>
                                <div>Gestor de usuarios</div>
                            </div>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}AdministradorController?op=listarusuarios">
                        <div class="panel-footer">
                            <span class="pull-left">Ver usuarios</span>
                            <span class="pull-right"><span class="glyphicon glyphicon-chevron-right"></span></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>

            <div class="col-lg-3 col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="glyphicon glyphicon-folder-open glyphicon-size"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge">${requestScope.totalareas}</div>
                                <div>Gestor de departamentos</div>
                            </div>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}AdministradorController?op=listarareas">
                        <div class="panel-footer">
                            <span class="pull-left">Ver departamentos</span>
                            <span class="pull-right"><span class="glyphicon glyphicon-chevron-right"></span></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
