<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es" ng-app="app">
<head>
<!-- 	<meta charset="ISO-8859-1"> -->
	<title>SARC II Plantilla base</title>
	<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css" />">
	<link href="<c:url value="/css/sb-admin-2.css" />" rel="stylesheet"><!-- Estilos de SBadmin 2 -->
	<link href="<c:url value="/fonts/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css"><!-- Fuente (SBadmin 2) -->
	<link rel="stylesheet" href="<c:url value="/css/sarcII.css" />"><!-- Estilos adicionales de SerWeb01 -->
	<link rel="stylesheet" href="<c:url value="/css/bootstrap-datetimepicker.css" />"><!-- Estilos Date Time Picker -->
	<link rel="stylesheet" href="<c:url value="/css/dataTables.bootstrap.css" />"><!-- Estilos DataTable -->
	<!--Estilos de Wizard -->
	<link href="<c:url value="/css/bootstrap-wizard.css" />" rel="stylesheet" />
	<link href="<c:url value="/css/chosen.css" />" rel="stylesheet" />
	<link href="<c:url value="/site/css/propiasSistema.css" />" rel="stylesheet" />
	<link href="<c:url value="/site/css/angular-tooltips.css" />" rel="stylesheet" />
	<script>
 	  function preventBack(){window.history.forward();}
	  setTimeout("preventBack()", 0);
//  	  alert("going back");
 	  window.onunload=function(){null};
	</script>
</head>
<body>
	<header id="header" class="collapse in cabecero"><!-- Cabecero -->
		<div class="container-fluid">
			<div class="row oficina">
				<span id="separador"></span>
				<div class="col-sm-2">
					<img src="<c:url value="/img/logocne.png" />">
				</div>
				<div class="col-sm-7" id="paddingTop_3">
					<span class="nombreSistema">Sistema Automatizado del Registro Civil Venezolano</span>
					<br>
					<span>${mainForm.oficina}</span>
				</div>
				<div class="col-sm-3">
					<div class="estadoDelSistema">
						Estado de la conexi&oacute;n: <b class="ok"><span class="glyphicon glyphicon-ok-sign"></span> En L&iacute;nea</b>
					</div>
					<div ng-app="tiempo" id="paddingTop_8">
						<div style="margin-top: 5px;">
							<div style="margin-top: 5px;" ng-controller="fechaHora">
								<span>{{fecha | date:'dd'}} de {{fecha | date:'MMMM'}} del {{fecha | date:'yyyy'}}</span> &ensp;
								<span>{{fecha | date: 'h:mm:ss'}}</span>
								<br>
								<span id="font400">${mainForm.nombreUsuario}</span>
								<br>
								<span id="font400">${mainForm.cargoUsuario}</span>
							</div>
						</div>
					</div>
				</div>
				<span id="separador"></span>
			</div>
		</div>
	</header>
	<nav id="barraNavegacion" class="navbar navbar-inverse navbar-sarc"><!-- Barra de navegación -->
		<div class="container-fluid" ng-controller="menuController">
			<ul class="nav navbar-nav">
