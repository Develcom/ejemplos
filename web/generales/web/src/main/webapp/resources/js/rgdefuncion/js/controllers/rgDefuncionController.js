//controlador del template: Identifica el Tramite y presenta la primera pantalla
App.controller('RgDefuncionController',function($scope,
		$http,
		$rootScope,
		$uibModal,
		$log,
		$state,
		$stateParams,
		$location,
		$timeout){
//	App.controller('RgDefuncionController',function($scope,$http,$rootScope,footerFactory,pasosFactory,$uibModal,$log){
	console.log('RgDefuncionController');
	//variables requeridas para registrar defuncion, estas van con mayusculas para que concuerden con los metodos de java
	var vm=this;
	vm.drools={};/////////////
	vm.acta = {};
	vm.acta.participante = {};
	var modelos=[];
	var formulario;
	vm.formularios=[];
	var modelo={"accion":"","value":true};
	var solicitud = $rootScope.objectSelected;


	//usado para tener los acronimos de los familiares

	vm.acroParticipantes=[{"familiares":["MAD","PAP","COUNI", "HIJ"]},
	                      {"testigos":["TA1","TA2"]}
	];



	//usado para ocultar o mostrar informacion
	vm.visibilidad = {};
	vm.visibilidad.registro=true;
	vm.visibilidad.recaudos=false;
	//usado para alerts
	vm.message = {};

	vm.pasos={"Paso":"Solicitud de Registro Defunción"};

//	funcion que evalua el cmabio en los radio button de la gate 0 
	vm.change= function(value) {//el value es el valor del radio button
		console.log("entro al change");
		if(value=="Inscripcion"){
			vm.drools.Inscripcion=true;
			vm.drools.Insercion=false;
		}else{
			vm.drools.Inscripcion=false;
			vm.drools.Insercion=true;
		}
		vm.consultarDrools();
	}

	//funcion para abrir el modal cuando un certificado es invalido
	openModalCertInv = function(){
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-registrarDefuncion/resources/pages/modal/modal_confir_invalid_EV14.html',
			controller: 'ModalInvalidEV14Controller',
			resolve: {
				numCertificado: function () {
					return vm.drools.numCertificado;
				}
			}
		});

		modalInstance.result.then(function (numCertificado) {
			$log.info('ok');
			//$parent.cont=true;
			//vm.cancelByEV14=true;

		}, function () {
			flash("NOTA: N&uacute;mero de certificado m&eacute;dico de defunci&oacute;n inv&aacute;lido.");
			$log.info('Modal dismissed at: ' + new Date());
		});

	};

	openModalNui = function(mensaje){
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-registrarDefuncion/resources/pages/modal/modal_nui.html',
			controller: 'ModalNuiController',
			resolve: {
				mensaje: function () {
					return mensaje;
				}
			}
		})

		modalInstance.result.then(function (numCertificado) {
			$log.info('ok');
			//ahora hay que validar el ECU

			vm.consultarEcu();


		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};

	openModalEcu = function(mensaje){
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: '/web-registrarDefuncion/resources/pages/modal/modal_ecu.html',
			controller: 'ModalEcuController',
			resolve: {
				mensaje: function () {
					return mensaje;
				}
			}
		});

		modalInstance.result.then(function (numCertificado) {
			$log.info('ok');
			//ahora hay que validar el ECU

			vm.accionBotones('datFallecido');


		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};

	//fin funcion para abrir el modal cuando un certificado es invalido

	function invocarControlador(accion,parametro, $scope, $http){
		console.log('esta en la funcion invocarControlador RgDefuncion');
		switch(accion) {
		case 'ev14':		
			$http({
				method: 'GET',						
				url: '/web-registrarDefuncion/validarCertificadoEV/?numCertificado='+parametro 
			}).then(function successCallback(response) {
				if(response.data.errCode != null){
					capturarError(response)
				}else{
					// aqui  estas tomando el valor del controlador java
					console.log(response.data);
					vm.certificadoValido=response.data;
					if(!vm.certificadoValido){
						openModalCertInv();
					}else{
						flash("NOTA: N&uacute;mero de certificado m&eacute;dico de defunci&oacute;n v&aacute;lido.");
						vm.consultarDrools();
					}
				}		
			},
			function errorCallback(response) {
				console.log(response);
			});	
			break;

		case 'consultarDrools':
			$http({
				method: 'POST',
				url: '/web-registrarDefuncion/consultarDrools',
				data:vm.drools
			}).then(function successCallback(response) {
				console.log(response.data);

				/******** revisar esto (lo de evaluar)*********/
				if(response.data.evaluar){
					//footerFactory.addFormulario(response.data.ruta);
					addFormulario(response.data.ruta);
				}else{
					if(response.data.vista!=undefined){
						vm.recaudos=response.data.lista;
						//$scope.paso
						vm.visibilidad.registro=false;
						vm.visibilidad.recaudos=true;

					}else{
						console.log("Antes redirije a la vista del acta");
						open('lg');	
					}
				}

			}, function errorCallback(response) {
				console.log("error: "+response.data);
			});//Fin llamada ajax
			break;
			
		case 'nui':
			//cableo el mensaje (en esta seccion debe haber una llamada a
			//servicio que valide el nui)
			var mensaje = "El(La) fallecido(a) ten&iacute;a NUI asignado";

			openModalNui(mensaje);

			break;
			//consulta todos los paises	
		case 'pais':
			$http({
				method: 'GET',
				url: '/web-registrarDefuncion/consultarPaises'
			}).then(function successCallback(paises) {
				console.log(paises.data);
				vm.paises = paises.data;

			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax

			break;

		case 'municipio':
			$http({
				method: 'GET',
				url: '/web-registrarDefuncion/consultarMunicipios/'+parametro
			}).then(function successCallback(municipios) {
				console.log(municipios.data);
				vm.municipios = municipios.data;

			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax
			break;

		case 'parroquia':
			$http({
				method: 'GET',
				url: '/web-registrarDefuncion/consultarParroquias/'+parametro
			}).then(function successCallback(parroquias) {
				console.log(parroquias.data);
				vm.parroquias = parroquias.data;

			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax
			break;

		case 'ocupacion':
			$http({
				method: 'GET',
				url: '/web-registrarDefuncion/consultarOcupacion'
			}).then(function successCallback(ocupacion) {
				console.log(ocupacion.data);
				vm.profesion = ocupacion.data;

			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax
			break;

		case 'participante':
			$http({
				method: 'GET',
				url: '/web-registrarDefuncion/consultarParticipanteTipo/'+parametro.numeroSolicitud+'/FAL'
			}).then(function successCallback(participante) {

				console.log(participante.data);

				vm.acta.datosFallecido = participante.data;
				//itera buscando la direccion
				var direccion=participante.data.direccion;
				for(i=0; i<direccion.length; i++){
					if(direccion[i].tipoDireccion.codigo=='NAC'){
						vm.acta.datosFallecido.direccionNacimiento=direccion[i].ubicacion;
						break;
					}
				}

				vm.consultarCiudadanos()

//				vm.acta.participante.fechaNacimiento = new Date(participante.data.fechaNacimiento); 

			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax
			break;
			
		case 'ciudadanos':

			vm.ciudadanos=[];

			$http({
				method: 'GET',
				url: '/web-registrarDefuncion/consultarParticipantes/'+parametro.numeroSolicitud
			}).then(function successCallback(response) {
				vm.acta.participante.hijos=[];
				participantes=response.data;
				for(var i=0; i<participantes.length;i++){
					if(participantes[i].rol=="MAD"){
						vm.acta.participante.madre=participantes[i];
					}if(participantes[i].rol=="PAP"){
						vm.acta.participante.padre=participantes[i];
					}if(participantes[i].rol=="HIJ"){
						//if(vm.acta.participante.hijos.length==0){
						//	vm.acta.participante.hijos=participantes[i];
						//}else{
						vm.acta.participante.hijos.push(participantes[i]);
						//}

					}if(participantes[i].rol=="COUNI"){
						vm.acta.participante.conyugue=participantes[i];
					}
					invocarControlador('ciudadano', participantes[i].documentoIdentidad[0].numero, $scope, $http);
				}
			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax
			break;
			
		case 'ciudadano':

			$http({
				method: 'GET',
				url: '/web-registrarDefuncion/consultarCiudadanos/'+parametro
			}).then(function successCallback(response) {
				vm.ciudadanos.push(response.data);

			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax
			break;

		case 'ecu':

			$http({
				method: 'GET',
				url: '/web-registrarDefuncion/validarECU/'+parametro.numeroSolicitud+'/FAL'
			}).then(function successCallback(ecu) {
				var mensaje;
				if(ecu.data.length > 0){
					vm.acta.ecu = ecu.data;
					mensaje = "Validacion de ECU satisfactoria. El fallecido tiene registrado: "+ vm.acta.ecu;
				}else{
					mensaje = "El ECU del(de la) ciudadano(a) no posee actas vinculadas";
				}
				openModalEcu(mensaje);
			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax

			break;

		case 'participantes':

			$http({
				method: 'GET',
				url: '/web-registrarDefuncion/validarECU/'+parametro.numeroSolicitud+'/FAL'
			}).then(function successCallback(ecu) {
				var mensaje;
				if(ecu.data.length > 0){
					vm.acta.ecu = ecu.data;
					mensaje = "Validaci&oacute;n de ECU satisfactoria. El fallecido tiene registrado: "+ vm.acta.ecu;
				}else{
					mensaje = "El ECU del(de la) ciudadano(a) no poseea actas vinculadas";
				}
				openModalEcu(mensaje);
			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax

			break;
			
		case 'comunidadIndigena':
			
			$http({
				method: 'GET',
				url: '/web-registrarDefuncion/consultarComunidadesIndigenas'
			}).then(function successCallback(data) {
				vm.comunidadIndigena = data.data;
			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax
			
			break;

		case 'nacionalidad':
			
			$http({
				method: 'GET',
				url: '/web-registrarDefuncion/consultarNacionalidades'
			}).then(function successCallback(data) {
				vm.nacionalidades = data.data;
			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax
			
			break;

		}
	}

	//funcion que anade los formularios retornados por drools
	function addFormulario(formulario){

		if(vm.formularios[vm.formularios.length - 1] != formulario){
			vm.formularios.push(formulario);	
		}

	}

	function getFormularios(){
		return vm.formularios.join('');
	};

	vm.calcularExtemporaneo = function(){
		if(vm.drools.fechaFallecimiento!=undefined){
			vm.fec=new Date();
			var dif=vm.fec - vm.drools.fechaFallecimiento;
			vm.diasDef = dif/(1000*60*60*24);
			if(Number(vm.diasDef)>=Number(2.001)){
				vm.drools.extemporaneo=true;
				flash("NOTA: Registro de Defunci&oacute;n extemporáneo");

			}else{
				vm.drools.extemporaneo=false;
			}			
		}


	}

	vm.consultarDrools = function(){
		console.log("consultarDrools en la funcion");
		invocarControlador('consultarDrools',vm.drools,$scope,$http);
	}

	vm.validarEV14 = function(){
		invocarControlador('ev14',vm.drools.numCertificado, $scope, $http);
	}

	vm.generarNUI = function(){
		invocarControlador('nui', '', $scope, $http);
	}

	vm.consultarPaises = function (){
		invocarControlador('pais', '', $scope, $http);
	}

	vm.consultarMunicipios = function (codEstado){
		invocarControlador('municipio', codEstado, $scope, $http);
	}

	vm.consultarParroquias = function (codMunicipio){
		invocarControlador('parroquia', codMunicipio, $scope, $http);
	}

	vm.consultarOcupacion = function (){
		invocarControlador('ocupacion', '', $scope, $http);
	}

	vm.consultarParticipanteTipo = function (solicitud){
		invocarControlador('participante', solicitud, $scope, $http);
	}

	vm.consultarEstados = function (pais){
		vm.estados=pais.estados;
	}

	vm.consultarEcu = function (){
		invocarControlador('ecu', solicitud, $scope, $http);
	}

	vm.consultarComunidadIndigena = function(){
		invocarControlador('comunidadIndigena', '', $scope, $http);
	}
	
	vm.consultarNacionalidad = function(){
		invocarControlador('nacionalidad', '', $scope, $http);
	}
	



	vm.consultarCiudadanos = function (){
		invocarControlador('ciudadanos', solicitud, $scope, $http);
	}




	///////////////acomodart esto, debe ser un ng init
	vm.consultarPaises();///////////
	vm.consultarParticipanteTipo(solicitud);
	vm.consultarOcupacion();///////////
	vm.consultarComunidadIndigena();
	vm.consultarNacionalidad();
	//console.log("Esto es el scope padre");
	//console.log($scope.solicitud);
	//////////

	var datos={gate:'GATE'};
	$http({
		method: 'POST',
		url: '/web-registrarDefuncion/iniciarTramite1',
		data:{gate0:'gate0'}
	}).then(function successCallback(response) {
		console.log("response.data");
		console.log(response.data);
		//footerFactory.addFormulario(response.data);
		addFormulario(response.data);
	}, function errorCallback(response) {
		console.log("error: "+response.data);
	});

	$scope.$watch(function(){return getFormularios()},function (newValue, oldValue) {
		if (newValue !== oldValue) {
			$rootScope.html=newValue;
		}
	});

	vm.accionBotones = function(accion){
		switch(accion) {
		case 'nui':
			vm.generarNUI();
			break;
		case 'ecu':
			vm.consultarEcu();
			//vm.visibilidad.ecu=true;
			//vm.visibilidad.detFallecido=false;
			//vm.visibilidad.datosDefuncion=false;
			//vm.visibilidad.datosCertificado=false;
			//vm.visibilidad.recaudos=false;
			break;

		case 'datFallecido':
			vm.visibilidad.ecu=false
			vm.visibilidad.detFallecido=true;
			vm.visibilidad.datosDefuncion=false;
			vm.visibilidad.datosCertificado=false;
			vm.visibilidad.recaudos=false;
			break;

		case 'datDefuncion':
			vm.visibilidad.ecu=false
			vm.visibilidad.detFallecido=false;
			vm.visibilidad.datosDefuncion=true;
			vm.visibilidad.datosCertificado=false;
			vm.visibilidad.recaudos=false;
			break;

		case 'datCertificado':
			vm.visibilidad.ecu=false
			vm.visibilidad.detFallecido=false;
			vm.visibilidad.datosDefuncion=false;
			vm.visibilidad.datosCertificado=true;
			vm.visibilidad.recaudos=false;
			vm.visibilidad.conyugue=false
			break;

		case 'datConyugue':
			//colocar validacion del conyugue
			if(vm.acta.participante.conyugue.length==0){
				//llamar a la visibilidad de los hijos
			}
			
			vm.visibilidad.datosCertificado=false;
			vm.visibilidad.conyugue=true;
			vm.visibilidad.hijos=false;
			break;	

		case 'datHijos':
			if(vm.acta.participante.hijos.length==0){
				//llamar a la visibilidad de los hijos
			}

			vm.visibilidad.conyugue=false;
			vm.visibilidad.hijos=true;
			break;	

		}
	}

	//funcion empleada para simular mensajes flash
	flash = function (mensage){
		vm.messageTxt=mensage;
		vm.visibilidad.flash=true;
		$timeout(function(){vm.visibilidad.flash = false}, 6000);  
	};

	//funcion empleada para agregar nodo al json del arbol de los hijos
	vm.addHijo = function(){
		var hijo = {"primerNombre": "",
				"segundoNombre":"",
				"primerApellido":"",
				"segundoApellido":"",
				"edad":"",
				"vive":"",
				"documentoIdentidad":[]};

		vm.acta.participante.hijos.push(hijo);
	}

});