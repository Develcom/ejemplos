
App.controller('controladorRRECO_AB', function($scope,$http,$uibModal,$rootScope) {
	
console.log("*****controladorRRECO_AB*****");

	
	var vm = this;
	vm.paso = 0;
	 //Guarda en un array las vistas presentadas durante el proceso
	 vm.vistas = [];
	 //arreglo de datos que han sido presentados en pantalla
	 vm.modelos = [];
	 //representa los datos actualmente presentes en pantalla
	 vm.modelo = {};
	 vm.titulos = [];
	 vm.activo = -1;
	 vm.confirmarDatosForm = false;
	 $scope.alfa= /^[a-zA-Z]*$/;
	 vm.estado ='iniciarTramite';
	 
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
	  vm.modelo.titulo = (response.data.modelo.titulo);
	  vm.presentarTitulo(vm.modelo.titulo);
	 
	 }
	  
	 vm.cancel = function(){
		 vm.confirmarSalidaModulo();
	 }
	 //cierra wizard desde el boton cancelar
	 vm.cancelar = function (){
		 vm.confirmarSalidaModulo();
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
	 vm.rutas = {
	            iniciarTramite : {ruta : '/web-nacimiento/iniciarTramiteReconocimiento', funcion : vm.presentarVista, metodo: 'POST'},
	            //iniciarTramite : {ruta : '/web-nacimiento/iniciarTramiteReconocimiento', funcion : vm.presentarVista, metodo: 'POST'},
	 };

	 //Datos a ser enviados al momento de cargar el modal  
	 vm.modelo={id:$rootScope.objectSelected.numeroSolicitud,  statu:$rootScope.objectSelected.codigoEstadoSolicitud, tramite : $rootScope.objectSelected.tramite.codigo};
	 llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	 
	
	 vm.siguiente = function(){
	  //guarda en la pila la vista actual
	  vm.vistas.push(vm.vista);
	  vm.modelos.push(vm.modelo);
	 
	  
	  llamadaAjax($http, $scope, vm, vm.rutas[vm.estado].ruta, vm.modelo, vm.rutas[vm.estado].funcion, vm.rutas[vm.estado].metodo);
	  
	 }
})