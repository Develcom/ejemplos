App.controller('PDD_REFILController', function($scope,$http,$uibModal,$rootScope, $sce) {
	
	 console.log("------ESTATUUUU controller PDD. eln codigo es:-----" + $rootScope.objectSelected.codigoEstadoSolicitud);

	 
	 var vm = this;
	    vm.paso = 0;
	    vm.estado = "iniciarTramite";
	
	    vm.errorTecla = {};
	    //Guarda en un array las vistas presentadas durante el proceso
	    vm.vistas = [];
	    //arreglo de datos que han sido presentados en pantalla
	    vm.modelos = [];
	    //representa los datos actualmente presentes en pantalla
	    vm.modelo = {};
	    vm.titulos = [];
	    vm.activo = -1;
	    vm.cerrarSolicitud = false;
	    vm.cargarDocModal = false;
	    vm.modalFolio = false;
	    vm.respuestaActualizarB = false;

	    /**
	     *identifica el titulo de la etapa del proceso y el indice activo para
	     *resaltarlo con el color azul
	     **/
	    vm.presentarTitulo = function (mTitulo) {
	        vm.activo = vm.titulos.indexOf(mTitulo);
	        if (vm.activo == -1) {
	            vm.titulos.push(mTitulo);
	            vm.activo = vm.titulos.length - 1;
	        }
	    }


	    /**
	     * Maneja como se muestran las vistas.
	     */
	    vm.presentarVista = function ($scope, vm, response) {
	        vm.vista = response.data.vista;
	        vm.modelo = response.data.modelo;
	        vm.presentarTitulo(response.data.modelo.titulo);
            vm.cargarDocModal = true;
  }

	    vm.presentarVista2 = function ($scope, vm, response) {
	        vm.vista = response.data.vista;
	        vm.modelo = response.data.modelo;
	        vm.presentarTitulo(response.data.modelo.titulo);
	        vm.respuestaActualizarB = true;
	    }


	    vm.cargarDoc = function () {

	        presentarModal($scope, $uibModal, vm.okCarga, '¿Se cargó el documento de forma correcta?', $rootScope.tituloWizard, 'c');
	    }

	    vm.okCarga = function () {
	        vm.cargarDocModal = false;
	        llamadaAjax($http, $scope, vm, '/web-nacimiento/carga_exitosaRRFIL', vm.modelo, vm.presentarVista2, "POST");
	        vm.respuestaActualizarB = true;

	    }

	    vm.respuestaActualizar = function () {
	    //	log.info("--------entro a actualizar--------");
	           llamadaAjax($http, $scope, vm, '/web-nacimiento/actualizarEstadoRRFIL', vm.modelo, vm.salirModulo, "POST");
	         //  log.info("--------salio a actualizar--------");  
	    }

	    vm.salirModulo = function () {
	        $rootScope.cancelar1();
	    }
	    
	    
	    vm.confirmarSalidaModulo = function () {
	        presentarModal($scope, $uibModal, vm.salirModulo, '¿Desea cancelar la solicitud?', $rootScope.tituloWizard, 's');
	    }

	    /**
	     * Carga los datos iniciales del proceso
	     */
 
	    vm.estados = {

	            iniciarTramite:{ruta:'/web-nacimiento/iniciarRRFIL', funcion:vm.presentarVista, metodo:"POST"}


	        };
    //Datos a ser enviados al momento de cargar el modal
	    vm.modelo = {id:$rootScope.objectSelected.numeroSolicitud, paso:0,
	        statu:$rootScope.objectSelected.codigoEstadoSolicitud,
	        tramite:$rootScope.objectSelected.tramite.codigo};
	     llamadaAjax($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
 

	    vm.siguiente = function () {
	        //guarda en la pila la vista actual
	        vm.vistas.push(vm.vista);
	        vm.modelos.push(vm.modelo);
	        console.log("vm.cargarDocModal---- " + vm.cargarDocModal);
	        if (vm.respuestaActualizarB) {
	            vm.respuestaActualizar();
	            return;
	        } else if (vm.cargarDocModal) {
	            console.log("cargaaaa");
	            vm.cargarDoc();
	            // vm.cargarDocModal = false;
	            return;
	          
	        }
      vm.paso++;
	        //llamadaAjax($http, $scope, vm, vm.rutas[vm.paso].ruta, vm.modelo, vm.rutas[vm.paso].funcion, vm.rutas[vm.paso].metodo);

	    }

//			vm.regresar= function(){
//				
	//
//				 vm.cerrarSolicitud = false;
//				 vm.cargarDocModal = false;
//				
//				vm.paso --;
	//
//				
//				console.log("REGRESGAR"+ vm.paso);
//				vm.vista = vm.vistas[vm.paso];
//				vm.modeloActual = vm.modelos[vm.paso];
//				vm.modelos.splice(vm.modelos.length-1,1);
//				vm.vistas.splice(vm.vistas.length-1,1);
//				vm.disabled = vm.vistas.length == 0;
//				vm.disabled = vm.vistas.length == 0;
//				
//				if(vm.activo < vm.titulos.length-1){
//					vm.titulos.splice(vm.activo+1, vm.titulos.length-vm.activo-1);
//				}
//				vm.activo = vm.titulos.indexOf(vm.modeloActual.titulo);		
//			}


	    vm.NombreSugeridoAgregarDocumento = function () {
	        //if(vm.modelo.uploadedfile !="") {
	        //$scope.banderaDisabled = true;
	        //}else{
	        //$scope.banderaDisabled = false;
	        //}
	    }

	    //cierra wizard desde el boton x
	    vm.cancel = function () {
	        vm.confirmarSalidaModulo();
	    }
	    //cierra wizard desde el boton cancelar
	    vm.cancelar = function () {
	        vm.confirmarSalidaModulo();
	    }

	});
