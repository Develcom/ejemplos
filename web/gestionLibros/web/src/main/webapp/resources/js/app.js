var App = angular.module('app', ['ngRoute','formly', 'formlyBootstrap','ngSanitize','ui.bootstrap' ]); 


App.config(function($routeProvider) {

	$routeProvider
		//.when('/', {
			//templateUrl	: 'pages/home.html',
			
		//})
		.when('/Matrimonio', {
			templateUrl : 'pages/Matrimonio.html',
			
		})
			.when('/UEH', {
			templateUrl : 'pages/UEH.html',
			
		})
		.when('/prueba', {
			templateUrl : 'pages/prueba.html',
			
		})
		.when('/notas', {
			templateUrl : 'pages/notasMarginales.html',
			
		})


		
});


App.controller('mainController', function($scope) {
	$scope.message = 'Hola, Mundo!';
});

App.controller('aboutController', function($scope) {
	$scope.message = 'Esta es la página "Acerca de"';
});

App.controller('contactController', function($scope) {
	$scope.message = 'Esta es la página de "Contacto", aquí podemos poner un formulario';
});

App.controller('fechaHora', function($scope, $interval){

$interval(function(){
$scope.fecha=new Date;
},1000);

});


App.controller('header', function($scope) {

    $scope.myVar = false;
    $scope.toggle = function() {
        $scope.myVar = !$scope.myVar;
    };
});


App.controller('solicitudesPendientes', ['$scope', function($scope) {
	$scope.Solicitudes  = [	{Nro:'4', 
							PNombreSolictante1:'Max', 
							SNombreSolictante1:'Clinton', 
							PApellidoSolictante1:'Harrigan',
							SApellidoSolictante1:'Chatergoon', 
							fechaNacimientoSolictante1:'30-04-1982',
							edadSolictante1:'28', 
							CISolictante1:'18276689', 
							NacionalidadSolictante1:'Venezolano',
							ECivilSolictante1:'Soltero',
							UEHSolictante1:'SI',
							PNombreSolictante2:'Mariangel', 
							SNombreSolictante2:'Margarita', 
							PApellidoSolictante2:'Gutierrez',
							SApellidoSolictante2:'Guaramato', 
							fechaNacimientoSolictante2:'23-05-1950',
							edadSolictante2:'65', 
							CISolictante2:'1345765', 
							NacionalidadSolictante2:'Venezolano',
							ECivilSolictante2:'Soltero',
							UEHSolictante2:'SI',
							Tramite:'Disolución',
							Hora:'10:43:44'},
							
							{Nro:'3', 
							PNombreSolictante1:'Daniel', 
							SNombreSolictante1:'Ibrain', 
							PApellidoSolictante1:'Meneses',
							SApellidoSolictante1:'Mejias', 
							fechaNacimientoSolictante1:'13-12-1989',
							edadSolictante1:'25', 
							CISolictante1:'19155187', 
							NacionalidadSolictante1:'Venezolano',
							ECivilSolictante1:'Soltero',
							UEHSolictante1:'NO',
							PNombreSolictante2:'Norelys', 
							SNombreSolictante2:'Andreina', 
							PApellidoSolictante2:'Meza',
							SApellidoSolictante2:'Guerra', 
							fechaNacimientoSolictante2:'10-10-1989',
							edadSolictante2:'27', 
							CISolictante2:'20402200', 
							NacionalidadSolictante2:'Venezolano',
							ECivilSolictante2:'Soltero',
							UEHSolictante2:'NO',
							Tramite:'Union Estable de Hecho',
							Hora:'9:11:44'},

							{Nro:'1', 
							PNombreSolictante1:'Edwin', 
							SNombreSolictante1:'Saul', 
							PApellidoSolictante1:'Aponte',
							SApellidoSolictante1:'Diaz', 
							fechaNacimientoSolictante1:'22-02-1987',
							edadSolictante1:'28', 
							CISolictante1:'1234567', 
							NacionalidadSolictante1:'Venezolano',
							ECivilSolictante1:'Soltero',
							UEHSolictante1:'NO',
							PNombreSolictante2:'Alismar', 
							SNombreSolictante2:'Geraldine', 
							PApellidoSolictante2:'Torrealba',
							SApellidoSolictante2:'Juarez', 
							fechaNacimientoSolictante2:'10-10-1989',
							edadSolictante2:'27', 
							CISolictante2:'19344234', 
							NacionalidadSolictante2:'Venezolano',
							ECivilSolictante2:'Casado',
							UEHSolictante2:'NO',
							Tramite:'Union Estable de Hecho',
							Hora:'8:15:23'},

							{Nro:'2', 
							PNombreSolictante1:'Maria', 
							SNombreSolictante1:'Josefina', 
							PApellidoSolictante1:'Mata',
							SApellidoSolictante1:'Loca', 
							fechaNacimientoSolictante1:'22-02-1987',
							edadSolictante1:'28', 
							CISolictante1:'1234567', 
							NacionalidadSolictante1:'Venezolano',
							ECivilSolictante1:'Soltero',
							UEHSolictante1:'NO',
							PNombreSolictante2:'Pedro', 
							SNombreSolictante2:'Jose', 
							PApellidoSolictante2:'Perez',
							SApellidoSolictante2:'Pirela', 
							fechaNacimientoSolictante2:'10-10-1989',
							edadSolictante2:'27', 
							CISolictante2:'19344234', 
							NacionalidadSolictante2:'Venezolano',
							ECivilSolictante2:'Soltero',
							UEHSolictante2:'SI',
							Tramite:'Union Estable de Hecho',
							Hora:'8:54:23'}

							
							];

		$scope.contador=4;

	$scope.submitForm = function (formData) {

	if($scope.formData.tipo == null){
	
		alert('mamalo'+ c);
	}else{
		 alert('Datos enviados exitosamente');
	}
   
  	};

 	$scope.agregarSolicitud = function () {

	 	$scope.fecha= new Date;
	 	$scope.Hora= $scope.fecha.getHours() + ":" + $scope.fecha.getMinutes() +":"+ $scope.fecha.getSeconds();
	    $scope.Solicitudes.push({Nro: $scope.contador= 1+ $scope.contador, 
	    						 Nombre:$scope.FormNombre, 
	    						 apellido:$scope.FormApellido, 
	    						 Tramite:$scope.FormTramite, 
	    						 Hora:$scope.Hora,});
	    
	    $scope.FormNombre = '';
	    $scope.FormApellido = '';
	    $scope.FormTramite = '';



  	};

  	$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});

	$scope.Quitar = function() { 
	  var Rock = $scope.Solicitudes.length;
	 
	  if(Rock > 0){	  	
	  	$scope.Solicitudes.splice(0, 1);
	  }	else {
	 
	  }	
	};

	$scope.Total = function () {
	    return $scope.Solicitudes.length;
	};

}]);

