//controlador del template: Identifica el Tramite y presenta la primera pantalla
App.controller('testController',function($scope,$http,$rootScope,footerFactory,pasosFactory,$parse){
	console.log('TestController');
	var vm=this;
	//vm.estaFallecidoEV14=true;////////revisar esto
//	vm.continuarEvaluando=true;
	vm.continuarEvaluando=footerFactory.getContinuarEvaluando();
	
	vm.pasos={"Paso":"Solicitud de Registro Deuncion"};
	

//	funcion que evalua el cmabio en los radio buton de la gate 0 
	vm.change= function(value) {//el value es el valor del radio button
		console.log("entro al change");
		if(value=="Inscripcion"){
			vm.Inscripcion=true;
			delete vm.Insercion;
			footerFactory.setInscripcion();
		}else{
			delete vm.Inscripcion;
			vm.Insercion=true;
			footerFactory.setInsercion();
		}
		footerFactory.asignarModelo(vm);
	};

	vm.setIdentificacion=footerFactory.setEstaFallecidoConID;//asigna valor de si tiene identificacion o no el fallecido
	vm.setFallecidoDentroTN=footerFactory.setVzlanoOExtrajFallecidoDentroTN;//asigna el valor de venezolano o extranjero fallecido en territorio nacional 
	vm.setEstaFallecidoEV14=footerFactory.setEstaFallecidoEV14;//asigna si el fallecido posees certificado medico ev14

	var datos={gate:'GATE'};
	$http({
		method: 'POST',
		url: '/web-registrarDefuncion/iniciarTramite1',
		data:{gate0:'gate0'}
	}).then(function successCallback(response) {
		console.log("response.data");
		console.log(response.data);
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
	
	$scope.$watch(function(){return footerFactory.getContinuarEvaluando()},function (newValue, oldValue) {
		if (newValue !== oldValue) {
			console.log(newValue);
			vm.continuarEvaluando=newValue;
		}
	});
	
});