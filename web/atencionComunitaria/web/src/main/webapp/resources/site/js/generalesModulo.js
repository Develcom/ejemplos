function functionDataSolicitante($http, response, $state, $stateParams, $scope){
	if(response.data != null){
		if($scope.elm == 'ciudadanoDiv'){
			$scope.listDocumentos = [
			                         {codigo : "CED", descripcion: "CÉDULA/NUI"},
			                         {codigo : "PAS", descripcion: "PASAPORTE"}];
			$scope.tipoSolicitante = 'D';
		}else if($scope.elm == 'enteDiv'){
			$scope.listTipoEnte = response.data.entes;
			$scope.listTipoDocEnte = response.data.documentosEnte;
			$scope.tipoSolicitante = 'E';
		}
	}
	
}

function functionNuevoTramite($http, response, $state, $stateParams, $scope){
	if(response.data != null){
		$scope.listModulos = response.data.modulos;
	}
}

function functionCheckTipoTramite($http, response, $state, $stateParams, $scope){
	if(response.data != null){
		$scope.listTramites = response.data.tramites;
	}
}

function crearSolicitudAction($http, response, $state, $stateParams, $scope){
	if(response.data != null && response.data != ""){
		$scope.blnSolicitud = true;
		$scope.numSol = response.data.numeroSolicitud;
		$scope.tipo = response.data.solicitante.tipo;
		if(response.data.solicitante.tipo == 'E'){
			$scope.nomOrgano = response.data.solicitante.documentoEntePublico.entePublico.nombre;
			$scope.tipoDocOrgano = response.data.solicitante.documentoEntePublico.tipoDocumentoEntePublico;
			$scope.numDocOrgano = response.data.solicitante.documentoEntePublico.numero;
		}
		if(response.data.solicitante.tipo == 'D'){
			$scope.nomCdanoP = response.data.solicitante.ciudadano.primerNombre;
			$scope.nomCdanoS = response.data.solicitante.ciudadano.segundoNombre;
			$scope.apeCdanoP = response.data.solicitante.ciudadano.primerApellido;
			$scope.apeCdanoS = response.data.solicitante.ciudadano.segundoApellido;
			var documentoIdentidad = response.data.solicitante.ciudadano.documentoIdentidad;
			if(documentoIdentidad != null && documentoIdentidad[0]){
				$scope.tipoDoc = documentoIdentidad[0].tipoDocumento.nombre;
				$scope.numDoc = documentoIdentidad[0].numero;
			}
			
		}
		$scope.modulo = response.data.modulo.nombre;
		$scope.tramite = response.data.tramite.nombre;
		$scope.estado_solicitud = response.data.estadoSolicitud;
		$scope.oficina = response.data.oficina.nombre;
		$scope.fecha_inicio = response.data.fechaInicio;
		$scope.BlnExitoso = 1;
		if($stateParams.bln == 0 || $stateParams.bln == '0'){
			$scope.BlnExitoso = 0;
		}
		movementScale("glyphicon.glyphicon-ok.glyphSizeMin","span.");
	}else{
		$scope.BlnExitoso = 0;
		$stateParams.errorSol = "numSol_001";
		$state.transitionTo('errorSol', $stateParams);
	}
}

function listarSolicitudAction($http, response, $state, $stateParams, $scope){
	$scope.dataLoaded = "90%";
	if(response.data != null && response.data.length > 0){
		$scope.ContentListSolicitudes = response.data;
		$scope.listSolicitudes = response.data;
		$scope.totalItems = response.data.length;
		$scope.itemsPerPage = 10;
		$scope.dataLoaded = "100%";
	}else{
		$scope.listSolicitudes = null;
		$stateParams.errorSol = "solicitud_001";
		$state.transitionTo('errorSol', $stateParams);
	}
}

function consultarDetalleSolicitudAction($http, response, $state, $stateParams, $scope){
	if(response.data != null){
		$scope.blnSolicitud = true;
		$scope.numSol = response.data.numeroSolicitud;
		$scope.tipo = response.data.solicitante.tipo;
		$scope.tipoEnte = response.data.solicitante.tipo;
		if(response.data.solicitante.tipo == 'E'){
			$scope.nomOrgano = response.data.solicitante.documentoEntePublico.entePublico.nombre;
			$scope.tipoDocOrgano = response.data.solicitante.documentoEntePublico.tipoDocumentoEntePublico;
			$scope.numDocOrgano = response.data.solicitante.documentoEntePublico.numero;
		}
		if(response.data.solicitante.tipo == 'D'){
			$scope.nomCdanoP = response.data.solicitante.ciudadano.primerNombre;
			$scope.nomCdanoS = response.data.solicitante.ciudadano.segundoNombre;
			$scope.apeCdanoP = response.data.solicitante.ciudadano.primerApellido;
			$scope.apeCdanoS = response.data.solicitante.ciudadano.segundoApellido;
			var documentoIdentidad = response.data.solicitante.ciudadano.documentoIdentidad;
			if(documentoIdentidad != null){
				$scope.tipoDoc = documentoIdentidad[0].tipoDocumento.nombre;
				$scope.numDoc = documentoIdentidad[0].numero;
			}
		}
		$scope.modulo = response.data.modulo.nombre;
		$scope.tramite = response.data.tramite.nombre;
		$scope.estado_solicitud = response.data.estadoSolicitud;
		$scope.oficina = response.data.oficina.nombre;
		$scope.fecha_inicio = response.data.fechaInicio;
		acordeonGo(1);
		$("#continuarBtn").esconderElemento();
		$("#anteriorBtn").aparecerElemento();
		$('#anteriorBtn').habilitar();
		$scope.BlnDetalle = true;
	}else{
		$stateParams.errorSol = "numsol_002";
		$state.transitionTo('errorSol', $stateParams);
	}
}

function validarLibrosActivosPorOficina($http, response, $state, $stateParams, $scope){
	$stateParams.url=response.data[0];
	$state.transitionTo('atencionComunitaria', $stateParams);
}

function functionTestOauth(){
	
}
function functionLogout($http, response, $state, $stateParams, $scope){
	if(response.data != null && response.data[0] != null){
		window.location = response.data[0];
	}
}

function functionWorkspace($http, response, $state, $stateParams, $scope){
	if(response != null){
		$scope.templateWorkspace = response;
	}
}