<!-- 				<li><a href="#/atencionComunitaria"><span style="font-size: 2.5ex;" class="fa fa-group"></span></a></li> -->
				<li>
					<a ng-click="menu('atencionComunitaria')">
						<span style="font-size: 2.5ex;" class="fa fa-group"></span>
					</a>
				</li>
				<li><a ng-click="menu('general')"><span class="fa fa-book"></span> Nacimiento</a></li>
				<li><a ng-click="menu('general')"><span class="fa fa-book"></span> Defunci&oacute;n</a></li>
				<li><a ng-click="menu('general')"><span class="fa fa-book"></span> Matrimonio</a></li>
				<li><a ng-click="menu('general')"><span class="fa fa-book"></span> Nacionalidad y capacidad</a></li>
				<li><a ng-click="menu('general')"><span class="fa fa-book"></span> Residencia y extranjería</a></li>
				<li><a ng-click="menu('general')"><span class="fa fa-book"></span> Uni&oacute;n estable de hecho</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li ng-controller="header"><a href="" data-toggle="collapse" data-target="#header" ng-click="toggle()">
					<span class="fa fa-angle-up" ng-hide="myVar"></span>
					<span class="fa fa-angle-down" ng-show="myVar"></span> &nbsp;</a>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="badge badge-ok"><span class="glyphicon glyphicon-cog"></span></span></a>
					<ul class="dropdown-menu">
						<li><a href=""><span class="fa fa-bar-chart-o"></span> Estad&iacute;sticas</a></li>
						<li role="separator" class="divider"></li>
						<li><a href=""><span class="glyphicon glyphicon-cog"></span> Administraci&oacute;n del registro</a></li>
						<li><a href=""><span class="glyphicon glyphicon-cog"></span> Configuraci&oacute;n de la oficina</a></li>
						<li role="separator" class="divider"></li>
						<li><a ng-click="menu('indexLibro')"><span class="glyphicon glyphicon-book"></span> Gesti&oacute;n de Libros de Registro Civil</a></li>
						<li role="separator" class="divider"></li>
						<li><a ng-click="menu('gestionECU')"><span class="glyphicon glyphicon-user"></span> Gesti&oacute;n de ECU</a></li>
						<li role="separator" class="divider"></li>
						<li><a href=""><span class="glyphicon glyphicon-user"></span> Gesti&oacute;n de usuarios del sistema</a></li>
						<li role="separator" class="divider"></li>
						<li><a ng-click="menu('logOut')"><span class="glyphicon glyphicon-user"></span> Salir del Sistema</a></li>
					</ul>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href=""><span class="badge badge-warning"><span class="glyphicon glyphicon-bell"></span> 2</span></a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href=""><span class="badge badge-success"><span class="glyphicon glyphicon-time"></span> 8</span></a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href=""><span class="badge badge-primary"><span class="glyphicon glyphicon-search"></span></span></a></li>
			</ul>
		</div>
	</nav>
	
	<aside class="container-fluid ng-scope">
<br/>
<div class="panel panel-default ng-scope">
	<div class="panel-heading">
		<span class="glyphicon glyphicon-list-alt"></span> Lea Detenidamente
	</div>
	<div class="panel-body">
		<div class="row">
			<div class="col-sm-1 pull-left">
				&nbsp;
			</div>
			<div class="col-sm-10">
				<div class="alert alert-warning" role="alert">
					<div class="container-fluid">
						<div class="row">
							<div class="col-sm-1">
								<span style="font-size: 3em;" class="glyphicon glyphicon-alert"></span>
							</div>
							<p id="generico" class="col-sm-11">
								<b>Atenci&oacute;n:</b> <span id="error_message"></span>Acceso no autorizado
							</p>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-1 pull-left">
				&nbsp;
			</div>
		</div>
	</div>
	<div class="panel-footer">
		<div class="row">
			<div class="col-sm-1 pull-right">&nbsp;</div>
<!-- 			<input type="button" value="Finalizar" class="btn btn-danger pull-right btn-sm" ng-click="finalizar()"> -->
		</div>
	</div>
</div>		&emsp;&nbsp;
	</aside>
