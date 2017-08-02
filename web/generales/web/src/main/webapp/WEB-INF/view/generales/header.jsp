<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<title>Sistema Automatizado de Registro Civil</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<link href="<c:url value="/bootstrapcss/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/bootstrapcss/bootstrap-theme.min.css" />"	rel="stylesheet">
<link href="<c:url value="/css/propiasSistema.css" />"	rel="stylesheet">
<link href="<c:url value="/css/font-awesome.css" />" rel="stylesheet">

</head>

<div id="header" class="header">
	<div class="row">
		<div class="col-sm-1">
			<img src="<c:url value="/images/logocne.png" />" class="img-responsive">
		</div>
		<div class="col-sm-8">
			<span class="nombreSistema">Sistema Automatizado del Registro Civil Venezolano</span> 
			<br>
			<span class="oficina">${mainForm.oficina}</span>
		</div>
		<div class="col-sm-3 oficina">
			<div ng-controller="tiempoCtrl">
				<div style="margin-top: 5px;">
					<span my-current-time="formatD"></span> &ensp; <span my-current-time="formatH"></span>
				</div>
				<div style="margin-top: 5px;">
					<span>${mainForm.nombreUsuario} (${mainForm.cargoUsuario})</span>
				</div>
			</div>
		</div>
	</div>
</div>