<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="es">
	<head>
		<title>SARC</title>
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<link href="css/sb-admin-2.css" rel="stylesheet"><!-- Estilos de SBadmin 2 -->
		<link href="fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"><!-- Fuente (SBadmin 2) -->
		<link rel="stylesheet" href="css/sarcII.css"><!-- Estilos adicionales de SerWeb01 -->
		<link rel="stylesheet" href="site/css/propiasSistema.css">
		<script>
		  function preventBack(){window.history.forward();}
		  setTimeout("preventBack()", 0);
// 		  alert("going back");
		  window.onunload=function(){null};
		</script>		
	</head>
	<body ng-app="app">
		<div class="container">
	        <div class="row" ng-controller="registroUsuarios">
	            <div class="col-md-4 col-md-offset-4">
	                <div class="login-panel panel panel-default">
	                    <div class="panel-heading text-center">
							<p>
								<img src="img/logocne2.png">
							</p>
	                        <h3 class="panel-title">Por Favor Ingrese sus Datos</h3>
	                    </div>
	                    <div class="panel-body" >
	                        <form name="formUser" role="form">
	                            <fieldset>
	                                <div class="form-group">
<%-- 	                                	<form:input path="usuario"/> --%>
<%-- 	                                	<form:input path="usuario" class="form-control" ng-model="user.nombreusuario" required data-toggle="tooltip" title="Ej.: usuario@sudominio.com" placeholder="Correo electrónico" name="email" type="email" autofocus /> --%>
	                                    <input class="form-control" ng-model="user.nombreusuario" required data-toggle="tooltip" title="Ej.: usuario@sudominio.com" placeholder="Correo electrónico" name="email" type="email" autofocus>
	                                </div>
	                                <div class="form-group">
<%-- 	                                	<form:input path="password"/> --%>
<%-- 	                                	<form:input path="password" class="form-control" ng-model="user.clave" required placeholder="Contraseña" name="password" type="password" value=""/> --%>
	                                    <input class="form-control" ng-model="user.clave" required placeholder="Contraseña" name="password" type="password" value="">
	                                </div>
	                                <div class="checkbox">
	                                    <label>
	                                        <input name="remember" type="checkbox" value="Remember Me">Recuérdame
	                                    </label>
	                                </div>
	                                <!-- Change this to a button or input when using this as a form -->
<!-- 	                                <a href="plantilla_base.html" class="btn btn-lg btn-success btn-block">Entrar al SARC II</a> -->
	                                <input type="submit" value="Entrar" class="btn btn-lg btn-success btn-block" ng-click="validar()">
<!-- 									<input type="submit" value="Entrar al SARC II" class="btn btn-lg btn-success btn-block"> -->
	                            </fieldset>
	                        </form>
	                    </div>
						<div class="panel-footer">
							<div class="row">
								<div class="col-md-12">
									<p class="pull-right"><a href="#">¿Olvidó su Clave o Contraseña?</a></p>
								</div>	
							</div>	
						</div>
	                </div>
	            </div>
	        
	        	<div class="col-md-4" ng-if="profiles.msj != undefined || profiles.msj != null">
	        		<div class="login-panel panel panel-default">
	        			<div class="alert alert-warning alert-noMarginBottom" role="alert" >
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-2"> 
										<p >
											<span style="font-size: 2em;" class="glyphicon glyphicon-alert"></span>
										</p>
									</div>
									<div class="col-sm-10">
										<p id="generico" >
											<b>Atenci&oacute;n:</b> {{profiles.msj}} 
										</p>
									</div>
								</div>
							</div>
						</div>
	        		</div>
	        	</div>
	        </div>
	    </div>
	</body>
<!--   	<script src="/js/camposFormly.js"></script> -->
<!-- 	<script src="angular/angular.min.js"></script> -->
<!-- 	<script src="js/moment.js"></script> -->
<!-- 	<script src="angular/i18n/angular-locale_es-ve.js"></script> -->
<!-- 	<script src="js/jquery.min.js"></script> -->
<!-- 	<script src="js/bootstrap.min.js"></script> -->
<!-- 	<script src="js/sb-admin-2.js"></script> -->
<!-- 	<script src="js/angular-route.js"></script> -->
<!-- 	<script src="js/angular-ui-router.js"></script> -->
<!-- 	<script src="js/angular-storage.js"></script> -->
<!-- 	<script src="js/angular-resource.js"></script> -->
<!-- 	<script src="site/js/seguridad/CryptoJSCipher.js"></script> -->
<!-- 	<script src="site/js/seguridad/angularjs-crypto.js"></script> -->
<!-- 	<script src="site/js/seguridad/aes.js"></script> -->
<!-- 	<script src="site/js/seguridad/mode-ecb.js.js"></script> -->
<!-- 	<script src="site/js/seguridad/services-encrypt.js"></script> -->
<!-- 	<script src="js/app_seguridad.js"></script> -->
<!-- 	<script src="js/validateJavascript.js" type="text/javascript"></script> -->
	
	












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
   		<script src="<c:url value="/js/jquery.dataTables.js" />"></script>
   		<script src="<c:url value="/js/jquery.dataTables.columnFilter.js" />"></script>
   		<script src="<c:url value="/js/angular-datatables.js" />"></script>
   		<script src="<c:url value="/js/angular-datatables.columnfilter.js" />"></script>
   		<script src="<c:url value="/js/dataTables.bootstrap.js" />"></script>
   		<script src="<c:url value="/js/sb-admin-2.js" />"></script>
		<script src="<c:url value="/js/swcalendar/swCalendar.js" />"></script>  
		<script src="<c:url value="/js/swcalendar/calendario.js" />"></script>  
   	<!--/  BLOQUE NUEVO --> 
	<script src="<c:url value="/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/js/dataTables.bootstrap.js" />"></script>
	
	<script type="text/javascript">// Script necesario para el funcionamiento del menu de navegaciï¿½n
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

<!--   <script src="/web-atencionComunitaria/site/js/moduloAtnComunitaria.js"></script> -->
<!--   <script src="/web-atencionComunitaria/site/js/generalesModulo.js"></script> -->
  
<!--   <script src="/web-gestionLibros/site/js/moduloLibro.js"></script> -->
  
  <script src="<c:url value="/site/js/modalController.js" />"></script>
<!--   <script src="/web-nacimiento/resources/site/js/generalesNacimiento.js" type="text/javascript"></script> -->
<!--   <script src="/web-nacimiento/resources/site/js/nacimientoController.js" type="text/javascript"></script> -->
  
<!--   <script src="/web-ecu/resources/site/js/generalesECU.js" type="text/javascript"></script> -->
<!--   <script src="/web-ecu/resources/site/js/ecuController.js" type="text/javascript"></script> -->
		<script src="<c:url value="js/angular-resource.js" />"></script>
		<script src="<c:url value="site/js/seguridad/CryptoJSCipher.js" />"></script>
		<script src="<c:url value="site/js/seguridad/angularjs-crypto.js" />"></script>
		<script src="<c:url value="site/js/seguridad/aes.js" />"></script>
		<script src="<c:url value="site/js/seguridad/mode-ecb.js.js" />"></script>
		<script src="<c:url value="site/js/seguridad/services-encrypt.js" />"></script>
		<script src="<c:url value="js/app_seguridad.js" />"></script>


<!-- 	<script> 
 		$(document).ready(function(){
 			$('[data-toggle="tooltip"]').tooltip();   
 		});
	</script> -->
</html>