
App.controller('controladorEPDIC', function($scope,$http,$uibModal,$rootScope) {
	
	console.log("ESTADO SOLICITUD controladorEPDIC"+$rootScope.objectSelected.codigoEstadoSolicitud);
	
	

	
	$scope.html;
	$scope.ruta;
	$scope.datos;
	$scope.pais1={};
	$scope.estado1={};
	$scope.parroquia1={};
	$scope.municipio1={};
	var estadoSolicitud = "PV";
	
	
	vm=this;
	vm.pasos=0;
	$scope.formulario={};
	$scope.formulario.numSolicitud = $rootScope.objectSelected.numeroSolicitud;
	$scope.formulario.permiso;
	$scope.tipoEscenario = false;
	$scope.vistaPermiso = false;
	
		
	
	//funcion init para mostrar el reporte
	
	console.log("*******************OBJETO SELECCIONADO********"+$scope.formulario.numSolicitud);

//	$scope.rutaPdf;
//	
//	$http({
//		method: 'POST',
//		url: '/web-permisoInhumacionCremacion/leerPermisoEPDIC',
//		data:{id:$rootScope.objectSelected.numeroSolicitud}, 
//	}).then(function successCallback(response) {
//			
//		console.log("*****response+data****"+response.data);
//		
//		rutaData = "http://localhost:8080/web-permisoInhumacionCremacion/resources/pages/plantillas/2016180MC1585.pdf"; //response.data.ruta;
//		console.log("******rutaData*****"+rutaData);
//		
//		template='<embed width=\'900\' height=\'450\' src=\''+ rutaData +'\' type=\'application/pdf\'></embed>';
//		console.log("***template***"+template);
//		$scope.rutaPdf = rutaData;	
//		
//		
//	}, function errorCallback(error) {
//		console.log("error: "+error.data);
//	});
	
	
	urlTemplates=[
	              	'/web-generales/AccesoEPDIC',
		      		//'/web-generales/tipoPermiso',
		      		'/web-generales/permisoInhumacionCremacion',
		      		'',
		      		//'/web-permisoInhumacionCremacion/generarReporte/'
		      		'/web-generales/generarReporte',
		      		'',
		      		'',
		      		'',
		      		'',
		      		'/web-generales/leerPermisoEPDIC',
		      		'/web-generales/validarReporteConforme'
		      	]
	
	bodyHtml=[];
	
	console.log("antes de la llamada ajax del js $scope.formulario.numSolicitud"+ $scope.formulario.numSolicitud);
	console.log("******************urlTemplates[0]****"+urlTemplates[0]);
	$http({
		method: 'POST',
		url: urlTemplates[0],
		data:{numSolicitud:$rootScope.objectSelected.numeroSolicitud},
		headers:{Accept:'text/html'}
	}).then(function successCallback(response) {
		bodyHtml[vm.pasos]= response.data;
		$scope.html= response.data;


	}, function errorCallback(response) {
		console.log("error: "+response.data);
	});
	
	if($rootScope.objectSelected.codigoEstadoSolicitud == estadoSolicitud){
		vm.pasos= 9;
		
		$scope.campoObservacion = false;
	}	
	
	console.log("*******************OBJETO SELECCIONADOoooooo********"+$scope.formulario.numSolicitud);
	console.log("*************vm.pasos*******"+vm.pasos);
		
	
//	$rootScope.cerrarModal();
//	
//	$scope.cancel = function () {
//		$uibModalInstance.dismiss('cancel');
//	}
	
	////////
	
	//cerrar modal boton "x" cancel()
	$scope.cancel = function(){
		$rootScope.cancelar1();
	}

	$scope.cancelar = function (){
		$rootScope.cancelar1();
	}
	

	///// BOTON CONTINUAR ///////
	$scope.eventoBoton = function(){
		
		
		vm.pasos++;
		
		if(vm.pasos == 3){
			abrirModalVerificarDatos();
			return;
		}else
		if(vm.pasos == 4){ //igual que
			
			
			console.log("****FORMULARIO*****"+$scope.formulario.numSolicitud);
			console.log("****FORMULARIO PERMISO*****"+$scope.formulario.permiso);
			
			modalEnviarDatos(urlTemplates[vm.pasos],$scope.formulario);
			return;
		}else		
		if(vm.pasos == 11){
			abrirVentanaConforme(urlTemplates[vm.pasos],$scope.formulario);
			return;
		}else
		if($rootScope.objectSelected.codigoEstadoSolicitud == "NC"){
			vm.pasos= 2;
			
			console.log("************VM PASOS OBSERVACION*******"+vm.pasos);
			//return;
		}	
				
		var datos={};
		datos =$rootScope.objectSelected.numeroSolicitud;
		llamadaAjax(urlTemplates[vm.pasos],datos);
		
	}
	
	llamadaAjax = function(urlPasos,datos){
		
		console.log("******************URL_PARA_JAVA"+urlPasos);
		console.log("******************URL_PARA_JAVA"+datos);
		
	$http({
		method: 'POST',
		url: urlPasos,
		headers:{Accept:'text/html'},
		data:{numSolicitud:$rootScope.objectSelected.numeroSolicitud} //datos
		
	}).then(function successCallback(response) {
		bodyHtml[vm.pasos]= response.data;
		//$scope.html= response.data;
		
		$scope.html = response.data.html;
		$scope.formulario = response.data.datosAPintar;
		
		//console.log("*******RETORNOooo*****"+$scope.formulario.primerNombreAutoriza);

		
	}, function errorCallback(response) {
		console.log("error: "+response.data);
	});
	}

	
	llamadaAjax(urlTemplates[vm.pasos],{numSolicitud:$rootScope.objectSelected.numeroSolicitud});

	$scope.regresar1=function(){
		//alert("REGRESAR: "+vm.pasos);
		if($rootScope.objectSelected.codigoEstadoSolicitud == "NC"){
			vm.pasos= 1;
			
			console.log("************VM PASOS OBSERVACION*******"+vm.pasos);
		}	
		vm.pasos--;
		$scope.html= bodyHtml[vm.pasos];
	};
	
	restarPasos = function(){
		vm.pasos --;
		return;
	}
	
	
	abrirVentanaConforme = function(url,datos){
		var conforme ="conforme";
		var noConforme ="noConforme";
		console.log("*********datos abrir ventana conforme******"+datos.permiso);
		if(datos.permiso == conforme){
			console.log("***********datos.permiso.conforme***"+datos.permiso);
			abrirPopConforme();
		}else if(datos.permiso == noConforme){
			abrirPopNoConforme();
		}
		
	}
	
	abrirPopConforme =function(){
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-permisoInhumacionCremacion/pages/modal/conformeDatosPermiso.html',
			controller: 'ConformeDatosPermisoCtrl',
			resolve: {
			}
		});
		modalInstance.result.then(function () {


		}, function () {
			console.log("************VENGO DE CANCELAR");
			restarPasos();
		});
	}
	
	abrirPopNoConforme =function(){
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-permisoInhumacionCremacion/pages/modal/noConformeDatosPermiso.html',
			controller: 'NoConformeDatosPermisoCtrl',
			resolve: {
			}
		});
		modalInstance.result.then(function () {


		}, function () {
			console.log("************VENGO DE CANCELAR");
			restarPasos();
		});
	}	
	
	modalEnviarDatos = function(url,datos){
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-permisoInhumacionCremacion/pages/modal/enviarDatosForm.html',
			controller: 'EnviarDatosFormCtrl',
			resolve: {
				
			}
		});

		modalInstance.result.then(function () {
		console.log('***************Resultado modal envio datos****'+datos.numSolicitud);
		console.log('***************url imprimirr'+url);
			
		guardarDatosFormulario(datos);
		llamadaAjax(url,datos);
		
		}, function () {
			console.log("************VENGO DE CANCELAR");
			restarPasos();
			
			console.log("**************VMPASOS"+vm.pasos);
			
		});//validar cuando no sea correcto para cambiar estatus de solicitud a 
		
		
	}
	
	//abre modal tipo warning indicando verificar datos( no crea alguna accion en los form)
	abrirModalVerificarDatos = function(){
		
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-permisoInhumacionCremacion/pages/modal/validarDatosForm.html',
			controller: 'ValidarDatosFormCtrl',
			resolve: {
				
			}
		});

		modalInstance.result.then(function () {
			
			return true;
			//eventoBotonFooter();
			//$parent.cont=true;
			//vm.cancelByEV14=true;

		}, function () {
			
		});
		
	}
	
	$scope.buscarPais = function(){
		
		$http({
			method: 'GET',
			url: '/web-permisoInhumacionCremacion/consultarPais'
		}).then(function successCallback(pais) {
			console.log(pais.data);
			$scope.paises = pais.data;
			//$scope.estados=$scope.paises[0].estados;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});

	}

	$scope.buscarPais();
	//$scope.formulario.estado = null;
	
	//consulta busqueda de estados por estado
	$scope.buscarEstado = function (){
		$scope.formulario.pais = $scope.pais1.nombre;
		console.log("Codico del pais"+$scope.pais1.codigo);
		$http({
			method: 'GET',
			url: '/web-permisoInhumacionCremacion/consultarEstados/'+$scope.pais1.codigo
		}).then(function successCallback(estado) {
			console.log(estado.data);
			$scope.estados = estado.data;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});//Fin llamada ajax

	}

	
	$scope.buscarMunicipio = function(){
		$scope.formulario.estado = $scope.estado1.nombre;
		
		console.log("Codico del estado"+$scope.estado1.codigo);
		$http({
			method: 'GET',
			url: '/web-permisoInhumacionCremacion/consultarMunicipios/'+$scope.estado1.codigo
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipios = municipio.data;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});//Fin llamada ajax
		
	}
	
	$scope.mostrarCampoObservacion = function(){
		
		$scope.campoObservacion = true;
	}
	
	$scope.ocultarCampoObservacion = function(){
		
		$scope.campoObservacion = false;
	}
	
	$scope.buscarParroquia = function(){
		
		$scope.formulario.municipio= $scope.municipio1.nombre;
		console.log("CODIGO_MUNICIPIO"+$scope.municipio1.codigo);
		$http({
			method: 'GET',
			url: '/web-permisoInhumacionCremacion/consultarParroquias/'+$scope.municipio1.codigo
		}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquias = parroquia.data;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});//Fin llamada ajax
		
	}
	
	$scope.obtenerParroquia = function(){
		$scope.formulario.parroquia = $scope.parroquia1.nombre;
	}
	
	$scope.validarCert= function(){
		$http({
			method: 'GET',
			url: '/web-permisoInhumacionCremacion/validarCertificadoEV/?numCertificado='+$scope.formulario.certificadoDefuncion
		}).then(function successCallback(certificadoDefuncion) {
			
			console.log(certificadoDefuncion.data);
			$scope.certificado = certificadoDefuncion.data;
			
			if(!$scope.certificado){
				console.log('niooo');
				abrirVentanaCertificadoinvalido();
				//$uibModal.close();	
			
			}

		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});//Fin llamada ajax
		
	}
	
	
	abrirVentanaCertificadoinvalido = function(numCertificado){
		
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-permisoInhumacionCremacion/pages/modal/validarCertificadoInvalido.html',
			controller: 'ValidarCertificadoInvalidoCtrl',
			resolve: {
				numCertificado: function () {
					return numCertificado;
				}
			}
		});

		modalInstance.result.then(function (numCertificado) {
			
			//$log.info('ok');
			//$parent.cont=true;
			//vm.cancelByEV14=true;

		}, function () {
			//$log.info('Modal dismissed at: ' + new Date());
		});
		
	}
	
	
	guardarDatosFormulario = function(datos){
		$http({
			method: 'POST',
			url: '/web-permisoInhumacionCremacion/guardarDatosForm',
			data: datos
		}).then(function successCallback(response) {
			
			console.log(response.data);


		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});//Fin llamada ajax
	}
	
	
