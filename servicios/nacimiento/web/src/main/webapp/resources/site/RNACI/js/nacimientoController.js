App.controller("nacController",function($rootScope, $scope, $http, $location, $state, $stateParams, $window, $route, $uibModal){
	var vm=this;
	vm.pasos=['Solicitud de Registro'];
	vm.activo=0;
	vm.removerClaseParticipantes=false;
	vm.autenticar=false;
	vm.solicitarDatos=false; //le dice al controlador java si debe cargar una nueva entrada en el formulario para participantes
	vm.enProgreso=false;
	vm.guardar=false;
	vm.success=false;
	vm.userFields =[];
	vm.user={};
	//Carga la vista inicial al consultar
	var metodo = "POST";
	var urlContext = "/web-nacimiento/iniciarModulo";
	var performFunction = iniciarModulo;
	var datos = {gate0: 'GATE0'};
	genericAjax($http, $state, $stateParams, $scope, metodo, urlContext, datos, performFunction);
	
	$scope.$watch("userFields", function(newValue, oldValue){
		if(newValue != null){
			vm.userFields = newValue;
		}
	});
	$scope.$watch("user", function(newValue, oldValue){
		if(newValue != null){
			vm.user = newValue;
		}
	});
	$scope.$watch("tag", function(newValue, oldValue){
		if(newValue != null){
			vm.pasos.push(newValue);
			vm.activo = vm.activo + 1;
		}
	});
//	vm.userFields= $scope.userFields;
//	vm.user= $scope.user;
//	  $http({
//		  method: 'POST',
//		  url: '/web-nacimiento/iniciarModulo',
//		  data:{gate0:'GATE0'}
//	  }).then(function successCallback(response) {
//		  //verifica la existencia de funciones en el objeto
//		  var campos=response.data.fields;
//		  for(i=0;i<campos.length;i++){
//			  for(var key in campos[i]){
//				  console.log("campo "+i+" "+key);
//				  if(key=="controller"){
//					  myFunc=campos[i].controller;
//					  console.log("este es el objeto: "+myFunc);
//					  funcion = new Function("return " + myFunc)();
//					  response.data.fields[i].controller=funcion;
//				  }
//			  }
//		  }
//		  vm.userFields=response.data.fields;
//		  vm.user=response.data.modelo;
//	  }, function errorCallback(response) {
//		  console.log("error");
//	  });
	
	 vm.submit=function(userData){
		 var metodo = "POST";
		 var urlContext = "/web-nacimiento/nacimientoModulo";
		 var performFunction = nacimientoModulo;
		 var datos = userData;
		 var modelo = vm.user;
		 var fields = vm.userFields;
		 
		 //===========
		 vm.enProgreso=true;
		 vm.autenticar=datos.autenticar;
		 vm.continuar = datos.continuar;
		 if(vm.continuar){
			 /*for(var name in userData){
				 var objeto = userData[name];
				 for(var name2 in objeto){
					 var valor=objeto[name2];
					 if(valor!=undefined){
						 datos[name+'_'+name2]=valor;
						 console.log("clave: "+name+'_'+name2);
						 console.log("Valor: "+valor);
					 }
				 }
			 }*/
			 genericAjax($http, $state, $stateParams, $scope, metodo, urlContext, datos, performFunction);
		 }
	 }

	 vm.anterior=function(){
		 alert("Hey! Can i back at this time?!"); 
	 }	 
});

App.controller("participantesAautenticarController",function($scope){
	$scope.ciudadanos=['Madre','Padre','Adoptado','Tesigo 1','Testigo 2'];
	$scope.documentos=['CEDULA','NUI','PASAPORTE'];
	
});
