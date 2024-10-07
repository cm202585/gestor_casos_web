<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                    data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Desplegar navegación</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}AdministradorController?op=menuadministrador">
                <span class="glyphicon glyphicon-list-alt"></span> MENU ADMINISTRATIVO
            </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                        role="button" aria-haspopup="true"
                        aria-expanded="false">
                        <span class="glyphicon glyphicon-user"></span> Usuarios<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/AdministradorController?op=nuevousuario"><span class="glyphicon glyphicon-plus"></span> Registrar usuario</a></li>
                        <li><a href="${pageContext.request.contextPath}/AdministradorController?op=listarusuarios"><span class="glyphicon glyphicon-list"></span> Ver lista de usuarios</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"
                        role="button" aria-haspopup="true"
                        aria-expanded="false">
                        <span class="glyphicon glyphicon-book"></span> Areas<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/AdministradorController?op=listarareas"><span class="glyphicon glyphicon-list"></span> Ver lista de areas</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/LoginController?op=cerrar"><span class="glyphicon glyphicon-log-out"></span> Cerrar Sesion</a></li>
            </ul>
        </div>
    </div>
</nav>