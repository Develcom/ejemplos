/**
 * 
 */
App.controller("RRFILController",function($scope, $http, $rootScope, $uibModal, $log){
	
	$scope.html;
	$scope.datos;
	$scope.pais1={};
	$scope.estado1={};
	$scope.parroquia1={};
	$scope.municipio1={};
	$scope.diasabled=false;
	$scope.disabled=false;
	$scope.tipoDoc = false;
	vm=this;
	vm.pasos=0;
	$scope.formulario={};// modelo del form

	urlTemplates=[
                    '/web-generales/iniciarRRFIL', 
	              	'/web-generales/RRFIL_hijo',
	              	'/web-generales/RRFIL_datos_acta',
//		      		'/web-generales/RRFIL_acta_nac' ,
//	              	'/web-generales/RRFIL_hijos' ,
//		      		'/web-generales/RRFIL_madre' ,
//	              	'/web-generales/RRFIL_padre' ,
//		      		'/web-generales/RRFIL_adoptado' ,
//		      		'/web-generales/RRFIL_acta_prim' 
//		      		'/web-nacimiento/site/RRFIL/pages/template/form/certificado_medico_nacimiento_adopcion',
//		      		'/web-nacimiento/site/RRFIL/pages/template/form/circunstancias_especiales_acto_adopcion',
//		      		'/web-nacimiento/site/RRFIL/pages/template/form/datos_adoptado',
//		      		'/web-nacimiento/site/RRFIL/pages/template/form/datos_madre',
//		      		'/web-nacimiento/site/RRFIL/pages/template/form/datos_padre',
//		      		'/web-nacimiento/site/RRFIL/pages/template/form/datos_testigos_adopcion',
//		      		'/web-nacimiento/site/RRFIL/pages/template/form/documentos_adopcion',
//		      		'/web-nacimiento/site/RRFIL/pages/template/form/inscripcion_decision_judicial_adopcion'
		      	]
	
	bodyHtml=[];
	console.log("url····················3333333"+urlTemplates[0]);
	$http({
		method: 'POST',
		url:urlTemplates[0],
		data:{id:$rootScope.objectSelected.numeroSolicitud},
		headers:{Accept:'text/html'}
	}).then(function successCallback(response) {
		bodyHtml[vm.pasos]= response.data;
		$scope.html= response.data.html;
		$scope.formulario = response.data.datosAPintar;
		console.log($scope.formulario);
		llamarDisabled();
//		llamarMetodoHabilitar();
		
		
		//$scope.formulario['MAD'].documentoIdentidad[0].numero = $scope.formulario['MAD'].documentoIdentidad[0].numero.slice(2);
		//$scope.formulario['HIJ'].fechaNacimiento = Date($scope.formulario['HIJ'].fechaNacimiento)
		
		
		

	}, function errorCallback(response) {
		console.log("error: "+response.data);
	});
	
	
	$scope.cancel = function(){
		$rootScope.cancelar1();
	}

	$scope.cancelar = function (){
		$rootScope.cancelar1();
	}
	
	///// BOTON CONTINUAR ///////
	$scope.eventoBoton = function(){
		
		vm.pasos++;
//		if(vm.pasos == 3){
//			abrirModalVerificarDatos();
//			return;
//		}
		if(vm.pasos ==3 ){ 
			abrirModalActaDataVerificacion();
			return;
		}
		console.log("PASOS NUM CONTINUAR!!!!!"+vm.pasos);
		llamadaAjax(urlTemplates[vm.pasos]);
	}
	
	
	
	llamadaAjax = function(urlPasos){
		console.log("PASOS LLAMADA AJAX!!!!!"+urlPasos);
	$http({
		method: 'POST',
		url: urlPasos,
		headers:{Accept:'text/html'},
		data:{id:$rootScope.objectSelected.numeroSolicitud}
	}).then(function successCallback(response) {
		//console.log(response.data);
		bodyHtml[vm.pasos]= response.data;
		$scope.html= response.data.html;
		$scope.formulario = response.data.datosAPintar;
		console.log($scope.formulario);
	    console.log("Estoy en la parte donde ya llene formulario y el html");
	    llamarDisabled();
//	    llamarMetodoHabilitar();
		
		
		//console.log($scope.html)
	}, function errorCallback(response) {
		console.log("error:::::::: "+response.data);
	});
	
	}
	
	
	$scope.regresar1=function(){
		//alert("REGRESAR: "+vm.pasos);
		vm.pasos--;
		$scope.html= bodyHtml[vm.pasos];
		console.log("PASOS NUM REGRESAR!!!!!"+vm.pasos);
		
		
		llamadaAjax(urlTemplates[vm.pasos]);
	};
	
//	llamarMetodoHabilitar = function(){
//		console.log($scope.formulario);
//		console.log($scope.formulario['MAD'].primerNombre);
//		console.log($scope.formulario['HIJ'].primerNombre);
//		if ($scope.formulario['MAD'] != null){
//			if ($scope.formulario['MAD'].primerNombre != null)
//					diasabled = false
//					else
//						diasabled = true
//		
//			if ($scope.formulario['MAD'].segundoNombre != null)
//				diasabled = false
//				else
//					diasabled = true
//			if ($scope.formulario['MAD'].primerApellido != null)
//				diasabled = false
//				else
//					diasabled = true
//			if ($scope.formulario['MAD'].segundoApellido != null)
//				diasabled = false
//				else
//					diasabled = true
//		}
//	}
			
	irImprimirPermisoFooter = function(){
		$scope.tipoEscenario = false;
		$scope.permisos = false;
		$scope.permisoFormModal = false;
		$scope.permisoFormFooter = false;
		$scope.permisoForm = false;
		$scope.vistaPermiso = false;
		$scope.conforme = false;
		$scope.irImprimirPermiso=true;
		
	}

	
	$scope.validarConforme = function(){
		
			abrirVentanaConforme();
		
	}
	
	
	abrirVentanaConforme = function(){
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
			
			//console.log(parametro.data);
			//irImprimirPermisoFooter
				//eventoBoton3();

		}, function () {
			
		});
	}
	
	
	
	
	modalActaRegistrada = function(){
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-nacimiento/RRFIL/modal/invalidarActaPrimigenia.html',
			controller: 'validarActaCtrl',
			resolve: {
				
			}
		});

		modalInstance.result.then(function () {
		console.log('Resultado modal');
			//$rootScope.cerrarModal();
				//debe actualizar estatus solicitud a pendiente por verificador y cierra la modal
				//eventoBoton3();
			//enviarDatosActualizarSolicitud();		

		}, function () {
			
		});//validar cuando no sea correcto para cambiar estatus de solicitud a 
		
		
	}
	
	//abre modal tipo warning indicando verificar datos( no crea alguna accion en los form)
	abrirModalActaDataVerificacion = function(){
		
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-nacimiento/RRFIL/page/modal/datosActaConfirmacion.html',
			controller: 'validarActaCtrl',
			resolve: {
				
			}
		});

		modalInstance.result.then(function () {
			
			
			//eventoBotonFooter();
			//$parent.cont=true;
			//vm.cancelByEV14=true;

		}, function () {
			
		});
		
	}
	llamarDisabled = function() {
		/////////////MAD
	if($scope.formulario['MAD']!= null){
		   if($scope.formulario['MAD'].primerNombre!=null){
			   $scope.diasabled=true;
			   console.log($scope.formulario['MAD'].primerNombre);
		   }else{
			   $scope.diasabled=false;
		      }
			if($scope.formulario['MAD'].segundoNombre!=null){
			   $scope.diasabled=true;
			   console.log($scope.formulario['MAD'].segundoNombre);
		   }else{
			   $scope.diasabled=false;
		      }
			if($scope.formulario['MAD'].primerApellido!=null){
			   $scope.diasabled=true;
		   }else{
			   $scope.diasabled=false;
		      }
			if($scope.formulario['MAD'].segundoApellido!=null){
			   $scope.diasabled=true;
		   }else{
			   $scope.diasabled=false;
		      }
			if($scope.formulario['MAD'].documentoIdentidad[0].numero!=null){
				console.log($scope.formulario['MAD'].documentoIdentidad[0].numero);
			   $scope.diasabled=true;
		   }else{
			   $scope.diasabled=false;
		      }
			if($scope.formulario['MAD'].documentoIdentidad[0].tipoDocumento.nombre!=null){
			   console.log($scope.formulario['MAD'].documentoIdentidad[0].tipoDocumento.nombre);
				$scope.diasabled=true;
		   }else{
			   $scope.diasabled=false;
		      }
			}
        /////////////HIJ
	if($scope.formulario['HIJ']!= null){
		   if($scope.formulario['HIJ'].primerNombre!=null){
			   $scope.disabled=true;
			   console.log($scope.formulario['HIJ'].primerNombre);
		   }else{
			   $scope.disabled=false;
		      }
			if($scope.formulario['HIJ'].segundoNombre!=null){
			   $scope.disabled=true;
			   console.log($scope.formulario['HIJ'].segundoNombre);
		   }else{
			   $scope.disabled=false;
		      }
			if($scope.formulario['HIJ'].primerApellido!=null){
			   $scope.disabled=true;
		   }else{
			   $scope.disabled=false;
		      }
			if($scope.formulario['HIJ'].segundoApellido!=null){
			   $scope.disabled=true;
		   }else{
			   $scope.disabled=false;
		      }
			if($scope.formulario['HIJ'].documentoIdentidad[0].numero!=null){
				console.log($scope.formulario['HIJ'].documentoIdentidad[0].numero);
			   $scope.disabled=true;
		   }else{
			   $scope.disabled=false;
		      }
			if($scope.formulario['HIJ'].documentoIdentidad[0].tipoDocumento.nombre!=null){
			   console.log($scope.formulario['HIJ'].documentoIdentidad[0].tipoDocumento.nombre);
				$scope.disabled=true;
		   }else{
			   $scope.disabled=false;
		      }
			}

	}

	
	
	
