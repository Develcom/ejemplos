App.controller("RNACI1_Controller",function($scope,$http,$rootScope,$uibModal, $log, validaciones){
	
	var vm=this;
	//Lista de titulos del proceso
	vm.titulos=[];
	//Resaltador del paso actual del proceso
	vm.activo="0";
	//Presenta la barra de progreso
	vm.enProgreso=false;
	//campos del formulario
	vm.vistas =[];
	//modelo asociado al formulario
	vm.modelo={};
	//guarda los modelos asociados a cada formulario para presentar las opciones seleccionadas por el usuario
	//en caso de que se presione la tecla anterior
	vm.modelos=[];
	vm.varias=false;
	vm.permiteVarias=true;
	//variable que se utiliza en caso de que alguna de las preguntas de Drools dependa de la anterior inmediata
	vm.memoria=false;
	//estado del proceso
	vm.modelo.estado = 'inicializacion';
	
	$scope.cambiarVista = false;
	$scope.padreAutenticado = false;

	
	vm.guardarHistorial = function(){
			vm.vistas.push(vm.vista);		
			vm.modelos.push(vm.modelo);
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
	
	/**
	 * Inicializa los datos para el proceso referente a la solicitud
	 */
	vm.inicializarDatos = function($scope, vm, response){
		vm.guardarHistorial();
		
		vm.modelo = response.data.modelo;
		vm.vista = response.data.vista;
		vm.presentarTitulo(vm.modelo.titulo);
		console.log("<---------------------Valor de Mandatario--------------------->")
		console.log(vm.modelo.valorMandatario);
		if(vm.modelo.valorMandatario == true){
			console.log("entrando en valor mandatario igual a true");
			$scope.valorMandatario = true;
			$scope.valorNoMandatario = false;
			vm.modelo.pojo.mandatario= true;
		}
		if(vm.modelo.valorMandatario == false){
			console.log("entrando en valor mandatario igual a false");
			$scope.valorMandatario = false;
			$scope.valorNoMandatario = true;
			vm.modelo.pojo.mandatario= false;
		}
		console.log("scope valor mandatario------>"+$scope.valorMandatario);
		console.log("scope valor no mandatario--->"+$scope.valorNoMandatario);
		console.log("pojo valor mandatario------->"+vm.modelo.pojo.mandatario);
		vm.modelo.estado = 'drools';
	}
	
	/**
	 * Maneja el resultado de las consultas a drools
	 */

	vm.presentarVistaDrools = function($scope, vm, response){
		console.log("entrando en presentar Vista Drools");
		if(!response.data.modelo.falloAutenticacion)
			vm.guardarHistorial();
		if(response.data.vista !== undefined && $scope.cambiarVista == false){
			
			vm.vista += response.data.vista;
		}
		if(response.data.vista !== undefined && $scope.cambiarVista == true){
			
			vm.vista = response.data.vista;
		}
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(vm.modelo.titulo);
		if(vm.modelo.estado === 'drools' && vm.modelo.pojo.continuarEvaluando == 'false'){
			vm.modelo.estado = 'presentarLista';
			llamadaAjax2($http, $scope, vm, vm.rutas[vm.modelo.estado].ruta, vm.modelo, vm.rutas[vm.modelo.estado].funcion, vm.rutas[vm.modelo.estado].metodo);				
		}else if(vm.modelo.estado === 'presentarLista'){
			vm.modelo.estado = 'autenticar';
			vm.modelo.indiceParticipante = 0;
		}else if(vm.modelo.estado === 'autenticar'){
			if(!vm.modelo.falloAutenticacion){
				vm.modelo.indiceParticipante++;
			}
		}
	}
	
	vm.controlarDeclaracionJurada = function($scope, vm, response){
		vm.guardarHistorial();
		
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(vm.modelo.titulo);
		if(vm.modelo.estado === 'declaracionJurada'){
			vm.modelo.nParticipanteDeclaracion++;
			if(vm.modelo.nParticipanteDeclaracion === vm.modelo.listaCodigosDeclaracion.length())
				vm.modelo.estado = guardar;
		}
	}
	vm.controlarDeclaracionJurada
	
	vm.rutas = {
			inicializacion : {ruta: '/web-nacimiento/iniciarTramite', funcion: vm.inicializarDatos, metodo : "POST"},
            drools : {ruta : '/web-nacimiento/consultarDrools', funcion : vm.presentarVistaDrools, metodo : "POST"},
            presentarLista : {ruta : '/web-autenticarCiudadano/lista', funcion : vm.presentarVistaDrools, metodo : "POST"},
            autenticar : {ruta : '/web-autenticarCiudadano/AutenticarControlador', funcion : vm.presentarVistaDrools, metodo : "POST"},
            declaracionJurada : {ruta : '/web-autenticarCiudadano/datosDeclaracionJurada', funcion : vm.controlarDeclaracionJurada, metodo : "POST"},
            generarPDF : {ruta : '/web-autenticarCiudadano/generarPDFDeclaracionJurada', funcion : vm.controlarDeclaracionJurada, metodo : "POST"},
            confirmar : {ruta : '/web-generales/actualizarAbierta', funcion : vm.finalizarYactualizar, metodo : "POST"}
	};

	vm.modelo['solicitud'] = $rootScope.objectSelected; 
	llamadaAjax2($http, $scope, vm, vm.rutas[vm.modelo.estado].ruta, vm.modelo, vm.rutas[vm.modelo.estado].funcion, vm.rutas[vm.modelo.estado].metodo);		

	$scope.control1=false;
	vm.siguiente = function(){
		vm.disabled = false;
		console.log("la fecha y tal---->"+ vm.modelo.fecha_ + " el control---->" + $scope.control1);
		if(vm.modelo.fecha_ != undefined && $scope.control1==false){
			var d = new Date(vm.modelo.fecha_.ano2, vm.modelo.fecha_.mes2.numero, vm.modelo.fecha_.dia2);
			console.log(vm.modelo.fecha_);
			$scope.cambiarVista = false;
			$scope.control1 = true;
			vm.modelo.hoy = new Date();
			var resta= vm.modelo.hoy - d;
			vm.diasDef = resta/(1000*60*60*24);
			if(Number(vm.diasDef)<=Number(90)){
				vm.modelo.pojo.extemporaneo=false;
				$scope.cambiarVista = true;
			}else{
				vm.modelo.pojo.extemporaneo=true;
				$scope.cambiarVista = true;
			}
			console.log(vm.modelo.pojo.extemporaneo);
		}
//		console.log("La ruta en este momento es:---->"+ vm.rutas[vm.modelo.estado].ruta );
//		console.log("El modelo en este momento es:---->"+ vm.modelo);
//		console.log("La funcion en este momento es:---->"+ vm.rutas[vm.modelo.estado].funcion);
//		console.log("El metodo en este momento es:---->"+vm.rutas[vm.modelo.estado].metodo);
		if(vm.modelo.estado=="ORE1_->_ORE2"){
			vm.levantarModalOre1();
		}else if (vm.modelo.fechaPadre){
			var fNacimientoPadre = new Date(vm.modelo.fechaPadre.ano2, vm.modelo.fechaPadre.mes2.numero, vm.modelo.fechaPadre.dia2)
			var hoy = new Date();
			var fechaResta = hoy - fNacimientoPadre;
			var diasRestantes = parseInt(fechaResta/(1000*60*60*24*365));
			console.log(fechaResta +"<----fecha ------ edad---->" + diasRestantes);
			if (diasRestantes>=14){
				vm.modelo.pojo.padreMayor14 = true;
			}else{
				vm.modelo.pojo.padreMayor14 = false;
			}
			console.log("Estado-->" + vm.modelo.estado);
			$scope.cambiarVista = true;
			llamadaAjax2($http, $scope, vm, vm.rutas[vm.modelo.estado].ruta, vm.modelo, vm.rutas[vm.modelo.estado].funcion, vm.rutas[vm.modelo.estado].metodo);
			
		}else {
			if(vm.modelo.pojo.indigena == false && vm.modelo.pojo.motivo != null){	
				llamadaAjax2($http, $scope, vm, '/web-nacimiento/decJurada', vm.modelo, vm.funcionVacia3, 'POST');
				return
			}
			if(vm.modelo.pojo.indigena == true && vm.modelo.pojo.comunidadInd!=null && vm.modelo.pojo.motivo != null){	
				llamadaAjax2($http, $scope, vm, '/web-nacimiento/decJurada', vm.modelo, vm.funcionVacia3, 'POST');
				return
			}
			llamadaAjax2($http, $scope, vm, vm.rutas[vm.modelo.estado].ruta, vm.modelo, vm.rutas[vm.modelo.estado].funcion, vm.rutas[vm.modelo.estado].metodo);
		}
	}
	

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
		vm.activo = vm.titulos.indexOf(vm.modelo.titulo);		
	}
	console.log(vm.modelo);
	vm.dLugarNacimiento = false;
	vm.primeraVista = function(){
		console.log(vm.modelo);
		console.log("vamos a cablear un valor ya que el servicio da un problemita, luego se cambia");
		console.log("valor del mandatario del vm.modelo "+vm.modelo.valorMandatario);
//		if(vm.modelo.valorMandatario == true){
//			console.log("entrando en valor mandatario igual a true");
//			$scope.valorMandatario = true;
//			$scope.valorNoMandatario = false;
//			vm.modelo.pojo.mandatario= true;
//		}
//		if(vm.modelo.valorMandatario == false){
//			console.log("entrando en valor mandatario igual a false");
//			$scope.valorMandatario = false;
//			$scope.valorNoMandatario = true;
//			vm.modelo.pojo.mandatario= false;
//		}
		
		if (vm.modelo.pojo.inscripcion == true)
			vm.modelo.pojo.insercion = false;
		if (vm.modelo.pojo.inscripcion == false)
			vm.modelo.pojo.insercion = true;
		vm.dLugarNacimiento = true;
		console.log("Vamos a ver que tiene el scope");console.log($scope);
		
		vm.siguiente();
	}
	$scope.diasbledLugar=false;
	vm.levantarModal1 = function(){
		presentarModal(
				$scope,
				$uibModal,
				vm.siguiente, // ///esto no va a
				// aca
				'Se procederá a realizar un registro de nacimiento por Inscripción, ocurrido fuera del territorio nacional (No declarado ante una Representación Diplomática o Consular). ¿ Desea Continuar?',
				$rootScope.tituloWizard, 's');
		console.log(vm.modelo.pojo.lugarNacimientoVenezuela);
		if (vm.modelo.pojo.lugarNacimientoVenezuela== false)
			$scope.cambiarVista = true;
		if (vm.modelo.pojo.lugarNacimientoVenezuela == true)
			$scope.cambiarVista = false;
		$scope.diasbledLugar=true;
	}
	$scope.ocultarHospitalario = false;
	$scope.ocultarExtrahospitalario = false;
	$scope.ocultarDecisionJudicial = false;
	$scope.ocultarMedidaProteccion = false;
	
	vm.otro = function(){
		console.log("Entrando en definir tipo de nacimiento en territorio nacional");
		console.log("lugarnacimiento= " + vm.modelo.pojo.lugarNacimientoVenezuela);
		console.log("insercion= " + vm.modelo.pojo.insercion);
		if (vm.modelo.pojo.lugarNacimientoVenezuela==true && vm.modelo.pojo.insercion==true){
			console.log("*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*");
			$scope.ocultarHospitalario = true;
			$scope.ocultarExtrahospitalario = true;
			vm.siguiente();
		}
		if (vm.modelo.pojo.lugarNacimientoVenezuela== true && vm.modelo.pojo.insercion==false){
			$scope.ocultarHospitalario = false;
			$scope.ocultarExtrahospitalario = false;
			vm.siguiente();
		}
		$scope.diasbledLugar=true;
	}
	
	vm.definirPadres = function(){
		console.log(vm.modelo.tipoPadres);
		if (vm.modelo.tipoPadres == 1){
				vm.modelo.pojo.madreyPadre = true;
				vm.modelo.pojo.madreoPadre = false;
				vm.modelo.pojo.naturalizados = false;
			}
		if (vm.modelo.tipoPadres == 2){
			vm.modelo.pojo.madreyPadre = false;
			vm.modelo.pojo.madreoPadre = true;
			vm.modelo.pojo.naturalizados = false;
		}
		if (vm.modelo.tipoPadres == 3){
			vm.modelo.pojo.madreyPadre = false;
			vm.modelo.pojo.madreoPadre = false;
			vm.modelo.pojo.naturalizados = true;
		}
		vm.siguiente();
	console.log(vm.modelo.pojo);
	}
	
	vm.definirNacimiento = function(){
		console.log(vm.modelo.pojo);
		if (vm.modelo.tipoNacimiento == 1){
			$scope.ocultarHospitalario = false; vm.modelo.pojo.hospitalario = true;
			$scope.ocultarExtrahospitalario = true; vm.modelo.pojo.extrahospitalario = false;
			$scope.ocultarDecisionJudicial = true; vm.modelo.pojo.decisionJudicial = false;
			$scope.ocultarMedidaProteccion = true; vm.modelo.pojo.medidaProteccion = false;
			
			vm.modelo.estado="fueraDrools";
			$scope.cambiarVista = false;
			llamadaAjax2($http, $scope, vm, '/web-nacimiento/fueraDrools', vm.modelo, vm.funcionVacia, 'POST');
			return
		}
		if (vm.modelo.tipoNacimiento == 2){
			$scope.ocultarHospitalario = true; vm.modelo.pojo.hospitalario = false;
			$scope.ocultarExtrahospitalario = false; vm.modelo.pojo.extrahospitalario = true;
			$scope.ocultarDecisionJudicial = true; vm.modelo.pojo.decisionJudicial = false;
			$scope.ocultarMedidaProteccion = true; vm.modelo.pojo.medidaProteccion = false;
		}
		if (vm.modelo.tipoNacimiento == 3){
			$scope.ocultarHospitalario = true; vm.modelo.pojo.hospitalario = false;
			$scope.ocultarExtrahospitalario = true; vm.modelo.pojo.extrahospitalario = false;
			$scope.ocultarDecisionJudicial = false; vm.modelo.pojo.decisionJudicial = true;
			$scope.ocultarMedidaProteccion = true; vm.modelo.pojo.medidaProteccion = false;
			$scope.cambiarVista = true;
		}
		if (vm.modelo.tipoNacimiento == 4){
			$scope.ocultarHospitalario = true; vm.modelo.pojo.hospitalario = false;
			$scope.ocultarExtrahospitalario = true; vm.modelo.pojo.extrahospitalario = false;
			$scope.ocultarDecisionJudicial = true; vm.modelo.pojo.decisionJudicial = false;
			$scope.ocultarMedidaProteccion = false; vm.modelo.pojo.medidaProteccion = true;
			$scope.cambiarVista = true;
		}
		vm.siguiente();
	console.log(vm.modelo.pojo);
	}
	
	vm.definirDocumentoPublico = function(){
		console.log($scope);
		vm.modelo.estado="fueraDrools";
		
		$scope.cambiarVista = false;
		
		
		llamadaAjax2($http, $scope, vm, '/web-nacimiento/fueraDrools', vm.modelo, vm.funcionVacia, 'POST');

		
		console.log("fin de la funcion definir documentopublico ");

	}
	vm.funcionVacia = function($scope, vm, response){
		vm.vista +=response.data.vista;
		console.log("antes de pasarle la info-------->");
		console.log(vm.modelo);
		vm.modelo = response.data.modelo;
		console.log("despues de pasarle la info-------->");
		console.log(vm.modelo);
	}
	vm.funcionCantidadHijos = function($scope, vm, response){
		vm.vista+=response.data.vista;
		vm.modelo = response.data.modelo;
		vm.modelo.cantidadHijos = [
		    "1","2","3","4","5","6","7","8","9"
			];
		
	}
	
	$scope.cargarOrden = function(){
		console.log("valor de cantidad de hijos");
		console.log(vm.modelo.cantidadHijos);
		var arreglo=[];
		
		for (i=1; i<=vm.modelo.cantidadHijos; i++){
			
			arreglo.push(i+" - "+vm.modelo.cantidadHijos);
			
		}
		vm.modelo.ordenHijos = arreglo;
		console.log(vm.modelo.ordenHijos);
	}
	
	$scope.finOrden = function(){
		
		$scope.cambiarVista = true;
		vm.modelo.estado="drools";
		console.log(vm.modelo);
		vm.siguiente();
	}
	
	vm.definirCantidad = function(){
		if(vm.modelo.DHijos==true){
			vm.modelo.estado="cantidadHijos";
			$scope.cambiarVista = false;
			llamadaAjax2($http, $scope, vm, '/web-nacimiento/cantidadHijos', vm.modelo, vm.funcionCantidadHijos, 'POST');
			
		}else{
			$scope.cambiarVista = true;
			vm.modelo.estado="drools";
			vm.siguiente();
		}
	}
	$scope.ocultarNumeroEV=true;
	vm.poseeEV = function(){
		if (vm.modelo.pojo.ev25==true){
			$scope.ocultarNumeroEV=false;
		}else{
			
			$scope.ocultarNumeroEV=true;
			vm.modelo.estado = 'definirORE';
			$scope.motivos=["Extravío", "Robo", "Otro"];
			llamadaAjax2($http, $scope, vm, '/web-nacimiento/motivosNoEv25', vm.modelo, vm.levantarORE, 'POST');
			}
	}
	vm.levantarORE = function($scope, vm, response){
		
		console.log("vista inicial?" + vm.vista);
		console.log(response.data.vista);
		console.log(response.data.modelo);
		vm.vista += response.data.vista;
		
		vm.modelo = response.data.modelo;
	}
	$scope.levantarModal3 = function(){
		console.log("estoy entrando en el modal 3");
		var cadenaValor = "¿Se enviará oficio a la Oficina Regional Electoral ya que el presentado(a) no posee certificado médico de nacimiento EV-25, y se cancelará la solicitud. ¿Desea continuar?";
		presentarModal(
				$scope,
				$uibModal,
				$scope.presentarORE,
				cadenaValor,
				$rootScope.tituloWizard, 's');
		console.log(vm.modelo.pojo.lugarNacimientoVenezuela);
		
	}
	
	$scope.presentarORE = function(){
		console.log("entrando a presentar ore");
		llamadaAjax2($http, $scope, vm, '/web-nacimiento/oficioORE1', vm.modelo, vm.funcionORE1, 'POST');
	}
	vm.funcionORE1 = function ($scope, vm, response){
		console.log(response.data.vista);
		console.log(response.data.modelo);
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.modelo.estado = "ORE1_->_ORE2";
		
	}
	vm.levantarModalOre1 = function(){
		var cadenaValor = "¿Los datos ingresados son correctos?";
		presentarModal(
				$scope,
				$uibModal,
				$scope.presentarORE2,
				cadenaValor,
				$rootScope.tituloWizard, 's');
	}
	$scope.presentarORE2 = function(){
		llamadaAjax2($http, $scope, vm, '/web-nacimiento/oficioORE2', vm.modelo, vm.funcionORE2, 'POST');
	}
	vm.funcionORE2 = function ($scope, vm, response){
		console.log(response.data.vista);
		console.log(response.data.modelo);
//		console.log(response.data.estado);
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		$rootScope.cancelar1();
		
		
	}
	
	$scope.mostrarPanelFooter1 = false;
	$scope.disabledNumeroEV25 = false;
	$scope.validarEV25 = function(){
		console.log($scope.numEV25);
		if ($scope.numEV25!=undefined){
			vm.modelo.numeroEV25 = $scope.numEV25;
			llamadaAjax2($http, $scope, vm, '/web-nacimiento/validarActa', vm.modelo, vm.funcionVacia2, 'POST');
			$scope.disabledNumeroEV25 = true;
		}
	}
	
	vm.funcionVacia2 = function($scope, vm, response){
		console.log($scope);
		console.log(response.data);
		if (response.data==false){
			$scope.cambiarVista = true;
			vm.modelo.estado='drools';
			vm.siguiente();
		}else{
			console.log("entrando a el else");
			$scope.levantarModal2();
		}
	}
	
	$scope.levantarModal2 = function(){
		var cadenaValor = "El número "+ vm.modelo.numeroEV25 + " del certificado médico de nacimiento EV-25 ya se encuentra registrado ¿Es correcto?";
		presentarModal(
				$scope,
				$uibModal,
				$scope.finalizar1,
				cadenaValor,
				$rootScope.tituloWizard, 's');
		console.log(vm.modelo.pojo.lugarNacimientoVenezuela);
		
	}
	
	$scope.finalizar1 = function(){
		console.log("entrando en Finalizar 1");
		$scope.cambiarVista = true;
		
		vm.modelo.estado='finalizar1';
		llamadaAjax2($http, $scope, vm, '/web-nacimiento/finalizar1', vm.modelo, vm.funcionVacia3, 'POST');
	}
	vm.funcionVacia3 = function($scope, vm, response){
		console.log("Entrando en funcion vacia 3");
		console.log(response.data.vista);
		console.log(response.data.modelo);
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
	}
	
	$scope.validarFecha = function(){
		console.log("entrando a validar fecha");
		if (vm.modelo.fecha_ != undefined){
			console.log("la fecha es----->" + vm.modelo.fecha_);
		}
	}
	vm.padreBiologico = function(){
		$scope.cambiarVista = false;
		
//		vm.siguiente();
		//comprobar que viene padre con 'try'
		
			if(vm.modelo['PAD'] != undefined)
			{
				$scope.padreAutenticado = true;
				vm.modelo.pojo.padreBiologicoPresente = true;
				console.log("estoy en el primer try------>"+vm.modelo['PAD']);
				vm.definirPadrePresente();
//				vm.siguiente();
			}
		
			if(vm.modelo['PAP'] != undefined){
				$scope.padreAutenticado = true;
				vm.modelo.pojo.padreBiologicoPresente = true;
				console.log("estoy en el segundo try------>"+vm.modelo['PAP'])
				vm.definirPadrePresente();
//				vm.siguiente();
			}
		
			if(vm.modelo['PAD'] == undefined && vm.modelo['PAP'] == undefined)
				vm.siguiente();
		console.log(vm.modelo.pojo);
	}
	
	vm.definirCambioEV25 = function(){
		$scope.cambiarVista = false;
		
		vm.siguiente();
	}
	vm.definirCambioNombrePadre = function(){
		$scope.cambiarVista = false;
		
		vm.siguiente();
	}
	vm.definirPadrePresente = function(){
		$scope.cambiarVista = false;
		
		vm.siguiente();
	}
	vm.definirMotivoNegativa = function(){
		if(vm.modelo.pojo.confirmaDatosPadre==false){
			$scope.cambiarVista = false;
//			vm.siguiente();
			console.log("popular el select con los motivos de negativa");
			vm.modelo.motivosNegativa = ["Desconocimiento","Negativa","Violacion","Incesto"];
			vm.siguiente();
		}else{
			$scope.cambiarVista = false;
			vm.siguiente();
		}
		
	}
	$scope.cargarMotivosNegativa = function() {
		console.log(vm.modelo);
		try{
			if(vm.modelo['MAM'] != undefined){
			if(vm.modelo['MAM'].segundoApellido == null){
				console.log(vm.modelo['MAM'].segundoApellido);
				$scope.levantarModal4();
			}else{
				vm.siguiente();
			}
		}
		}catch(e){
			
		}
		try{
			if(modelo['MAD'] != undefined){
			if (vm.modelo['MAD'].segundoApeliido == null){
				console.log(vm.modelo['MAD'].segundoApeliido);
				$scope.levantarModal4();
			}else{
				vm.siguiente();
			}
		}
		}catch(e){
			
		}
		
	}
	$scope.levantarModal4 = function(){
		var cadenaValor = "La  madre  declarante posee solo  un apellido";
		presentarModal(
				$scope,
				$uibModal,
				$scope.determinarApellido,
				cadenaValor,
				$rootScope.tituloWizard, 's');
		console.log(vm.modelo.pojo.lugarNacimientoVenezuela);
		}
	$scope.determinarApellido = function(){
		llamadaAjax2($http, $scope, vm, '/web-nacimiento/determinarApellido', vm.modelo, vm.funcionVacia3, 'POST');
				
	}
	vm.definirApellido = function(){
		vm.siguiente();
	}
	
	vm.definirIndigena = function(){
		$scope.buscarComunidades();
		if(vm.modelo.pojo.indigena == true){
			$scope.hideComunidad = false;
			$scope.cambiarVista = false;
			$scope.buscarComunidades();
			vm.siguiente();
		}
		else{
			$scope.hideComunidad = true;
			$scope.cambiarVista = true;
			vm.siguiente();
		}
	}
	
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
	
	$scope.buscarPais = function() {

		$http({
			method : 'POST',
			url : '/web-generales/consultarPaisTodos',
			data : {}
		}).then(function successCallback(pais) {
			console.log(pais.data);
			$scope.paises = pais.data;
			$scope.estados = $scope.paises.estados;
		}, function errorCallback(error) {
			console.log("error: " + error.data);
		});

	}

	$scope.buscarPais();
	// $scope.formulario.estado = null;

	$scope.buscarEstado = function() {
		if (vm.modelo['presentado'].direccion[0].pais.codigo == "VEN") {
//			vm.modelo['presentado'].direccion[0].pais.codigo
			$http({
				method : 'POST',
				url : '/web-generales/consultarEstadoPorPais',
				data : {
					codigo : vm.modelo['presentado'].direccion[0].pais.codigo
				}

			}).then(function successCallback(estado) {
				$scope.disa = false
				console.log(estado.data);

				$scope.estados = estado.data;
				$scope.municipios = $scope.estados.municipios;
			}, function errorCallback(error) {
				console.log("error: " + error.data);
			});
		} else {
			$scope.disa = true
		}
	}

	$scope.buscarMunicipio = function() {

		$http({
			method : 'POST',
			url : '/web-generales/consultarMunicipioPorEstado',
			data : {
				codigo : vm.modelo['presentado'].direccion[0].estado.codigo
			}
		}).then(function successCallback(municipio) {
			console.log(municipio.data);
			$scope.municipios = municipio.data;
			$scope.parroquias = $scope.municipios.parroquias;
		}, function errorCallback(error) {
			console.log("error: " + error.data);
		});

	}

	$scope.buscarParroquia = function() {

		$http(
				{
					method : 'POST',
					url : '/web-generales/consultarParroquiaPorMunicipio',
					data : {
						codigo : vm.modelo['presentado'].direccion[0].municipio.codigo
					}
				}).then(function successCallback(parroquia) {
			console.log(parroquia.data);
			$scope.parroquias = parroquia.data;
			// $scope.parroquias=$scope.municipios.parroquias;
		}, function errorCallback(error) {
			console.log("error: " + error.data);
		});

	}

	
	
	
});

