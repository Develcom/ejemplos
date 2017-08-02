App.controller('controladorRDEFUNoConformeActa',function($scope,$http,$rootScope,$uibModal){

	var vm = this;
	$scope.tituloWizard = "Registrar defunción";
	//Guarda en un array las vistas presentadas durante el proceso
	vm.vistas = [];
	//arreglo de datos que han sido presentados en pantalla
	vm.modelos = [];
	//representa los datos actualmente presentes en pantalla
	vm.modelo = {};
	vm.titulos = [];
	vm.activo = -1;
	//estado del proceso
	vm.estado = 'inicializacion';
	vm.pasos=0;
	
	 //funciones necesarias para las validaciones de los tooltip
	 //=======================================================

		$scope.vPatron = {
				AlfaNumerico : /^[A-Za-z0-9]*$/,
				Alfa : /^[a-zA-Z]*$/,
				FormatoNombres: /^((([a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
				FormatoCementerio : /^[a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ'-]*$/,
				AlfaText : 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones y acentos',
				Pcementerio : 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones, acentos y n&uacute;meros',
				Fnumero : 'Este campo admite solo n&uacute;meros',
				Facta : 'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 20 caracteres',
				Ffolio : 'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 5 caracteres',
				Ftomo : 'Este campo admite solo n&uacute;meros y tiene un minimo y maximo de 2 caracteres',
				TipoCed : /^[V|v|E|e]/,
				Numerico : /^[0-9]/,
				cedulaMaxLength : "9",
				cedulaMinLength : "6",
				nombreMaxlength : "50",
				nombreMinlength : "2",
				CedulaText : "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 6 caracteres y un m&aacute;ximo de 9 caracteres <br/> Ejemplo: 12345678",
				NumeroDocumento : /^[0-9]{6,11}$/,
				excepciones : [ 'Backspace', 'Tab', ' ', 'ArrowLeft',
						'ArrowRight', 'Delete', 'Caps Lock', 'Shift',
						'Control' ],
				capTuraEvento : function(event, patron) {

				}

			};
	
	/**
	 * 
	 * @param $http: servicio ajax de angularjs
	 * @param $scope: scope del controlador
	 * @param vm : identificador del controlador
	 * @param url : ruta a la cual se realiza el llamado ajax 
	 * @param datos : enviados en la solicitud
	 * @param successCallback : función a ejecutar cuando la respuesta es satisfactoria
	 * @param metodo : POST, GET, PUT...
	 */
	llamadaAjax = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
	 $http({
	  method: metodo,
	  url: url,
	  data: angular.toJson(datos)//JSON.stringify(datos)
	 }).then(function successCallback(response) {
	  accionSatisfactoria($scope, vm, response);
	 }, function errorCallback(response) {
	  console.log("error: "+response.data);
	 });
	}
	
	
	/**
	 *identifica el titulo de la etapa del proceso y el indice activo para 
	 *resaltarlo con el color azul 
	 **/
	vm.presentarTitulo = function(mTitulo){
		vm.activo = vm.titulos.indexOf(mTitulo);
		if(vm.activo == -1){
			vm.titulos.push(mTitulo);
			vm.activo = vm.titulos.length-1;
		}
	}
	
	vm.modalValidarEcu = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		console.log (vm.modelo);
		vm.presentarTitulo(response.data.modelo.titulo);
		//vm.estado = 'validarActa2';
	}
	
	vm.validarActa = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		console.log("modelooooooooooooo  "+vm.modelo);
		//vm.estado = 'validarActa3';
	}
	
	vm.certificado = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
	}
	
	vm.datosFamiliares = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		console.log("Estoy en datos familiares");
		console.log(vm.modelo['COUNI']);
		if(vm.modelo['COUNI']== null){
			console.log("Estoy en datos familiares dentro del conyugue sin cedula");
			$scope.modalSinConyugue();
			
		}
	}
	vm.datosHijos = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		vm.verificarHijo();
	}
	
	vm.datosPadres = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		console.log("i know");
		vm.verificarPadres();
	}
	vm.declarante = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
	}
	vm.datosConsular = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
	}
	vm.inscripDecisionJudicial = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
	}
	vm.datosTestigo = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
	}
	vm.observacion = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
	}
	vm.docPresentados = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
	}
	vm.verVista = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
	}
	vm.verImpresionActa = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
	}
	vm.conformidad= function($scope, vm, response){
		console.log("vm.conformidad");
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		  if(vm.pasos >= 1){
			  vm.confirmarOficio = true;
		  }
		  
	}
	 vm.finalizarTramite = function($scope, vm, response){
		  vm.finalizar();
		 }
	
	

	vm.presentarVista = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		console.log(vm.modelo);
		vm.presentarTitulo(response.data.modelo.titulo);
		vm.estado = 'iniciarTramite';
	}
	
	vm.validacionEcu = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
	}
	
	 vm.funcion = function(parametro){
		 if(parametro == "conforme"){
			 vm.abrirModalConforme();
		 }else if (parametro == "noConforme"){
			 vm.abrirModalNoConforme();
		 }
	 }
	
	 vm.abrirModalConforme = function(){
		 presentarModal($scope,$uibModal,vm.okConforme,'Verificación del acta exitosa',$rootScope.tituloWizard,'c');
	 }
	 vm.abrirModalNoConforme = function (){
		 presentarModal($scope,$uibModal,vm.noConforme,'Verificación del acta no conforme',$rootScope.tituloWizard,'c');	
		 
	 }
	 vm.okConforme = function(){
		 vm.estado="actualizarEstado";
		 llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	 }
	 vm.noConforme = function(){
		 llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/validarActa', vm.modelo, vm.modalValidarEcu, "POST");
	 }

	vm.actualizarEstadoPV = function(){
		console.log("Estoy en actualizarEstadoPV");
		 vm.estado = 'actualizarEstado';
		 llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
		
	}
	
	vm.imprimirBorrador = function(){
		 presentarModal($scope,$uibModal,vm.actaBorrador,'¿Usted desea imprimir el acta en formato borrador?',$rootScope.tituloWizard,'s');
	}
	
	 vm.abrirmodalConfirmar = function(){
		 presentarModal($scope,$uibModal,vm.okRespuesta,'¿Se confirma la impresión?',$rootScope.tituloWizard,'s');
		 vm.impreso = false;
	 }
	 
		vm.okRespuesta = function(){
			vm.estado="validarConformeActa";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
		}
	 
	 vm.impresion = function (){
		 vm.impreso=true;
		 vm.confirmarImpresion2();
	 }
	 
	 vm.actaBorrador = function(){
		 vm.estado = 'imprimirActaBorrador';
		 //llamadaAjax($http, $scope, vm, vm.rutas[vm.pasos].ruta, vm.modelo, vm.rutas[vm.pasos].funcion, vm.rutas[vm.pasos].metodo);
		 llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	 }
	 
	 vm.confirmarImpresion2 = function(){
		 presentarModal($scope,$uibModal,vm.confirmar,'¿Se confirma la impresión?',$rootScope.tituloWizard,'c');
		 vm.estado = 'validarConformeActa';
	 }
	 
	 
	 vm.confirmar = function(){
		 llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	 } 
	
	 vm.inicioActa = function(){
		 vm.estado = 'validarActa';
		 llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	 }
	 
	 vm.confirmarSalidaModulo = function(){
		 presentarModal($scope,$uibModal,vm.salirModulo,'¿Desea cancelar la solicitud?',$rootScope.tituloWizard,'s');
	 }
	  
	
	vm.CorregirDatos = function(){
	presentarModal($scope,$uibModal,vm.okverif,'Por favor confirme los datos antes de enviar',$rootScope.tituloWizard,'a');
	}
	
	vm.okverif = function(){
		console.log('verificar datos');
	}
	
	vm.Confirmardatos = function(){
		presentarModal($scope,$uibModal,vm.actualizarEstadoPV,'\u00bfLos datos ingresados son correctos?',$rootScope.tituloWizard,'s');
	}
	
	vm.actualizarEstadoPV = function(){
		 vm.estado = 'pendienteVer';
		 llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
		
	}
	
	 vm.confirmarSalidaModulo = function(){
		 presentarModal($scope,$uibModal,vm.salirModulo,'¿Desea cancelar la solicitud?',$rootScope.tituloWizard,'s');
	 }
	
	 vm.salirModulo = function(){
		 $rootScope.cancelar1();
	 }
	
	vm.finalizar = function(){
		vm.cancelar();
	}

	/**
	 * Carga los datos iniciales del proceso
	 */
	vm.rutas = {
		      inicializacion : {ruta: '/web-registrarDefuncion/iniciarTramiteRDEFU', funcion: vm.presentarVista, metodo : "POST"},
			  iniciarTramite:{ruta: '/web-registrarDefuncion/validarActaNoC', funcion: vm.modalValidarEcu, metodo : "POST"},
			  validarActa2:{ruta: '/web-registrarDefuncion/validarActa2NoC', funcion: vm.validarActa, metodo : "POST"},
			  validarActa3:{ruta: '/web-registrarDefuncion/validarActa3NoC', funcion: vm.certificado, metodo : "POST"},
			  validarActa4:{ruta: '/web-registrarDefuncion/validarActa4NoC', funcion: vm.datosFamiliares, metodo : "POST"},
			  datosFamiliar:{ruta: '/web-registrarDefuncion/datosFamiliarNoC', funcion: vm.datosHijos, metodo : "POST"},
			  datosFamiliar2:{ruta: '/web-registrarDefuncion/datosFamiliar2NoC', funcion: vm.datosPadres, metodo : "POST"},
			  datosDeclarante:{ruta: '/web-registrarDefuncion/datosDeclaranteNoC', funcion: vm.declarante, metodo : "POST"},
			  extractoConsular:{ruta: '/web-registrarDefuncion/extractoConsularNoC', funcion: vm.datosConsular, metodo : "POST"},
			  inscripcionDecisionJudicial:{ruta: '/web-registrarDefuncion/inscripcionDecisionJudicialNoC', funcion: vm.inscripDecisionJudicial, metodo : "POST"},
			  datosTestigo:{ruta: '/web-registrarDefuncion/datosTestigosNoC', funcion: vm.datosTestigo, metodo : "POST"},
			  observaciones:{ruta: '/web-registrarDefuncion/observacionesNoC', funcion: vm.observacion, metodo : "POST"},
			  documentosPresentados:{ruta: '/web-registrarDefuncion/documentosPresentadosNoC', funcion: vm.docPresentados, metodo : "POST"},
			  iniciarActa:{ruta: '/web-registrarDefuncion/iniciarActaNoC', funcion: vm.verVista, metodo : "POST"},
			  imprimirActaBorrador:{ruta: '/web-registrarDefuncion/imprimirActaBorradorNoC', funcion: vm.verImpresionActa, metodo : "POST"},
			  validarConformeActa:{ruta: '/web-registrarDefuncion/validarConformeActaNoC', funcion: vm.conformidad, metodo : "POST"},
			  actualizarEstado:{ruta: '/web-registrarDefuncion/actualizarEstado', funcion: vm.finalizarTramite, metodo : "POST"}

	};
	
	

	//Datos a ser enviados al momento de cargar el modal  
	vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, paso:0, estatus:$rootScope.objectSelected.codigoEstadoSolicitud};
	llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	
	vm.siguiente = function(){
		//Si estoy en la vista 1 entonces seteo el estado de la siguiente vista, en este caso la vista 1 es: iniciarTramite y la 2 es validarActa2
		if(vm.estado=="inicializacion"){
			vm.estado="iniciarTramite";
		} else if(vm.estado=="iniciarTramite"){
			vm.estado="validarActa2";
		}else if(vm.estado=="validarActa2"){
			vm.estado="validarActa3";
		}else if(vm.estado=="validarActa3"){
			vm.estado="validarActa4";
		}else if(vm.estado=="validarActa4"){
			vm.estado="datosFamiliar";
		}else if(vm.estado=="datosFamiliar"){
			vm.estado="datosFamiliar2";
		}else if(vm.estado=="datosFamiliar2"){
			vm.estado="datosDeclarante";
		}else if(vm.estado=="datosDeclarante"){
			vm.estado="extractoConsular";
		}else if(vm.estado=="extractoConsular"){
			vm.estado="inscripcionDecisionJudicial";
		}else if(vm.estado=="inscripcionDecisionJudicial"){
			vm.estado="datosTestigo";
		}else if(vm.estado=="datosTestigo"){
			vm.estado="observaciones";
		}else if(vm.estado=="observaciones"){
			vm.estado="documentosPresentados";
		}else if(vm.estado=="documentosPresentados"){
			vm.estado="iniciarActa";
		}else if(vm.estado=="iniciarActa"){
			vm.estado="imprimirActaBorrador";
		}else if(vm.estado=="imprimirActaBorrador"){
			vm.estado="validarConformeActa";
		}else if(vm.estado=="validarConformeActa"){
			vm.estado="actualizarEstado";
		}
		vm.disabled= false;
		vm.vistas.push(vm.vista);
		vm.modelos.push(vm.modelo);
		if(vm.impreso){
			console.log("si ta fina la impresion");
			 vm.abrirmodalConfirmar();
			 return;
		 }
		if(vm.estado == "imprimirActaBorrador"){
			vm.imprimirBorrador();
			return;
		}
//		if(vm.estado == "ActaBorrador"){
//			vm.abrirmodalConfirmar();
//			return;
//		}
		if(vm.confirmarOficio){
			  vm.funcion(vm.modelo.permiso);
			  return;
			  }
		if(vm.modelo.permiso == "conforme"){
			console.log("Estoy en vm.siguiente dentro del if de conforme");
			vm.estado="actualizarEstado";
			vm.abrirModalConforme();
			return;
		}
		if(vm.modelo.permiso == "noConforme"){
			console.log("Estoy en vm.siguiente dentro del if de noConforme");
			vm.estado="actualizarEstado";
			vm.abrirModalNoConforme();
			return;
		}

		
		//se setea el estado igual a 1 para que el boton regresar se ponga enabled
		vm.paso=1;
		llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	}
	vm.regresar= function(){
		if (vm.estado == "iniciarTramite"){
			vm.estado = "inicializacion";
			vm.paso=0;
		}else if (vm.estado == "validarActa2"){
			vm.estado = "iniciarTramite";
		}else if(vm.estado == "validarActa3"){
			vm.estado = "validarActa2";
		}else if(vm.estado == "validarActa4"){
			vm.estado = "validarActa3";
		}else if(vm.estado == "datosFamiliar"){
			vm.estado = "validarActa4";
		}else if(vm.estado == "datosFamiliar2"){
			vm.estado = "datosFamiliar";
		}else if(vm.estado == "datosDeclarante"){
			vm.estado = "datosFamiliar2";
		}else if(vm.estado == "extractoConsular"){
			vm.estado = "datosDeclarante";
		}else if(vm.estado == "inscripcionDecisionJudicial"){
			vm.estado = "extractoConsular";
		}else if(vm.estado == "datosTestigo"){
			vm.estado = "inscripcionDecisionJudicial";
		}else if(vm.estado == "observaciones"){
			vm.estado = "datosTestigo";
		}else if(vm.estado == "documentosPresentados"){
			vm.estado = "observaciones";
		}
		else if(vm.estado == "iniciarActa"){
			vm.estado = "documentosPresentados";
		}else if(vm.estado == "imprimirActaBorrador"){
			vm.estado = "iniciarActa";
		}else if(vm.estado == "validarConformeActa"){
			vm.estado = "imprimirActaBorrador";
		}else if(vm.estado == "actualizarEstado"){
			vm.estado = "validarConformeActa";
		}

		vm.vista = vm.vistas[vm.vistas.length - 1];
		vm.modelo = vm.modelos[vm.modelos.length - 1];
		vm.modelos.splice(vm.modelos.length - 1, 1);
		vm.vistas.splice(vm.vistas.length - 1, 1);
		if (vm.activo < vm.titulos.length) {
			vm.titulos.splice(vm.activo + 1, vm.titulos.length
					- vm.activo - 1);
		}
		vm.activo = vm.titulos.indexOf(vm.modelo.titulo);
		
	}
	 //cierra wizard desde el boton x
		vm.cancel = function(){
			vm.confirmarSalidaModulo();
		}

	vm.cancelar = function (){
		$rootScope.cancelar1();
	}
	
	
	$scope.mostrar = function(){		
		$scope.ocultarMostrar = true;
	}
	
	$scope.ocultar = function(){		
		$scope.ocultarMostrar = false;
	}
	
	//	funciones de pais para datos del fallecido, correcciones hector zea
	$scope.obtenerPais = function(){
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarPais'
		}).then(function successCallback(pais) {
			
			console.log(pais.data);
			$scope.pais = pais.data;
			
			//$scope.estados=$scope.paises[0].estados;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	}
	$scope.obtenerPais();
	

	$scope.buscarEstado = function (){
		vm.modelo.pais = vm.modelo['FAL'].direccion[0].pais.nombre;
		console.log("Codico del pais"+vm.modelo['FAL'].direccion[0].pais.codigo);
		 if (vm.modelo['FAL'].direccion[0].pais.codigo == "VEN"){
			 $scope.disabled = false;
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarEstados/'+vm.modelo['FAL'].direccion[0].pais.codigo,
			data:{codigo:vm.modelo['FAL'].direccion[0].pais.codigo}
		}).then(function successCallback(estado) {
			$scope.disa = false;
			console.log(estado.data);
			$scope.estados = estado.data;
			$scope.municipios = $scope.estados.municipios;
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
		 }else{
			 	$scope.disa = true;
			    $scope.estados=null;
			    $scope.municipios=null;
			    $scope.parroquias=null;
			
		 }
		 console.log(vm.modelo['FAL'].direccion[0].pais);
	}
	
	vm.clearFields = function(){
		vm.modelo.estado = null;
		vm.modelo.municipio = null;
		vm.modelo.parroquia = null;
		return;
	}
	
	$scope.buscarMunicipio = function(){
		vm.modelo.estado = vm.modelo['FAL'].direccion[0].estado.nombre;
		console.log("Codico del estado"+vm.modelo['FAL'].direccion[0].estado.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarMunicipio/'+vm.modelo['FAL'].direccion[0].estado.codigo,
			data:{codigo:vm.modelo['FAL'].direccion[0].estado.codigo}
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipios = municipio.data;
			$scope.parroquias = $scope.municipios.parroquias;
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
	}
	
	$scope.buscarParroquia = function(){
		vm.modelo.municipio= vm.modelo['FAL'].direccion[0].municipio.nombre;
		console.log("CODIGO_MUNICIPIO"+vm.modelo['FAL'].direccion[0].municipio.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarParroquia/'+vm.modelo['FAL'].direccion[0].municipio.codigo,
			data:{codigo:vm.modelo['FAL'].direccion[0].municipio.codigo}
		}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquias = parroquia.data;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});//Fin llamada ajax
	}
	$scope.obtenerParroquia = function(){
		vm.modelo.parroquia = vm.modelo['FAL'].direccion[0].parroquia.nombre;
	}
	
//	fin de funciones para pais en datos fallecido
	
//inicio de funciones para pais en datos defuncion
	
	$scope.obtenerPais1 = function(){
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarPais'
		}).then(function successCallback(pais) {
			
			console.log(pais.data);
			$scope.pais = pais.data;
			
			//$scope.estados=$scope.paises[0].estados;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	}
	$scope.obtenerPais1();
	

	$scope.buscarEstado1 = function (){
		vm.modelo.pais = vm.modelo['datosFAL'].direccion[0].pais.nombre;
		console.log("Codico del pais"+vm.modelo['datosFAL'].direccion[0].pais.codigo);
		 if (vm.modelo['datosFAL'].direccion[0].pais.codigo == "VEN"){
			 $scope.disabled = false;
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarEstados/'+vm.modelo['datosFAL'].direccion[0].pais.codigo,
			data:{codigo:vm.modelo['datosFAL'].direccion[0].pais.codigo}
		}).then(function successCallback(estado) {
			$scope.disaDEF = false;
			console.log(estado.data);
			$scope.estadosDEF = estado.data;
			$scope.municipiosDEF = $scope.estadosDEF.municipiosDEF;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
		 }else{
			 	$scope.disaDEF = true;
				$scope.estadosDEF=null;
			    $scope.municipiosDEF=null;
			    $scope.parroquiasDEF=null;
			
		 }
		 console.log(vm.modelo['datosFAL'].direccion[0].pais);
	}
	
	vm.clearFields = function(){
		vm.modelo.estado = null;
		vm.modelo.municipio = null;
		vm.modelo.parroquia = null;
		return;
	}
	
	$scope.buscarMunicipio1 = function(){
		vm.modelo.estado = vm.modelo['datosFAL'].direccion[0].estado.nombre;
		console.log("Codico del estado"+vm.modelo['datosFAL'].direccion[0].estado.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarMunicipio/'+vm.modelo['datosFAL'].direccion[0].estado.codigo,
			data:{codigo:vm.modelo['datosFAL'].direccion[0].estado.codigo}
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipiosDEF = municipio.data;
			$scope.parroquiasDEF = $scope.municipiosDEF.parroquiasDEF;
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
	}
	
	$scope.buscarParroquia1 = function(){
		vm.modelo.municipio= vm.modelo['datosFAL'].direccion[0].municipio.nombre;
		console.log("CODIGO_MUNICIPIO"+vm.modelo['datosFAL'].direccion[0].municipio.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarParroquia/'+vm.modelo['datosFAL'].direccion[0].municipio.codigo,
			data:{codigo:vm.modelo['datosFAL'].direccion[0].municipio.codigo}
		}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquiasDEF = parroquia.data;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});//Fin llamada ajax
	}
	$scope.obtenerParroquia1 = function(){
		vm.modelo.parroquia = vm.modelo['datosFAL'].direccion[0].parroquia.nombre;
	}
	
	//fin para funciones de pais de defuncion	
	
	
//inicio de funciones para pais en datos TESTIGO1
	
	$scope.obtenerPaisT1 = function(){
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarPais'
		}).then(function successCallback(pais) {
			
			console.log(pais.data);
			$scope.pais = pais.data;
			
			//$scope.estados=$scope.paises[0].estados;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	}
	$scope.obtenerPaisT1();
	

	$scope.buscarEstadoT1 = function (){
		vm.modelo.pais = vm.modelo['TA1'].direccion[0].pais.nombre;
		console.log("Codico del pais"+vm.modelo['TA1'].direccion[0].pais.codigo);
		 if (vm.modelo['TA1'].direccion[0].pais.codigo == "VEN"){
			 $scope.disabled = false;
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarEstados/'+vm.modelo['TA1'].direccion[0].pais.codigo,
			data:{codigo:vm.modelo['TA1'].direccion[0].pais.codigo}
		}).then(function successCallback(estado) {
			$scope.disaTA1 = false;
			console.log(estado.data);
			$scope.estadosTA1 = estado.data;
			$scope.municipiosTA1 = $scope.estadosTA1.municipiosTA1;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
		 }else{
			 	$scope.disaTA1 = true;
				$scope.estadosTA1=null;
			    $scope.municipiosTA1=null;
			    $scope.parroquiasTA1=null;
			
		 }
		 console.log(vm.modelo['TA1'].direccion[0].pais);
	}
	
	
	$scope.buscarMunicipioT1 = function(){
		vm.modelo.estado = vm.modelo['TA1'].direccion[0].estado.nombre;
		console.log("Codico del estado"+vm.modelo['TA1'].direccion[0].estado.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarMunicipio/'+vm.modelo['TA1'].direccion[0].estado.codigo,
			data:{codigo:vm.modelo['TA1'].direccion[0].estado.codigo}
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipiosTA1 = municipio.data;
			$scope.parroquiasTA1 = $scope.municipiosTA1.parroquiasTA1;
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
	}
	
	$scope.buscarParroquiaT1 = function(){
		vm.modelo.municipio= vm.modelo['TA1'].direccion[0].municipio.nombre;
		console.log("CODIGO_MUNICIPIO"+vm.modelo['TA1'].direccion[0].municipio.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarParroquia/'+vm.modelo['TA1'].direccion[0].municipio.codigo,
			data:{codigo:vm.modelo['TA1'].direccion[0].municipio.codigo}
		}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquiasTA1 = parroquia.data;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});//Fin llamada ajax
	}
	$scope.obtenerParroquiaT1 = function(){
		vm.modelo.parroquia = vm.modelo['TA1'].direccion[0].parroquia.nombre;
	}
	
	//fin para funciones de pais de TESTIGO1
	
//inicio de funciones para pais en datos TESTIGO2
	
	$scope.obtenerPaisT2 = function(){
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarPais'
		}).then(function successCallback(pais) {
			
			console.log(pais.data);
			$scope.pais = pais.data;
			
			//$scope.estados=$scope.paises[0].estados;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	}
	$scope.obtenerPaisT2();
	

	$scope.buscarEstadoT2 = function (){
		vm.modelo.pais = vm.modelo['TA2'].direccion[0].pais.nombre;
		console.log("Codico del pais"+vm.modelo['TA2'].direccion[0].pais.codigo);
		 if (vm.modelo['TA2'].direccion[0].pais.codigo == "VEN"){
			 $scope.disabled = false;
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarEstados/'+vm.modelo['TA2'].direccion[0].pais.codigo,
			data:{codigo:vm.modelo['TA2'].direccion[0].pais.codigo}
		}).then(function successCallback(estado) {
			$scope.disaTA2 = false;
			console.log(estado.data);
			$scope.estadosTA2 = estado.data;
			$scope.municipiosTA2 = $scope.estadosTA2.municipiosTA2;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
		 }else{
			 	$scope.disaTA2 = true;
				$scope.estadosTA2=null;
			    $scope.municipiosTA2=null;
			    $scope.parroquiasTA2=null;
			
		 }
		 console.log(vm.modelo['TA2'].direccion[0].pais);
	}
	
	
	$scope.buscarMunicipioT2 = function(){
		vm.modelo.estado = vm.modelo['TA2'].direccion[0].estado.nombre;
		console.log("Codico del estado"+vm.modelo['TA2'].direccion[0].estado.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarMunicipio/'+vm.modelo['TA2'].direccion[0].estado.codigo,
			data:{codigo:vm.modelo['TA2'].direccion[0].estado.codigo}
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipiosTA2 = municipio.data;
			$scope.parroquiasTA2 = $scope.municipiosTA2.parroquiasTA2;
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
	}
	
	$scope.buscarParroquiaT2 = function(){
		vm.modelo.municipio= vm.modelo['TA2'].direccion[0].municipio.nombre;
		console.log("CODIGO_MUNICIPIO"+vm.modelo['TA2'].direccion[0].municipio.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarParroquia/'+vm.modelo['TA2'].direccion[0].municipio.codigo,
			data:{codigo:vm.modelo['TA2'].direccion[0].municipio.codigo}
		}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquiasTA2 = parroquia.data;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});//Fin llamada ajax
	}
	$scope.obtenerParroquiaT2 = function(){
		vm.modelo.parroquia = vm.modelo['TA2'].direccion[0].parroquia.nombre;
	}
	
	//fin para funciones de pais de TESTIGO2	
		
	//	inicio de funciones de pais para couni
	$scope.obtenerPais2 = function(){
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarPais'
		}).then(function successCallback(pais) {
			
			console.log(pais.data);
			$scope.pais = pais.data;
			
			//$scope.estados=$scope.paises[0].estados;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	}
	$scope.obtenerPais2();
	

	$scope.buscarEstado2 = function (){
		vm.modelo.pais = vm.modelo['COUNI'].direccion[0].pais.nombre;
		console.log("Codico del pais"+vm.modelo['COUNI'].direccion[0].pais.codigo);
		 if (vm.modelo['COUNI'].direccion[0].pais.codigo == "VEN"){
			 $scope.disabled = false;
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarEstados/'+vm.modelo['COUNI'].direccion[0].pais.codigo,
			data:{codigo:vm.modelo['COUNI'].direccion[0].pais.codigo}
		}).then(function successCallback(estado) {
			$scope.disaCOUNI =false;
			console.log(estado.data);
			$scope.estadosCOUNI = estado.data;
			$scope.municipiosCOUNI = $scope.estadosCOUNI.municipiosCOUNI;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
		 }else{
			 	$scope.disaCOUNI = true;
				$scope.estadosCOUNI=null;
			    $scope.municipiosCOUNI=null;
			    $scope.parroquiasCOUNI=null;
			
		 }
		 console.log(vm.modelo['COUNI'].direccion[0].pais);
	}
	
	vm.clearFields = function(){
		vm.modelo.estado = null;
		vm.modelo.municipio = null;
		vm.modelo.parroquia = null;
		return;
	}
	
	$scope.buscarMunicipio2 = function(){
		vm.modelo.estado = vm.modelo['COUNI'].direccion[0].estado.nombre;
		console.log("Codico del estado"+vm.modelo['COUNI'].direccion[0].estado.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarMunicipio/'+vm.modelo['COUNI'].direccion[0].estado.codigo,
			data:{codigo:vm.modelo['COUNI'].direccion[0].estado.codigo}
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipiosCOUNI = municipio.data;
			$scope.parroquiasCOUNI = $scope.municipiosCOUNI.parroquiasCOUNI;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
	}
	
	$scope.buscarParroquia2 = function(){
		vm.modelo.municipio= vm.modelo['COUNI'].direccion[0].municipio.nombre;
		console.log("CODIGO_MUNICIPIO"+vm.modelo['COUNI'].direccion[0].municipio.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarParroquia/'+vm.modelo['COUNI'].direccion[0].municipio.codigo,
			data:{codigo:vm.modelo['COUNI'].direccion[0].municipio.codigo}
		}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquiasCOUNI = parroquia.data;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});//Fin llamada ajax
	}
	$scope.obtenerParroquia2 = function(){
		vm.modelo.parroquia = vm.modelo['COUNI'].direccion[0].parroquia.nombre;
	}
	
	
//	fin funciones para pais de couni
	
	
	
	$scope.obtenerPais5 = function(){
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarPais'
		}).then(function successCallback(pais) {
			
			console.log(pais.data);
			$scope.pais = pais.data;
			
			//$scope.estados=$scope.paises[0].estados;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	}
	$scope.obtenerPais5();
	

	$scope.buscarEstado5 = function (){
		vm.modelo.pais = vm.modelo['declarante'].direccion[0].pais.nombre;
		console.log("Codico del pais"+vm.modelo['declarante'].direccion[0].pais.codigo);
		 if (vm.modelo['declarante'].direccion[0].pais.codigo == "VEN"){
			 $scope.disabled = false;
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarEstados/'+vm.modelo['declarante'].direccion[0].pais.codigo,
			data:{codigo:vm.modelo['declarante'].direccion[0].pais.codigo}
		}).then(function successCallback(estado) {
			$scope.disaDec =false;
			console.log(estado.data);
			$scope.estadosDEC = estado.data;
			$scope.municipiosDEC = $scope.estadosDEC.municipiosDEC;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
		 }else{
			 	$scope.disaDec = true;
				$scope.estadosDEC=null;
			    $scope.municipiosDEC=null;
			    $scope.parroquiasDEC=null;
			
		 }
		 console.log(vm.modelo['COUNI'].direccion[0].pais);
	}
	
	vm.clearFields = function(){
		vm.modelo.estado = null;
		vm.modelo.municipio = null;
		vm.modelo.parroquia = null;
		return;
	}
	
	$scope.buscarMunicipio5 = function(){
		vm.modelo.estado = vm.modelo['declarante'].direccion[0].estado.nombre;
		console.log("Codico del estado"+vm.modelo['declarante'].direccion[0].estado.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarMunicipio/'+vm.modelo['declarante'].direccion[0].estado.codigo,
			data:{codigo:vm.modelo['declarante'].direccion[0].estado.codigo}
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipiosDEC = municipio.data;
			$scope.parroquiasDEC = $scope.municipiosDEC.parroquiasDEC;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);	
		});
	}
	
	$scope.buscarParroquia5 = function(){
		vm.modelo.municipio= vm.modelo['declarante'].direccion[0].municipio.nombre;
		console.log("CODIGO_MUNICIPIO"+vm.modelo['declarante'].direccion[0].municipio.codigo);
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarParroquia/'+vm.modelo['declarante'].direccion[0].municipio.codigo,
			data:{codigo:vm.modelo['declarante'].direccion[0].municipio.codigo}
		}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquiasDEC = parroquia.data;
			
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});//Fin llamada ajax
	}
	$scope.obtenerParroquia5 = function(){
		vm.modelo.parroquia = vm.modelo['declarante'].direccion[0].parroquia.nombre;
	}
	
	
	
	
	$scope.asignacion = function(){
		if(vm.modelo['ABU']!=null){
			vm.modelo['declarante']=vm.modelo['ABU'];
		}
		if(vm.modelo['SUE']!=null){
			vm.modelo['declarante']=vm.modelo['SUE'];
		}
		if(vm.modelo['BIAOM']!=null){
			vm.modelo['declarante']=vm.modelo['BIAOM'];
		}
		if(vm.modelo['BIN']!=null){
			vm.modelo['declarante']=vm.modelo['BIN'];
		}
		if(vm.modelo['CBA']!=null){
			vm.modelo['declarante']=vm.modelo['CBA'];
		}
		if(vm.modelo['COUND']!=null){
			vm.modelo['declarante']=vm.modelo['COUND'];
		}
		if(vm.modelo['HRM']!=null){
			vm.modelo['declarante']=vm.modelo['HRM'];
		}
		if(vm.modelo['HIJD']!=null){
			vm.modelo['declarante']=vm.modelo['HIJD'];
		}
		if(vm.modelo['NIE']!=null){
			vm.modelo['declarante']=vm.modelo['NIE'];
		}
		if(vm.modelo['NUE']!=null){
			vm.modelo['declarante']=vm.modelo['NUE'];
		}
		if(vm.modelo['PCV']!=null){
			vm.modelo['declarante']=vm.modelo['PCV'];
		}
		if(vm.modelo['SOB']!=null){
			vm.modelo['declarante']=vm.modelo['SOB'];
		}
		if(vm.modelo['TIO']!=null){
			vm.modelo['declarante']=vm.modelo['TIO'];
		}
		if(vm.modelo['YNO']!=null){
			vm.modelo['declarante']=vm.modelo['YNO'];
		}
		if(vm.modelo['PAD']!=null){
			vm.modelo['declarante']=vm.modelo['PAD'];
		}
		if(vm.modelo['MAD']!=null){
			vm.modelo['declarante']=vm.modelo['MAD'];
		}
	}
	

	
	
	///////esto es la edad///////////////////////////////////////////////////////
	$scope.edadFAL = function(){
		console.log("Estoy en la edad del fallecido");
		if(vm.modelo['FAL']!=null){
			var fnacM = new Date(vm.modelo['FAL'].fechaNacimiento)
			console.log("FECHA del fallecido: "+fnacM);
			var hoy = Date.now()
			edM = parseInt((hoy - fnacM)/365/24/60/60/1000)
			vm.modelo['FAL'].edad=edM
			if(vm.modelo['FAL'].edad!=null){
				 $scope.disabledEDADFAL=true;
			   }else{
				   $scope.disabledEDADFAL=false;
			      }
			}
		}
	
	$scope.edadMAM = function(){
		console.log("Estoy en la edad del madre");
		if(vm.modelo['MAM']!=null){
			var fnacM = new Date(vm.modelo['MAM'].fechaNacimiento)
			console.log(fnacM);
			var hoy = Date.now()
			edM = parseInt((hoy - fnacM)/365/24/60/60/1000)
			vm.modelo['MAM'].edad=edM
			if(vm.modelo['MAM'].edad!=null){
				 $scope.disabledEDADMAM=true;
			   }else{
				   $scope.disabledEDADMAM=false;
			      }
			//console.log("MAM"+edM);
			//console.log($scope.modelo['MAM'].Edad);
			}
		}
	
	$scope.edadPAP = function(){
		console.log("Estoy en la edad del padre");
		if(vm.modelo['PAP']!=null){
			var fnacM = new Date(vm.modelo['PAP'].fechaNacimiento)
			console.log(fnacM);
			var hoy = Date.now()
			edM = parseInt((hoy - fnacM)/365/24/60/60/1000)
			vm.modelo['PAP'].edad=edM
			if(vm.modelo['PAP'].edad!=null){
				 $scope.disabledEDADPAP=true;
			   }else{
				   $scope.disabledEDADPAP=false;
			      }
			//console.log("MAM"+edM);
			//console.log($scope.modelo['MAM'].Edad);
			}
		}
	$scope.edadTA1 = function(){
//		console.log("Estoy en la edad del padre");
		if(vm.modelo['TA1']!=null){
			var fnacM = new Date(vm.modelo['TA1'].fechaNacimiento)
			console.log(fnacM);
			var hoy = Date.now()
			edM = parseInt((hoy - fnacM)/365/24/60/60/1000)
			vm.modelo['TA1'].edad=edM
			if(vm.modelo['TA1'].edad!=null){
				 $scope.disabledEDADTA1=true;
			   }else{
				   $scope.disabledEDADTA1=false;
			      }
			//console.log("MAM"+edM);
			//console.log($scope.modelo['MAM'].Edad);
			}
		}
	
	$scope.edadTA2 = function(){
//		console.log("Estoy en la edad del padre");
		if(vm.modelo['TA2']!=null){
			var fnacM = new Date(vm.modelo['TA2'].fechaNacimiento)
			console.log(fnacM);
			var hoy = Date.now()
			edM = parseInt((hoy - fnacM)/365/24/60/60/1000)
			vm.modelo['TA2'].edad=edM
			if(vm.modelo['TA2'].edad!=null){
				 $scope.disabledEDADTA2=true;
			   }else{
				   $scope.disabledEDADTA2=false;
			      }
				
			}
		}
	
	$scope.edadDeclarante = function(){
		console.log("Estoy en la edad del padre");
		if(vm.modelo['declarante']!=null){
			var fnacM = new Date(vm.modelo['declarante'].fechaNacimiento)
			console.log(fnacM);
			var hoy = Date.now()
			edM = parseInt((hoy - fnacM)/365/24/60/60/1000)
			vm.modelo['declarante'].edad=edM
			if(vm.modelo['declarante'].edad!=null){
				 $scope.disabledEDADdeclarante=true;
			   }else{
				   $scope.disabledEDADdeclarante=false;
			      }
			//console.log("MAM"+edM);
			//console.log($scope.modelo['MAM'].Edad);
			}
		}
