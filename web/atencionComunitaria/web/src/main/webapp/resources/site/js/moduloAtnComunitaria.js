App.service('modalFactory',function(){
	var cancelar=false;
	return {
		setCancelar: function (valor) {
			cancelar=valor;
		},
		getCancelar: function () {
			return cancelar;
		}
	};	
});

App.controller('ModalInstanceCtrl', function ($scope, $route, $state,$uibModalInstance,modalFactory) {

	$scope.$watch(function(){return modalFactory.getCancelar()},function (newValue, oldValue) {
		if (newValue !== oldValue) {$scope.cancel();}
	});
	$scope.cancelar = function () {
		$uibModalInstance.close();
		$state.forceReload();
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
		modalFactory.cancel();
	};
});


App.controller('atencionControlador', function ($rootScope, $scope, $http, $location, $state, $stateParams, $window, $route, $uibModal, registrarRutasService) {

	var vm = this;
	vm.modelo = {};
	vm.bDeclarante = false;
	vm.bEntePublico = false;

	$rootScope.panel = 0;
	$scope.vPatron = {
			AlfaNumerico: /^[A-Za-z0-9]*$/,
			Alfa: /^[a-zA-Z]*$/,
			FormatoNombres:  /^[a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ'-]*$/,
			AlfaText: 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones y acentos',
			TipoCed: /^[V|v|E|e]/,
			Cedula: /^[0-9]/,
			cedulaMaxLength: "9",
			cedulaMinLength: "6",
			nombreMaxlength: "50",
			nombreMinlength: "2",
			CedulaText: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 6 caracteres y un m&aacute;ximo de 9 caracteres <br/> Ejemplo: 12345678",
			NumeroDocumento: /^[0-9]{6,11}$/,
			NumeroDocumentoText: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 6 caracteres y un m&aacute;ximo de 11 caracteres <br/> Ejemplo: 12345678",
			NumeroDocumentoEnteText: "Este campo no permite caracteres especiales <br/> Ejemplo: 123ER678",
			excepciones : ['Backspace', ' ', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete'],
			capTuraEvento : function(event, patron){

			}
	};

	$scope.onKeyDown = function(event, validacion, id){
		var excepciones = $scope.vPatron.excepciones;
		for(var i = 0; i < excepciones.length; i++){
			if (event.key === excepciones[i])
				return;
		}
		var patron = $scope.vPatron.Cedula;
		if (!patron.test(event.key)) {
			event.preventDefault();
			$scope.errorTecla[id] = true;
			return;
		}
		$scope.errorTecla[id]= false;
	}

	$scope.onKeyDownAlfa = function(event, validacion, id){
		var excepciones = $scope.vPatron.excepciones;
		for(var i = 0; i < excepciones.length; i++){
			if (event.key === excepciones[i])
				return;
		}
		var patron = $scope.vPatron.FormatoNombres;
		if (!patron.test(event.key)) {
			event.preventDefault();
			$scope.errorTecla[id] = true;
			return;
		}
		$scope.errorTecla[id]= false;
	}

	$scope.onKeyDownAlfaNum = function(event, validacion, id){
		var excepciones = $scope.vPatron.excepciones;
		for(var i = 0; i < excepciones.length; i++){
			if (event.key === excepciones[i])
				return;
		}
		var patron = $scope.vPatron.AlfaNumerico;
		if (!patron.test(event.key)) {
			event.preventDefault();
			$scope.errorTecla[id] = true;
			return;
		}
		$scope.errorTecla[id]= false;
	}

	$scope.onBlur = function(){
		$scope.errorTecla[id] = false;
	}

	$scope.clearFields = function(form){
		if(form.$name == "myFormAtencion"){
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
		}else if(form.$name == "formTipoSolicitud"){
			$scope.listTramites = null;
			$scope.blnCampoLlenar = null;
			$scope.tipoTramite = null;
			$scope.codTramite = null;
			$scope.tramiteSelected = null;
			$scope.valorTramite = null;
			$scope.tipoDocumentoSolicitud = null;
			$scope.tipoDocConsult = null;
			$scope.nroDocumentoConsult = null;
			$scope.nroDocumentoSolicitud = null;
			$scope.nroSolicitud = null;
			$("input[name=modulo_field]").removeAttr("checked");
			$("input[name=tipoSolicitud]").removeAttr("checked");
		}
	}
	$scope.reloadPage = function(size){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "/web-atencionComunitaria/pages/modalCancelar.html",
			controller: 'ModalInstanceCtrl',
			size: size,
			resolve: {

			}
		});
	}
	$scope.tipoDocumentoChange = function(){
		$scope.tipoDocCdano = null;
		$scope.nroDocCed = null;
		$scope.nroDoc = null;
	}
	$scope.triggerPopOver = function(elem){
		$('input[name=' + elem + ']').trigger('popoverValid');
	}

	$scope.tSolicitante = function(elm, e){
		var datos = {};
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
		var datos = {};
		var metodo = "POST";
		var modulo = "/web-atencionComunitaria/atComun/" + elm;
		$scope.elm = elm;
		if(elm == "enteDiv"){
			vm.bDeclarante = false;
			vm.bEntePublico = true;
		}else if(elm == "ciudadanoDiv"){
			vm.bDeclarante = true;
			vm.bEntePublico = false;
		}

		var performFunction = functionDataSolicitante;
		genericAjax($http, $state, $stateParams, $scope, metodo, modulo, datos, performFunction);
		$scope.heightPerformPeticionDatos = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/0.9) + 'px;';
	}

	$scope.$watch("listDocumentos", function(newValue, oldValue){
		if(newValue != null){
			$scope.blnEntidad = $scope.elm;
		}
	});


	$scope.$watch("listTipoEnte", function(newValue, oldValue){
		if(newValue != null){
			$scope.blnEntidad = $scope.elm;
		}
	});
	$scope.continuarTipoSolicitud=function(nombreModulo, e){
		var blnContinue = true;
		var blnIdentidad = true;
		if($scope.blnEntidad == 'ciudadanoDiv'){
			var tipoDocumento = $scope.myFormAtencion.tipoDocumento_field.$modelValue;
			if(tipoDocumento == null){
				blnContinue = false;
				blnIdentidad = false;
			}
			if((tipoDocumento != null && tipoDocumento.codigo == 'CED') && (($scope.myFormAtencion.tipoDocCdano_field.$modelValue == '' || $scope.myFormAtencion.tipoDocCdano_field.$modelValue == null) || ($scope.myFormAtencion.nroDocCed_field.$modelValue == '' || $scope.myFormAtencion.nroDocCed_field.$modelValue == null))){
				blnContinue = false;
				blnIdentidad = false;
			}
			if((tipoDocumento != null && tipoDocumento.codigo != 'CED') && ($scope.myFormAtencion.nroDoc_field.$modelValue == '' || $scope.myFormAtencion.nroDoc_field.$modelValue == null)){
				blnContinue = false;
				blnIdentidad = false;
			}
			if(!blnIdentidad){
				if(
						($scope.myFormAtencion.nomCdanoP_field.$modelValue == '' || $scope.myFormAtencion.nomCdanoP_field.$modelValue == null) 
						&&
						($scope.myFormAtencion.nomCdanoS_field.$modelValue == '' || $scope.myFormAtencion.nomCdanoS_field.$modelValue == null) 
						&&
						($scope.myFormAtencion.apeCdanoP_field.$modelValue == '' || $scope.myFormAtencion.apeCdanoP_field.$modelValue == null) 
						&&
						($scope.myFormAtencion.apeCdanoS_field.$modelValue == '' || $scope.myFormAtencion.apeCdanoS_field.$modelValue == null) 
				){
					blnContinue = false;
				}else{
					blnContinue = true;
				}
			}
		}else if($scope.blnEntidad == 'enteDiv'){
			if($scope.myFormAtencion.tipoEnte_field.$modelValue == null){
				blnContinue = false;
			}
			if($scope.myFormAtencion.tipoEnte_field.$modelValue == null){
				blnContinue = false;
			}
			if($scope.myFormAtencion.nroOficio_field.$modelValue == '' || $scope.myFormAtencion.nroOficio_field.$modelValue == null){
				blnContinue = false;
			}
		}

		if($("#waitingDiv").is(":visible")){
			return false;
		}
		if(blnContinue){
			$rootScope.panel = 1;
			$scope.blnTipoSolicitud = "";
			$("input[name=solicitud]").removeAttr("checked");
			$scope.heightPerformPeticionDatos = 0;
			$scope.heightPerform = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/1) + 'px;';
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
		var metodo = "POST";
		var datos ={};
		datos.ruta = registrarRutasService.propiedades['sarc.web.atc.nuevaSolicitud.url'];
		var modulo = registrarRutasService.propiedades['sarc.web.atc.direccionesControlador.url'];
		var performFunction = functionNuevoTramite;
		genericAjax($http, $state, $stateParams, $scope, metodo, modulo, datos, performFunction);
	}
	$scope.$watch("tipoDocumentoSolicitud", function(newValue, oldValue){
		$rootScope.tipoDocumentoSolicitud = newValue;
	})
	$scope.changeTipoConsult = function(){
		$scope.tipoDocConsult = undefined;
		$scope.nroDocumentoConsult = undefined;
		$scope.nroDocumentoSolicitud = undefined;
	}
	$scope.tipoConsulta = function(key){
		if(key == 1){
			$scope.blnCampoLlenar = 'documentoIdent';
			$scope.NumeroSolicitudSeprtr = '';
		}
		if(key == 2){
			$scope.blnCampoLlenar = 'nroOficioSolicitud'; 
			$scope.NumeroSolicitudSeprtr = '1';
			$scope.placeHolderModel = 'N\u00famero de solicitud'; 
			$scope.docOrSol = true; 
			$scope.nroSolicitud = null;
		}
		if(key == 3){
			$scope.blnCampoLlenar = 'nroOficioSolicitud'; 
			$scope.NumeroSolicitudSeprtr = '0';
			$scope.placeHolderModel = 'N\u00famero de documento'; 
			$scope.docOrSol = false; 
			$scope.nroSolicitud = $scope.nroOficio;
		}
	}
	$scope.checkTipoTramite=function(tipo, tramiteSelected, e){

		if(vm.bDeclarante){
			vm.modelo.ciudadano = "D";
		}else if(vm.bEntePublico){
			vm.modelo.ciudadano = "E";
		}

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
			var metodo = "POST";
			var datos = vm.modelo.ciudadano;
			var modulo = registrarRutasService.propiedades['sarc.web.atc.consultTramite.url'] + tipo;
			var performFunction = functionCheckTipoTramite;
			genericAjax($http, $state, $stateParams, $scope, metodo, modulo, datos, performFunction);
			$scope.tramitesCol = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/2) + 'px;';
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
		$scope.tipoDocumentoSolicitud = null;
		$scope.tipoDocConsult = null;
		$scope.nroDocumentoConsult = null;
		$scope.nroDocumentoSolicitud = null;

		$scope.nroSolicitud = null;
		$scope.tipoDocEnteConsult = null;
		$("input[name=tipoSolicitud]").removeAttr("checked");

		$scope.tipoDocumentoSolicitud = $scope.tipoDocumento;
		$scope.tipoDocConsult = $scope.tipoDocCdano;
		$scope.nroDocumentoConsult = $scope.nroDocCed;
		$scope.nroDocumentoSolicitud = $scope.nroDoc;
		if($scope.blnEntidad == 'ciudadanoDiv'){
			$scope.nroSolicitud = $scope.nroDoc;
		}else if($scope.blnEntidad == 'enteDiv'){
			$scope.nroSolicitud = $scope.nroOficio;
		}
		$scope.tipoDocEnteConsult = $scope.tipoDocEnte;

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
			$rootScope.panel = 2;
			$scope.blnDatos = true;
		}else{

		}

	}
	$scope.finCreacionSolicitud=function(myFormAtencion, e){
		$scope.master = myFormAtencion;
		var method = "POST";
		var action = null;		
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
			if($scope.nroDocCed != null || $scope.nroDoc != null){
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
			$rootScope.nueva = 1;
			data.modulo = $scope.tipoTramite;
			data.nombreModulo = $scope.tramiteSelected;
			data.tramite = $scope.codTramite;
			data.nombreTramite = $scope.valorTramite;
			action = routeFunctionPerform;
		}
		if($scope.blnTipoSolicitud == "consultaSolicitud"){
			$rootScope.nueva = 0;
			data.tipoSolicitante = $scope.tipoSolicitante;
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
			action = routeFunctionPerform;
		}
		var modulo = registrarRutasService.propiedades['sarc.web.atc.finCreacionSolicitud.url'];
		genericAjax($http, $state, $stateParams, $scope, method, modulo, data, action);
	}
	$scope.$watch("heightPerformPeticionDatos", function(newValue, oldValue){
		$rootScope.heightPerformPeticionDatos = newValue;
	})
	$scope.backAcordeon = function(){
		var numero = $rootScope.panel;
		$rootScope.panel = numero - 1;
		if($rootScope.panel == 0){
			$scope.heightPerformPeticionDatos = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/0.9) + 'px;';
		}
		if($rootScope.panel == 1){
			$scope.tipoTramite = null;
			$scope.codTramite = null;
			$scope.blnTipoSolicitud = null;
			$scope.blnCampoLlenar = null;
			$scope.blnDatos = false;
			$("input[name=solicitud]").removeAttr("checked");
			$scope.heightPerform = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/1) + 'px;';
		}
		if($rootScope.panel == 2){
			$scope.blnDatos = false;
		}
	}
});


