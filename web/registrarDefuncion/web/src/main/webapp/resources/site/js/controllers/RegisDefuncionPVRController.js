App.controller('controladorRDEFUPVR',function($scope,$http,$rootScope,$uibModal,validaciones){

	var vm = this;
	$scope.validaciones = validaciones;
	$scope.tituloWizard = "Registrar defunción";
	//Guarda en un array las vistas presentadas durante el proceso
	vm.vistas = [];
	//arreglo de datos que han sido presentados en pantalla
	vm.modelos = [];
	//representa los datos actualmente presentes en pantalla
	vm.modelo = {};
	vm.titulos = [];
	vm.activo = -1;
	vm.hijos = [];
	//estado del proceso
	vm.estado = "inicializacion";
	vm.pasos=0;
	$scope.disabled=false;
	$scope.disabled2=false;
	vm.disabled=true;
	vm.papRequired=true;
	vm.mamRequired=true;
	vm.HIJ1Required=true;
	vm.PVRdiabled=true;
	vm.banderaModal = false;


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

	vm.hola = function(valor){
		console.log(valor);
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

	$scope.prueba = function(id) {
		console.log(id);

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

	//=======================================================


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

	vm.conformidad= function($scope, vm, response){
		console.log("vm.conformidad");
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(response.data.modelo.titulo);
		vm.banderaModal = true;

	}
	vm.finalizarTramite = function($scope, vm, response){
		vm.finalizar();
	}

	vm.presentarVista = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		console.log(vm.modelo);
		vm.presentarTitulo(response.data.modelo.titulo);
		vm.estado = 'conformeActa';
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
		llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/actualizarEstado', vm.modelo, vm.finalizar, "POST");
		
	}
	vm.noConforme = function(){
		llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/actualizarEstado', vm.modelo, vm.finalizar, "POST");
	}

	vm.imprimir = function() {
		vm.windowPrintBrowser();
		presentarModal($scope, $uibModal, vm.impresion,'¿La impresión es satisfactoria?',$rootScope.tituloWizard, 's');
	}
	vm.impresion = function (){
		vm.impreso=true;
		vm.estado = 'conformeActa';
	}
	 vm.confirmarImpresion = function(){
		 presentarModal($scope,$uibModal,vm.impreso,'¿Se confirma la impresión?',$rootScope.tituloWizard,'c');
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
			inicializacion : {ruta: '/web-registrarDefuncion/iniciarActaPVR', funcion: vm.presentarVista, metodo : "POST"},
			conformeActa:{ruta: '/web-registrarDefuncion/validarConformeRC', funcion: vm.conformidad, metodo : "POST"},

	};
	vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, pasos:0, estatus:$rootScope.objectSelected.codigoEstadoSolicitud};
	llamadaAjax2($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);

	vm.siguiente = function(){
		vm.disabled= false;
		vm.vistas.push(vm.vista);
		vm.modelos.push(vm.modelo);
		if(vm.banderaModal){
			vm.funcion(vm.modelo.permiso);
			return;
		}
//		  if(vm.siguiente){
//			  vm.confirmarImpresion();
//		  return;
//		  }
		llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	}


	vm.regresar= function(){
		vm.banderaModal = false;
//			vm.vista = vm.vistas[vm.vistas.length-1];
		vm.modelo = vm.modelos[vm.modelos.length-1];
		vm.modelos.splice(vm.modelos.length-1,1);
		vm.vistas.splice(vm.vistas.length-1,1);
		vm.disabled = vm.vistas.length == 0;
		vm.activo = vm.titulos.indexOf(vm.modelo.titulo);
		
		if(vm.activo < vm.titulos.length-1){
			vm.titulos.splice(vm.activo+1, vm.titulos.length-vm.activo-1);
		}

		vm.activo = vm.titulos.indexOf(vm.modelo.titulo);	
	}
	
		vm.cancel = function(){
			vm.confirmarSalidaModulo();
		}

		vm.cancelar = function (){
			$rootScope.cancelar1();
		}
		
		$scope.mostrarCampoObservacion = function(){		
			$scope.campoObservacion = true;
		}
		
		$scope.ocultarCampoObservacion = function(){		
			$scope.campoObservacion = false;
		}
		
	    vm.windowPrintBrowser = function () {
	        document.getElementById("plugin").focus();
	        document.getElementById("plugin").contentWindow.print();
	        
	    }

	});
