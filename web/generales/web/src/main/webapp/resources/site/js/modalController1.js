App.controller("formController",function($scope,$http,$rootScope,$uibModal, $log){
	console.log('Iniciando controlador de la modal 1*******');
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
	vm.estado = 'inicializacion';

	
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
		vm.loadFunciones(response.data.vista);
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
			vm.loadFunciones(response.data.vista);
			vm.vista = response.data.vista;
		}
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(vm.modelo.titulo);
		if(vm.estado === 'drools' && vm.modelo.pojo.continuarEvaluando == 'false'){
			vm.estado = 'presentarLista';
			llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);				
		}
	}

	vm.presentarListaDrools = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		vm.presentarTitulo(vm.modelo.titulo);
		vm.estado = 'autenticar';
	}
	
	
	vm.rutas = {
			inicializacion : {ruta: '/web-autenticarCiudadano/iniciarTramite', funcion: vm.inicializarDatos, metodo : "POST"},
            drools : {ruta : '/web-autenticarCiudadano/consultarDrools', funcion : vm.presentarVistaDrools, metodo : "POST"},
            presentarLista : {ruta : '/web-autenticarCiudadano/lista', funcion : vm.presentarListaDrools, metodo : "POST"},
            confirmar : {ruta : '/web-generales/actualizarAbierta', funcion : vm.finalizarYactualizar, metodo : "POST"}
	};

	vm.modelo['solicitud'] = $rootScope.objectSelected; 
	llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);		

	vm.siguiente = function(){
		llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);				
	}
	

	/**
	 * Convierte cadenas de texto a funciones
	 */
	vm.loadFunciones=function(campos){
		for(i=0;i<campos.length;i++){
			for(var key in campos[i]){
				console.log("campo "+i+" "+key);
				if(key==="controller" && campos[i].controller.indexOf('function')!==-1){
					myFunc=campos[i].controller;
					funcion = new Function("return " + myFunc)();
					campos[i].controller=funcion;
				}
				if(key==="parsers"){
					myFunc=campos[i].parsers[0];
					funcion = new Function("return " + myFunc)();
					campos[i].parsers[0]=funcion;
				}
				if(key==="templateOptions"){
					for(var key2 in campos[i].templateOptions){
						if(key2==="onKeypress"){
							myFunc=campos[i].templateOptions.onKeypress;
							funcion = new Function("return " + myFunc)();
							campos[i].templateOptions.onKeypress=funcion;								
						}
					}
				}
				if(key==="watcher"){
					for(var key2 in campos[i].watcher){
						if(key2==="listener"){
							myFunc=campos[i].watcher.listener;
							funcion = new Function("return " + myFunc)();
							campos[i].watcher.listener=funcion;								
						}
					}
				}
			}
		}
		console.log('fin');
	}
	
});

