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
            <a class="navbar-brand" href="${pageContext.request.contextPath}/ProgramadorController?op=menuprogramador">
                <span class="glyphicon glyphicon-list-alt"></span> MENU JEFE DE PROGRAMADOR
            </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/ProgramadorController?op=registrocasos"><span class="glyphicon glyphicon-plus"></span> Ver casos pendientes</a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/LoginController?op=cerrar"><span class="glyphicon glyphicon-log-out"></span> Cerrar Sesión</a></li>
            </ul>
        </div>
    </div>
</nav>