//	$scope.formulario = function(){
//		permiso ='';
//	}
	

	
	
	//comentado el 23/06 xq no recuerdo haberlo usado y debido al problema en el repositorio se pisaron muchos cambios
//	$http({
//		method: 'POST',
//		url: '/web-permisoInhumacionCremacion/iniciarTramite',
//		data:{id:$rootScope.objectSelected.numeroSolicitud}
//		
//	}).then(function successCallback(response) {
//		console.log("response.data");
//		console.log(response.data);
//		if(response.data[0] == 'tipoEscenario'){
//			$scope.tipoEscenario = true;
//		}else if(response.data[0] == 'vistaPermiso'){
//			$scope.vistaPermiso = true;
//		}
//
//	}, function errorCallback(response) {
//		console.log("error: "+response.data);
//	});
//	
})

App.controller('pendienteImprimir', function($scope,$http,$uibModal,$rootScope) {
	
	$scope.numeroSolicitud = $rootScope.objectSelected.numeroSolicitud;
	$scope.codigoEstado = "PENDIENTE POR IMPRIMIR";
	$scope.tramiteSolicitud = "REGISTRAR PERMISO DE INHUMACION Y/O CREMACION"
	
	$scope.data="data del scope";
	var count = 0;
	
	$scope.botonImprimir = function(){
		count ++;
		
		if(count ===3){
			
		}else{
			abrirModalImpresioSatisfactoria();
		}
		
		
	}
	
	abrirModalImpresioSatisfactoria = function(){
		
			var modalInstance = $uibModal.open({
				backdrop: 'static',
				keyboard: false,
				animation: $scope.animationsEnabled,
				templateUrl: '/web-permisoInhumacionCremacion/pages/modal/impresion_satisfactoria.html',
				controller: 'impresionSatisfactoriaCtrl',
				resolve: {
				}
			});
			modalInstance.result.then(function () {


			}, function () {
				console.log("************VENGO DE CANCELAR");
				//restarPasos();
			});
		
		
	}
	
	$scope.eventoBotonSiguiente = function(){
		
	}
	
	
})




