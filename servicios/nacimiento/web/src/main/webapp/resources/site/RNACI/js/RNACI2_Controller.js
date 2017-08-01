App.controller("RNACI2_Controller",function($scope,$http,$rootScope,$uibModal, $log, validaciones){
	
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
	vm.funcionVacia3 = function($scope, vm, response){
		console.log("Entrando en funcion vacia 3");
		console.log(response.data.vista);
		console.log(response.data.modelo);
		vm.vista = response.data.vista;
		vm.modelo = response.data.modelo;
		console.log(vm.vista);
	}
	console.log($scope);
	console.log($scope.$parent.objectSelected);
	console.log(vm.modelo);
	vm.modelo.estado = $scope.$parent.objectSelected;
	console.log(vm.modelo);
	vm.modelo.funcion = vm.funcionVacia3;
//	vm.modelo.statu = "PI";
	llamadaAjax2($http, $scope, vm, '/web-nacimiento/vistaPreviaORE', vm.modelo, vm.modelo.funcion, 'POST');
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});