<!-- 	<aside class="container-fluid ng-scope" ng-view="" ></aside> -->
	<!-- Fin de la zona de carga -->
	<footer id="footer">
		<p>Sistema automtizado del Registro Civil Venezolano. Desarrollado por Hi-Soft Panamá, Todos los derechos reservados. © 2015</p>
	</footer>
		
		
		<script src="<c:url value="/js/jquery-2.0.3.min.js" />"></script>
		<script src="<c:url value="/site/js/transit.js" />"></script>
		<script src="<c:url value="/js/api-check.min.js" />"></script>
		<script src="<c:url value="/angular/angular.min.js" />"></script>
	<script src=<c:url value="/angular/i18n/angular-locale_es-ve.js" />></script>
    <script src="<c:url value="/js/angular-sanitize.min.js" />"></script>
    <script src="<c:url value="/js/angular-route.js" />"></script>
    <script src="<c:url value="/js/angular-storage.js" />"></script>
	<script src="<c:url value="/js/angular-ui-router.js" />"></script>
	
	
	<script src="<c:url value="/angular/angular-messages.js" />"></script>
  	<script src="<c:url value="/js/formly.min.js" />"></script>
  	<script src="<c:url value="/js/angular-formly-templates-bootstrap.min.js" />"></script>
  	
	
    <script src="<c:url value="/angular/angular-animate.js" />"></script>
    <script src="<c:url value="/js/ui-bootstrap-tpls-0.14.3.js" />" type="text/javascript"></script>
   
   	<!--  BLOQUE NUEVO -->
   		<script src="<c:url value="/js/moment.js" />"></script>
   		<script src="<c:url value="/angular/angular-file-upload/angular-file-upload.min.js" />"></script>
   		<script src="<c:url value="/angular/checklist-model.js" />"></script>
   		<script src="<c:url value="/angular/checklist-model.js" />"></script>
   		<script src="<c:url value="/js/swcalendar/swCalendar.js" />"></script>
   		<script src="<c:url value="/js/swcalendar/calendario.js" />"></script>
   		<script src="<c:url value="/js/jquery.dataTables.js" />"></script>
   		<script src="<c:url value="/js/jquery.dataTables.columnFilter.js" />"></script>
   		<script src="<c:url value="/js/angular-datatables.js" />"></script>
   		<script src="<c:url value="/js/angular-datatables.columnfilter.js" />"></script>
   		<script src="<c:url value="/js/dataTables.bootstrap.js" />"></script>
   		<script src="<c:url value="/js/sb-admin-2.js" />"></script>
		<script src="<c:url value="/js/swcalendar/swCalendar.js" />"></script>  
   	<!--/  BLOQUE NUEVO --> 
	<script src="<c:url value="/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/js/dataTables.bootstrap.js" />"></script>
	
	<script type="text/javascript">// Script necesario para el funcionamiento del menu de navegaci�n
        $(".nav a").on("click", function(){
          $(".nav").find(".active").removeClass("active");
          $(this).parent().addClass("active");
        });
        $(document).ready(function(){
            $('[data-toggle="popover"]').popover();   
        });
        $('[data-toggle="popover"]').popover();   
   </script>
   
   <!-- Script Wizard -->
   <script src="<c:url value="/js/chosen.jquery.js" />"></script>
   <script src="<c:url value="/js/prettify.js" />" type="text/javascript"></script>
   
  <!-- SITE -->
  <script src="<c:url value="/site/js/angular-tooltips.js"/>"></script>
  <script src="<c:url value="/js/camposFormly.js" />"></script>
  <script src="<c:url value="/js/app.js" />"></script>
  <script src="<c:url value="/site/js/general.js" />"></script>
  <script src="<c:url value="/site/js/modalAngularPerform.js" />"></script>
  <script src="<c:url value="/site/js/customDirective.js" />"></script>
  <script src="<c:url value="/site/js/moduloWorkspace.js" />"></script>
  <script src="<c:url value="/site/js/validacionModulo.js" />" type="text/javascript"></script>
  <script src="<c:url value="/site/js/validateJavascript.js" />" type="text/javascript"></script>
  <script src="<c:url value="/js/swcalendar/swCalendar.js" />"></script>  
   		
 <script src="<c:url value="/js/rgdefuncion/js/controllers/actaController.js" />"></script>
  <script src="<c:url value="/js/rgdefuncion/js/controllers/footerController.js" />"></script>
  <script src="<c:url value="/js/rgdefuncion/js/controllers/modalCertificadoEV14Ctrl.js" />"></script>  
  <script src="<c:url value="/js/rgdefuncion/js/controllers/modalDemoController.js" />"></script>
  <script src="<c:url value="/js/rgdefuncion/js/controllers/modalInstanceCertificadoEV14Ctrl.js" />"></script>
  <script src="<c:url value="/js/rgdefuncion/js/controllers/modalInstancePrintEV14Ctrl.js" />"></script>
  <script src="<c:url value="/js/rgdefuncion/js/controllers/modalInvalidEV14Controller.js" />"></script>
  <script src="<c:url value="/js/rgdefuncion/js/controllers/modalNuiController.js"/>"></script>
  <script src="<c:url value="/js/rgdefuncion/js/controllers/modalEcuController.js"/>"></script>
  <script src="<c:url value="/js/rgdefuncion/js/controllers/rgDefuncionController.js"/>"></script>

