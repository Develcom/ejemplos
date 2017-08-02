
App.controller('controladorEPDIC_PV', function($scope,$http,$uibModal,$rootScope,validaciones) {
	
	var vm = this;
	
	vm.paso = 0;
	$scope.validaciones = validaciones;
	vm.errorTecla = {};
	 //Guarda en un array las vistas presentadas durante el proceso
	 vm.vistas = [];
	 //arreglo de datos que han sido presentados en pantalla
	 vm.modelos = [];
	 //representa los datos actualmente presentes en pantalla
	 vm.modelo = {};
	 vm.titulos = [];
	 vm.activo = -1;
	 vm.confirmarDatosForm = false;
	 vm.modelo.permiso ={};
	 vm.estado= "iniciarTramite";
	 //estado del proceso
	// vm.estado = 'iniciarTramiteEDPIC';
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
		     //vm.tpermiso=response.data.modelo.tpermiso;
		// vm.presentarTitulo(response.data.modelo.titulo);

		 switch (vm.modelo.titulo) {
		     case "Permiso de inhumacion":
		        $scope.permiso="Permiso de inhumación";
		        break;
		     case "Permiso de cremacion":
		     $scope.permiso="Permiso de cremación";
		        break;
		     case "Permiso de inhumacion y cremacion":
		     $scope.permiso="Permiso de inhumación y cremación";
		        break;
		     }
		 //vm.presentarTitulo($scope.permiso);
		      // $scope.permiso=vm.tpermiso;
		 vm.modelo.titulo = $scope.permiso;
		        vm.presentarTitulo(vm.modelo.titulo);
		        vm.estado="validaReporte";
		        
		 //vm.confirmarDatosForm = true;
		
		}
	 
//	 vm.presentarVista = function($scope, vm, response){
//	  vm.vista = response.data.vista;
//	  vm.modelo = response.data.modelo;
//	 // vm.presentarTitulo(response.data.modelo.titulo);
//	  
//	  switch (vm.modelo.titulo) {
//      case "Permiso de inhumacion":    
//         $scope.permiso="Permiso de inhumación";       
//         break;
//      case "Permiso de cremacion":
//    	  $scope.permiso="Permiso de cremación";    
//         break;  
//      case "Permiso de inhumacion y cremacion":
//    	  $scope.permiso="Permiso de inhumación y cremación";    
//         break;
//      case "Certificacion del registrador":
//    	  $scope.permiso="Certificación del registrador";    
//         break;
//      }
//	  
//	  vm.presentarTitulo($scope.permiso);
//	  
//	  if(vm.paso >= 1){
//		  vm.confirmarDatosForm = true;
//	  }
//	 }
	 
	 vm.presentarVista2 = function($scope, vm, response){
		  vm.vista = response.data.vista;
		  vm.modelo = response.data.modelo;
		  vm.modelo.titulo = (response.data.modelo.titulo);
		  if(vm.modelo.titulo == "Certificacion del registrador"){
			  vm.modelo.titulo = "Certificación del registrador";
		  }
		  
		  vm.presentarTitulo(vm.modelo.titulo);
			  

			  vm.confirmarDatosForm = true;
		  
		 }
//	 

	 vm.finalizar = function(){
		 console.log("fin");
		 vm.cancelar();
	 }
//	 vm.resolve = function (){
//			return numCertificado;
//	 }
	 vm.funcion = function(parametro){
		 if(parametro == "conforme"){
			 vm.abrirModalConforme();
		 }else if (parametro == "noConforme"){
			 vm.abrirModalNoConforme();
		 }
	 }
	 
	 vm.abrirModalConforme = function(){
		 presentarModal($scope,$uibModal,vm.okConforme,'Verificación de permiso exitoso',$rootScope.tituloWizard,'c');
	 }
	 vm.abrirModalNoConforme = function (){
		 presentarModal($scope,$uibModal,vm.noConforme,'Verificación de permiso no conforme',$rootScope.tituloWizard,'c');	
	 }
	 vm.okConforme = function(){
		 llamadaAjax($http, $scope, vm, '/web-permisoInhumacionCremacion/actualizarEstado', vm.modelo, vm.finalizar, "POST");
	 }
	 vm.noConforme = function(){
		 llamadaAjax($http, $scope, vm, '/web-permisoInhumacionCremacion/actualizarEstado', vm.modelo, vm.finalizar, "POST");
	 }
	 vm.finalizar = function(){
		 $rootScope.cancelar1();
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
	 vm.estados = {
	      iniciarTramite: {ruta : '/web-permisoInhumacionCremacion/iniciarTramiteEPDIC', funcion : vm.presentarVista, metodo: 'POST'},
	       validaReporte: {ruta : '/web-permisoInhumacionCremacion/validarReporteConforme', funcion : vm.presentarVista2, metodo: 'POST'},
	   };

	 //Datos a ser enviados al momento de cargar el modal  
	 vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, paso:0, statu:$rootScope.objectSelected.codigoEstadoSolicitud};
	 llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);

	 vm.siguiente = function(){
		 console.log(vm.paso);
		 vm.paso++;
	  //guarda en la pila la vista actual
	  vm.vistas.push(vm.vista);
	  vm.modelos.push(vm.modelo);
	 
	  if(vm.confirmarDatosForm){
		  
		  vm.funcion(vm.modelo.permiso);
	  return;
	  
	  }

	 
	  llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
}
	 
	 
	 //cierra wizard desde el boton x
		vm.cancel = function(){
			vm.confirmarSalidaModulo();
		}
	//cierra wizard desde el boton cancelar
		vm.cancelar = function (){
			vm.confirmarSalidaModulo();
		}
		
	//boton regresar
		vm.regresar= function(){
			vm.confirmarDatosForm = false;
			$scope.campoObservacion = false;


			vm.vista = vm.vistas[vm.vistas.length - 1];
	         vm.modelo = vm.modelos[vm.modelos.length - 1];
	         vm.modelos.splice(vm.modelos.length - 1, 1);
	         vm.vistas.splice(vm.vistas.length - 1, 1);
	         vm.disabled = vm.vistas.length == 0;
	         vm.disabled = vm.vistas.length == 0;
	         vm.paso--;
	         
	         vm.activo = vm.titulos.indexOf(vm.modelo.titulo);
	         if (vm.activo < vm.titulos.length) {
	             vm.titulos.splice(vm.activo + 1, vm.titulos.length
	                 - vm.activo - 1);
	         }
	         vm.activo = vm.titulos.indexOf(vm.modelo.titulo);
			}
