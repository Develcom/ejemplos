var App = angular.module('app', ['ngRoute','ui.router','formly', 'ngMessages', 'formlyBootstrap','ngSanitize','ui.bootstrap', 'customDirectives','validaciones', '720kb.tooltips','sw.calendar','camposFormly','angularjs-crypto', 'calendario', 'ngResource']);
App.config(function($provide, $stateProvider, $urlRouterProvider, formlyConfigProvider, tooltipsConfProvider) {
	
	$provide.decorator('$state', function($delegate, $stateParams) {
        $delegate.forceReload = function() {
            return $delegate.go($delegate.current, $stateParams, {
                reload: true,
                inherit: false,
                notify: true
            });
        };
        return $delegate;
    });
	
    $urlRouterProvider.otherwise("general");
    
    $stateProvider
	    $stateProvider
	    .state("atencionComunitaria",{
	    	controller: "atencionControlador",
	    	url : "",
	    	params:{
	    		token:null,
	    		url: null
	    	},
	    	resolve: {
	    		myResolve1:
	    			function($http, $stateParams, $state) {
	    			var url = "/web-generales/iniciarAtencionComunitaria";
	    		}
	    	},
	    	templateUrl: function (stateParams){
	    		return "/web-generales/iniciarAtencionComunitaria";
	    	}
	    })
    	.state("general",{
    		controller: "workspace",
    		url : "",
    		params:{
				token:null,
				url: null
			},
			resolve: {
				myResolve1:
					function($http, $stateParams, $state) {
						var url = "/web-generales/listadoSolicitudes";
				}
		    },
			templateUrl: function (stateParams){
				return "/web-generales/view/listadoSolicitudes.html";
			}
    	})
    	.state("logOut",{
			controller: "logoutController",
		})
		.state("login",{
			templateUrl: "/web-sistemaPrincipal/"
		})
		
    	.state("validarLibro",{
    		controller: "libroValidarAbiertoControlador",
    	})
    	.state("gestionECU",{
        	controller: "gestionECU",
        	templateUrl: function (stateParams){
				return "/web-generales/iniciarGestionECU";
        	}
        })
        

        		.state("detalleSolicitud", {
					controller: "crearSolControlador",
					params:{
						id:null
					},
					templateUrl: "/web-atencionComunitaria/view/detalleSolicitud.html"
				})
				.state("errorSol", {
					controller: "errorCreacionControlador",
					params:{
						errorSol:null,
						errorMsg: null,
						current: null,
						token: null
					},
					templateUrl: "/web-generales/view/errors/errorDisplay.html"
				})
				.state("errorSolECU", {
					controller: "errorECUCreacionControlador",
					params:{
						errorSol:null,
						errorMsg: null,
						current: null,
						token: null
					},
					templateUrl: "/web-ecu/view/errors/errorDisplay.html"
				})
				.state("listSolicitud", {
					controller: "listarSolControlador",
					url: "",
					params: {
						tipoSolicitante: null,
						tipo: null,
						nro: null,
						token: null
					  },
					templateUrl: "/web-atencionComunitaria/view/listadoSolicitudes.html"
				})
				
		.state("indexLibro",{
			
    		controller: "cargarIndexControlador",
			//url: "",
        	templateUrl: function (stateParams){
        		return "/web-generales/iniciarGestionLibro";
        	}
    	})
		    	.state("cargarTramite",{			
		    		controller: "cargarTramiteControlador",
		    		url: "",
		    		params:{
		    			operacion: null,
		    			lista:null		    	          
					},
		    		templateUrl: "/web-gestionLibros/view/fragTablaLibroCivil.html"
		    	})
		    	.state("visualizarActa",{			
		    		controller: "actaControlador",
		    		url: "",
		    		params:{
		    			operacion: null,
		    			html:null,
						ruta: null
					},
		    		templateUrl: "/web-gestionLibros/view/fragVisualizarFormato.html"
		    	}
		    	).state("error", {
					controller: "errorLibro",
					url: "",
					params:{
						errorMsg: null
					},
					templateUrl: "/web-gestionLibros/view/errors/errorDisplay.html"
		    	}
		    	).state("satisfactorio",{
		    		controller: "satisfactorioLibro",
		    		url: "",
		    		params:{
						satisfactorioMsg: null
					},
					templateUrl: "/web-gestionLibros/view/success/satisfactorioDisplay.html"
		    	})
		    
        
		
		.state("nacimiento",{
        	controller: "nacController",
        	controllerAs: "vm",
        	url: "",
        	templateUrl: "/web-nacimiento/pages/templates/template_nacimiento.html"
        })
        
        /*estado del modulo permiso inhumacion cremacion cuando es pendiente por imprimir*/
		.state("pendientePorImprimir",{
			controller: "pendienteImprimir",
			url: "",
			params:{
				parametro: null
			},
			templateUrl: "/web-permisoInhumacionCremacion/pages/templates/ficha_tecnica.html"
		})
        /*estado del modulo autenticar ciudadano cuando es pendiente por imprimir*/
		.state("pendientePorImprimirAutenticar",{
			controller: "ImprimirControlador",
			url: "",
			params:{
				parametro: null
			},
			templateUrl: "/web-autenticarCiudadano/pages/templates/imprimir/ficha_tecnica.html"
		})
        
});

