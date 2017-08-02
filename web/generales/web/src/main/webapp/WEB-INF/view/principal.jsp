<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<!DOCTYPE HTML>
<html style="overflow:hidden;" ng-app="angularPrincipal">
<jsp:include page="./generales/header.jsp"></jsp:include>

<body>
	<div class="container" style="width: 100%;">
		<div id='waitingDivTop' style=' display:none; top: 0; right: 0; bottom: 0; left: 0; z-index: 1040; background-color: #000; width: 100%; height: 100%; opacity: 0.4; position: absolute;' class=''> 
			<img style='top: 35%; left: 45%; width: 50px; position: absolute;' src="<c:url value="/img/loading.GIF" />"  class='img-responsive'> 
			
		</div>
		<div class="col-sm-2 menu row-centered" ng-controller="iniciaModuloControlador">
			<br />
			<div class="btn-group-vertical" role="group" id="menNavigator" aria-label="Vertical button group" >
				
				
				<a href="#/atencionComunitaria" style="" id="atencionComunitaria">
					<button type="button" class="btn menuPesta" id="gestionSol" ng-click="iniciarModulo('atencionComunitaria')"  >
						Gesti&oacute;n de Solicitud
					</button>
				</a>
				<a href="#/autenticarCiudadano" style="" id="autenticarCiudadano">
					<button type="button" class="btn menuPesta" id="autenticarCiu" ng-click="iniciarModulo('autenticarCiudadano')" >
						Autenticar Ciudadano
					</button>
				</a>
				<br/>
				<form:form method="POST" action="acceso" id="accesoAct" style="visibility:hidden;">
				</form:form>
			</div>
		</div>
		<div class="col-sm-10">
			<div id="cabecera" class="container" style="width: 99%; overflow: hidden;">
				
			</div>
			<div id="body" ng-view="" class="ng-scope container" style="width: 99%; overflow: hidden;">
				
			</div>
<!-- 			 <div class="row col-sm-10" id="opciones" style="display:none;"> -->
<!-- 			 	<div class="col-sm-2 mensajeria">Acciones:</div> -->
<!-- 			 	<div class="col-sm-4">&emsp;</div> -->
<!-- 			 	<div class="col-sm-1 mensajeria" > -->
<!-- 			 		<button class="btn" id="anteriorBtn" onclick="anterior();">Regresar</button> -->
<!-- 			 	</div> -->
<!-- 			 	<div class="col-sm-1">&emsp;</div> -->
<!-- 			 	<div class="col-sm-1 mensajeria" > -->
<!-- 			 		<button class="btn" id="cancelarBtn">Cancelar</button> -->
<!-- 			 	</div> -->
<!-- 			 	<div class="col-sm-1">&emsp;</div> -->
<!-- 			 	<div class="col-sm-1 mensajeria" > -->
<!-- 			 		<button class="btn" id="continuarBtn" onclick="continuar();">Continuar</button> -->
<!-- 			 		<button class="btn" id="finalizarBtn" >Finalizar</button> -->
<!-- 			 	</div> -->
<!-- 			 </div> -->
		</div>
	</div>
</body>

<jsp:include page="./generales/footer.jsp"></jsp:include>
<script>
	$('#body').css('height',($(document).height() - ($('#opciones').height()+ $('#header').height())));
</script>
</html>