<%--   <script src="<c:url value="http://localhost:8080/web-registrarDefuncion/site/js/controllers/actaController.js" />"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-registrarDefuncion/site/js/controllers/footerController.js" />"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-registrarDefuncion/site/js/controllers/modalCertificadoEV14Ctrl.js" />"></script>   --%>
<%--   <script src="<c:url value="http://localhost:8080/web-registrarDefuncion/site/js/controllers/modalDemoController.js" />"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-registrarDefuncion/site/js/controllers/modalInstanceCertificadoEV14Ctrl.js" />"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-registrarDefuncion/site/js/controllers/modalInstancePrintEV14Ctrl.js" />"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-registrarDefuncion/site/js/controllers/modalInvalidEV14Controller.js" />"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-registrarDefuncion/site/js/controllers/modalNuiController.js"/>"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-registrarDefuncion/site/js/controllers/rgDefuncionController.js"/>"></script> --%>


	   		


  <script src="/web-permisoInhumacionCremacion/site/js/controllers/modalPermisoController.js"></script>
  <script src="/web-permisoInhumacionCremacion/site/js/controllers/pInhumacionCremacionController.js"></script>
  <script src="/web-permisoInhumacionCremacion/site/js/controllers/enviarDatosFormController.js"></script>
  <script src="/web-permisoInhumacionCremacion/site/js/controllers/conformeDatosPermisoController.js"></script>
  <script src="/web-permisoInhumacionCremacion/site/js/controllers/validarCertificadoInvalidoController.js"></script>
  <script src="/web-permisoInhumacionCremacion/site/js/controllers/validarDatosFormController.js"></script>
  
<%--   <script src="<c:url value="http://localhost:8080/web-permisoInhumacionCremacion/site/js/controllers/modalPermisoController.js" />"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-permisoInhumacionCremacion/site/js/controllers/pInhumacionCremacionController.js" />"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-permisoInhumacionCremacion/site/js/controllers/enviarDatosFormController.js" />"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-permisoInhumacionCremacion/site/js/controllers/conformeDatosPermisoController.js" />"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-permisoInhumacionCremacion/site/js/controllers/validarCertificadoInvalidoController.js" />"></script> --%>
<%--   <script src="<c:url value="http://localhost:8080/web-permisoInhumacionCremacion/site/js/controllers/validarDatosFormController.js" />"></script> --%>
  
  
  
  <script src="/web-atencionComunitaria/site/js/moduloAtnComunitaria.js"></script>
  <script src="/web-atencionComunitaria/site/js/generalesModulo.js"></script>
  
  <script src="/web-gestionLibros/site/js/moduloLibro.js"></script>
  
  <script src="<c:url value="/site/js/modalController.js" />"></script>
<!--   <script src="/web-nacimiento/resources/site/js/generalesNacimiento.js" type="text/javascript"></script> -->
<!--   <script src="/web-nacimiento/resources/site/js/nacimientoController.js" type="text/javascript"></script> -->
  
  <script src="/web-ecu/resources/site/js/generalesECU.js" type="text/javascript"></script>
  <script src="/web-ecu/resources/site/js/ecuController.js" type="text/javascript"></script>
<!--   <script src="/web-autenticarCiudadano/js/app.js" type="text/javascript"></script> -->
		<script src="<c:url value="js/angular-resource.js" />"></script>
		<script src="<c:url value="site/js/seguridad/CryptoJSCipher.js" />"></script>
		<script src="<c:url value="site/js/seguridad/angularjs-crypto.js" />"></script>
		<script src="<c:url value="site/js/seguridad/aes.js" />"></script>
		<script src="<c:url value="site/js/seguridad/mode-ecb.js.js" />"></script>
		<script src="<c:url value="site/js/seguridad/services-encrypt.js" />"></script>
		<script src="<c:url value="js/app_seguridad.js" />"></script>

  
</body>