////////////////////////////////////////////////////////////////////////////
	
///////////////comunidad indigena///////////////////////////////////////////////////////////////////////
	$scope.buscarComunidades = function(){		
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/consultarComunidadIndigenaTodos'
		}).then(function successCallback(comunidad) {
			///console.log(ocupacion.data);
			$scope.comunidades = comunidad.data;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	}
	$scope.buscarComunidades();
///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	$scope.edadHIJ1 = function(){
		if(vm.modelo['HIJ1']!=null){
			var fnacM = new Date(vm.modelo['HIJ1'].fechaNacimiento)
			console.log(fnacM);
			var hoy = Date.now()
			edM = parseInt((hoy - fnacM)/365/24/60/60/1000)
			vm.modelo['HIJ1'].edad=edM
			if(vm.modelo['HIJ1'].edad!=null){
				 $scope.disabledEDADHIJ1=true;
			   }else{
				   $scope.disabledEDADHIJ1=false;
			      }
			//console.log("MAM"+edM);
			//console.log($scope.modelo['MAM'].Edad);
			}
		}

	
	$scope.buscarOcupacion = function() {
	      $http({
	       method : 'GET',
	       url : '/web-registrarDefuncion/consultarOcupacion'
	      }).then(function successCallback(ocupacion) {
	       // /console.log(ocupacion.data);
	       $scope.ocupaciones = ocupacion.data;
	      }, function errorCallback(error) {
	       console.log("error: " + error.data);
	      });
	     }
	     $scope.buscarOcupacion();
	
	     $scope.numeroControl = 0;
	     $scope.setCedula = function() {
	    	 
				if (vm.modelo['FAL'] != null && $scope.numeroControl < 1) {
					$scope.pregunta = "Si";
					$scope.tipoDocumento = vm.modelo['FAL'].documentoIdentidad[0].tipoDocumento.codigo;
					
					if ($scope.tipoDocumento == 'CED') {
						
						$scope.cedula = vm.modelo['FAL'].documentoIdentidad[0].numero;
						$scope.respuesta = [];
						$scope.respuesta = $scope.cedula.split("-");
						$scope.numFAL = parseInt($scope.respuesta[1]
								.toString());
						$scope.numeroControl++;
						
					}else{
						$scope.numFAL = vm.modelo['FAL'].documentoIdentidad[0].numero;
						$scope.numeroControl++;
					}
				}else{
					$scope.pregunta = "No";
					vm.modelo['FAL'].documentoIdentidad[0].numero = $scope.numFAL;
				}
//				vm.modelo['FAL'].documentoIdentidad[0].numero = $scope.numFAL;
				console.log(vm.modelo['FAL']);
				
				$scope.buscarNacionalidad();
				return $scope.numFAL;
			}

	     $scope.numeroControlDeclarante = 0;
	     $scope.setCedulaDeclarante = function() {
	    	 
				if (vm.modelo['declarante'] != null && $scope.numeroControlDeclarante < 1) {
					$scope.preguntaDeclarante = "Si";
					$scope.tipoDocumento = vm.modelo['declarante'].documentoIdentidad[0].tipoDocumento.codigo;
					
					if ($scope.tipoDocumento == 'CED') {
						
						$scope.cedula = vm.modelo['declarante'].documentoIdentidad[0].numero;
						$scope.respuesta = [];
						$scope.respuesta = $scope.cedula.split("-");
						$scope.numDeclarante = parseInt($scope.respuesta[1]
								.toString());
						$scope.numeroControlDeclarante++;
						
					}else{
						$scope.numDeclarante = vm.modelo['declarante'].documentoIdentidad[0].numero;
						$scope.numeroControlDeclarante++;
					}
				}else{
					$scope.preguntaDeclarante = "No";
					vm.modelo['declarante'].documentoIdentidad[0].numero = $scope.numDeclarante;
				}
				vm.modelo['declarante'].documentoIdentidad[0].numero = $scope.numDeclarante;
				
				$scope.buscarNacionalidad();
				return $scope.numDeclarante;
			}

	     
	     
	     $scope.numeroControl2 = 0;
	     $scope.setCedula2 = function() {
	    	 
				if (vm.modelo['COUNI'] != null && $scope.numeroControl2 < 1) {
					$scope.pregunta2 = "Si";
					$scope.tipoDocumento = vm.modelo['COUNI'].documentoIdentidad[0].tipoDocumento.codigo;
					
					if ($scope.tipoDocumento == 'CED') {
						
						$scope.cedula = vm.modelo['COUNI'].documentoIdentidad[0].numero;
						$scope.respuesta = [];
						$scope.respuesta = $scope.cedula.split("-");
						$scope.numCOUNI = parseInt($scope.respuesta[1]
								.toString());
						$scope.numeroControl2++;
						
					}else{
						$scope.numCOUNI = vm.modelo['COUNI'].documentoIdentidad[0].numero;
						$scope.numeroControl2++;
					}
				}else{
					//$scope.pregunta2 = "No";
					//console.log("num couni" + $scope.numCOUNI);
					//vm.modelo['COUNI'].documentoIdentidad[0].numero = $scope.numCOUNI;
				}
//				vm.modelo['COUNI'].documentoIdentidad[0].numero = $scope.numCOUNI;
				
				$scope.buscarNacionalidad();
				return $scope.numCOUNI;
			}

	     $scope.numeroControl3 = 0;
	     $scope.setCedula3 = function() {
	    	 
				if (vm.modelo['MAM'] != null && $scope.numeroControl3 < 1) {
					$scope.pregunta3 = "Si";
					$scope.tipoDocumento = vm.modelo['MAM'].documentoIdentidad[0].tipoDocumento.codigo;
					
					if ($scope.tipoDocumento == 'CED') {
						
						$scope.cedula = vm.modelo['MAM'].documentoIdentidad[0].numero;
						$scope.respuesta = [];
						$scope.respuesta = $scope.cedula.split("-");
						$scope.numMAM = parseInt($scope.respuesta[1]
								.toString());
						$scope.numeroControl3++;
						
					}else{
						$scope.numMAM = vm.modelo['MAM'].documentoIdentidad[0].numero;
						$scope.numeroControl3++;
					}
				}else{
					$scope.pregunta3 = "No";
					vm.modelo['MAM'].documentoIdentidad[0].numero = $scope.numMAM;
				}
//				vm.modelo['MAM'].documentoIdentidad[0].numero = $scope.numMAM;
				
				$scope.buscarNacionalidad();
				return $scope.numMAM;
			}

	     $scope.numeroControl4 = 0;
	     $scope.setCedula4 = function() {
	    	 
				if (vm.modelo['PAP'] != null && $scope.numeroControl4 < 1) {
					$scope.pregunta4 = "Si";
					$scope.tipoDocumento = vm.modelo['PAP'].documentoIdentidad[0].tipoDocumento.codigo;
					
					if ($scope.tipoDocumento == 'CED') {
						
						$scope.cedula = vm.modelo['PAP'].documentoIdentidad[0].numero;
						$scope.respuesta = [];
						$scope.respuesta = $scope.cedula.split("-");
						$scope.numPAP = parseInt($scope.respuesta[1]
								.toString());
						$scope.numeroControl4++;
						
					}else{
						$scope.numPAP = vm.modelo['PAP'].documentoIdentidad[0].numero;
						$scope.numeroControl4++;
					}
				}else{
					$scope.pregunta4 = "No";
					vm.modelo['PAP'].documentoIdentidad[0].numero = $scope.numPAP;
				}
//				vm.modelo['PAP'].documentoIdentidad[0].numero = $scope.numPAP;
				
				$scope.buscarNacionalidad();
				return $scope.numPAP;
			}

	     $scope.numeroControl5 = 0;
	     $scope.setCedula5 = function() {
	    	 
				if (vm.modelo['HIJ1'] != null && $scope.numeroControl5 < 1) {
					$scope.pregunta5 = "Si";
					$scope.tipoDocumento = vm.modelo['HIJ1'].documentoIdentidad[0].tipoDocumento.codigo;
					
					if ($scope.tipoDocumento == 'CED') {
						
						$scope.cedula = vm.modelo['HIJ1'].documentoIdentidad[0].numero;
						$scope.respuesta = [];
						$scope.respuesta = $scope.cedula.split("-");
						$scope.num5 = parseInt($scope.respuesta[1]
								.toString());
						$scope.numeroControl5++;
						
					}else{
						$scope.num5 = vm.modelo['HIJ1'].documentoIdentidad[0].numero;
						$scope.numeroControl5++;
					}
				}else{
					$scope.pregunta5 = "No";
					vm.modelo['HIJ1'].documentoIdentidad[0].numero = $scope.num5;
				}
//				vm.modelo['HIJ1'].documentoIdentidad[0].numero = $scope.num5;
				
				$scope.buscarNacionalidad();
				return $scope.num5;
			}
	     
	     
	     $scope.numeroControl6 = 0;
	     $scope.setCedula6 = function() {
	    	 
				if (vm.modelo['TA1'] != null && $scope.numeroControl6 < 1) {
					$scope.pregunta6 = "Si";
					$scope.tipoDocumento = vm.modelo['TA1'].documentoIdentidad[0].tipoDocumento.codigo;
					
					if ($scope.tipoDocumento == 'CED') {
						
						$scope.cedula = vm.modelo['TA1'].documentoIdentidad[0].numero;
						$scope.respuesta = [];
						$scope.respuesta = $scope.cedula.split("-");
						$scope.numTA1 = parseInt($scope.respuesta[1]
								.toString());
						$scope.numeroControl6++;
						
					}else{
						$scope.numTA1 = vm.modelo['TA1'].documentoIdentidad[0].numero;
						$scope.numeroControl6++;
					}
				}else{
					$scope.pregunta6 = "No";
					vm.modelo['TA1'].documentoIdentidad[0].numero = $scope.numTA1;
				}
//				vm.modelo['TA1'].documentoIdentidad[0].numero = $scope.numTA1;
				
				$scope.buscarNacionalidad();
				return $scope.numTA1;
			}
	     
	     
	     $scope.numeroControl7 = 0;
	     $scope.setCedula7 = function() {
	    	 
				if (vm.modelo['TA2'] != null && $scope.numeroControl7 < 1) {
					$scope.pregunta7 = "Si";
					$scope.tipoDocumento = vm.modelo['TA2'].documentoIdentidad[0].tipoDocumento.codigo;
					
					if ($scope.tipoDocumento == 'CED') {
						
						$scope.cedula = vm.modelo['TA2'].documentoIdentidad[0].numero;
						$scope.respuesta = [];
						$scope.respuesta = $scope.cedula.split("-");
						$scope.numTA2 = parseInt($scope.respuesta[1]
								.toString());
						$scope.numeroControl7++;
						
					}else{
						$scope.numTA2 = vm.modelo['TA2'].documentoIdentidad[0].numero;
						$scope.numeroControl7++;
					}
				}else{
					$scope.pregunta7 = "No";
					vm.modelo['TA2'].documentoIdentidad[0].numero = $scope.numTA2;
				}
//				vm.modelo['TA2'].documentoIdentidad[0].numero = $scope.numTA2;
				
				$scope.buscarNacionalidad();
				return $scope.numTA2;
			}
	     
	     

	     $scope.cambioDatos = function(){
	    	 if (vm.modelo['MAD'] != null){
	    		 vm.modelo['MAM'] = vm.modelo['MAD'];
	    	 }
	    	 if (vm.modelo['PAD'] != null){
	    		 vm.modelo['PAP'] = vm.modelo['PAD'];
	    	 }
	     }
	     
	     
	     
	     $scope.buscarNacionalidad = function() {
	         $http({
	          method : 'GET',
	          url : '/web-registrarDefuncion/cosultarNacionalidad'
	         }).then(function successCallback(nacionalidad) {
	          // console.log(nacionalidad.data);
	          $scope.nacionalidades = nacionalidad.data;
	         }, function errorCallback(error) {
	          console.log("error: " + error.data);
	         });
	        }
	
	
	// /////////////////////////////////////////////
	// Combo de Ocupaciones
	$scope.buscarOcupacion = function() {
		$http({
			method : 'GET',
			url : '/web-registrarDefuncion/consultarOcupacion'
		}).then(function successCallback(ocupacion) {
			// /console.log(ocupacion.data);
			$scope.ocupaciones = ocupacion.data;
		}, function errorCallback(error) {
			console.log("error: " + error.data);
		});
	}
	$scope.buscarOcupacion();

	// /Nacionalidad condicionada por el tipo de Doc
	$scope.buscarNacionalidad = function() {
		$http({
			method : 'GET',
			url : '/web-registrarDefuncion/cosultarNacionalidad'
		}).then(function successCallback(nacionalidad) {
			// console.log(nacionalidad.data);
			$scope.nacionalidades = nacionalidad.data;
		}, function errorCallback(error) {
			console.log("error: " + error.data);
		});
	}

	$scope.buscarNacionalidad();

	// Combo de Ocupaciones
	$scope.buscarOficina = function() {
		$http({
			method : 'GET',
			url : '/web-registrarDefuncion/consultarOficinasTodos'
		}).then(function successCallback(oficina) {
			// /console.log(ocupacion.data);
			$scope.oficinas = oficina.data;
		}, function errorCallback(error) {
			console.log("error: " + error.data);
		});
	}
	$scope.buscarOficina();

	
	
	$scope.documentosPresentados = function(){
		console.log("Aqui van los documentos?");
		console.log($scope);
		
	}

	vm.mostrarFecha = function(){
		vm.fec=new Date();
		var dif=vm.fec - vm.modelo.pojo.fechaFallecimiento;
		vm.diasDef = dif/(1000*60*60*24);
		if(Number(vm.diasDef)>=Number(2.001)){
			vm.modelo.extemporaneo=true;
		}else{
			vm.modelo.extemporaneo=false;
		}
	};

	calcularExtemporaneo = function(){
		vm.fec=new Date();
		var dif=vm.fec - vm.modelo.pojo.fechaFallecimiento;
		vm.diasDef = dif/(1000*60*60*24);
		if(Number(vm.diasDef)>=Number(2.001)){
			vm.modelo.extemporaneo=true;
		}else{
			vm.modelo.extemporaneo=false;
		}
	};

	$scope.controlNumero1;
	$scope.mostrar = false;
	var controlNumero1 = 0;
	vm.addHijo = function (){
		$scope.mostrar = true;
		var hijo = {
				"primerNombre_hijo" : "",
				"segundoNombre_hijo" : "",
				"primerApellido_hijo" : "",
				"segundoApellido_hijo" : "",
				"documentoIdentidad" : [],
				"edad" : "",
				"vive" : ""
		}
		vm.hijos.push(hijo);
		
		console.log(vm.hijos);
		controlNumero1++;
	}
	  
	
	
	// Restar formilarios hijos
    vm.restHijo = function() {
     console.log("Entrandoooooo al boton restar!!!!!");
     // console.log($scope.h +" ---"+ $scope.control);
     // if ($scope.control == $scope.h) {
     // $scope.control == vm.modelo['HijoAdop'].ndHijos
     vm.hijos.splice(vm.hijos.length - 1, 1);
     console.log(vm.hijos);
     controlNumero1--;

    }

		
	 $scope.prueba = function (){
			vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, pasePorValidarActa:false, pasos:0, estatus:$rootScope.objectSelected.codigoEstadoSolicitud};
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			//llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			console.log(vm.modelo);
		}
		$scope.prueba();
	$scope.transform = function (){
		console.log("Estoy entrando en el transform");
		var contando = 0;
		for (k in vm.modelo)
			{
//				console.log(k.substring(0,3));
				if(k.substring(0,3)=="HIJ"){
					contando++;
				}
			}
		console.log(contando);

		if(vm.modelo['HIJ1']!=null)
			{
				hijo1 = vm.modelo['HIJ1'];
				//vm.hijos.push(hijo1);
				console.log(hijo1);
			}
		if(vm.modelo['HIJ2']!=null)
		{
			hijo2 = vm.modelo['HIJ2'];
			vm.hijos.push(hijo2);
			console.log(hijo2);
		}
		if(vm.modelo['HIJ3']!=null)
		{
			hijo3 = vm.modelo['HIJ3'];
			vm.hijos.push(hijo3);
			console.log(hijo3);
		}
		if(vm.modelo['HIJ4']!=null)
		{
			hijo4 = vm.modelo['HIJ4'];
			vm.hijos.push(hijo4);
			console.log(hijo4);
		}
		if(vm.modelo['HIJ5']!=null)
		{
			hijo5 = vm.modelo['HIJ5'];
			vm.hijos.push(hijo5);
			console.log(hijo5);
		}
		console.log("----------------veamos que tiene el modelo en este momento-------------------");
		console.log(vm.modelo);
		console.log("----------------vm.hijos------------------------------------");
		console.log(vm.hijos);
	}
	 vm.onKeyDown = function(event, validacion, id) {
	      var excepciones = validaciones[validacion].excepciones;
	      for (var i = 0; i < excepciones.length; i++) {
	       if (event.key === excepciones[i])
	        return;
	      }
	      var patron = new RegExp(validaciones[validacion].expReg);
	      if (!patron.test(event.key)) {
	       event.preventDefault();
	       vm.errorTecla[id] = true;
	       return;
	      }
	      vm.errorTecla[id] = false;
	     }

	     vm.onBlur = function() {
	      vm.errorTecla[id] = false;
	     }
	
	 	$scope.mostrarCampoObservacion = function(){		
			$scope.campoObservacion = true;
		}
		
		$scope.ocultarCampoObservacion = function(){		
			$scope.campoObservacion = false;
		}
		
		//modal que se presenta en caso que la solicitud no tenga datos del conyugue
		$scope.modalSinConyugue = function(){
		
			presentarModal($scope,$uibModal, vm.pasarSiguienteConyugue(), 'Los datos del cónyuge no aplican',$rootScope.tituloWizard,'c');
			
			
		}
		//funcion que permite pasar a la siguiente pantalla luego de los datos del conyugue
		vm.pasarSiguienteConyugue = function(){
			console.log("confirmado");
			vm.estado="datosFamiliar";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
		}
		
		//funcion que se encarga de verificar si existen datos del padre o de la madre, de ser asi, dichos campos seran requeridos en el formulario
		//en caso de que ni el padre ni la madre esten presentes se mostrara un modal para pasar a la siguiente pantalla
		vm.verificarPadres = function(){
			if(vm.modelo['PAP']== null && vm.modelo['PAD']== null){
				console.log("Estoy en padre");
				vm.papRequired=false;
			}
			if(vm.modelo['MAM']== null && vm.modelo['MAD']== null){
				console.log("Estoy en madre");
				vm.mamRequired=false;
			}
			if(vm.papRequired==false && vm.mamRequired==false){
				presentarModal($scope,$uibModal, vm.pasarSiguientePadres(), 'Los datos de los padres no aplican',$rootScope.tituloWizard,'c');
			}
		}
		//funcion que permite pasar a la siguiente pantalla luego de los datos de los padres
		vm.pasarSiguientePadres = function(){
			console.log("confirmado");
			vm.estado="datosDeclarante";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
		}
		
		vm.verificarHijo = function(){
			if(vm.modelo['HIJ1']== null){
				console.log("Estoy en HIJO");
				vm.HIJ1Required=false;
			}
		}
		
		vm.imprimir = function() {
			vm.windowPrintBrowser();
			presentarModal($scope, $uibModal, vm.okF,
					'¿La impresión es satisfactoria?',
					$rootScope.tituloWizard, 's');
		}
		
	    vm.windowPrintBrowser = function () {

	        document.getElementById("plugin").focus();

	        document.getElementById("plugin").contentWindow.print();
	        
	    }
	    
	    vm.okF = function(){
			console.log("entroooo");
			vm.impreso = true;
		}
});