App.controller('reconocerHijos', ['$scope', function($scope) {

$scope.Hijos  = [
							{
							CheckBox:'false',
							CIHijo:'31444323', 
							PNombreHijo:'Juanito', 
							SNombreHijo:'Alimaña', 
							PApellidoHijo:'Meza',
							SApellidoHijo:'', 
							ActaNacimiento:'A008123457',
							fechaNacimientoHijo:'11-11-2006',
							edadHijo:'9',
							OURC:'UPRC Candelaria'
							},

							{
							CheckBox:'false',
							CIHijo:'22002244', 
							PNombreHijo:'Pedro', 
							SNombreHijo:'Navaja', 
							PApellidoHijo:'Meza',
							SApellidoHijo:'', 
							ActaNacimiento:'A003033223',
							fechaNacimientoHijo:'12-12-1997',
							edadHijo:'18',
							OURC:'UPRC Catedral'
							},

				];

$scope.Total2 = function () {
	    return $scope.Solicitudes.length;
	};
}]);

function Imprimir(imagen) {
  newWindow = window.open(", ","FormatoCPM.jpg","width=700,height=500,left=200,top=60");
  newWindow.document.open();
  newWindow.document.write('<html><head></head><body onload="window.print()"><img src="'+ imagen +'"/></body></html>');  
  newWindow.document.close();
  newWindow.focus();
};

App.controller('registroUsuarios',['$scope', function($scope){

	function validar() {

	var clave = $scope.Clave;
    var clave2 = $scope.Clave2;
    if (clave != clave2) {
        $scope.Clave2("demo").innerHTML = inpObj.validationMessage;
    }
};
	
}]);
App.directive('carta', function ($compile) {
	  return {
		//scope de la directiva
		//ele: elemento donde es declarada la directiva
		//attrs: atributos del elemento, compartidos por todas las directivas enlazadas
	    link: function (scope, ele, attrs) {
		      scope.$watch(attrs.carta, function(html) {
	        ele.html(html);
	    	  $compile(ele.contents())(scope);
	      });
	    }
	  };
	});