App.controller('crearSolControlador', function ($scope, $http, $state, $location, $stateParams, registrarRutasService, $window, $rootScope) {

	var self = this;
	var data = {};
	var method = "POST";
	self.home = registrarRutasService.propiedades['sarc.web.home.url'];	
	$rootScope.home = self.home;
	var modulo = registrarRutasService.propiedades['sarc.web.atc.consultDetalle.url'] + $stateParams.id;
	var action = crearSolicitudAction;
	genericAjax($http, $state, $stateParams, $scope, method, modulo, data, action);

	self.finalizarDetalle = function(){
		$window.location.href = $rootScope.home;
	}
	$scope.finalizarDetalle = self.finalizarDetalle;
});


App.controller('detalleControlador', function ($scope, $http, $location, $state, $stateParams, $window, $rootScope, registrarRutasService, $window) {

	$scope.$watch("masterForm", function(newValue, oldValue){
		var masterForm = newValue;
		if(masterForm != null && masterForm != ""){
			if(masterForm != "init"){
				$scope.blnSolicitud = true;
				$scope.numSol = masterForm.numeroSolicitud;
				$scope.tipo = masterForm.solicitante.tipo;
				if(masterForm.solicitante.tipo == 'E'){
					$scope.nomOrgano = masterForm.solicitante.documentoEntePublico.entePublico.nombre;
					$scope.tipoDocOrgano = masterForm.solicitante.documentoEntePublico.tipoDocumentoEntePublico;
					$scope.numDocOrgano = masterForm.solicitante.documentoEntePublico.numero;
				}
				if(masterForm.solicitante.tipo == 'D'){
					$scope.nomCdanoP = masterForm.solicitante.ciudadano.primerNombre;
					$scope.apeCdanoP = masterForm.solicitante.ciudadano.primerApellido;
					var documentoIdentidad = masterForm.solicitante.ciudadano.documentoIdentidad;
					$scope.tipoDoc = documentoIdentidad[0].tipoDocumento.nombre;
					$scope.numDoc = documentoIdentidad[0].numero;
				}
				$scope.modulo = masterForm.modulo.nombre;
				$scope.tramite = masterForm.tramite.nombre;
				$scope.estado_solicitud = masterForm.estadoSolicitud;
				$scope.oficina = masterForm.oficina.nombre;
				$scope.fecha_inicio = masterForm.fechaInicio;
				movementScale("glyphicon.glyphicon-ok.glyphSizeMin","span.");
			}
		}else{
			$scope.BlnExitoso = 0;
			$stateParams.errorSol = "crearSol_001";
			$state.transitionTo('errorSol', $stateParams);
		}
	});

	$scope.finalizarDetalle = function(registrarRutasService, $window){
		//$state.transitionTo('/');
		registrarRutasService.promise.then(function(){
			$window.location = registrarRutasService.propiedades['sarc.web.home.url'];

		});
	}	
	$scope.finalizarDetalle = function(registrarRutasService){
		//$state.transitionTo('/');
		$window.location = registrarRutasService.propiedades['sarc.web.home.url'];

	}
});
App.controller('listarSolControlador', function ($scope, $http, $location, $state, $stateParams, $window, $rootScope, $timeout, $interval, registrarRutasService, $window) {
	$scope.panel = 1;
	$scope.currentPage = 1;
	$scope.itemsPerPage = 10;
	$scope.maxSize = 5;
	$scope.totalItems = 10;
	$scope.BlnLoad = 0;
	$scope.dataLoaded = "0%";
	movementScale("glyphicon.glyphicon-comment.glyphSizeMin","span.");
	var lsControlador = this;
	var vm = this;
	$scope.tramitesCol = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())*1.5) + 'px;';
	var data = {};
	var method = "POST";
	var modulo = registrarRutasService.propiedades['sarc.web.atc.listSolicitudes.url'] + $stateParams.tipoSolicitante + "/" + $stateParams.tipo + "/" + $stateParams.nro;
	var action = listarSolicitudAction;
	$scope.dataLoaded = "10%";
	genericAjax($http, $state, $stateParams, $scope, method, modulo, data, action);

	$scope.$watch("itemsPerPage", function(newValue, oldValue){
		$rootScope.itemsPerPage = newValue;
	});
	$scope.$watch("ContentListSolicitudes", function(newValue, oldValue){
		$timeout(function() {
			$scope.BlnLoad = 1;
		}, 500);
	});
	$scope.cambioItemPerPage = function(newItemPerPage, e){
		$timeout(function() {
			var h = $("#table_id").css("height").replace("px","");
			var total = Number(h) + 200;
			$scope.tramitesCol = total + "px";

		}, 1000);
	}
	$scope.selectNumeroSolicitud=function(id, e){
		if($scope.solicitudSelected == id){
			$scope.solicitudSelected = undefined;
		}else{
			$scope.solicitudSelected = id;
		}

	}
	$scope.consultDetalleSol=function(e){
		var data = {};
		var id = $scope.solicitudSelected;
		if(id == undefined){
			return null;
		}
		var method = "POST";
		var modulo = registrarRutasService.propiedades['sarc.web.atc.consultDetalle.url'] + id;
		var action = consultarDetalleSolicitudAction;
		genericAjax($http, $state, $stateParams, $scope, method, modulo, data, action);
		$scope.panel = 2;
		$scope.tramitesCol = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/1.1) + 'px;';
	}
	$scope.regresarListado=function(e){
		$scope.panel = 1;
		$scope.tramitesCol = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/1.1) + 'px;';
		$scope.BlnDetalle = false;
	}

	$scope.finalizarDetalle = function(){
		//$state.transitionTo('atencionComunitaria');
		registrarRutasService.promise.then(function(){
			$window.location = registrarRutasService.propiedades['sarc.web.home.url'];

		});
	}          

	$scope.finalizarDetalle = function(){
		//$state.transitionTo('atencionComunitaria');
		$window.location = registrarRutasService.propiedades['sarc.web.home.url'];
	}

});
