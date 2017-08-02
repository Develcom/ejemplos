var App = angular.module('app', ['ngRoute','formly', 'formlyBootstrap','ngSanitize','ui.bootstrap']);
App.config(function($routeProvider) {

	$routeProvider
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
	

	}else{
		
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


App.controller('participantesController1',function($scope,$rootScope,$http){
	console.log("Ejecutando angular autenticar ciudadano");
	angular.element(document.querySelector('#opciones')).css('display','');
	this.sinDoc=false;
	$scope.entePublico=true;
	$rootScope.marcados=[];
	$rootScope.panel=1;
	$http({
        method: 'GET',
        url: '/web-autenticarCiudadano/consultarSolicitud'
    }).then(
	function(response) {
		if(response.data.tipoSolicitante=="D"){
			$scope.entePublico=false;
			console.log("Tipo de participante: "+$scope.entePublico);
		}
			$scope.lista=response.data.listaParticipantes;
			$scope.solicitante=response.data.solicitante;
	},
	function(response) {
		console.log('consulta errada');
	});	
	$scope.almacenarMarcados=function(item,marcado,$event){
		var indice; 
		if(!marcado){
			this.sinDoc=false;
			$(event.target).parent().next().children().prop('checked',false);
			indice=$rootScope.marcados.indexOf(item);
			if(indice>-1)
				$rootScope.marcados.splice(indice,1);
			return
		}
		$rootScope.marcados.push(item);
	}
});

App.controller('navController',function($scope,$http){
	var urlBaseJson="https://localhost:8443/web-autenticarCiudadano/resources/js/json/";
	$scope.clickSiguiente=function(){
		 $http({
			  method: 'GET',
			  url: "https://localhost:8443/AngularBootstrapWizard/resources/pages/wizard1.html"
			}).then(function successCallback(response) {
				console.log('respuesta: '+response.data);
			  }, function errorCallback(response) {
				  console.log('error en la solicitud');
			  });	
	};
});
App.controller('participantesController',function($scope,$http){
	var vm=this;
	vm.lista=['Madre','Padre','Abuelo','Abuela'];
});

App.controller('documentosController',function($scope){
	$scope.documentos=['CEDULA','NUI','PASAPORTE'];
	$scope.placeHolders=['V-123456789','123456789','123456789'];
	$scope.nroDocumento;
	$scope.selectDoc=function(dato){
		if(dato=='CEDULA'){
			$scope.placeHolder='V123456789';
			$scope.minima_longitud=6;
			$scope.maxima_longitud=11;
			$scope.patron=new RegExp('^(V|v|E|e)+[0-9]+[a-z]');
			return;
		}
		if(dato=='NUI'){
			$scope.placeHolder='123456789';
			$scope.minima_longitud=8;
			$scope.maxima_longitud=9;
			$scope.patron=new RegExp('[aA-zZ]+[0-9]|[0-9]');
			return;
		}
		if(dato=='PASAPORTE'){
			$scope.placeHolder='123456789';			
			$scope.minima_longitud=6;
			$scope.maxima_longitud=11;
			$scope.patron=new RegExp('[aA-zZ]+[0-9]|[0-9]');
		}
	};
	$scope.alTeclear=function(){
		console.log('ha ingresado: '+$scope.nroDocumento);
	};
});

App.controller('MainCtrl', function MainCtrl(formlyVersion, $timeout) {
    var vm = this;
    vm.onSubmit = onSubmit;

    vm.author = { 
      name: 'Kent C.',
      url: 'Dodds' 
    };
    vm.exampleTitle = 'model property'; 
    vm.env = {
      angularVersion: angular.version.full,
      formlyVersion: formlyVersion
    };

    vm.model = {
      name: {
        first: 'Gandalf',
        last: 'The Gray'
      },
      email: 'gandalf@example.com',
      username: 'yoiamgandalf'
    };
    
    vm.fields = [
      {
        key: 'first',
        type: 'input',
        model: vm.model.name,
        templateOptions: {
          label: 'First Name'
        }
      },
      {
        key: 'last',
        type: 'input',
        model: vm.model.name,
        templateOptions: {
          label: 'Last Name'
        }
      },
      {
        key: 'email',
        type: 'input',
        templateOptions: {
          label: 'Email Address',
          type: 'email'
        }
      },
      {
        key: 'username',
        type: 'input',
        templateOptions: {
          label: 'Username'
        }
      },
      {
        key: 'other',
        type: 'input',
        templateOptions: {
          label: 'Other model'
        },
        expressionProperties: {
          'templateOptions.disabled': '!options.data.modelLoaded'
        },
        data: {
          modelLoaded: false
        }
      }
    ];
    
    $timeout(function() {
    }, 1000).then(function() {
      var field = vm.fields[4];
      console.log('hi');
      field.model = {
        other: 'some value'
      };
      field.data.modelLoaded = true;
      field.runExpressions();
    });
    
    vm.originalFields = angular.copy(vm.fields);

    function onSubmit() {
      alert(JSON.stringify(vm.model), null, 2);
    }
  });
App.factory('intercambiador',function(){
	var titulo;
	addTitulo=function(mititulo){
		console.log("insertando titulo: "+mititulo);
		titulo=mititulo;
	};
	getTitulo=function(){return titulo};
	return {addTitulo: addTitulo, getTitulo: getTitulo};
});

App.controller('testController',function($scope,$http,intercambiador,$rootScope){
	var vm=this;
	$scope.titulo='asada';
	intercambiador.addTitulo('qwerty');
	$rootScope.titulo='hola hola';
    $scope.$watch('titulo', function (newValue, oldValue) {
        if (newValue !== oldValue) intercambiador.addTitulo(newValue);
    });
	$http({
		  method: 'GET',
		  url: 'ajaxControllerTest.html'
		}).then(function successCallback(response) {
			$rootScope.html=response.data;
			console.log('Exitoso: '+response.data);
		  }, function errorCallback(response) {
			  console.log("error");
		  });
	$scope.insertar=function(){console.log('se hizo click en el botón');};
	$scope.porAjax=function(){alert("por ajax");};
	});
App.controller('wizardTituloControlador',function($scope,intercambiador,$rootScope){
    $scope.$watch(function () { return intercambiador.getTitulo(); }, function (newValue, oldValue) {
        if (newValue !== oldValue) $rootScope.titulo = newValue;
    });
});

App.directive('dynamic', function ($compile) {
	  return {
	    link: function (scope, ele, attrs) {
		      scope.$watch(attrs.dynamic, function(html) {
		      console.log('elemento: '+ele.attr('dynamic'));
		      var htmlContenido=$('#contenedor1').html();
		      var scopeContenido=scope.html;
	    	  console.log('compilando $ html.......:'+$('#contenedor1').html());
	    	  console.log('html: '+html);
	    	  console.log('compilando $ dynamic.......:'+$('#contenedor1').attr('dynamic'));
	    	  console.log('scope.html: '+scope.html);
	        ele.html(html);
	        $compile(ele.contents())(scope);
	      });
	    }
	  };
	});
App.controller('MyController',function($scope){
	  $scope.click = function(arg) {
		    alert('Clicked ' + arg);
		  }
		  $scope.html = '<a ng-click="click(1)" href="#">Click me</a>';
		  $scope.click2 = function(arg) {
			    alert('Clicked 2222 ' + arg);
			  }
			  $scope.html = '<a ng-click="click(1)" href="#">Click me</a>';
});


App.controller('ModalDemoCtrl', function ($scope, $uibModal, $log) {
	  $scope.items = 1;
	  $scope.animationsEnabled = true;
	  $scope.open = function (size) {

	    var modalInstance = $uibModal.open({
	      backdrop: 'static',
	      keyboard: false,
	      animation: $scope.animationsEnabled,
	      templateUrl: 'pages/template.html',
	      controller: 'ModalInstanceCtrl',
	      size: size,
	      windowTopClass: 'app-modal-window',
	      resolve: {
	        items: function () {
	          return $scope.items;
	        }
	      }
	    });

	    modalInstance.result.then(function () {
	      $log.info('ok');
	    }, function () {
	      $log.info('Modal dismissed at: ' + new Date());
	    });
	  };


	});

	App.controller('ModalInstanceCtrl', function ($rootScope, $scope, $uibModalInstance,$http, servicioProgreso, items) {
	  $scope.pasoDrools=items;//"hola";
	    $scope.$watch('pasoDrools', function (newValue, oldValue) {
	        if (newValue !== oldValue) servicioProgreso.setProgreso(newValue);
	    });
	  $scope.ok = function () {
		  console.log('hola: '+$scope.pasoDrools);
			$http({
				  method: 'GET',
				  url: '/AngularBootstrapWizard/consultarDrools/'+$scope.pasoDrools
				}).then(function successCallback(response) {
					$rootScope.html=response.data;
					console.log('Exitoso: '+response.data);
					$scope.pasoDrools++;
				  }, function errorCallback(response) {
					  console.log("error");
				  });
	  };

	  $scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };
	});
	
	App.directive('carta', function ($compile) {
		  return {
		    link: function (scope, ele, attrs) {
			      scope.$watch(attrs.carta, function(html) {
		        ele.html(html);
		    	  $compile(ele.contents())(scope);
		      });
		    }
		  };
		});
	
	App.controller('paso1Controller',function(){
		var vm=this;
		vm.paso1=function(){
			alert('Hola paso1');
		}
	});
	App.controller('paso2Controller',function(){
		var vm=this;
		vm.paso1=function(){
			alert('Hola paso2');
		}
	});
	App.controller('progresoController',function($scope,servicioProgreso){
		var vm=this;
		vm.maximo=4;
		vm.valor=1;
	    $scope.$watch(function () { return servicioProgreso.getProgreso(); }, function (newValue, oldValue) {
	        if (newValue !== oldValue) vm.valor = newValue;
	    });
	});
	App.factory('servicioProgreso',function(){
		var progreso;
		setProgreso=function(mProgreso){
			progreso=mProgreso;
		};
		getProgreso=function(){return progreso};
		return {setProgreso: setProgreso, getProgreso: getProgreso};		
	});
	App.controller('formController',function(){
	    var vm = this;
	    vm.model = {};
	    vm.options = {};
	    vm.fields = [
	                 {
	                   key: 'lastName',
	                   type: 'input',
	                   templateOptions: {
	                     label: 'Last Name'
	                   },
	                   expressionProperties: {
	                     'templateOptions.disabled': '!model.firstName'
	                   }
	                 },
	                 {
	                   key: 'knowIpAddress',
	                   type: 'checkbox',
	                   templateOptions: {
	                     label: 'I know what an IP address is...'
	                   }
	                 },
	                 {
	                   key: 'ipAddress',
	                   type: 'input',
	                   templateOptions: {
	                     label: 'IP Address',
	                     placeholder: '127.0.0.1'
	                   },
	                   hideExpression: '!model.knowIpAddress',
	                   validators: {
	                     ipAddress: {
	                       expression: function(viewValue, modelValue) {
	                         var value = modelValue || viewValue;
	                         return !value || /(\d{1,3}\.){3}\d{1,3}/.test(value);
	                       },
	                       message: '$viewValue + " is not a valid IP Address"'
	                     }
	                   }
	                 }
	               ];

	});