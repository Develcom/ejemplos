var appPrincipal = angular.module("angularPrincipal", ['ngRoute'])
    /*.config(function($routeProvider){
        $routeProvider
            .when("/atencionComunitaria", {
                controller: "MainCtrl",
                //controllerAs: "vm",
                templateUrl: "/web-atencionComunitaria/atComun/iniciar"
            })
			.when("/autenticarCiudadano", {
                controller: "MainCtrl",
                //controllerAs: "vm",
                templateUrl: "/web-autenticarCiudadano/iniciar"
            })
			.when("/adopcion", {
                controller: "adopcionControlador",
                //controllerAs: "vm",
                templateUrl: "/web-adopcion/adopModulo/iniciar",
				 data: {
				  pageClass: 'adopcionModule'
				}
            })
			.when("/inicio", {
                controller: "MainCtrl",
                //controllerAs: "vm",
                templateUrl: "/web-general/iniciar"
            })
			//.otherwise({
			//	redirectTo: '/inicio'
			//});
    })*/

.controller('MainCtrl', function ($scope) {
	$scope.message = "HOLA MUNDO JUAN";
});

function LoginCheckCtrl($scope) {
    $scope.loginData = false
    $scope.login = function() {
        console.log($scope.loginData  ? 'logged in' : 'not logged in');
        $scope.loginData = true;
    };
    $scope.logout = function() {
        console.log($scope.loginData ? 'logged in' : 'not logged in');
        $scope.loginData = false;
    };
}

//Controlador que inicia el módulo solicitado en la pantalla principal
appPrincipal.controller('iniciaModuloControlador', function($scope,$http) {
	
	$scope.iniciarModulo=function(nombreModulo, e){
		if($("#waitingDivTop").is(":visible")){
			return false;
		}
		$("#waitingDivTop").aparecerElemento();
		console.log("Iniciando m\u00f3dulo: "+nombreModulo);
		
		$http({
	            method: 'GET',
	            url: '/web-general/cabeceraRender',
				data: {}
	        }).then(
			function(response) {
				console.log('consulta satisfactoria');
				angular.element(document.querySelector('#cabecera')).html(response.data);
				console.log(response);
				//$("#atencionComunitaria").trigger("click");
				$("#waitingDivTop").esconderElemento();
			},
			function(response) {
				console.log('consulta errada');
			});
	}
	$scope.continuarModulo=function(nombreModulo, e){
		var modulo = $.trim($("#body").attr("class").replace("page",""));
		var method = "GET";
		/*$("#continuarTag").attr("href","#/" + modulo);*/
		//var datos = { 'madre_check': 'true', 'padre_check': 'true'};
		var datos = "";
		
		if(modulo.indexOf("/web-adopcion/adopModulo/modulo") != -1){
			var madre = "";
			var padre = "";
			if($('input[name="padre_check"]:checked').length > 0){
				padre = "true";
			}
			if($('input[name="madre_check"]:checked').length > 0){
				madre = "true";
			}
			method = "POST";
			httpGetFuncAdop($http, method, modulo, madre, padre, "body");
		}else{
			httpGetFunc($http, method, modulo, datos, "body");
		}
		/*$http({
	            method: 'GET',
	            url: modulo
				//,data: {}
	        }).then(
			function(response) {
				console.log('consulta satisfactoria');
				angular.element(document.querySelector('#body')).html(response.data);
				console.log(response);
				//$("#atencionComunitaria").trigger("click");
				$("#waitingDivTop").esconderElemento();
			},
			function(response) {
				console.log('consulta errada');
			});*/
		//alert($.trim($("#body").attr("class").replace("page","")));
		if(modulo.indexOf("/web-adopcion/adopModulo/modulo") != -1){
			$("#body").attr("class","page /web-adopcion/adopModulo/adopIdent");
		}
	}
	
});
appPrincipal.controller('atencionController', function($scope) {
		$scope.message = 'Look! I am an about page.';
	});