function asyncMethod($http, $stateParams, $state, url) {
	  /* perform some asynchronous operation, resolve or reject the promise when appropriate.*/
	$http({
		  method: 'GET',
		  url: url,
		  async: false
	}).then(function successCallback(response) {
		$stateParams.url = response.data

	  }, function errorCallback(response) {
		  $stateParams.errorSol = response.status;
		  $stateParams.errorMsg = response.statusText;
		  $stateParams.current = $state.current.name;
		  $state.transitionTo('errorSol', $stateParams);
	});
	  
}

App.filter('ageFilter', function() {
	function calculateAge(birthday) {
        var ageDifMs = Date.now() - birthday.getTime();
        var ageDate = new Date(ageDifMs); 
        return Math.abs(ageDate.getUTCFullYear() - 1970);
    }
    function monthDiff(d1, d2) {
      if (d1 < d2){
       var months = d2.getMonth() - d1.getMonth();
       return months <= 0 ? 0 : months;
      }
      return 0;
    }       
    return function(birthdate) {
    	if(birthdate != null){
    		var born = new Date(birthdate);
    		var age = calculateAge(born);
            if (age == 0){
              return monthDiff(born, new Date()) + ' Meses';
            }else if (age == 1){
            	return age + ' A\u00f1o'; 
            }else if (age > 1){
            	return age + ' A\u00f1os'; 
            }
            return age;
    	}else{
    		return "";
    	}
          
    }; 
});

App.filter('offset', function() {
    return function(input, start) {
    	if(input != undefined){
    		return input.slice(start);
    	}
 	};
})

/*Presenta la informacion de la ofician y la hora en el cabecero*/
App.controller('fechaHora', function($scope, $interval, $http){

	vm = this;
	vm.modeloActual={};
	vm.presentarInfo = function($scope, vm, response){
		vm.modeloActual = response.data.modelo;
	}
	llamadaAjax1($http, $scope, vm, '/web-generales/detallesOficina', vm.modeloActual, vm.presentarInfo, 'POST');

$interval(function(){
$scope.fecha=new Date;
},1000);

});
App.controller('menuController', function ($rootScope, $scope, $http, $location, $state, $stateParams, $window, $route, $uibModal) {
	$rootScope.http = $http;
	$rootScope.state = $state;
    $scope.menu = function(estado) {

    	$state.transitionTo(estado, $stateParams);
    };
    
    $scope.$watch("token", function(newValue, oldValue){
		if(newValue != null){
			$rootScope.token = newValue;
		}
	});
});

App.controller('header', function($scope) {

    $scope.myVar = false;
    $scope.toggle = function() {
        $scope.myVar = !$scope.myVar;
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
App.directive('tcompile', function ($compile, $templateCache, $controller) {
	  return {
	    link: function (scope, ele, attrs) {
		      scope.$watch(attrs.tcompile, function(newValue) {
		    	  if(attrs.contentTemplate != "" && newValue != null){
		    		  ele.html(attrs.contentTemplate);
			    	  $compile(ele.contents())(scope);
		    	  }
		      });
	    }
	  };
	});

//Constantes para obtener la url actual
App.constant('PARAMETROS', {
	BaseUrlSarc: '/sarc/api/v1/',
	RootPath: function(){
		var constructor = window.location;	
		return ''+ constructor.protocol +'//' + constructor.hostname
		+ ((constructor.port != '' )?':'+ constructor.port:'') + '/' + constructor.pathname.split('/')[1];		
	}
});

//Servicio para obtener la URL de SARC-INBOX-WEB
App.service('enlaceService', ['$http','PARAMETROS','$q', function ($http,PARAMETROS,$q) {
	this.promise;
	var self = this;
	this.solicitanteTemp='';
	var deferred = $q.defer();

	$http({
		url: PARAMETROS.RootPath() + PARAMETROS.BaseUrlSarc + 'propiedades',
		method: 'GET',
		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	}).then(function(data){		  
		self.propiedades = data.data;
		deferred.resolve("procesado =" + data);

		self.inboxWeb  = function(){
			self.inbox = self.propiedades["sarc.web.home.url"];
			return $http({		
				url: self.propiedades["sarc.web.home.url"],
				method: 'GET',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			});
		}
		
	}, function(data){
		console.log("Error al cargar el archivo");
		deferred.resolve("error");
	}
	);		
	self.promise = deferred.promise;

}]);

//Controlador que llama el Servicio para el Enlace Directo a SARC-INBOX-WEB
App.controller('enlaceController',
['$scope', '$http','$rootScope','$window','$state','$location','enlaceService', 
 function($scope, $http, $rootScope, $window, $state, $location, enlaceService){
	var self = this;

	enlaceService.promise.then(function(){
		enlaceService.inboxWeb().success(function(data) {
		})
		.error(function(dataT, status, headers, config) {
		});
	});

	self.activarlinkInboxWeb = function (){
		$window.location=enlaceService.inbox;		
	}
}]);

