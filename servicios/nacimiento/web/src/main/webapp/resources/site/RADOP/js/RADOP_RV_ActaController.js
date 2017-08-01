/**
 * 
 */
App
		.controller(
				'RADOP_RV_ActaController',
				function($scope, $http, $rootScope, $uibModal, validaciones) {
					 console.log("------ESTATUUUU-----" + $rootScope.objectSelected.codigoEstadoSolicitud);
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
//						 vm.modelo.permiso ={};
						 vm.modelo.permisoRC = {};
						 ///Para colocar el titulo al wizard
						  $scope.tituloWizard = "Registrar adopción";

						  /**
							  *identifica el titulo de la etapa del proceso y el indice activo para 
							  *resaltarlo con el color azul 
							  **/
							  vm.presentarTitulo = function(mTitulo) {
									vm.activo = vm.titulos.indexOf(mTitulo);
									if (vm.activo == -1) {
										vm.titulos.push(mTitulo);
										vm.activo = vm.titulos.length - 1;
									}
								}
						  
							/**
							 * llamadaAjax
							 */
							llamadaAjax = function($http, $scope, vm, url, datos,
									accionSatisfactoria, metodo) {
								$http({
									method : metodo,
									url : url,
									data : JSON.stringify(datos)
								}).then(function successCallback(response) {
									vm.vista = response.data.vista;
									vm.modelo = response.data.modelo;
									console.log(vm.modelo);
									//llamarDisabled();
									vm.presentarTitulo(response.data.modelo.titulo);
									console.log(vm.modelo);
								}, function errorCallback(response) {
									console.log("error: " + response.data);
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
							  vm.presentarTitulo(response.data.modelo.titulo);
							 }, function errorCallback(response) {
							  console.log("error: "+response.data);
							 });
							}
						  
						 
						 
						 /**
						  * Maneja como se muestran las vistas.
						  */
						 vm.presentarVista = function($scope, vm, response){
						  vm.vista = response.data.vista;
						  vm.modelo = response.data.modelo;
						  vm.presentarTitulo(response.data.modelo.titulo);
						  if(vm.paso >= 1){
							  vm.confirmarDatosForm = true;
						  }
						 }
						 

						 vm.finalizar = function(){
							 console.log("fin");
							 vm.cancelar();
						 }
//						 vm.resolve = function (){
//								return numCertificado;
//						 }
						 vm.funcion = function(parametro){
							 if(parametro == "conforme"){
								 console.log("conforme");
								 vm.abrirModalConforme();
							 }else if (parametro == "noConforme"){
								 vm.abrirModalNoConforme();
							 }
						 }
						 
						 vm.abrirModalConforme = function(){
							 presentarModal($scope,$uibModal,vm.okConforme,'Verificación del oficio conforme',$rootScope.tituloWizard,'co');
						 }
						 vm.abrirModalNoConforme = function (){
							 presentarModal($scope,$uibModal,vm.noConforme,'Verificación del oficio no conforme',$rootScope.tituloWizard,'co');	
						 }
						 vm.okConforme = function(){
							 console.log("cambia estatus y llama a finalizar");
							 llamadaAjax($http, $scope, vm, '/web-nacimiento/actualizarEstado', vm.modelo, vm.finalizar, "POST");
							 $rootScope.cancelar1();
						 }
						 vm.noConforme = function(){
							 llamadaAjax($http, $scope, vm, '/web-nacimiento/actualizarEstado', vm.modelo, vm.finalizar, "POST");
							 $rootScope.cancelar1();
						 }
						 vm.finalizar = function(){
							 console.log("cerro");
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
						 vm.rutas = [
						       {ruta : '/web-nacimiento/iniciarTramiteRADOP', funcion : vm.presentarVista, metodo: 'POST'},
						       {ruta : '/web-nacimiento/RADOP_RV_verificacion', funcion : vm.presentarVista, metodo: 'POST'},
						   ];

						 //Datos a ser enviados al momento de cargar el modal  
						 vm.modelo={id:$rootScope.objectSelected.numeroSolicitud, paso:0, estatu:$rootScope.objectSelected.codigoEstadoSolicitud};
						 llamadaAjax2($http, $scope, vm, vm.rutas[vm.paso].ruta, vm.modelo, vm.rutas[vm.paso].funcion, vm.rutas[vm.paso].metodo);

						 vm.siguiente = function(){
						  //guarda en la pila la vista actual
						  vm.vistas.push(vm.vista);
						  vm.modelos.push(vm.modelo);
						  if(vm.confirmarDatosForm){
							  console.log("vm.modelo.permiso"+vm.modelo.permisoRC);
							  vm.funcion(vm.modelo.permisoRC);
						  return;
						  }
						  vm.paso++;
						  console.log("Pasoooooo"+vm.paso)
						   console.log("rutaaaaa"+vm.rutas[vm.paso].ruta)
						  llamadaAjax2($http, $scope, vm, vm.rutas[vm.paso].ruta, vm.modelo, vm.rutas[vm.paso].funcion, vm.rutas[vm.paso].metodo);
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
//					        ---------funciones internas ------------      //
							$scope.mostrarCampoObservacion = function(){
								 vm.confirmarDatosForm = true;
								$scope.campoObservacion = true;
							}
							
							$scope.ocultarCampoObservacion = function(){
								 vm.confirmarDatosForm = true;
								$scope.campoObservacion = false;
							}
						
				});

