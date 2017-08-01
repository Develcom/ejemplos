<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
	<title>SARC</title>
	    <link href="<c:url value="/resources/css/body.css" />" rel="stylesheet">
		<style type="text/css">
			body {
				background-image: url('http://crunchify.com/bg.png');
			}
		</style>
	</head>
	<body>
		<br>
		<div style="text-align: center">
			<h2>
				Bienvenido<br> <br>
			</h2>
			<h3>
				<a href="welcome.html">HelloWorldController</a>
			</h3>
		</div>
		<script src="<c:url value="/webjars/angularjs/1.3.15/angular.min.js" />"></script>
		<script src="<c:url value="/resources/js/angularjs/app.js" />"></script>
		<script src="<c:url value="/resources/js/js.js" />"></script>		
	</body>
</html>