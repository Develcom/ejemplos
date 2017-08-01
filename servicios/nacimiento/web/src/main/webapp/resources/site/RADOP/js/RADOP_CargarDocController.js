App.controller('RADOP_CargarDocController',function($scope,$http,$rootScope,$uibModal){
console.log("------ESTATUUUU-----" + $rootScope.objectSelected.codigoEstadoSolicitud);
	
	var vm = this;
	vm.paso = 0;
	//$scope.validaciones = validaciones;
	vm.errorTecla = {};
	 //Guarda en un array las vistas presentadas durante el proceso
	 vm.vistas = [];
	 //arreglo de datos que han sido presentados en pantalla
	 vm.modelos = [];
	 //representa los datos actualmente presentes en pantalla
	 vm.modelo = {};
	 vm.modelo.DJ ="false";
	 vm.titulos = [];
	 vm.activo = -1;
	 vm.enviarOficio = false;
	 vm.actualizar = false;
	 vm.cargarDocModal = false;
	 ///Para colocar el titulo al wizard
	 $scope.tituloWizard = "Registrar adopción";	 
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

//		llamadaAjax = function($http, $scope, vm, url, datos, accionSatisfactoria, metodo){
//			$http({
//				method: metodo,
//				url: url,
//				data:JSON.stringify(datos)
//			}).then(function successCallback(response) {
//				vm.vista= response.data.vista;
//				vm.modelo = response.data.modelo;
//				//llamarDisabled();
//				 vm.cargarDocModal = true;
//			    vm.presentarTitulo(response.data.modelo.titulo);
//				console.log(vm.modelo);
//			}, function errorCallback(response) {
//				console.log("error: "+response.data);
//			});
//		}
	 
	 /**
	  * Maneja como se muestran las vistas.
	  */
	 vm.presentarVista = function($scope, vm, response){
	  vm.vista = response.data.vista;
	  vm.modelo = response.data.modelo;
	  vm.presentarTitulo(response.data.modelo.titulo);
		  vm.cargarDocModal = true;
	 }
	 
	 vm.presentarVista2 = function($scope, vm, response){
	   vm.vista = response.data.vista;
	   vm.modelo = response.data.modelo;
	   vm.presentarTitulo(response.data.modelo.titulo);
	   console.log("pasa a true");
		  vm.enviarOficio= true;
		 }
	 
	 vm.presentarVista3 = function($scope, vm, response){
		  vm.vista = response.data.vista;
		  vm.modelo = response.data.modelo;
		  vm.presentarTitulo(response.data.modelo.titulo);
		  vm.actualizar= true;
		 }
	 
	 
	 vm.cargarDoc = function(){
		 		 
		 presentarModal($scope,$uibModal,vm.okCarga,'¿Se cargó el documento de forma correcta?',$rootScope.tituloWizard,'co');
	 }
	 
	 vm.okCarga = function(){
		 //vm.cargarDocModal = false;
		 llamadaAjax($http, $scope, vm, '/web-nacimiento/RADOP_CargarDocSatisf', vm.modelo, vm.presentarVista2, "POST");
			
	 }
	 
//	vm.respuestaDJ = function(){
//			
//		presentarModal2($scope, $uibModal, vm.okFuncion, vm.noFuncion, '¿Desea enviar un oficio al tribunal?',$rootScope.tituloWizard,'s');
////		/consultarDecisionJudicial
////		llamadaAjax($http, $scope, vm, '/web-nacimiento/consultarDecisionJudicial', vm.modelo, vm.d, "POST");
//	}
	
	/////////////////////////////////////////////////////////////
	vm.respuestaDJ =function(){

		
		var modalInstance = $uibModal.open({
			templateUrl: '/web-generales/pages/templates/imprimir/modalConfirmacion.html',
			controller: function($scope,$uibModalInstance){
				$scope.tipo = 's';
				 $scope.mensaje='¿Desea enviar un oficio al tribunal?';
				$scope.titulo = $scope.tituloWizard;;
			
				 $scope.cancel = function () {
					 console.log("NOOOOOO1");
				        $uibModalInstance.dismiss('cancel');
//				    	 $uibModalInstance.close(true);
				        vm.noFuncion();
//				        vm.noImprimirBorrador();
				        
				    };
				    $scope.ok = function () {
				    	console.log("SIIIIIIIII");
				    	    $uibModalInstance.close(true);
				    	    vm.okFuncion();
//				    	    vm.imprimirborrador();
				    	    
				    };
				    $scope.cancelar = function () {
				    	console.log("NOOOOOO2");
				    	vm.noFuncion();
			    	    $uibModalInstance.close(true);
//			    	    vm.noImprimirBorrador();
			    	    
				    }
			}
		});
		modalInstance.result.then(function () {

		}, function () {
			console.log("************VENGO DE CANCELAR");
			
		});
		
	}
	
	////////////////////////////////////////////////////////////
	
	vm.okFuncion = function(){
		vm.actualizar= true;
		vm.modelo.DJ="true";
		llamadaAjax($http, $scope, vm, '/web-nacimiento/RADOP_DecisionJudicial', vm.modelo, vm.presentarVista3, "POST");
		llamadaAjax($http, $scope, vm, '/web-nacimiento/consultarDecisionJudicial', vm.modelo, vm.d, "POST"); 
		console.log(vm.modelo);
	}
	
	vm.noFuncion = function(){	
		console.log("pues vamos al otro modal");
		presentarModal($scope,$uibModal,vm.PRNM,'El libro diario fue actualizado exitosamente',$rootScope.tituloWizard,'a');	
	}
	
	 vm.PRNM = function(){
		 vm.modelo.DJ="false";
		 presentarModal($scope,$uibModal,vm.cambioStatus,'Pendiente por registrar nota marginal',$rootScope.tituloWizard,'a');
		 
	 }
	 
	 vm.cambioStatus = function(){
		 llamadaAjax($http, $scope, vm, '/web-nacimiento/actualizarEstado', vm.modelo, vm.finalizar, "POST");			
		 $rootScope.cancelar1();
	 }
	 
	 vm.finalizar = function(){
		 $rootScope.cancelar1();
	 }
	 
	 vm.d = function($scope, vm, response){
		 console.log("Datos de la decision judicial");
		 vm.modelo = response.datos;
		 vm.modelo = response.data;
		 console.log(vm.modelo);
		 console.log(vm.modelo['IDJ']);
	 }
	 
	 vm.confirmarSalidaModulo = function(){
		 presentarModal($scope,$uibModal,vm.salirModulo,'¿Desea cancelar la solicitud?',$rootScope.tituloWizard,'s');
	 }
	 vm.salirModulo = function(){
		 $rootScope.cancelar1();
	 }
	
	 /**
	  * Carga los datos iniciales del proceso
	  */
	 vm.rutas = [
	       {ruta : '/web-nacimiento/iniciarTramiteRADOP', funcion : vm.presentarVista, metodo: 'POST'}
	   ];

	 //Datos a ser enviados al momento de cargar el modal  
	 vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, paso:0, 
			 estatu:$rootScope.objectSelected.codigoEstadoSolicitud, 
			 tramite : $rootScope.objectSelected.tramite.codigo,
			 DJ : vm.modelo.DJ};
	 llamadaAjax($http, $scope, vm, vm.rutas[vm.paso].ruta, vm.modelo, vm.rutas[vm.paso].funcion, vm.rutas[vm.paso].metodo);
	 
	
	 vm.siguiente = function(){
	  //guarda en la pila la vista actual
	  vm.vistas.push(vm.vista);
	  vm.modelos.push(vm.modelo);
	  console.log("vm.cargarDocModal---- " + vm.cargarDocModal);
	  if(vm.enviarOficio){
		  console.log("enviar el oficio???");
		  vm.respuestaDJ();
		  vm.enviarOficio = false;
		  return;
	  }else if(vm.cargarDocModal){
		  console.log("cargaaaa");
		  vm.cargarDoc();
		  vm.cargarDocModal = false;
		  return;
	  }else if(vm.actualizar){
		  console.log("entrando a actualizar");
		 // vm.actualizacion();
		  llamadaAjax($http, $scope, vm, '/web-nacimiento/actualizarEstado', vm.modelo, vm.salirModulo, "POST")
		  //vm.actualizar = false;
		  return;
	  }
	  
	  vm.paso++;
	  //llamadaAjax($http, $scope, vm, vm.rutas[vm.paso].ruta, vm.modelo, vm.rutas[vm.paso].funcion, vm.rutas[vm.paso].metodo);
	  
	 }
	 
//		vm.regresar= function(){
//			
//
//			 vm.enviarOficio = false;
//			 vm.cargarDocModal = false;
//			
//			vm.paso --;
//
//			
//			console.log("REGRESGAR"+ vm.paso);
//			vm.vista = vm.vistas[vm.paso];
//			vm.modeloActual = vm.modelos[vm.paso];
//			vm.modelos.splice(vm.modelos.length-1,1);
//			vm.vistas.splice(vm.vistas.length-1,1);
//			vm.disabled = vm.vistas.length == 0;
//			vm.disabled = vm.vistas.length == 0;
//			
//			if(vm.activo < vm.titulos.length-1){
//				vm.titulos.splice(vm.activo+1, vm.titulos.length-vm.activo-1);
//			}
//			vm.activo = vm.titulos.indexOf(vm.modeloActual.titulo);		
//		}
		
		
		function NombreSugeridoAgregarDocumento() {
			if(vm.modelo.uploadedfile !="") {
				$scope.banderaDisabled = true;
			}else{
				$scope.banderaDisabled = false;
			}
		} 
	 
	 //cierra wizard desde el boton x
		vm.cancel = function(){
			vm.confirmarSalidaModulo();
		}
	//cierra wizard desde el boton cancelar
		vm.cancelar = function (){
			vm.confirmarSalidaModulo();
		}
	
	

});