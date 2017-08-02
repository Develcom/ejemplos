<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.lang.String" %>
<div id="subCabecera">
	<div id="subHeader" class="row">
		<div class="col-sm-1 solicitud" style="text-align: center;">
<!-- 			<i class="fa fa-users fa-3x fa-fw"></i> -->
			<img src="<c:url value="/img/people.png" />" class="img-responsive">
		</div>
		<div class="col-sm-3">
			<br>
			<p class="bannerModulo TabLeft">${general.nombreModulo}&nbsp;&nbsp;</p>
		</div>
		<div class="col-sm-4">
			<br>
			<p class="bannerModulo TabCenter">${general.nombreProceso}&nbsp;&nbsp;</p>
		</div>
		<div class="col-sm-4">
			<div style="text-align: right;" class="solicitud">
				<c:if test="${general.nroInstancia != null && general.nroInstancia != ''}">
					Nro. Solicitud: <span id="nroSolicitud">${general.nroInstancia}</span>
				</c:if>
				<c:if test="${general.nroInstancia == null || general.nroInstancia == ''}">
					&ensp;&ensp;
				</c:if>
			</div>
			<p class="bannerModulo TabRight">&ensp;</p>
		</div>
	</div>
	
	<!-- SECCION DE MENSAJES DE ERROR E INFORMACION DEL SISTEMA -->
	<div class="row">
		<div class="col-xs-1 mensajeria">
			<h4>&ensp;</h4>
		</div>
		<div class="col-xs-11">
			<div id="mensajes" class="row mensajeria">
				<c:if test="${general.tipo == 'I' || general.tipo == 'E'}">
					<c:if test="${general.tipo == 'I'}">
						<div class="alert alert-warning alertPerformDesign" role="alert">
							<!--<span class="glyphicon glyphicon-info-sign infoSign" aria-hidden="true"></span>-->
							<img src="<c:url value="/images/sign_warning.png" />" class="img-responsive img_info_1">
							<h4 style="float:left;"> <strong>${general.mensaje }</strong> </h4>
							&emsp;&nbsp;
						</div>
					</c:if>
					<c:if test="${general.tipo == 'E'}">
						<div class="alert alert-danger alertPerformDesign" role="alert">
							<!--<span class="glyphicon glyphicon-exclamation-sign errorSign" aria-hidden="true"></span>-->
							<img src="<c:url value="/images/sign_error.png" />" class="img-responsive img_info_1">
							<h4 style="float:left;"> <strong>${general.mensaje }</strong> </h4>
							&emsp;&nbsp;
						</div>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
</div>