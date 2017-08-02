App.controller('ModalDemoCtrl', function ($rootScope, footerFactory, $scope, $uibModal, $log) {
	$scope.animationsEnabled = true;
	$scope.pasos=[];
	
	$scope.open = function (solicitud) {
		//Carga el nombre del Proceso en el titulo del wizard
		$rootScope.tituloWizard='Registrar Defunción';
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: 'template2.html',
			controller: 'ModalInstanceCtrl',
			windowClass: 'app-modal-window',
			resolve: {
		        solicitud: function () {
		          return solicitud;
		        }
		      }
		});

		modalInstance.result.then(function (solicitud) {
			
			$log.info('ok');
		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};

	//	Data de prueba
	$scope.data=[{"estadoSolicitud":"ABIERTA","fechaFin":null,"fechaInicio":"2016-04-04","numeroSolicitud":"201619OBM293","motivoCambioEstado":null,"solicitante":{"tipo":"D","ciudadano":{"capacidadRegistrada":null,"comunidadIndigena":null,"condicionNacionalidad":null,"estadoCiudadano":null,"estadoCivil":null,"estadoECU":null,"fechaNacimiento":null,"fechaResidencia":null,"nacionalidad":null,"numeroHijosRegistrados":0,"primerApellido":"Chirino","primerNombre":"Joelvin","segundoApellido":"Urbano","segundoNombre":"Enrique","sexo":null,"uehRegistrada":null,"direccionesElectronica":null,"documentoIdentidad":[{"numero":"12977999","estado":null,"tipoDocumento":{"codigo":"CED","descripcion":null,"fechaFin":null,"fechaInicio":null,"nombre":"CÉDULA"},"loteNUI":null}],"telefonos":null,"direccion":null,"codigoNui":null,"estatusNui":null,"ecu":null,"condicionNacimiento":null,"uehregistrada":null},"documentoEntePublico":null},"funcionario":{"capacidadRegistrada":null,"comunidadIndigena":null,"condicionNacionalidad":null,"estadoCiudadano":null,"estadoCivil":null,"estadoECU":null,"fechaNacimiento":null,"fechaResidencia":null,"nacionalidad":"AFGANA","numeroHijosRegistrados":0,"primerApellido":"apellido funcionario","primerNombre":"nombre funcionario1","segundoApellido":"1","segundoNombre":"1","sexo":null,"uehRegistrada":null,"direccionesElectronica":null,"documentoIdentidad":null,"telefonos":null,"direccion":null,"codigoNui":null,"estatusNui":null,"ecu":null,"condicionNacimiento":null,"descripcion":null,"fechaFin":null,"fechaInicio":null,"fechaResolucion":null,"numeroResolucion":0,"usuarios":null,"tipoFuncionario":null,"acta":null,"oficina":null,"uehregistrada":null},"oficina":{"codigo":"OBM","descripcion":"OFICINA ACCIDENTAL","fechaFin":null,"fechaGaceta":"2015-06-25","fechaInicio":"2015-06-25","nombre":"OFICINA ACC. BELLO MONTE","numeroGaceta":0,"Direccion":{"ubicacion":null,"parroquia":null,"estado":null,"pais":null,"tipoDireccion":null,"ciudad":null,"municipio":null},"Funcionario":null,"TipoOficina":{"codigo":"ACCID","descripcion":"ACCIDENTALES","fechaFin":null,"fechaInicio":"2015-06-25","nombre":"ACCIDENTALES","modulo":null},"Solicitud":null,"funcionario":null,"solicitud":null,"tipoOficina":{"codigo":"ACCID","descripcion":"ACCIDENTALES","fechaFin":null,"fechaInicio":"2015-06-25","nombre":"ACCIDENTALES","modulo":null},"direccion":{"ubicacion":null,"parroquia":null,"estado":null,"pais":null,"tipoDireccion":null,"ciudad":null,"municipio":null}},"actas":null,"tramite":{"codigo":"RDEFU","descripcion":"REGISTRO DE DEFUNCIÓN","fechaFin":null,"fechaInicio":"2015-06-25","nombre":"REGISTRAR DEFUNCIÓN"},"modulo":{"codigo":"DEFUN","descripcion":"MODULO DE DEFUNCION","fechaFin":null,"fechaInicio":"2015-06-25","nombre":"DEFUNCIÓN","Tramite":null,"tramite":null}},
	             {"estadoSolicitud":"ABIERTA","fechaFin":null,"fechaInicio":"2016-04-05","numeroSolicitud":"201696OBM6","motivoCambioEstado":null,"solicitante":{"tipo":"E","ciudadano":null,"documentoEntePublico":{"numero":"12345","tipoDocumentoEntePublico":"DECISIÓN JUDICIAL","fechaDocumento":null,"enteOrigen":"MINISTERIO DE COMUNICACIÓN E INFORMACIÓN","entePublico":{"codigo":"MCI","descripcion":null,"fechaFin":null,"fechaInicio":null,"nombre":"MINISTERIO DE COMUNICACIÓN E INFORMACIÓN"}}},"funcionario":{"capacidadRegistrada":null,"comunidadIndigena":null,"condicionNacionalidad":null,"estadoCiudadano":null,"estadoCivil":null,"estadoECU":null,"fechaNacimiento":null,"fechaResidencia":null,"nacionalidad":"AFGANA","numeroHijosRegistrados":0,"primerApellido":"apellido funcionario","primerNombre":"nombre funcionario1","segundoApellido":"1","segundoNombre":"1","sexo":null,"uehRegistrada":null,"direccionesElectronica":null,"documentoIdentidad":null,"telefonos":null,"direccion":null,"codigoNui":null,"estatusNui":null,"ecu":null,"condicionNacimiento":null,"descripcion":null,"fechaFin":null,"fechaInicio":null,"fechaResolucion":null,"numeroResolucion":0,"usuarios":null,"tipoFuncionario":null,"acta":null,"oficina":null,"uehregistrada":null},"oficina":{"codigo":"OBM","descripcion":"OFICINA ACCIDENTAL","fechaFin":null,"fechaGaceta":"2015-06-25","fechaInicio":"2015-06-25","nombre":"OFICINA ACC. BELLO MONTE","numeroGaceta":0,"Direccion":{"ubicacion":null,"parroquia":null,"estado":null,"pais":null,"tipoDireccion":null,"ciudad":null,"municipio":null},"Funcionario":null,"TipoOficina":{"codigo":"ACCID","descripcion":"ACCIDENTALES","fechaFin":null,"fechaInicio":"2015-06-25","nombre":"ACCIDENTALES","modulo":null},"Solicitud":null,"funcionario":null,"solicitud":null,"tipoOficina":{"codigo":"ACCID","descripcion":"ACCIDENTALES","fechaFin":null,"fechaInicio":"2015-06-25","nombre":"ACCIDENTALES","modulo":null},"direccion":{"ubicacion":null,"parroquia":null,"estado":null,"pais":null,"tipoDireccion":null,"ciudad":null,"municipio":null}},"actas":null,"tramite":{"codigo":"RDEFU","descripcion":"REGISTRO DE DEFUNCIÓN","fechaFin":null,"fechaInicio":"2015-06-25","nombre":"REGISTRAR DEFUNCIÓN"},"modulo":{"codigo":"DEFUN","descripcion":"MODULO DE DEFUNCION","fechaFin":null,"fechaInicio":"2015-06-25","nombre":"DEFUNCIÓN","Tramite":null,"tramite":null}}
	];


	//	Data de prueba

});

App.controller('ModalInstanceCtrl', function ($rootScope, footerFactory, $scope, $uibModalInstance,$http,solicitud) {
	var vm=this;

	$scope.solicitud=solicitud;
	$scope.cont=false;
	console.log("numSolicitud" +" - "+ $scope.solicitud);
	
	$scope.ok = function (){
		console.log('hola');
	};
	
	$scope.cancel = function () {
		console.log("cancelado");
		footerFactory.restartModelo();
		$uibModalInstance.dismiss('cancel');
	};
});