//Inician las istrucciobes del controlador de fecha y hora
appPrincipal.controller('tiempoCtrl', ['$scope', function ($scope) {
  $scope.formatD = 'fullDate';
  $scope.formatH = 'h:mm:ss a';
}]);

appPrincipal.directive("myCurrentTime", function(dateFilter){
    return function(scope, element, attrs){
        var format;
        
        scope.$watch(attrs.myCurrentTime, function(value) {
            format = value;
            updateTime();
        });
        
        function updateTime(){
            var dt = dateFilter(new Date(), format);
            element.text(dt);
        }
        
        function updateLater() {
            setTimeout(function() {
              updateTime(); // update DOM
              updateLater(); // schedule another update
            }, 1000);
        }
        
        updateLater();
    }
});

appPrincipal.directive('routeData', function() {
    return {
      controller: 'RouteDataController',
      controllerAs: 'RouteData',
      bindToController: true
    }
  })
  
  .controller('RouteDataController', function($rootScope, $route) {
    
    var self = this;
    
    $rootScope.$on('$routeChangeSuccess', setCurrentRouteData);
    
    setCurrentRouteData();
    
    function setCurrentRouteData() {
		if($route.current == null){
			return false;
		}
      angular.extend(self, $route.current.$$route.data || {});
    }
	
  })

  .config(function($routeProvider) {
    
    $routeProvider
        .when('/about', {
            templateUrl: '/web-adopcion/adopModulo/welcome',
            controller: 'aboutController',
            controllerAs: 'actrl',
            data: {
              pageClass: 'page-about'
            }
        })

        .when('/contact', {
            templateUrl: 'page-contact.html',
            controller: 'contactController',
            controllerAs: 'cctrl',
            data: {
              pageClass: 'page-contact'
            }
        })
		.when("/atencionComunitaria", {
                controller: "atencionControlador",
                controllerAs: "atCtrl",
                //templateUrl: "/web-atencionComunitaria/atComun/iniciar_modulo",
				templateUrl: "/web-atencionComunitaria/view/formAtencion_inicio.html",
				data: {
				  pageClass: '/web-atencionComunitaria/atComun',
				  nivel: 'finalizarCreacion'
				}
            })
				.when("/detalleSolicitud/:id", {
					controller: "crearSolControlador",
					controllerAs: "csControlador",
					templateUrl: "/web-atencionComunitaria/view/detalleSolicitud.html",
					data: {
					  pageClass: '/web-atencionComunitaria/atComun',
					  nivel: 'detalle-solicitud'
					}
				})
				.when("/atencionComun/:errorSol", {
					controller: "errorCreacionControlador",
					controllerAs: "ErrCsControlador",
					templateUrl: "/web-atencionComunitaria/view/errorDisplay.html",
					data: {
					  pageClass: '/web-atencionComunitaria/atComun',
					  nivel: 'detalle-solicitud'
					}
				})
				.when("/listSolicitud/:tipo/:nro", {
					controller: "listarSolControlador",
					controllerAs: "lsControlador",
					templateUrl: "/web-atencionComunitaria/view/listadoSolicitudes.html",
					data: {
					  pageClass: '/web-atencionComunitaria/atComun',
					  nivel: 'listado-solicitud'
					}
				})
				.when("/listSolicitud/:nro", {
					controller: "listarSolControladorDetalle",
					controllerAs: "lsControlador",
					templateUrl: "/web-atencionComunitaria/view/listadoSolicitudes.html",
					data: {
					  pageClass: '/web-atencionComunitaria/atComun',
					  nivel: 'listado-solicitud'
					}
				})
		.when("/autenticarCiudadano", {
			controller: "MainCtrl",
			//controllerAs: "vm",
			templateUrl: "/web-autenticarCiudadano/iniciar",
			data: {
			  pageClass: 'autenticarCiudadanoModulo'
			}
		})
		.when("/adopcion", {
			controller: "adopcionControlador",
			controllerAs: "adopCtrl",
			templateUrl: "/web-adopcion/adopModulo/iniciar",
			data: {
			  pageClass: '/web-adopcion/adopModulo',
			  nivel: 'modulo'
			}
		})
		.when("/modulo/adopcionModulo/", {
			controller: "ctrlModulo",
			controllerAs: "ctrlModulo",
			templateUrl: "/web-adopcion/adopModulo/modulo/",
			 data: {
			  pageClass: 'adopcionModulo',
			  nivel: '1'
			}
		})
		.when("/inicio", {
			controller: "MainCtrl",
			//controllerAs: "vm",
			templateUrl: "/web-general/iniciar",
			data: {
			  pageClass: 'inicioModulo'
			}
		});
    
  });
  appPrincipal.controller('atencionControlador', function ($scope, $http, $location) {
	//var testing = document.getElementById("body").className;
	//var modulo = $.trim($("#body").attr("class").replace("page",""));
	//var modulo = $.trim($("#body").attr("class").replace("page",""));
	//alert(ctrlModulo.modulo);
	//$("#continuarTag").attr('href', '#/modulo/adopcionModulo/');
	if($.trim($("#cabecera").html()) == ''){
		if($("#waitingDivTop").is(":visible")){
			return false;
		}
		$("#waitingDivTop").aparecerElemento();
		
		$http({
	            method: 'GET',
	            url: '/web-general/cabeceraRender',
				data: {}
	        }).then(
			function(response) {
				console.log('consulta satisfactoria');
				angular.element(document.querySelector('#cabecera')).html(response.data);
				console.log(response);
				//$("#atencionComunitaria").trigger("click");
				$("#waitingDivTop").esconderElemento();
			},
			function(response) {
				console.log('consulta errada');
			});
	}
	$scope.vPatron = {
        text: 'guest',
        AlfaNumerico: /^[A-Za-z0-9]*$/,
		Alfa: /^[a-zA-Z]*$/,
		TipoCed: /^[V|v|E|e]/,
		Cedula: /^[0-9]{1,11}$/,
		NumeroDocumento: /^[0-9]{1,9}$/,
      };
	$scope.tipoSolicitante=function(elm, e){
		var datos = {};
		if($("#waitingDiv").is(":visible")){
			return false;
		}
		$scope.tipoDocumento = null;
		$scope.tipoDocCdano = null;
		$scope.nroDocCed = null;
		$scope.nroDoc = null;
		$scope.nomCdanoP = null;
		$scope.nomCdanoS = null;
		$scope.apeCdanoP = null;
		$scope.apeCdanoS = null;
		$scope.tipoEnte = null;
		$scope.tipoDocEnte = null;
		$scope.nroOficio = null;
		$("#waitingDiv").aparecerElemento();
		var data = {};
		var method = "POST";
		var modulo = "/web-atencionComunitaria/atComun/" + elm;
		var res = $http.post(modulo);
			res.success(function(data, status, headers, config) {
				//alert(data);
				if(data != null){
					if(elm == 'ciudadanoDiv'){
						$scope.listDocumentos = data.documentos;
					}else if(elm == 'enteDiv'){
						$scope.listTipoEnte = data.entes;
						$scope.listTipoDocEnte = data.documentosEnte;
					}
				}
			});
			res.error(function(data, status, headers, config) {
				alert( "failure message: " + JSON.stringify({data: data}));
			});
		//$("#contentTipoSolicitante div").hide("slow");
		//httpGetFunc($http, 'GET', '/web-atencionComunitaria/atComun/' + elm, datos, elm, -1);
		//$('#' + elm).show("slow");
		$scope.blnEntidad = elm;
		$("#waitingDiv").esconderElemento();
		initializeButtons();
		
	}
	
	$scope.continuarTipoSolicitud=function(nombreModulo, e){
		var modulo = $.trim($("#body").attr("class").replace("page",""));
		var method = "GET";
		var datos = "";
		var numero = $scope.num_acordeon;
		var blnContinue = true;
		var blnIdentidad = true;
		if($scope.blnEntidad == 'ciudadanoDiv'){
			if($scope.tipoDocumento == null){
				blnContinue = false;
				blnIdentidad = false;
			}
			if(($scope.tipoDocumento != null && $scope.tipoDocumento.codigo == 'CED') && (($scope.tipoDocCdano == '' || $scope.tipoDocCdano == null) || ($scope.nroDocCed == '' || $scope.nroDocCed == null))){
				blnContinue = false;
				blnIdentidad = false;
			}
			if(($scope.tipoDocumento != null && $scope.tipoDocumento.codigo != 'CED') && ($scope.nroDoc == '' || $scope.nroDoc == null)){
				blnContinue = false;
				blnIdentidad = false;
			}
			if(!blnIdentidad){
				if(
					($scope.nomCdanoP == '' || $scope.nomCdanoP == null) 
					&&
					($scope.nomCdanoS == '' || $scope.nomCdanoS == null) 
					&&
					($scope.apeCdanoP == '' || $scope.apeCdanoP == null) 
					&&
					($scope.apeCdanoS == '' || $scope.apeCdanoS == null) 
				){
					blnContinue = false;
				}else{
					blnContinue = true;
				}
			}
		}else if($scope.blnEntidad == 'enteDiv'){
			if($scope.tipoEnte == null){
				blnContinue = false;
			}
			if($scope.tipoEnte == null){
				blnContinue = false;
			}
			if($scope.nroOficio == '' || $scope.nroOficio == null){
				blnContinue = false;
			}
		}
		
		if($("#waitingDiv").is(":visible")){
			return false;
		}
		if(blnContinue){
			if(numero != -1){
				acordeonGo(numero);
				$scope.num_acordeon = numero + 1;
			}
			$scope.blnTipoSolicitud = "";
			//httpGetFunc($http, method, modulo, datos, "tSolicitud", numero);
			$("#continuarBtn").hide();
			//$("#datosBtn").show();
			$("#tSolicitud").aparecerElemento();
			$("#anteriorBtn").aparecerElemento();
			$("#anteriorBtn").habilitar();	
		}else{
			
		}
	}
	$scope.nuevoTramite=function(e){
		$scope.blnTipoSolicitud = "nuevaSolicitud";
		$scope.tipoTramite = null;
		$scope.codTramite = null;
		$scope.tramiteSelected = null;
		$scope.valorTramite = null;
		$scope.listTramites = null;
		//$("#datosBtn").aparecerElemento();
		var method = "POST";
		var datos = "";
		var modulo = "/web-atencionComunitaria/atComun/nuevaSolicitud";
		var res = $http.post(modulo);
			res.success(function(data, status, headers, config) {
				//alert(data);
				if(data != null){
					$scope.listModulos = data.modulos;
				}
			});
			res.error(function(data, status, headers, config) {
				alert( "failure message: " + JSON.stringify({data: data}));
			});
		//httpGetFunc($http, method, modulo, datos, "nuevoTramite", -1);
	}
	$scope.checkTipoTramite=function(tipo, tramiteSelected, e){
		$scope.tipoTramite = tipo;
		$scope.tramiteSelected = tramiteSelected;
		if(tipo == 'NOTAS'){
			$scope.codTramite = tipo;
			$scope.valorTramite = tramiteSelected;
			$scope.listTramites = null;	
		}else{
			$scope.codTramite = null;
			$scope.valorTramite = null;
			$scope.listTramites = null;
			var method = "POST";
			var datos = "";
			var modulo = "/web-atencionComunitaria/atComun/consultTramite/" + tipo;
			var res = $http.post(modulo);
				res.success(function(data, status, headers, config) {
					//alert(data);
					if(data != null){
						$scope.listTramites = data.tramites;
					}
				});
				res.error(function(data, status, headers, config) {
					alert( "failure message: " + JSON.stringify({data: data}));
				});
			$(".tramitesCol").css("height",(($(document).height() - ($('#opciones').height()  + $('#header').height() + $('#footer').height()))/ 2.4));	
		}
	}
	$scope.selectedTramite=function(modulo, tipoTramite, codTramite){
		$scope.tramiteSelected = modulo;
		$scope.valorTramite = tipoTramite;
		$scope.codTramite = codTramite;
	}
	$scope.consultarSolicitud=function(e){
		$scope.blnTipoSolicitud = "consultaSolicitud";
		$scope.blnTipoConsulta = $scope.blnEntidad;
		$scope.blnCampoLlenar = null;
		$scope.tipoTramite = null;
		$scope.codTramite = null;
		$scope.tramiteSelected = null;
		$scope.valorTramite = null;
		//$("#datosBtn").aparecerElemento();
		/*var method = "GET";
		var datos = "";
		var modulo = "/web-atencionComunitaria/atComun/consultaSolicitud";
		httpGetFunc($http, method, modulo, datos, "consultaTramite", -1);*/
	}
	$scope.continuarDatosSolicitud=function(e){
		var blnContinuar = true;
		if($scope.blnTipoSolicitud == 'consultaSolicitud'){
			if($scope.blnCampoLlenar == "documentoIdent"){
				if($scope.tipoDocumentoSolicitud == null){
					blnContinuar = false;
				}
				if($scope.tipoDocumentoSolicitud != null && $scope.tipoDocumentoSolicitud.codigo == 'CED'){
					if($scope.tipoDocConsult == null || $scope.tipoDocConsult == ''){
						blnContinuar = false;
					}
					if($scope.nroDocumentoConsult == null || $scope.nroDocumentoConsult == ''){
						blnContinuar = false;
					}
				}
				if($scope.tipoDocumentoSolicitud != null && $scope.tipoDocumentoSolicitud.codigo != 'CED'){
					if($scope.nroDocumentoSolicitud == null || $scope.nroDocumentoSolicitud == ''){
						blnContinuar = false;
					}
				}
			}
			if($scope.blnCampoLlenar == "nroOficioSolicitud"){
				if($scope.nroSolicitud == null || $scope.nroSolicitud == ''){
					blnContinuar = false;
				}
			}
		}
		if(blnContinuar){
			var numero = $scope.num_acordeon;
			acordeonGo(numero);
			$scope.num_acordeon = numero + 1;
			$scope.blnDatos = true;
			$("#datosBtn").esconderElemento();
			$("#finCreacionSol").aparecerElemento();
			//blnTipoSolicitud
		}else{
			
		}
		
	}
	$scope.finCreacionSolicitud=function(myFormAtencion, e){
		// /page2
		//$location.path(valor);
		$scope.master = myFormAtencion;
		var data = {};
		data.name = $scope.blnTipoSolicitud;
		if($scope.blnEntidad == "enteDiv"){
			data.tipo = "E";
			data.tipoEnte = $scope.tipoEnte.codigo;
			if($scope.tipoDocEnteConsult != null){
				data.tipoDocEnte = $scope.tipoDocEnteConsult.codigo;
			}else{
				data.tipoDocEnte = $scope.tipoDocEnte.codigo;
			}
			data.numeroOficio = $scope.nroOficio;
		}
		if($scope.blnEntidad == "ciudadanoDiv"){
			data.tipo = "D";
			if($scope.tipoDocumento != null){
				data.tipoDocumento = $scope.tipoDocumento.codigo;
				if(data.tipoDocumento != null && data.tipoDocumento != ''){
					if(data.tipoDocumento == "CED"){
						data.numeroDocumento = $scope.tipoDocCdano + "-" + $scope.nroDocCed;
					}else{
						data.numeroDocumento = $scope.nroDoc;
					}
				}
			}
			if($scope.nomCdanoP != null && $scope.nomCdanoP != ''){
				data.primerNombre = $scope.nomCdanoP;
			}
			if($scope.nomCdanoS != null && $scope.nomCdanoS != ''){
				data.segundoNombre = $scope.nomCdanoS;
			}
			if($scope.apeCdanoP != null && $scope.apeCdanoP != ''){
				data.primerApellido = $scope.apeCdanoP;
			}
			if($scope.apeCdanoS != null && $scope.apeCdanoS != ''){
				data.segundoApellido = $scope.apeCdanoS;
			}
		}
		if($scope.blnTipoSolicitud == "nuevaSolicitud"){
			data.modulo = $scope.tipoTramite;
			data.nombreModulo = $scope.tramiteSelected;
			data.tramite = $scope.codTramite;
			data.nombreTramite = $scope.valorTramite;
		}
		if($scope.blnTipoSolicitud == "consultaSolicitud"){
			data.tipoConsulta = $scope.blnCampoLlenar;
			if($scope.blnCampoLlenar == "nroOficioSolicitud"){
				if($scope.docOrSol){
					data.documentoOrSolicitud = "numeroSolicitud";
					data.numeroSolicitud = $scope.nroSolicitud;
				}else{
					data.documentoOrSolicitud = "numeroDocumento";
					data.numeroConsultDocumento = $scope.nroSolicitud;
				}
			}
			if($scope.blnCampoLlenar == "documentoIdent"){
				data.tipoDocumentoSolicitud = $scope.tipoDocumentoSolicitud.codigo;
				if(data.tipoDocumentoSolicitud == "CED"){
					data.nroDocumentoSolicitud =  $scope.tipoDocConsult + "-" + $scope.nroDocumentoConsult;
				}else{
					data.nroDocumentoSolicitud = $scope.nroDocumentoSolicitud;
				}
			}
		}
		var method = "POST";
		var modulo = "/web-atencionComunitaria/atComun/finCreacionSolicitud";
		httpGetFuncResponse($http, $location, method, modulo, data);
		/*if($scope.blnTipoSolicitud == 'nuevaSolicitud'){
			//DETALLE DE SOLICITUD
		}
		
		if($scope.blnTipoSolicitud == 'consultaSolicitud'){
			//LISTADO DE SOLICITUDES
		}*/
	}
	$scope.backAcordeon = function(){
		var numero = $scope.num_acordeon;
		anterior(numero);
		$scope.num_acordeon = numero - 1;
		if($scope.num_acordeon == 1){
			initializeButtons();
			$scope.tipoTramite = "";
			$scope.codTramite = "";
			$scope.blnTipoSolicitud = "";
			$scope.blnCampoLlenar = "";
			$scope.blnDatos = false;
			$("input[name=solicitud]").removeAttr("checked");
		}
		if($scope.num_acordeon == 2){
			$("#datosBtn").aparecerElemento();
			$("#finCreacionSol").esconderElemento();
			$scope.blnDatos = false;
		}
	}
});
appPrincipal.controller('errorCreacionControlador', function ($scope, $http, $location) {
	var data = {};
	var method = "POST";
	var modulo = "/web-atencionComunitaria/atComun/atencionComun/errorSol";
	var res = $http.post(modulo);
		res.success(function(data, status, headers, config) {
			if(data != null && data[0] != null){
				$scope.error_tittle = data[0];
			}
			if(data != null && data[1] != null){
				$scope.error_message = data[1];
			}
		});
		res.error(function(data, status, headers, config) {
			alert( "failure message: " + JSON.stringify({data: data}));
		});
	$scope.finalizarDetalle = function(){
		$location.path("/atencionComunitaria");
	}
});
appPrincipal.controller('crearSolControlador', function ($scope, $http, $location, $routeParams) {
	//alert($routeParams.id);
	var data = {};
	//data.numero_solicitud = $routeParams.id;
	var method = "POST";
	var modulo = "/web-atencionComunitaria/atComun/consultDetalle/" + $routeParams.id;
	var res = $http.post("/web-atencionComunitaria/atComun/consultDetalle/" + $routeParams.id);
		res.success(function(data, status, headers, config) {
			//alert(data);
			if(data != null && data != ""){
				$scope.blnSolicitud = true;
				$scope.numSol = data.numeroSolicitud;
				$scope.tipo = data.solicitante.tipo;
				if(data.solicitante.tipo == 'E'){
					$scope.nomOrgano = data.solicitante.documentoEntePublico.entePublico.nombre;
					$scope.tipoDocOrgano = data.solicitante.documentoEntePublico.tipoDocumentoEntePublico;
					$scope.numDocOrgano = data.solicitante.documentoEntePublico.numero;
				}
				if(data.solicitante.tipo == 'D'){
					$scope.nomCdanoP = data.solicitante.ciudadano.primerNombre;
					$scope.apeCdanoP = data.solicitante.ciudadano.primerApellido;
					/*if(data.solicitante.ciudadano.primerNombre != null || data.solicitante.ciudadano.segundoNombre != null){
						if(data.solicitante.ciudadano.primerNombre.indexOf(",") != -1){
							var nombre_compuesto = data.solicitante.ciudadano.primerNombre.split(",");
							$scope.nomCdanoP = nombre_compuesto[0];
							$scope.nomCdanoS = nombre_compuesto[1];
						}else{
							$scope.nomCdanoP = data.solicitante.ciudadano.primerNombre;
						}
					}
					if(data.solicitante.ciudadano.primerApellido != null || data.solicitante.ciudadano.segundoApellido != null){
						if(data.solicitante.ciudadano.primerApellido.indexOf(",") != -1){
							var apellido_compuesto = data.solicitante.ciudadano.primerApellido.split(",");
							$scope.apeCdanoP = apellido_compuesto[0];
							$scope.apeCdanoS = apellido_compuesto[1];
						}else{
							$scope.apeCdanoP = data.solicitante.ciudadano.primerApellido;
						}
					}*/
					var documentoIdentidad = data.solicitante.ciudadano.documentoIdentidad;
					$scope.tipoDoc = documentoIdentidad[0].tipoDocumento.nombre;
					$scope.numDoc = documentoIdentidad[0].numero;
				}
				$scope.modulo = data.modulo.nombre;
				$scope.tramite = data.tramite.nombre;
				$scope.estado_solicitud = data.estadoSolicitud;
				$scope.oficina = data.oficina.nombre;
				$scope.fecha_inicio = data.fechaInicio;
			}else{
				$location.path("/atencionComun/:errorSol");
			}
			
		});
		res.error(function(data, status, headers, config) {
			alert( "failure message: " + JSON.stringify({data: data}));
		});
	//httpGetFuncResponse($http, $location, method, modulo, data);
	
	$scope.finalizarDetalle = function(){
		//iniciarModulo('atencionComunitaria');
		$location.path("/atencionComunitaria");
	}
});
appPrincipal.controller('listarSolControlador', function ($scope, $http, $location, $routeParams) {
	//alert($routeParams.tipo);
	//alert($routeParams.nro);
	$(".tramitesCol").css("height",(($(document).height() - ($('#opciones').height()  + $('#header').height() + $('#footer').height()))/ 1.8));
	var data = {};
	//data.numero_solicitud = $routeParams.id;
	var method = "POST";
	var modulo = "/web-atencionComunitaria/atComun/listSolicitudes/" + $routeParams.tipo + "/" + $routeParams.nro;
	var res = $http.post("/web-atencionComunitaria/atComun/listSolicitudes/" + $routeParams.tipo + "/" + $routeParams.nro);
		res.success(function(data, status, headers, config) {
			//alert(data);
			if(data != null && data.length > 0){
				$scope.listSolicitudes = data;
				initializeButtons();
				$scope.BlnDetalle = false;
			}else{
				$scope.listSolicitudes = null;
				$location.path("/atencionComun/:errorSol");
			}
		});
		res.error(function(data, status, headers, config) {
			alert( "failure message: " + JSON.stringify({data: data}));
		});
	$scope.selectNumeroSolicitud=function(id, e){
		$scope.solicitudSelected = id;
	}
	$scope.consultDetalleSol=function(e){
		var data = {};
		var id = $scope.solicitudSelected;
		//data.numero_solicitud = $routeParams.id;
		var method = "POST";
		var modulo = "/web-atencionComunitaria/atComun/consultDetalle/" + id;
		var res = $http.post("/web-atencionComunitaria/atComun/consultDetalle/" + id);
			res.success(function(data, status, headers, config) {
				//alert(data);
				if(data != null){
					$scope.blnSolicitud = true;
					$scope.numSol = data.numeroSolicitud;
					$scope.tipo = data.solicitante.tipo;
					$scope.tipoEnte = data.solicitante.tipo;
					if(data.solicitante.tipo == 'E'){
						$scope.nomOrgano = data.solicitante.documentoEntePublico.entePublico.nombre;
						$scope.tipoDocOrgano = data.solicitante.documentoEntePublico.tipoDocumentoEntePublico;
						$scope.numDocOrgano = data.solicitante.documentoEntePublico.numero;
					}
					if(data.solicitante.tipo == 'D'){
						$scope.nomCdanoP = data.solicitante.ciudadano.primerNombre;
						$scope.apeCdanoP = data.solicitante.ciudadano.primerApellido;
						var documentoIdentidad = data.solicitante.ciudadano.documentoIdentidad;
						$scope.tipoDoc = documentoIdentidad[0].tipoDocumento.nombre;
						$scope.numDoc = documentoIdentidad[0].numero;
					}
					$scope.modulo = data.modulo.nombre;
					$scope.tramite = data.tramite.nombre;
					$scope.estado_solicitud = data.estadoSolicitud;
					$scope.oficina = data.oficina.nombre;
					$scope.fecha_inicio = data.fechaInicio;
					acordeonGo(1);
					$("#continuarBtn").esconderElemento();
					$("#anteriorBtn").aparecerElemento();
					$('#anteriorBtn').habilitar();
					$scope.BlnDetalle = true;
				}else{
					$location.path("");
				}
			});
			res.error(function(data, status, headers, config) {
				alert( "failure message: " + JSON.stringify({data: data}));
			});
	
	}
	$scope.regresarListado=function(e){
		anterior(2);
		$scope.BlnDetalle = false;
		initializeButtons();
	}
	$scope.finalizarDetalle = function(){
		//iniciarModulo('atencionComunitaria');
		$location.path("/atencionComunitaria");
	}
});
appPrincipal.controller('adopcionControlador', function ($scope, $http) {
	$('#opciones').displayOn();
	$('#finalizarBtn').displayOff();
	$('#cancelarBtn').deshabilitar();
	$('#cancelarBtn').esconderElemento();
	$('#anteriorBtn').esconderElemento();
	$('#continuarBtn').aparecerElemento();
	//var testing = document.getElementById("body").className;
	//var modulo = $.trim($("#body").attr("class").replace("page",""));
	//var modulo = $.trim($("#body").attr("class").replace("page",""));
	//alert(ctrlModulo.modulo);
	//$("#continuarTag").attr('href', '#/modulo/adopcionModulo/');
	$scope.continuarModulo=function(e){
		//alert("ADOPCION");
		var datos = { 'madre_check': 'true', 'padre_check': 'true'};
		//loadDatos(datos);
		if($("#waitingDiv").is(":visible")){
			return false;
		}
		$("#waitingDiv").aparecerElemento();
		$http({
	            method: 'GET',
	            url: '/web-adopcion/adopModulo/modulo'
	            /*,data: {datos}*/
	        }).then(
			function(response) {
				console.log('consulta satisfactoria');
				angular.element(document.querySelector('#body')).html(response.data);
				console.log(response);
				//$("#atencionComunitaria").trigger("click");
				$("#waitingDiv").esconderElemento();
			},
			function(response) {
				console.log('consulta errada');
			});
	}
});


function loadDatosAdop(madre, padre){
	if($('input[name="padre_check"]:checked').length > 0){
		padre = "true";
	}
	if($('input[name="madre_check"]:checked').length > 0){
		madre = "true";
	}
}