$scope.buscarPais = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarPaisTodos',
			data:{}
		}).then(function successCallback(pais) {
			console.log(pais.data);
			$scope.paises = pais.data;
			$scope.estados=$scope.paises.estados;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});

	}

	$scope.buscarPais();
	//$scope.formulario.estado = null;
	
$scope.buscarEstado = function(){
		if ($scope.pais1.codigo == "VEN"){
		$http({
			method: 'POST',
			url: '/web-generales/consultarEstadoPorPais',
			data:{codigo:$scope.pais1.codigo}
			
		}).then(function successCallback(estado) {
			$scope.disabled = false
			console.log(estado.data);
			
			$scope.estados = estado.data;
			$scope.municipios=$scope.estados.municipios;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
		}else{
			$scope.disabled = true
		}
	}	
	
$scope.buscarMunicipio = function(){
	
	$http({
		method: 'POST',
		url: '/web-generales/consultarMunicipioPorEstado',
		data:{codigo:$scope.estado1.codigo}
	}).then(function successCallback(municipio) {
		console.log(municipio.data);
		$scope.municipios = municipio.data;
		$scope.parroquias=$scope.municipios.parroquias;
	}, function errorCallback(error) {
		console.log("error: "+error.data);
	});

}	

$scope.buscarParroquia = function(){
	
	$http({
		method: 'POST',
		url: '/web-generales/consultarParroquiaPorMunicipio',
		data:{codigo:$scope.municipio1.codigo}
	}).then(function successCallback(parroquia) {
		console.log(parroquia.data);
		$scope.parroquias = parroquia.data;
		//$scope.parroquias=$scope.municipios.parroquias;
	}, function errorCallback(error) {
		console.log("error: "+error.data);
	});

}

$scope.buscarOficinas = function(){
	
	$http({
		method: 'POST',
		url: '/web-generales/consultarOficinasTodos',
		data:{}
	}).then(function successCallback(oficina) {
		console.log(oficina.data);
		$scope.oficinas = oficina.data;
		//$scope.estados=$scope.paises.estados;
	}, function errorCallback(error) {
		console.log("error: "+error.data);
	});

}
$scope.MostrarTipoDoc=function(variable){
	console.log(variable);  
	if (variable = 'Si')
		  $scope.tipoDoc=true;
	  else
		  $scope.tipoDoc=false;
	 }
	 
$scope.buscarOficinas();
	$scope.validarCert= function(){
		$http({
			method: 'GET',
			url: '/web-g/validarCertificadoEV/?numCertificado='+$scope.formulario.certificadoDefuncion
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
	
	
	$scope.radio = function(){
		permiso ='';
	}
	
	
	
//	$http({
//		method: 'POST',
//		url: '/web-nacimiento/iniciarTramite',
//		data:{id:$rootScope.objectSelected.numeroSolicitud}
//		
//	}).then(function successCallback(response) {
//		console.log("response.data");
//		console.log(response.data);
////		if(response.data[0] == 'tipoEscenario'){
////			$scope.tipoEscenario = true;
////		}else if(response.data[0] == 'vistaPermiso'){
////			$scope.vistaPermiso = true;
////		}
//
//	}, function errorCallback(response) {
//		console.log("error: "+response.data);
//	});
	
    //Añade otro hijo....
	$scope.hijo = [{ "nombre": "", "nombre2": "", "apellido": "", "apellido2": "", "Ci": "", "tip1": false, "tip2": false }];
	
    $scope.addHijo = function () {
        $scope.hijo.push({ "nombre": "", "nombre2": "", "apellido": "", "apellido2": "", "Ci": "", "tip1": false, "tip2": false});
        console.log("agregando hijos!!!!"); }	
	
//	var addhijos = function($scope){
//        $scope.form = [];
//        
//        $scope.addHijo = function () {
//        	cosole.log("fom fom fom");
//          $scope.form.push({ 
//        	  templateUrl: '/web-nacimiento/pages/templates/datos_hijos_solicitud_adopcions.html'
////            inlineChecked: false,
////            question: "",
////            questionPlaceholder: "foo",
////            text: ""	
//          });
//        };
//      }
	
})
