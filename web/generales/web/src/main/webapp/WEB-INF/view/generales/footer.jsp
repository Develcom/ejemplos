<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<footer style="">
	<div id="footer" class="footer">
		<div class="row">
			<div class="col-sm-3">
				${mainForm.direccionOficina}
			</div>
			<div class="col-sm-6" style="text-align: center;font-weight: bold;">
				CNE Consejo Nacional Electoral - Todos los derechos reservados
			</div>
			<div class="col-sm-3" style="text-align: right;">
				Registrado: ${mainForm.registrador}
			</div>
		</div>
	</div>
	<script type="text/javascript"  src="<c:url value="/angular/angular.min.js"/>"></script>
	<script type="text/javascript"  src="<c:url value="/angular-route/angular-route.js"/>"></script>
<%--     <script type="text/javascript"  src="<c:url value="/angular-formly/formly.js"/>"></script> --%>
<%--     <script type="text/javascript"  src="<c:url value="/angular-formly/formly.js"/>"></script> --%>
<%--     <script type="text/javascript"  src="<c:url value="/angular-formly-templates/angular-formly-templates-bootstrap.js"/>"></script> --%>
<%--     <script type="text/javascript"  src="<c:url value="/angular-formly-templates/angular-formly-templates-bootstrap.js"/>"></script> --%>
<%--     <script type="text/javascript"  src="<c:url value="/api-check/api-check.js"/>"></script> --%>
<%--     <script type="text/javascript"  src="<c:url value="/api-check/api-check.js"/>"></script> --%>
	<script type="text/javascript"	src="<c:url value="/jquery/jquery.min.js"/>"></script>
<%-- 	<script type="text/javascript"	src="<c:url value="/bootstrapjs/bootstrap.min.js"/>"></script> --%>
    <script type="text/javascript"  src="<c:url value="/ui-bootstrap/ui-bootstrap-tpls-0.13.4.min.js"/>"></script>
    <script type="text/javascript"  src="<c:url value="/bower-angular-animate-master/angular-animate.js"/>"></script>
	<script type="text/javascript"  src="<c:url value="/js/angularFormat.js"/>"></script>
	<script type="text/javascript"  src="<c:url value="/js/angularScript.js"/>"></script>
	<script type="text/javascript"  src="<c:url value="/js/general.js"/>"></script>
	<script type="text/javascript"  src="<c:url value="/js/transit.js"/>"></script>
</footer>

