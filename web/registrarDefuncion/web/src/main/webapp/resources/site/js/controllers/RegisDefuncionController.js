App.controller('controladorRDEFU',function($scope,$http,$rootScope,$uibModal,validaciones,$timeout){

	var vm = this;
	$scope.tituloWizard = "Registrar defunción";
	//Guarda en un array las vistas presentadas durante el proceso
	vm.vistas = [];
	//arreglo de datos que han sido presentados en pantalla
	vm.modelos = [];
	//Presenta la barra de progreso
	vm.enProgreso=false;
	//representa los datos actualmente presentes en pantalla
	vm.modelo = {};
	vm.titulos = [];
	vm.activo = "0";
	//estado del proceso
	vm.estado = 'inicializacion';
	vm.pasos=0;
	vm.varias=false;
	vm.permiteVarias=true;
	//variable que se utiliza en caso de que alguna de las preguntas de Drools dependa de la anterior inmediata
	vm.memoria=false;
	paso= 0;
	$scope.disabled=false;
	$scope.disabled2=false;
	vm.disabled=true;
	

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

	 //validaciones de patrones
	$scope.vPatron = {
	        AlfaNumerico: /^[A-Za-z0-9]*$/,
			Alfa: /^[a-zA-Z]*$/,
			FormatoNombres: /^((([a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
			//FormatoNombres:  /^[a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ'-]*$/,
			//FormatoCementerio:  /^[a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ'-]*$/,
			FormatoCementerio: /^((([a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
			AlfaText: 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones y acentos',
			Pcementerio: 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones, acentos y n&uacute;meros',
			FnumeroCertf: 'Este campo admite solo n&uacute;meros',
			TipoCed: /^[V|v|E|e]/,
			Cedula: /^[0-9]/,
			cedulaMaxLength: "9",
			cedulaMinLength: "6",
			nombreMaxlength: "50",
			nombreMinlength: "2",
			CedulaText: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 6 caracteres y un m&aacute;ximo de 9 caracteres <br/> Ejemplo: 12345678",
			NumeroDocumento: /^[0-9]{6,11}$/,
			excepciones : ['Backspace', 'Tab', ' ', 'ArrowLeft', 'ArrowRight', 'Delete', 'Caps Lock', 'Shift', 'Control'],
			capTuraEvento : function(event, patron){
				
			}
		
	      };
	
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
	 * Inicializa los datos para el proceso referente a la solicitud
	 */
	vm.inicializarDatos = function($scope, vm, response){
		vm.modelo = response.data.modelo;
		vm.vista = response.data.vista;
		vm.presentarTitulo(vm.modelo.titulo);
		vm.estado = 'drools';
	}

	/**
	 * Maneja el resultado de las consultas a drools
	 */

	vm.presentarVistaDrools = function($scope, vm, response){
		if(response.data.vista !== undefined){
			vm.vista +=  response.data.vista;
			
		}
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(vm.modelo.titulo);
		if(vm.estado == 'drools' && vm.modelo.pojo.continuarEvaluando == 'false'){
			if(vm.modelo.pojo.inscripcion===true) {
				vm.estado = 'integracionHtml2';
				llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
			}
			if(vm.modelo.pojo.insercion===true) {
				vm.estado = 'integracionHtml';
				llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
			}

	}
	}

	vm.guardo = function (){
		console.log("guado fecha de defuncion");
	}
	
	/**
	 * Maneja como se muestran las vistas.
	 */
	vm.presentarVista = function($scope, vm, response){
		vm.vista +=  response.data.vista;
		vm.modelo = response.data.modelo;
		console.log(vm.modelo);
		vm.presentarTitulo(response.data.modelo.titulo);
		vm.estado = 'integracionHtml2';
	}

	vm.presentarVista2 = function($scope, vm, response){
		//vm.siguiente=true;
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		console.log(vm.modelo);
		vm.presentarTitulo(response.data.modelo.titulo);
		vm.estado = 'modales';
	}

	vm.presentarVista3 = function($scope, vm, response){
		vm.vista =  response.data.vista;
		vm.modelo = response.data.modelo;
		console.log(vm.modelo);
		vm.presentarTitulo(response.data.modelo.titulo);
		vm.estado = 'integracionHtml2';
		llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/guardarDef', vm.modelo, vm.guardo, "POST");
	}
	
	vm.modalValidarEcu = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		console.log (vm.modelo);
		vm.presentarTitulo(response.data.modelo.titulo);
		vm.estado = 'validarActa2';
	}

	vm.ModalEcuTrue = function(){
		presentarModal($scope,$uibModal,vm.inicioActa,'"El ECU del ciudadano(a) que está intentando consultar no existe o no se encontraron datos asociados"',$rootScope.tituloWizard,'a');

	}

	vm.ModalEv14False = function(){
		presentarModal($scope,$uibModal,vm.inicioActa,'Se enviará oficio a la Oficina Regional Electoral debido a que el(la) declarante no posee certificado médico de defunción EV-14 y se cancelará la solicitud. ¿Desea continuar?',$rootScope.tituloWizard,'s');

	}

	vm.modalNumeroCertificado = function(){
		presentarModal($scope,$uibModal,vm.okNumCretificado,'El certificado medico EV-14 Nro '+vm.modelo.pojo.numeroCertificadoDef+', ya se encuentra registrado ¿Es correcto?',$rootScope.tituloWizard,'s');
	}

	vm.okNumCretificado = function(){
		llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/cancelarSolicitud', vm.modelo, vm.presentarVista3, "POST");
		
	}

	vm.inicioActa = function(){
		llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/guardarDef', vm.modelo, vm.guardo, "POST");
		llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/actualizarEstado', vm.modelo, vm.finalizar, "POST");
	}
	
	vm.solicitudCerrada = function(){
		presentarModal($scope,$uibModal,vm.inicioActa,'El libro diario fue actualizado exitosamente',$rootScope.tituloWizard,'a');

	}

	vm.ecuNull = function(){
		
		llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/validarEcu', vm.modelo, vm.levantarModal, "POST");

	}

	vm.levantarModal = function(){	
		if(vm.modelo.valorECU==undefined){
			vm.ModalEcuTrue();
		}
	}

	vm.siguienteImprimir = function(){
		vm.solicitudCerrada();
//		vm.estado = 'fin';
//		llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
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
			inicializacion : {ruta: '/web-registrarDefuncion/iniciarTramite', funcion: vm.inicializarDatos, metodo : "POST"},
			drools : {ruta : '/web-registrarDefuncion/consultarDrools', funcion : vm.presentarVistaDrools, metodo : "POST"},
			integracionHtml2 : {ruta: '/web-registrarDefuncion/escenario', funcion: vm.presentarVista2, metodo : "POST"},
			integracionHtml : {ruta: '/web-registrarDefuncion/permisoInhumacion', funcion: vm.presentarVista, metodo : "POST"},
			fin : {ruta: '/web-registrarDefuncion/actualizarEstado', funcion: vm.finalizar, metodo : "POST"},
	};



	//Datos a ser enviados al momento de cargar el modal   
	vm.modelo ={id:$rootScope.objectSelected.numeroSolicitud, paso:0, estatus:$rootScope.objectSelected.codigoEstadoSolicitud};
	llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);

	vm.siguiente = function(){
		//guarda en la pila la vista actual
		vm.disabled= false;
		vm.vistas.push(vm.vista);
		vm.modelos.push(vm.modelo);
		vm.pasos++;
		paso ++;
		if(vm.estado == "modales"){
			vm.ecuNull();
		}
		
		if(vm.vistas[3] == vm.vista && vm.modelo.pojo.inscripcion==true){
			
			vm.calcularExtemporaneo();
		}
		llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	}
	vm.modelo.fechaDefuncion = "";
	$scope.$watch(function() {
		  return vm.modelo.fechaDefuncion;
		}, function(newValue, oldValue) {
		  console.log("change detected: " + newValue);
		  vm.calcularExtemporaneo();
		  
		});
	
	
	
	vm.regresar= function(){
		vm.vista = vm.vistas[vm.vistas.length-1];
		vm.modelo = vm.modelos[vm.modelos.length-1];
		vm.modelos.splice(vm.modelos.length-1,1);
		vm.vistas.splice(vm.vistas.length-1,1);
		vm.disabled = vm.vistas.length == 0;
		vm.activo = vm.titulos.indexOf(vm.modelo.titulo);
		
		if(vm.activo < vm.titulos.length-1){
			vm.titulos.splice(vm.activo+1, vm.titulos.length-vm.activo-1);
		}
		if(vm.vistas.length == 0){
			$scope.hideInserc = false;
			$scope.hideInscrip = false;
			llamadaAjax($http, $scope, vm, '/web-registrarDefuncion/iniciarTramite', vm.modelo, vm.inicializarDatos, "POST");
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

	$scope.ocultarOtroItem = function(){	
		if(vm.modelo.pojo.desicionJudicial == "true"){
			$scope.item = false;
		}
	}

//	funcion que evalua el cmabio en los radio buton de la gate 0 
	vm.opciones= function() {//el value es el valor del radio button
		if(vm.modelo.pojo.inscripcion==true){	
			vm.modelo.pojo.insercion=false;
			$scope.hideInserc = true;
			$scope.hideInscrip = false;
		}if(vm.modelo.pojo.inscripcion==false){	
			vm.modelo.pojo.insercion=true;
			$scope.hideInscrip = true;
			$scope.hideInserc = false;
		}
		vm.siguiente();
	};
	
	vm.definirOpciones = function(){
		if (vm.modelo.tipo == 1){
			vm.modelo.pojo.desicionJudicial = true;
			vm.modelo.pojo.militaresEnCampana = false;
			vm.modelo.pojo.vznlFalfuerTerriNacDRDC = false;
			$scope.hideCampana = true;
			$scope.hideTerritorio = true;
			}
		if (vm.modelo.tipo == 2){
			vm.modelo.pojo.desicionJudicial = false;
			vm.modelo.pojo.militaresEnCampana = true;
			vm.modelo.pojo.vznlFalfuerTerriNacDRDC = false;
		}
		if (vm.modelo.tipo == 3){
			vm.modelo.pojo.desicionJudicial = false;
			vm.modelo.pojo.militaresEnCampana = false;
			vm.modelo.pojo.vznlFalfuerTerriNacDRDC = true;
		}
		vm.siguiente();
	}
	
	vm.definirItem = function(){
		if (vm.modelo.opcion == 1){
			vm.modelo.pojo.defuncion = true;
			vm.modelo.pojo.presuncionMuerte = false;
			vm.modelo.pojo.presuncionAusencia = false;	
			vm.modelo.pojo.declaracionAusencia = false;
			}
		if (vm.modelo.opcion == 2){
			vm.modelo.pojo.defuncion = false;
			vm.modelo.pojo.presuncionMuerte = true;
			vm.modelo.pojo.presuncionAusencia = false;	
			vm.modelo.pojo.declaracionAusencia = false;
		}
		if (vm.modelo.opcion == 3){
			vm.modelo.pojo.defuncion = false;
			vm.modelo.pojo.presuncionMuerte = false;
			vm.modelo.pojo.presuncionAusencia = true;	
			vm.modelo.pojo.declaracionAusencia = false;
		}
		if (vm.modelo.opcion == 4){
			vm.modelo.pojo.defuncion = false;
			vm.modelo.pojo.presuncionMuerte = false;
			vm.modelo.pojo.presuncionAusencia = false;	
			vm.modelo.pojo.declaracionAusencia = true;
		}
		vm.siguiente();
	}
	
	vm.ocultarMsj= function() {//el value es el valor del radio button
			$scope.campoMensaje2 = false;
	};
	
	vm.changeEv14Motivo= function() {
		vm.ModalEv14False();
	};


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

	vm.calcularExtemporaneo = function(){
		vm.formato12();
		vm.fec=new Date();
		if(vm.modelo.formato2 == 'pm'){
			newFormat = vm.finalHora;
			fechaDef = new Date(newFormat);		
		}if(vm.modelo.formato2 == 'am'){
			fechaDef = new Date(vm.fechaDef);
		}
		var dif=vm.fec - fechaDef;	
		vm.diasDef = dif/(1000*60*60*24);
		if(Number(vm.diasDef)>=Number(2.001)){
			vm.modelo.pojo.extemporaneo=true;
			if(vm.modelo.pojo.extemporaneo == true){
				$scope.campoMensaje2 = true;
			}
//			console.log("extemporaneo " + vm.modelo.pojo.extemporaneo);
		}else{
			vm.modelo.pojo.extemporaneo=false;
			$scope.campoMensaje2 = false;
//			console.log("extemporaneo " + vm.modelo.pojo.extemporaneo);
		}
	};

	vm.formato12 = function(){ 
		var prueba = vm.modelo.fechaDefuncion;
		vm.fechaDef = prueba;		
		//var fechaDefuncion = vm.fechaDef;
		if(vm.modelo.formato2 == 'pm'){
			var cambiar = new Array();			
			prueba = prueba.toString();
			cambiar = prueba.split(" ");
			horario = cambiar[4].split(":");
			horas1 = parseInt (horario[0]);
			calcularHoras = parseInt (horas1 + 12);
			hor = horas1.toString();
			cal = calcularHoras.toString();
			var resp = hor.replace(hor, cal);			
			vm.finalHora = cambiar[0]+" "+cambiar[1]+" "+cambiar[2]+" "+cambiar[3]+ " " + resp + ":" + horario[1] +":" + horario[2] +" "+cambiar[5]+" "+cambiar[6]+" "+cambiar[7]+" "+cambiar[8]+" "+cambiar[9]+" "+cambiar[10];
		//	newFormat = new Date();	
		}		
	}
	
	vm.fechaAutent = function(){ 
		vm.fec=new Date();
		var prueba2 = vm.fec.toString();
		var calcularFechaAut = new Array();
		calcularFechaAut = prueba2.split("G");
		vm.fech = calcularFechaAut[0];
	}
	
	$scope.validarCert= function(){
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/validarCertificadoEV/'+vm.modelo.pojo.numeroCertificadoDef
		}).then(function successCallback(numeroCertificadoDef) {

			console.log(numeroCertificadoDef.data);
			$scope.certificado = numeroCertificadoDef.data;
			if($scope.certificado == true){			
				vm.modelo.variableCertif =  "true";
				vm.modalNumeroCertificado();  
			}else if($scope.certificado == false){
				vm.modelo.variableCertif =  "false";
				vm.estado = 'integracionHtml2';
				$scope.campoMensaje2 = false;
				$scope.campoMensaje1 = true;
			}
		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});	
	}


	vm.onKeyDown = function(event,validacion,id){
		var excepciones = validaciones[validacion].excepciones;
		for(var i = 0; i < excepciones.length; i++){
			if (event.key === excepciones[i])
				return;
		}
		var patron = new RegExp(validaciones[validacion].expReg);
		if (!patron.test(event.key)) {
			event.preventDefault();
			vm.errorTecla[id] = true;
			return;
		}
		vm.errorTecla[id]= false;
	}

	vm.onBlur = function(){
		vm.errorTecla[id] = false;
	}

	$scope.validarMsj= function(){
		$http({
			method: 'GET',
			url: '/web-registrarDefuncion/validarCertificadoEV/'+vm.modelo.pojo.numCertificado
		}).then(function successCallback(numeroCertificadoDef) {

			console.log(numeroCertificadoDef.data);
			$scope.certificado = numeroCertificadoDef.data;

		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});
	}
	
	$scope.falId = function(){
		if(vm.modelo.Identificacion!=null){
			vm.modelo.pojo.estaFallecidoConID = true;
		}else{
			vm.modelo.pojo.estaFallecidoConID = false;
		}
	}
	
	$scope.search = function(){
		if (event.keyCode==8 || event.keyCode==46) 
	    { 
			$scope.campoMensaje1 = false;
	    }  
	}
	
	guardarDatosFormulario = function(datos){
		$http({
			method: 'POST',
			url: '/web-registrarDefuncion/guardarDatosFormDefuncion',
			data: datos
		}).then(function successCallback(response) {
			
			console.log(response.data);


		}, function errorCallback(error) {
			console.log("error: "+error.data);
		});//Fin llamada ajax
	}
	
	$scope.onKeyDownCertDef = function(event, validacion, id){
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

});