//	    vm.regresar = function () {
//	    	console.log("-----REGRESAR-----");
//	        vm.confirmarDatosForm = false;
//	        vm.vista = vm.vistas[vm.vistas.length - 1];
//	        vm.modelo = vm.modelos[vm.modelos.length - 1];
//	        vm.modelos.splice(vm.modelos.length - 1, 1);
//	        vm.vistas.splice(vm.vistas.length - 1, 1);
//	        vm.disabled = vm.vistas.length == 0;
//	        vm.disabled = vm.vistas.length == 0;
//	        vm.paso--;
//	        console.log("****before: " + vm.activo);
//	        vm.activo = vm.titulos.indexOf(vm.modeloActual.titulo);
//	        if (vm.activo < vm.titulos.length-1) {
//	            vm.titulos.splice(vm.activo + 1, vm.titulos.length
//	                - vm.activo - 1);
//	            vm.activo = vm.titulos.indexOf(vm.modeloActual.titulo);
//	        }
//	        
//
//	    }
//		vm.regresar= function(){
//			vm.confirmarDatosForm = false;
//			
//			vm.paso--;
//			vm.vista = vm.vistas[vm.paso];
//			vm.modeloActual = vm.modelos[vm.paso];
//			vm.modelos.splice(vm.modelos.length-1,1);
//			vm.vistas.splice(vm.vistas.length-1,1);
//			vm.disabled = vm.vistas.length == 0;
//			vm.disabled = vm.vistas.length == 0;
//
//			vm.activo = vm.titulos.indexOf(vm.modeloActual.titulo);
//			
//			if(vm.activo < vm.titulos.length-1){
//				vm.titulos.splice(vm.activo+1, vm.titulos.length-vm.activo-1);
//			}
//			vm.activo = vm.titulos.indexOf(vm.modeloActual.titulo);		
//		}
//        ---------funciones internas ------------      //
		$scope.mostrarCampoObservacion = function(){
			
			$scope.campoObservacion = true;
		}
		
		$scope.ocultarCampoObservacion = function(){
			
			$scope.campoObservacion = false;
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
			  
			 
	
})

