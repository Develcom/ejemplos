var App = angular.module('app', ['ngRoute','formly', 'ngMessages', 'formlyBootstrap','ngSanitize','ui.bootstrap','sw.calendar']);

App.controller('fechaHora', function($scope, $interval){

	$interval(function(){
		$scope.fecha=new Date;
	},1000);

});

App.controller('header', function($scope) {

	$scope.myVar = false;
	$scope.toggle = function() {
		$scope.myVar = !$scope.myVar;
	};
});

App.controller('ModalDemoCtrl', function ($rootScope, $scope, $uibModal, $log) {
	$scope.animationsEnabled = true;
	$scope.open = function (size) {
		//Carga el nombre del Proceso en el titulo del wizard
		$rootScope.tituloWizard='Registrar Defunción';
		var modalInstance = $uibModal.open({
			backdrop: 'static',
			keyboard: false,
			animation: $scope.animationsEnabled,
			templateUrl: 'template.html',
			controller: 'ModalInstanceCtrl',
			windowClass: 'app-modal-window'
		});

		modalInstance.result.then(function () {
			$log.info('ok');
		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};
});

// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.
// pasar el rootScope para identificar el módulo y hacer las solicitudes a drools
App.controller('ModalInstanceCtrl', function ($rootScope, $scope, $uibModalInstance,$http) {
	var vm=this;
	$scope.ok = function (){
		console.log('hola');
	}
});


App.directive('carta', function ($compile) {
	return {
		//scope de la directiva
		//ele: elemento donde es declarada la directiva
		//attrs: atributos del elemento, compartidos por todas las directivas enlazadas
		link: function (scope, ele, attrs) {
			scope.$watch(attrs.carta, function(html) {
				ele.html(html);
				$compile(ele.contents())(scope);
			});
		}
	};
});

//controlador del template: Identifica el Tramite y presenta la primera pantalla
App.controller('testController',function($scope,$http,$rootScope,footerFactory,pasosFactory,$parse){
	console.log('TestController');
	var vm=this;
	vm.accion;
	vm.pasos=[];//['Selecci\u00f3n de Participantes','Autenticaci\u00f3n de participantes'];
	vm.activo=0;
	vm.enProgreso=false;

	vm.modelo={"accion":"",
			"value":"true"};	
	
	vm.change= function(accion) {
		console.log("entro al change");
		footerFactory.addModelo(accion)
		footerFactory.setModelo(accion)
		vm.modelo.value="true";
		vm.acciob=accion;
	};
	
	vm.modeloFactory=footerFactory.getModelo();


	var datos={gate:'GATE'};
	$scope.lista=[];
	$http({
		method: 'POST',
		url: '/web-registrarDefuncion/iniciarTramite1',
//		url: '/web-registrarDefuncion/iniciarTramite1',
		data:{gate0:'gate0'}
	}).then(function successCallback(response) {
		console.log("response.data");
		
		console.log(response.data);
		console.log("response");
		console.log(response);
		footerFactory.addFormulario(response.data);
	}, function errorCallback(response) {
		console.log("error: "+response.data);
	});
	$scope.$watch(function(){return footerFactory.getFormularios()},function (newValue, oldValue) {
		if (newValue !== oldValue) {
			$rootScope.html=newValue;
			console.log('html: '+newValue);
		}
	});		
});

App.controller('footerController',function($scope,$http,footerFactory){
	var vm=this;
	
	$scope.$watch(function(){return footerFactory.getModelo()},function(newValue, oldValue){
			console.log("Entro al watch del getModelo");
			console.log(newValue +" - " + oldValue);
			vm.modelo=newValue;
		}
	);
	//watch para vigilar cauando cambien la puerta
	/*$scope.$watch(function(){return footerFactory.getGate()},function(newValue, oldValue){
		console.log("Entro al watch del getModelo");
		console.log(newValue +" - " + oldValue);
		vm.gate=newValue;
	}
	);*/
		
	vm.submit=function(){
//		for(var name in vm.user){
//			var objeto = vm.user[name];
//			if(typeof(objeto)=='object')
//				for(var name2 in objeto){
//					var valor=objeto[name2];
//					if(valor!=undefined){
//						footerFactory.addModelo(valor);//[name+'_'+name2]=valor;
//						console.log("clave: "+name+'_'+name2);
//						console.log("Valor: "+valor);
//					}
//				}
//			else
//				datos[name]=objeto;
//		}
		$http({
			method: 'POST',
			url: '/web-registrarDefuncion/consultarDrools',
//			url: '/web-registrarDefuncion/iniciarTramite1',
			data:vm
//		data:{gate0:'gate5'}
		}).then(function successCallback(response) {
			footerFactory.addFormulario(response.data);
			console.log(response.data);
		}, function errorCallback(response) {
			console.log("error: "+response.data);
		});			
	}

	vm.regresar=function(){
		footerFactory.removerFormulario();
	}
});

App.controller('pasosController',function($scope,pasosFactory){
	var vm=this;
	vm.pasos = pasosFactory.getPasos();
});

App.factory('footerFactory',function(){
	var accion;
	var gate;
	var modelos=[];
	var formulario;
	var formularios=[];
	var modelo={"accion":"","value":true};
	var interfaz ={
			setFormulario:function(mFormulario){
				formulario=mFormulario;
			},
			setModelo:function(accion){
				modelo={"accion":accion,"value":true};
			},
			getFormulario:function(){
				return formulario;
			},
			addFormulario:function(mFormulario){
				formularios.push(mFormulario);
			},
			addModelo:function(mModelo){
				accion=mModelo;
			},
			addModelos:function(mModelo){
				modelos.push(mModelo);
			},
			setGate:function(mGate){
				gate=mGate;
			},
			getModelo:function(){
				return modelo;
			},
//			getModelo:function(){
//				return accion;
//			},
			getModelos:function(){
				return modelos;
			},
			removerFormulario:function(){
				formularios.splice(formularios.length-1,1);
			},
			getFormularios:function(){
				return formularios.join('');
			},
			getGate:function(){
				return gate;
			}
	}
	return interfaz;
}
);
//factory que permite mantener los pasos en el que se encuentra el usuario
//para el registro de defuncion
App.factory('pasosFactory',function(){
	var pasos=['Solicitud de Registro de Defuncion'];
	var funciones={
			addPasos:function(paso){
				pasos.push(paso);
			},
			getPasos:function(){
				return pasos;
			}
	}
	return funciones;
});

