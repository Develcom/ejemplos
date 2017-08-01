
App.controller('imprimirRADOPACTAControlador', function($scope,$http,$uibModal,$rootScope) {
	//console.log("****ENTRO A imprimirEPDIC *****");
	//console.log("*****$rootScope.imprimirEPDIC*******_"+$rootScope.imprimirEPDIC);
	
	var vm = this;
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
	//vm.iniciarActa = false;
	//estado del proceso
	//vm.estado = 'inicializacion';

	/**
	 * Maneja la etapa de verificacion de la declaracion jurada
	 */
	vm.presentarPdf = function($scope, vm, response){
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;

	}
	/**
	 * llamadaAjax
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
	
	
	vm.abrirmodalConfirmar = function(){
		 presentarModal($scope,$uibModal,vm.okRespuesta,'¿Se confirma la impresión?',$rootScope.tituloWizard,'co');
		 vm.impreso = false;
	}
	
	vm.okRespuesta = function(){
		console.log("confirmado");
		vm.estado="finalizarTramite";
		llamadaAjax($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
	}
	 vm.imprimir = function(){
		 vm.windowPrintBrowser();
		 presentarModal($scope,$uibModal,vm.okF,'¿La impresión es satisfactoria?',$rootScope.tituloWizard,'s');
	 }
	vm.okF = function(){
		console.log("entroooo");
		vm.impreso = true;
	}
	vm.finalizar = function(){
		//vm.estado = "iniciarActa";	
		//presentarModal($scope,$uibModal,vm.ActualizarLibro,'aqui se va cambiar el estatus',$rootScope.tituloWizard,'a');
		llamadaAjax($http, $scope, vm, '/web-nacimiento/actualizarEstado', vm.modelo, vm.ActualizarLibro, "POST");		
	}
	vm.ActualizarLibro = function(){
		presentarModal($scope,$uibModal,vm.ActualizarECU,'Actualización del libro de registro civil exitosa',$rootScope.tituloWizard,'a');
	}
	vm.ActualizarECU = function(){
		presentarModal($scope,$uibModal,vm.fin,'El ECU del(de la) adoptado(a)  fue actualizado satisfactoriamente',$rootScope.tituloWizard,'a');	
	}
	vm.fin = function(){
		$rootScope.imprimirRADOPACTA = false;
	}
	
	
	vm.estados = {
			
	             iniciarTramite : {ruta: '/web-nacimiento/RADOP_presentarImprimirActa', funcion: vm.presentarPdf, metodo : "POST"},
				 finalizarTramite : {ruta: '/web-nacimiento/RADOP_presentarSatisfactorioActa', funcion: vm.presentarPdf, metodo : "POST"},
//				 iniciarActa   : {ruta: '/web-nacimiento/RADOP_datos_adop', funcion: vm.presentarPdf, metodo : "POST"},
	};

	//Datos a ser enviados al momento de cargar el modal 	
	vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, tramite : $rootScope.objectSelected.tramite.codigo, estatu :$rootScope.objectSelected.codigoEstadoSolicitud};
	if(vm.modelo.tramite == "RADOP"){
		vm.modelo.tramite = "Registrar Adopcion";
	}
	if(vm.modelo.statu == "PPI"){
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

