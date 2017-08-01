App.controller('RNACI_Acta_Controller', function($scope,$http,$uibModal,$rootScope, validaciones) {

$scope.tituloWizard = "Registrar nacimiento";
//muestra el titulo del modulo en la pantalla
$scope.validaciones = validaciones;
//variable para controlar las validaciones
 var vm = this;
 //Guarda en un array las vistas presentadas durante el proceso
 vm.vistas = [];
 //arreglo de datos que han sido presentados en pantalla
 vm.modelos = [];
 //representa los datos actualmente presentes en pantalla
 vm.modelo = {};
 vm.titulos = [];
 vm.impreso = false;
 vm.noConforme = false;

 vm.activo = 0;

 vm.estado = "iniciarTramite";
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
 /**
  * Maneja como se muestran las vistas.
  */
 vm.presentarVista = function($scope, vm, response){
  vm.vista = response.data.vista;
  vm.modelo = response.data.modelo;
  
  vm.presentarTitulo(response.data.modelo.titulo);
  
  vm.estado = "datosCertificadoNacimiento";
 }
 
 vm.presentarVista2 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	  
	  vm.estado = "datosMadreNacimiento";
	 }
 
 vm.presentarVista3 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	  
	  vm.estado = "datosPadreNacimiento";
	 }
 
 vm.presentarVista4 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	  
	  vm.estado = "datosDeclaranteNacimiento";
	 }
 
 vm.presentarVista5 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	  
	  vm.estado = "datosTestigosNacimiento";
	 }
 
 vm.presentarVista6 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	  
	  vm.estado = "datosActaInsertarNacimiento";
	 }
 
 vm.presentarVista7 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	  
	  vm.estado = "inscripcionMedidaProteccionNacimiento";
	 }
 vm.presentarVista8 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	  
	  vm.estado = "inscripcionDecisionJudicialNacimiento";
	 }
 vm.presentarVista9 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	  
	  vm.estado = "inscripcionExtemporaneaNacimiento";
	 }
 vm.presentarVista10 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	  
	  vm.estado = "circunstanciaEspecialNacimiento";
	 }
 vm.presentarVista11 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);
	  
	  vm.estado = "documentosPresentadosNacimiento";
	 }
 
 vm.presentarVista12 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);	 
	  
	  vm.estado = "imprimirActaNacimiento";
	 }
 vm.presentarVista13 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);	 
	  
	  vm.estado = "verificacionPadresNacimiento";
	 }
 vm.presentarVista14 = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  
	  vm.presentarTitulo(response.data.modelo.titulo);	 
	  
	  vm.estado = "finalizarTramiteNacimiento";
	 }
 vm.finalizarTramiteRNACI = function($scope, vm, response){
	  vm.finalizar();
	 }

	// validaciones de patrones
	$scope.vPatron = {
		AlfaNumerico : /^[A-Za-z0-9]*$/,
		Alfa : /^[a-zA-Z]*$/,
		FormatoNombres: /^((([a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
		FormatoCementerio: /^((([a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
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
	
	$scope.onKeyDownAlfa = function(event, validacion, id) {
		var excepciones = $scope.vPatron.excepciones;
		for (var i = 0; i < excepciones.length; i++) {
			if (event.key === excepciones[i])
				return;
		}
		var patron = $scope.vPatron.FormatoNombres;
		if (!patron.test(event.key)) {
			event.preventDefault();
			$scope.errorTecla[id] = true;
			return;
		}
		$scope.errorTecla[id] = false;
	}

	$scope.onKeyDownCementerio = function(event, validacion, id) {
		var excepciones = $scope.vPatron.excepciones;
		for (var i = 0; i < excepciones.length; i++) {
			if (event.key === excepciones[i])
				return;
		}
		var patron = $scope.vPatron.FormatoCementerio;
		if (!patron.test(event.key)) {
			event.preventDefault();
			$scope.errorTecla[id] = true;
			return;
		}
		$scope.errorTecla[id] = false;
	}

	$scope.onKeyDownNumerico = function(event, validacion, id) {
		var excepciones = $scope.vPatron.excepciones;
		for (var i = 0; i < excepciones.length; i++) {
			if (event.key === excepciones[i])
				return;
		}
		var patron = $scope.vPatron.Numerico;
		if (!patron.test(event.key)) {
			event.preventDefault();
			$scope.errorTecla[id] = true;
			return;
		}
		$scope.errorTecla[id] = false;
	}

	$scope.onBlur = function() {
		$scope.errorTecla[id] = false;
	}
 
 
 /**
  * Rutas para la secuencia de pantallas
  */
 vm.estados = {
	  iniciarTramite:{ruta: '/web-nacimiento/iniciarTramite', funcion: vm.presentarVista, metodo : "POST"},
	  datosCertificadoNacimiento: {ruta : '/web-nacimiento/datosCertificadoNacimiento', funcion : vm.presentarVista2, metodo : "POST"},
	  datosMadreNacimiento: {ruta : '/web-nacimiento/datosMadreNacimiento', funcion : vm.presentarVista3, metodo : "POST"},
	  datosPadreNacimiento: {ruta : '/web-nacimiento/datosPadreNacimiento', funcion : vm.presentarVista4, metodo : "POST"},
	  datosDeclaranteNacimiento: {ruta : '/web-nacimiento/datosDeclaranteNacimiento', funcion : vm.presentarVista5, metodo : "POST"},
	  datosTestigosNacimiento: {ruta : '/web-nacimiento/datosTestigosNacimiento', funcion : vm.presentarVista6, metodo : "POST"},
	  datosActaInsertarNacimiento: {ruta : '/web-nacimiento/datosActaInsertarNacimiento', funcion : vm.presentarVista7, metodo : "POST"},
	  inscripcionMedidaProteccionNacimiento: {ruta : '/web-nacimiento/inscripcionMedidaProteccionNacimiento', funcion : vm.presentarVista8, metodo : "POST"},
	  inscripcionDecisionJudicialNacimiento: {ruta : '/web-nacimiento/inscripcionDecisionJudicialNacimiento', funcion : vm.presentarVista9, metodo : "POST"},
	  inscripcionExtemporaneaNacimiento: {ruta : '/web-nacimiento/inscripcionExtemporaneaNacimiento', funcion : vm.presentarVista10, metodo : "POST"},
	  circunstanciaEspecialNacimiento: {ruta : '/web-nacimiento/circunstanciaEspecialNacimiento', funcion : vm.presentarVista11, metodo : "POST"},
	  documentosPresentadosNacimiento: {ruta : '/web-nacimiento/documentosPresentadosNacimiento', funcion : vm.presentarVista12, metodo : "POST"},
	  imprimirActaNacimiento: {ruta : '/web-nacimiento/imprimirActaNacimiento', funcion : vm.presentarVista13, metodo : "POST"},
	  verificacionPadresNacimiento: {ruta : '/web-nacimiento/verificacionPadresNacimiento', funcion : vm.presentarVista14, metodo : "POST"},
	  finalizarTramiteNacimiento: {ruta : '/web-nacimiento/actualizarEstadoNacimiento', funcion : vm.finalizarTramiteRNACI, metodo : "POST"}
 };

 //Datos a ser enviados al momento de cargar el modal  
 	vm.modelo = {
		id : $rootScope.objectSelected.numeroSolicitud,
		estatu : $rootScope.objectSelected.codigoEstadoSolicitud
	};
 //llamada ajax heredada del modulo de generales.js
 llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
 
 //funcion que permite cargar la siguiente vista
 vm.siguiente = function(){

  //guarda en la pila la vista actual
  vm.vistas.push(vm.vista);
  vm.modelos.push(vm.modelo);
	 if(vm.impreso){
		 vm.abrirmodalConfirmar();
	 }else if(vm.noConforme){
		 vm.abrirmodalNoConforme();
	 }else{	
	 
		 llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	 }
 }
 
 
 //funcion que hace regresar a la vista anterior dependiendo del estado de la vista actual
	vm.regresar= function(){
		if (vm.estado == "datosMadreNacimiento"){
			vm.estado = "iniciarTramite";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "datosCertificadoNacimiento";
		}else if(vm.estado == "datosPadreNacimiento"){
			vm.estado = "datosCertificadoNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "datosMadreNacimiento";
		}else if(vm.estado == "datosDeclaranteNacimiento"){
			vm.estado = "datosMadreNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "datosPadreNacimiento";
		}else if(vm.estado == "datosTestigosNacimiento"){
			vm.estado = "datosPadreNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "datosDeclaranteNacimiento";
		}else if(vm.estado == "datosActaInsertarNacimiento"){
			vm.estado = "datosDeclaranteNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "datosTestigosNacimiento";
		}else if(vm.estado == "inscripcionMedidaProteccionNacimiento"){
			vm.estado = "datosTestigosNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "datosActaInsertarNacimiento";
		}else if(vm.estado == "inscripcionDecisionJudicialNacimiento"){
			vm.estado = "datosActaInsertarNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "inscripcionMedidaProteccionNacimiento";
		}else if(vm.estado == "inscripcionExtemporaneaNacimiento"){
			vm.estado = "inscripcionMedidaProteccionNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "inscripcionDecisionJudicialNacimiento";
		}else if(vm.estado == "circunstanciaEspecialNacimiento"){
			vm.estado = "inscripcionDecisionJudicialNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "inscripcionExtemporaneaNacimiento";
		}else if(vm.estado == "documentosPresentadosNacimiento"){
			vm.estado = "inscripcionExtemporaneaNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "circunstanciaEspecialNacimiento";
		}else if(vm.estado == "imprimirActaNacimiento"){
			vm.estado = "circunstanciaEspecialNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "documentosPresentadosNacimiento";
		}else if(vm.estado == "verificacionPadresNacimiento"){
			vm.estado = "documentosPresentadosNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "imprimirActaNacimiento";
		}else if(vm.estado == "finalizarTramiteNacimiento"){
			vm.estado = "imprimirActaNacimiento";
			llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
			vm.estado = "verificacionPadresNacimiento";
		}
		
		
	}
	
	 //cerrar el wizzard
	 vm.finalizar = function(){
		// vm.cancelar();
		 $rootScope.cancelar1();
	 }
	 //cerrar el wizzard
	 vm.salirModulo = function(){
		 $rootScope.cancelar1();
	 }
	
	//Combo de Ocupaciones
	$scope.buscarOcupacion = function(){		
		$http({
			method: 'GET',
			url: '/web-nacimiento/consultarOcupacion'
		}).then(function successCallback(ocupacion) {
			///console.log(ocupacion.data);
			$scope.ocupaciones = ocupacion.data;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	}
	$scope.buscarOcupacion();
	
	///Nacionalidad condicionada por el tipo de Doc
	 $scope.buscarNacionalidad = function(){	
		$http({
			method: 'GET',
			url: '/web-nacimiento/cosultarNacionalidad'
		}).then(function successCallback(nacionalidad) {
			//console.log(nacionalidad.data);
			$scope.nacionalidades = nacionalidad.data;	
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	}
	$scope.buscarNacionalidad();
	    
	//Combo comunidad indigenas
	$scope.buscarComunidades = function(){		
		$http({
			method: 'GET',
			url: '/web-nacimiento/consultarComunidadIndigenaTodos'
		}).then(function successCallback(comunidad) {
			///console.log(ocupacion.data);
			$scope.comunidades = comunidad.data;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	}
	$scope.buscarComunidades();
	
	//select de paises
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
	
	//select de Estados segun el pais seleccionado
	$scope.buscarEstadoPresentado = function(){
		if (vm.modelo['presentado'].pais.codigo == "VEN"){
		$http({
			method: 'POST',
			url: '/web-generales/consultarEstadoPorPais',
			data:{codigo:vm.modelo['presentado'].pais.codigo}
			
		}).then(function successCallback(estado) {
			$scope.disaPresentado = false
			console.log(estado.data);
			
			$scope.estadosPresentado = estado.data;
			$scope.municipiosPresentado=$scope.estadosPresentado.municipiosPresentado;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
		}else{
			$scope.disaPresentado = true;
			$scope.estadosPresentado=null;
			$scope.municipiosPresentado=null;
			$scope.parroquiasPresentado=null;
		}
	}	
	//select de municipio segun el estado seleccionado
	$scope.buscarMunicipioPresentado = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarMunicipioPorEstado',
			data:{codigo:vm.modelo['presentado'].estado.codigo}
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipiosPresentado = municipio.data;
			$scope.parroquiasPresentado=$scope.municipiosPresentado.parroquiasPresentado;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	
	}	
	//select de parroquias segun el municipio seleccionado
	$scope.buscarParroquiaPresentado = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarParroquiaPorMunicipio',
			data:{codigo:vm.modelo['presentado'].municipio.codigo}
		}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquiasPresentado = parroquia.data;
			//$scope.parroquias=$scope.municipios.parroquias;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	
	}

	/**busqueda de Estado, municipio y parroquia de los demas participantes del acta
	 * 
	 */
	
	$scope.buscarEstadoDeclarante = function(){
		if (vm.modelo['declarante'].pais.codigo == "VEN"){
		$http({
			method: 'POST',
			url: '/web-generales/consultarEstadoPorPais',
			data:{codigo:vm.modelo['declarante'].pais.codigo}
			
		}).then(function successCallback(estado) {
			$scope.disaDeclarante = false
			console.log(estado.data);
			
			$scope.estadosDeclarante = estado.data;
			$scope.municipiosDeclarante=$scope.estadosDeclarante.municipiosDeclarante;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
		}else{
			$scope.disaDeclarante = true;
			$scope.estadosDeclarante=null;
			$scope.municipiosDeclarante=null;
			$scope.parroquiasDeclarante=null;
		}
	}	
	
	$scope.buscarMunicipioDeclarante = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarMunicipioPorEstado',
			data:{codigo:vm.modelo['declarante'].estado.codigo}
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipiosDeclarante = municipio.data;
			$scope.parroquiasDeclarante=$scope.municipiosDeclarante.parroquiasDeclarante;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	
	}	

	$scope.buscarParroquiaDeclarante = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarParroquiaPorMunicipio',
			data:{codigo:vm.modelo['declarante'].municipio.codigo}
		}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquiasDeclarante = parroquia.data;
			//$scope.parroquias=$scope.municipios.parroquias;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	
	}
	
	$scope.buscarEstadoMam = function(){
		if (vm.modelo['MAM'].pais.codigo == "VEN"){
		$http({
			method: 'POST',
			url: '/web-generales/consultarEstadoPorPais',
			data:{codigo:vm.modelo['MAM'].pais.codigo}
			
		}).then(function successCallback(estado) {
			$scope.disaMam = false
			console.log(estado.data);
			
			$scope.estadosMam = estado.data;
			$scope.municipiosMam=$scope.estadosMam.municipiosMam;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
		}else{
			$scope.disaMam = true;
			$scope.estadosMam=null;
			$scope.municipiosMam=null;
			$scope.parroquiasMam=null;
		}
	}	
	
	$scope.buscarMunicipioMam = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarMunicipioPorEstado',
			data:{codigo:vm.modelo['MAM'].estado.codigo}
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipiosMam = municipio.data;
			$scope.parroquiasMam=$scope.municipiosMam.parroquiasMam;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	
	}	

	$scope.buscarParroquiaMam = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarParroquiaPorMunicipio',
			data:{codigo:vm.modelo['MAM'].municipio.codigo}
		}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquiasMam = parroquia.data;
			//$scope.parroquias=$scope.municipios.parroquias;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	
	}
	
	$scope.buscarEstadoPap = function(){
		if (vm.modelo['PAP'].pais.codigo == "VEN"){
		$http({
			method: 'POST',
			url: '/web-generales/consultarEstadoPorPais',
			data:{codigo:vm.modelo['PAP'].pais.codigo}
			
		}).then(function successCallback(estado) {
			$scope.disaPap = false
			console.log(estado.data);
			
			$scope.estadosPap = estado.data;
			$scope.municipiosPap=$scope.estadosPap.municipiosPap;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
		}else{
			$scope.disaPap = true;
			$scope.estadosPap=null;
			$scope.municipiosPap=null;
			$scope.parroquiasPap=null;
		}
	}	
	
	$scope.buscarMunicipioPap = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarMunicipioPorEstado',
			data:{codigo:vm.modelo['PAP'].estado.codigo}
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipiosPap = municipio.data;
			$scope.parroquiasPap=$scope.municipiosPap.parroquiasPap;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	
	}	

	$scope.buscarParroquiaPap = function(){
		
		$http({
			method: 'POST',
			url: '/web-generales/consultarParroquiaPorMunicipio',
			data:{codigo:vm.modelo['PAP'].municipio.codigo}
		}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquiasPap = parroquia.data;
			//$scope.parroquias=$scope.municipios.parroquias;
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	
	}
	//Cerrar la ventana
	vm.cancelar = function() {
		// $rootScope.cancelar1();
		vm.confirmarSalidaModulo();
	}
	
	vm.confirmarSalidaModulo = function() {
		presentarModal($scope, $uibModal, vm.salirModulo,
				'¿Desea cancelar la solicitud?',
				$rootScope.tituloWizard, 's');
	}
	
	vm.confirmarSalidaModulo = function() {
		presentarModal($scope, $uibModal, vm.salirModulo,
				'¿Desea cancelar la solicitud?',
				$rootScope.tituloWizard, 's');
	}
	vm.salirModulo = function() {
		$rootScope.cancelar1();
	}
	//cerrar la ventana desde la X
	vm.cancel = function() {
		vm.confirmarSalidaModulo();
		// $rootScope.cancelar1();
	}

	//imprimir documento*******************************************************************************
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
	
	vm.abrirmodalConfirmar = function(){
		 presentarModal($scope,$uibModal,vm.okRespuesta,'¿Se confirma la impresión?',$rootScope.tituloWizard,'s');
		 vm.impreso=false;
	}
	vm.okRespuesta = function(){
		console.log("confirmado");
		vm.estado="verificacionPadresNacimiento";
		llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	}
	
	vm.abrirmodalNoConforme = function(){
		 presentarModal($scope,$uibModal,vm.cambiarEstadoSolicitud,'¿se confirma que no esta conforme?',$rootScope.tituloWizard,'s');
		 vm.noConforme=false;
	}
	
	vm.conforme = function(){
		presentarModal($scope,$uibModal,vm.cambiarEstadoSolicitud,'¿Verificación del declarante exitosa?',$rootScope.tituloWizard,'s');
	}
	
	vm.cambiarEstadoSolicitud = function(){
		llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	}
	
		
		// ---------funciones internas ------------ //
		$scope.mostrarCampoObservacion = function() {

			$scope.campoObservacion = true;
			// vm.abrirModalNoConforme();
			vm.confirmarDatosForm = true;
			vm.noConforme = true;
		}

		$scope.ocultarCampoObservacion = function() {

			$scope.campoObservacion = false;
			// vm.abrirModalConforme();
			vm.confirmarDatosForm = true;
			vm.noConforme = false;
		}
		

});

