<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.lang.String" %>
<%-- <%!Logger log=Logger.getLogger(getClass().getName()); %> --%>

<div id='waitingDiv' style=' display:none; top: 0; right: 0; bottom: 0; left: 0; z-index: 1040; background-color: #000; width: 100%; height: 100%; opacity: 0.4; position: absolute;' class=''> 
	<img style='top: 35%; left: 45%; width: 50px; position: absolute;' src="<c:url value="/img/loading.GIF" />"  class='img-responsive'> 
</div>
<br/>
<div class="row acordionTab"id="head1">
	<div class="col-sm-2" style="text-align: center;">N&uacute;mero Solicitud</div>
	<div class="col-sm-3" style="text-align: center;">Datos del Solicitante</div>
<!-- 	<div class="col-sm-2">Apellido del Ciudadano(a)</div> -->
	<div class="col-sm-1" style="text-align: center;">M&oacute;dulo de Registro Civil</div>
	<div class="col-sm-3" style="text-align: center;">Tipo Tr&aacute;mite</div>
	<div class="col-sm-1" style="text-align: center;">Estatus Solicitud</div>
	<div class="col-sm-2" style="text-align: center;">Fecha Inicio</div>
</div>
	
<div id="solicitudes" class="tramitesCol"> 
	<c:forEach items="${listaSolicitudes}" var="lista">
		<div class="row">
			<div class="col-sm-2">
				<input type="radio" name="solicitudes" value="<c:out value='${lista.numeroSolicitud}'/>"/> <c:out value="${lista.numeroSolicitud }"/>
			</div>
			<div class="col-sm-3" style="text-align: center;">
				<c:if test="${(lista.nombreCiudadano != null && lista.nombreCiudadano != '')}">
					<c:out value="${lista.nombreCiudadano }"/>&nbsp;
				</c:if>
				<c:if test="${(lista.apellidoCiudadano != null && lista.apellidoCiudadano != '' )}">
					<c:out value="${lista.apellidoCiudadano }"/>&nbsp;
				</c:if>
				<c:if test="${(lista.nombreCiudadano == null || lista.nombreCiudadano == '') && (lista.apellidoCiudadano == null || lista.apellidoCiudadano == '' )}">
					<c:if test="${(lista.nroDocumento != null && lista.nroDocumento != '' )}">
						<c:out value="${lista.nroDocumento }"/>
					</c:if>
					<c:if test="${(lista.nroDocumento == null || lista.nroDocumento == '' )}">
						&nbsp;
					</c:if>
				</c:if>
				<c:if test="${(lista.nombreCiudadano == null || lista.nombreCiudadano == '')}">
					&nbsp;
				</c:if>
				<c:if test="${(lista.apellidoCiudadano == null || lista.apellidoCiudadano == '' )}">
					&nbsp;
				</c:if>
			</div>
<%-- 			<div class="col-sm-2"><c:out value="${lista.apellidoCiudadano }"/></div> --%>
			<div class="col-sm-1" style="text-align: center;"><c:out value="${lista.moduloRegistro }"/></div>
			<div class="col-sm-3" style="padding-left: 30px"><c:out value="${lista.tipoTramite }"/></div>
			
			<div class="col-sm-1" style="text-align: center;"><c:out value="${lista.estatusSolicitud }"/></div>
<%-- 				<%log.info("Registr"); %> --%>
			
			<div class="col-sm-2" style="text-align: center;"><c:out value="${lista.fchaInicio }" /></div>
			
		</div>
	</c:forEach>
</div>
<div class="modal fade" id="myModal">
  <div class="col-sm-3 modalContentAlert">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Atenci&oacute;n</h4>
      </div>
      <div class="modal-body">
      	<div class="contentModal">
        	<p>Desea cancelar la operaci&oacute;n?</p>
        </div>
      </div>
      <div class="modal-footer">
      	<div class="mensajeria">
        	<button type="button" id="cancelarAction" class="btn">Si</button>
        	<button type="button" data-dismiss="modal" class="btn">No</button>
		</div>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript"  src="<c:url value="/js/autenticarCiudadano/listadoSolicitudes.js"/>"></script>
