<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.lang.String" %>
<c:if test="${general.mensaje != '' && general.mensaje != null}">
	<div class="alert alert-danger alertPerformDesign" role="alert" style="margin:20px; height: 300px;">
		<img src="<c:url value="/images/under_construction.png" />" class="img-responsive img_error">
		<h3 style="position: absolute; top: 10%; left: 10%; right: 0;"> 
			<strong>
				${general.mensaje}
				<!-- Error en el Módulo de Atención Comunitaria. Contacte con su administrador.-->
			</strong> 
		</h3>
		&emsp;&nbsp;
	</div>
</c:if>