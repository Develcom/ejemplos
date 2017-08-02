var App = angular.module('app', ['ngRoute','ui.router','formly', 'ngMessages', 'formlyBootstrap','ngSanitize','ui.bootstrap', 'customDirectives','validaciones', '720kb.tooltips','sw.calendar','camposFormly']);
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
    	.state("general",{
    		controller: "workspace",
    		url: "",
    		params:{
				token:null,
				url: null
			},
			resolve: {
				myResolve1:
					function($http, $stateParams, $state) {
						var token = getURLParameter("token");
						if($stateParams.token == null){
							$stateParams.token = token;
						}
						var url = "/web-generales/view/listadoSolicitudes.html";
//						var url = "/servicios-seguridad/api/mapping/generales/listadosolicitudes";//?access_token=" + $stateParams.token;
						asyncMethod($http, $stateParams, $state, url);
				}
		    },
			templateUrl: function (stateParams){
				var token = getURLParameter("token");
				if(stateParams.token == null){
					stateParams.token = token;
				}
//				return "/servicios-seguridad/api/mapping/generales/listadosolicitudes";//?access_token=" + stateParams.token;
				return "/web-generales/view/listadoSolicitudes.html"//"/servicios-seguridad/api/mapping/generales/listadosolicitudes?access_token=" + stateParams.token;
			}
    	})
    	.state("logOut",{
			controller: "logoutController",
	    	url: "",
	    	params:{
				token:null
			}
		})
		.state("login",{
	    	url: "",
	    	params:{
				token:null
			},
			templateUrl: "/web-sistemaPrincipal/"
		})
    	.state("validarLibro",{
    		controller: "libroValidarAbiertoControlador",
        	url: "",
        	params:{
				token:null
			},
    	})
    	.state("oauth",{
    		controller: "oauthController",
    		url: ""
    	})
    	.state("gestionECU",{
        	controller: "gestionECU",
        	url: "",
        	params:{
				token:null
			},
			resolve: {
				myResolve1:
					function($http, $stateParams, $state) {
						var token = getURLParameter("token");
						if($stateParams.token == null){
							$stateParams.token = token;
						}
						var url = "/web-ecu/view/gestionECU.html";//"/servicios-seguridad/api/mapping/ecu/gestionecu?access_token=" + $stateParams.token;
						//var url = "/servicios-seguridad/api/mapping/ecu/gestionecu?access_token=" + $stateParams.token;
						asyncMethod($http, $stateParams, $state, url);
				}
		    },
        	templateUrl: function (stateParams){
				return "/web-ecu/view/gestionECU.html";//"/servicios-seguridad/api/mapping/ecu/gestionecu?access_token=" + stateParams.token;
				//return "/servicios-seguridad/api/mapping/ecu/gestionecu?access_token=" + stateParams.token;

        	}
        })
        
        .state("atencionComunitaria",{
        	controller: "atencionControlador",
        	url: "",
        	params:{
				token:null
			},
        	resolve: {
				myResolve1:
					function($http, $stateParams, $state) {
						var token = getURLParameter("token");
						if($stateParams.token == null){
							$stateParams.token = token;
						}
						var url = "/web-atencionComunitaria/view/formAtencion_inicio.html" ;//"/servicios-seguridad/api/mapping/atencioncomunitaria/formatencioninicio?access_token=" + $stateParams.token;
						//var url = "/servicios-seguridad/api/mapping/atencioncomunitaria/formatencioninicio?access_token=" + $stateParams.token;
						asyncMethod($http, $stateParams, $state, url);
				}
		    },
        	templateUrl: function (stateParams){
        		return "/web-atencionComunitaria/view/formAtencion_inicio.html";//"/servicios-seguridad/api/mapping/atencioncomunitaria/formatencioninicio?access_token=" + stateParams.token;
        		//return "/servicios-seguridad/api/mapping/atencioncomunitaria/formatencioninicio?access_token=" + stateParams.token;
        	}
        })
        		.state("detalleSolicitud", {
					controller: "crearSolControlador",
					url: "",
					params:{
						id:null
					},
					templateUrl: "/web-atencionComunitaria/view/detalleSolicitud.html"
				})
				.state("errorSol", {
					controller: "errorCreacionControlador",
					url: "",
					params:{
						errorSol:null,
						errorMsg: null,
						current: null,
						token: null
					},
					templateUrl: "/web-generales/view/errors/errorDisplay.html"
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
    		url: "",
//    		templateUrl: "/web-gestionLibros/view/formularioLibro.html"
    		params:{
				token:null
			},
//        	templateUrl: function (stateParams){
//        		return "/servicios-seguridad/api/mapping/gestionlibros/formulariolibro?access_token=" + stateParams.token;
//
//            }
			resolve: {
				myResolve1:
					function($http, $stateParams, $state) {
						var token = getURLParameter("token");
						if($stateParams.token == null){
							$stateParams.token = token;
						}
						var url = "/servicios-seguridad/api/mapping/gestionlibros/formulariolibro?access_token=" + $stateParams.token
						asyncMethod($http, $stateParams, $state, url);
//						return "/servicios-seguridad/api/mapping/gestionlibros/formulariolibro?access_token=" + stateParams.token;
				}
		    },
        	templateUrl: function (stateParams){
        		return "/servicios-seguridad/api/mapping/gestionlibros/formulariolibro?access_token=" + stateParams.token;

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
});

function asyncMethodSec($http, $stateParams, $state, url) {
	  // perform some asynchronous operation, resolve or reject the promise when appropriate.
	
//	var mheader={};
//	mheader.Authorization='Baerer '+$stateParams.token;
	$http.defaults.headers.common.Authorization = 'Baerer '+$stateParams.token;
	
	$http({
		  method: 'POST',
		  url: url,
		//  headers:mheader,
		  async: false
	}).then(function successCallback(response) {
		$stateParams.url = response.data
		// this callback will be called asynchronously
	    // when the response is available
	  }, function errorCallback(response) {
		  $stateParams.errorSol = response.status;
		  $stateParams.errorMsg = response.statusText;
		  $stateParams.current = $state.current.name;
		  $state.transitionTo('errorSol', $stateParams);
	    // called asynchronously if an error occurs
	    // or server returns response with an error status.
	});
	  
}


function asyncMethod($http, $stateParams, $state, url) {
	  // perform some asynchronous operation, resolve or reject the promise when appropriate.
	
	var mheader={};
	mheader.Authorization='Baerer '+$stateParams.token;
	
	
	$http({
		  method: 'GET',
		  url: url,
		  async: false
	}).then(function successCallback(response) {
		$stateParams.url = response.data
		// this callback will be called asynchronously
	    // when the response is available
	  }, function errorCallback(response) {
		  $stateParams.errorSol = response.status;
		  $stateParams.errorMsg = response.statusText;
		  $stateParams.current = $state.current.name;
		  $state.transitionTo('errorSol', $stateParams);
	    // called asynchronously if an error occurs
	    // or server returns response with an error status.
	});
	  
}

App.filter('ageFilter', function() {
	function calculateAge(birthday) { // birthday is a date
        var ageDifMs = Date.now() - birthday.getTime();
        var ageDate = new Date(ageDifMs); // miliseconds from epoch
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
App.controller('menuController', function ($rootScope, $scope, $http, $location, $state, $stateParams, $window, $route, $uibModal) {
	$rootScope.http = $http;
	$rootScope.state = $state;
    $scope.menu = function(estado) {
    	if($rootScope.token == undefined){
    		var token = getURLParameter('token');
        	$scope.token = token;
    	}else{
    		$scope.token = $rootScope.token;
    	}
    	$stateParams.token = $scope.token;
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
App.directive('tcompile', function ($compile, $templateCache, $controller) {
	  return {
		//scope de la directiva
		//ele: elemento donde es declarada la directiva
		//attrs: atributos del elemento, compartidos por todas las directivas enlazadas
	    link: function (scope, ele, attrs) {
		      scope.$watch(attrs.tcompile, function(newValue) {
		    	  //scope.masterForm = newValue;
		    	  if(attrs.contentTemplate != "" && newValue != null){
		    		  ele.html(attrs.contentTemplate);
			    	  $compile(ele.contents())(scope);
//			    	  $controller("atencionControlador ", { $scope: scope, $element: ele });
//			    	  ele.html($compile($templateCache.get(attrs.contentTemplate))(scope));
//			    	  $compile(ele.contents())(scope);
//			    	  if(valor[0] != null && valor[0].tagName.toLowerCase() ==  'div'){
//			    		  ele.remove("div");
//			    		  $compile(ele.html(valor[0]));
//			    	  }
		    	  }
		      });
//		      scope.$watch(attrs.tcompileitem, function(newValue){
//		    	  rootScope;
//		    	  scope.itemPerPage = newValue;
//		    	  $compile(ele.contents())(scope);
//		  	});
	    }
	  };
	});