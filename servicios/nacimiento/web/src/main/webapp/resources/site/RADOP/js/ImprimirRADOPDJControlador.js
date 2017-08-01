
App.controller('imprimirRADOPDJControlador', function($scope,$http,$uibModal,$rootScope) {
	//console.log("****ENTRO A imprimirEPDIC *****");
	 console.log("------ESTATUUUU-----" + $rootScope.objectSelected.codigoEstadoSolicitud);
	//console.log("*****$rootScope.imprimirEPDIC*******_"+$rootScope.imprimirEPDIC);
	//para el git
	var vm = this;
	//estado del proceso
	vm.estado = "iniciarTramite";
	//Guarda en un array las vistas presentadas durante el proceso
	vm.vistas = [];
	//arreglo de datos que han sido presentados en pantalla
	vm.modelos = [];
	//representa los datos actualmente presentes en pantalla
	vm.modelo = {};
	vm.titulos = [];
	vm.activo = -1;
	vm.impreso = false;
	vm.iniciarActa = false;

	
	/**
	 * llamadaAjax
	 */
	llamadaAjax = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
		$http({
			method: metodo,
			url: url,
			data:JSON.stringify(datos)
		}).then(function successCallback(response) {
			vm.vista= response.data.vista;
			vm.modelo = response.data.modelo;
		    vm.presentarTitulo(response.data.modelo.titulo);
			console.log(vm.modelo);
		}, function errorCallback(response) {
			console.log("error: "+response.data);
		});
	}
	
	llamadaAjax2 = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
		datos.ruta = 'http://localhost:8080' + url;
	 $http({
	  method: metodo,
	  url: '/web-generales/direccionesControlador',
	  data: angular.toJson(datos)//JSON.stringify(datos)
	 }).then(function successCallback(response) {
	  accionSatisfactoria($scope, vm, response);
	 }, function errorCallback(response) {
	  console.log("error: "+response.data);
	 });
	}
	
	/**
	 * llamadaAjax
	 */
//	 llamadaAjax = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
//		 $http({
//		  method: metodo,
//		  url: url,
//		  data: angular.toJson(datos)//JSON.stringify(datos)
//		 }).then(function successCallback(response) {
//		  accionSatisfactoria($scope, vm, response);
//		 }, function errorCallback(response) {
//		  console.log("error: "+response.data);
//		 });
//		}
	
	/**
	 * Maneja la etapa de verificacion de la declaracion jurada
	 */
	vm.presentarPdf = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;

	}
	
	vm.abrirmodalConfirmar = function(){
		 presentarModal($scope,$uibModal,vm.okRespuesta,'¿Se confirma la impresión?',$rootScope.tituloWizard,'co');
		 vm.impreso = false;
	}
	vm.imprimir = function(){
		vm.windowPrintBrowser();
		 presentarModal($scope,$uibModal,vm.okF,'¿La impresión es satisfactoria?',$rootScope.tituloWizard,'s');
	}
	vm.okRespuesta = function(){
		//console.log("confirmado");
		vm.estado="finalizarTramite";
		llamadaAjax($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	}	
	vm.okF = function(){
		console.log("entroooo");	
		vm.impreso = true;
	}
	vm.finalizar = function(){
	    llamadaAjax($http, $scope, vm, '/web-nacimiento/actualizarEstado', vm.modelo, vm.cerrar, "POST");		
	}
	vm.cerrar = function(){
		 $rootScope.imprimirRADOP = false;
	}
//	vm.respuestaActualizar = function(){
//		presentarModal($scope,$uibModal,vm.solicitudPendiente,'Solicitud Pendiente por Elaborar Acta',$rootScope.tituloWizard,'s');
//	}
//	vm.solicitudPendiente = function(){
//		//presentarModal($scope,$uibModal,vm.libroActualizado,'El libro diario fue actualizado exitosamente',$rootScope.tituloWizard,'s');
//		console.log("paso aquiii");
//		vm.iniciarActa = true;
//	}
	
	
	vm.estados = {
			
	             iniciarTramite : {ruta: '/web-nacimiento/RADOP_presentarImprimirDJ', funcion: vm.presentarPdf, metodo : "POST"},
	             finalizarTramite : {ruta: '/web-nacimiento/RADOP_presentarSatisfactorioDJ', funcion: vm.presentarPdf, metodo : "POST"},

	};

	//Datos a ser enviados al momento de cargar el modal 	
	vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, tramite : $rootScope.objectSelected.tramite.codigo, estatu :$rootScope.objectSelected.codigoEstadoSolicitud};
	if(vm.modelo.tramite == "RADOP"){
		vm.modelo.tramite = "Registrar Adopcion";
	}
	if(vm.modelo.statu == "PI"){
		vm.modelo.statu = "Pendiente por imprimir";
	}
	
	llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	
	vm.siguiente = function(){
		 vm.vistas.push(vm.vista);
		 vm.modelos.push(vm.modelo);
		 if(vm.impreso){
			 vm.abrirmodalConfirmar();
			 return;
		 }			
		 llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	}
	
	 
	 //cierra wizard desde el boton x
		vm.cancel = function(){
			$rootScope.cancelar1();
		}
	//cierra wizard desde el boton cancelar
		vm.cancelar = function (){
			$rootScope.cancelar1();
		}
		vm.windowPrintBrowser = function () {

	        document.getElementById("plugin").focus();

	        document.getElementById("plugin").contentWindow.print();


	    }
})

