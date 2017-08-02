//Test de Autenticar ciudadano Angular

App.controller('participantesController1',function($scope,$rootScope,$http){
	console.log("Ejecutando angular autenticar ciudadano");
	angular.element(document.querySelector('#opciones')).css('display','');
	this.sinDoc=false;
	$scope.entePublico=true;
	$rootScope.marcados=[];
	$rootScope.panel=1;
	$http({
        method: 'GET',
        url: '/web-autenticarCiudadano/consultarSolicitud'
    }).then(
	function(response) {
		//Verifica el tipo de solicitante (ciudadano o Ente Publico)
		if(response.data.tipoSolicitante=="D"){
			$scope.entePublico=false;
			console.log("Tipo de participante: "+$scope.entePublico);
		}
			//Presenta la lista de participantes
			$scope.lista=response.data.listaParticipantes;
			//carga el nombre del participante ente publico
			$scope.solicitante=response.data.solicitante;
	},
	function(response) {
		console.log('consulta errada');
	});	
	// Almacena los elementos seleccionados de la pantalla en un array
	$scope.almacenarMarcados=function(item,marcado,$event){
		var indice; // indice del elemento marcado dentro del array
		if(!marcado){
			this.sinDoc=false;
			$(event.target).parent().next().children().prop('checked',false);
			indice=$rootScope.marcados.indexOf(item);
			if(indice>-1)
				$rootScope.marcados.splice(indice,1);
			return
		}
		// Si el elemento no existe en el array entonces es agregado
		$rootScope.marcados.push(item);
	}
});

App.controller('navController',function($scope,$http){
	var urlBaseJson="https://localhost:8443/web-autenticarCiudadano/resources/js/json/";
	$scope.clickSiguiente=function(){
		alert('click');
		 $http({
			  method: 'GET',
			  url: "https://localhost:8443/AngularBootstrapWizard/resources/pages/wizard1.html"
			}).then(function successCallback(response) {
				console.log("respuesta de la solicitud al servidor html..........");
				console.log('respuesta: '+response.data);
			    // this callback will be called asynchronously
			    // when the response is available
			  }, function errorCallback(response) {
				  console.log('error en la solicitud');
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  });	
	};
});
App.controller('participantesController',function($scope,$http){
	var vm=this;
	vm.lista=['Madre','Padre','Abuelo','Abuela'];
});

App.controller('documentosController',function($scope){
	$scope.documentos=['CEDULA','NUI','PASAPORTE'];
	$scope.placeHolders=['V-123456789','123456789','123456789'];
	$scope.nroDocumento;
	$scope.selectDoc=function(dato){
		if(dato=='CEDULA'){
			$scope.placeHolder='V123456789';
			$scope.minima_longitud=6;
			$scope.maxima_longitud=11;
			$scope.patron=new RegExp('^(V|v|E|e)+[0-9]+[a-z]');
			return;
		}
		if(dato=='NUI'){
			$scope.placeHolder='123456789';
			$scope.minima_longitud=8;
			$scope.maxima_longitud=9;
			$scope.patron=new RegExp('[aA-zZ]+[0-9]|[0-9]');
			return;
		}
		if(dato=='PASAPORTE'){
			$scope.placeHolder='123456789';			
			$scope.minima_longitud=6;
			$scope.maxima_longitud=11;
			$scope.patron=new RegExp('[aA-zZ]+[0-9]|[0-9]');
		}
	};
	$scope.alTeclear=function(){
		console.log('ha ingresado: '+$scope.nroDocumento);
	};
});

App.controller('MainCtrl', function MainCtrl(formlyVersion, $timeout) {
    var vm = this;
    // funcation assignment
    vm.onSubmit = onSubmit;

    // variable assignment
    vm.author = { // optionally fill in your info below :-)
      name: 'Kent C.',
      url: 'Dodds' // a link to your twitter/github/blog/whatever
    };
    vm.exampleTitle = 'model property'; // add this
    vm.env = {
      angularVersion: angular.version.full,
      formlyVersion: formlyVersion
    };

    vm.model = {
      name: {
        first: 'Gandalf',
        last: 'The Gray'
      },
      email: 'gandalf@example.com',
      username: 'yoiamgandalf'
    };
    
    vm.fields = [
      {
        key: 'first',
        type: 'input',
        model: vm.model.name,
        templateOptions: {
          label: 'First Name'
        }
      },
      {
        key: 'last',
        type: 'input',
        model: vm.model.name,
        templateOptions: {
          label: 'Last Name'
        }
      },
      {
        key: 'email',
        type: 'input',
        templateOptions: {
          label: 'Email Address',
          type: 'email'
        }
      },
      {
        key: 'username',
        type: 'input',
        templateOptions: {
          label: 'Username'
        }
      },
      {
        key: 'other',
        type: 'input',
        templateOptions: {
          label: 'Other model'
        },
        expressionProperties: {
          'templateOptions.disabled': '!options.data.modelLoaded'
        },
        data: {
          modelLoaded: false
        }
      }
    ];
    
    $timeout(function() {
    }, 1000).then(function() {
      var field = vm.fields[4];
      console.log('hi');
      field.model = {
        other: 'some value'
      };
      field.data.modelLoaded = true;
      field.runExpressions(); // re-run the expression properties
    });
    
    vm.originalFields = angular.copy(vm.fields);

    // function definition
    function onSubmit() {
      alert(JSON.stringify(vm.model), null, 2);
    }
  });
App.factory('intercambiador',function(){
	var titulo;
	addTitulo=function(mititulo){
		console.log("insertando titulo: "+mititulo);
		titulo=mititulo;
	};
	getTitulo=function(){return titulo};
	return {addTitulo: addTitulo, getTitulo: getTitulo};
});

App.controller('testController',function($scope,$http,intercambiador,$rootScope){
	var vm=this;
	$scope.titulo='asada';
	intercambiador.addTitulo('qwerty');
	$rootScope.titulo='hola hola';
	
    $scope.$watch('titulo', function (newValue, oldValue) {
        if (newValue !== oldValue) intercambiador.addTitulo(newValue);
    });
	$http({
		  method: 'GET',
		  url: 'pages/ajaxControllerTest.html'
		}).then(function successCallback(response) {
			$rootScope.html=response.data;
			console.log('Exitoso: '+response.data);
		    // this callback will be called asynchronously
		    // when the response is available
		  }, function errorCallback(response) {
			  console.log("error");
		    // called asynchronously if an error occurs
		    // or server returns response with an error status.
		  });
	$scope.insertar=function(){console.log('se hizo click en el botón');};
	$scope.porAjax=function(){alert("por ajax");};
	});
App.controller('wizardTituloControlador',function($scope,intercambiador,$rootScope){
	$scope.tituloWizard = 'Testing';
    $scope.$watch(function () { 
		 return intercambiador.getTitulo(); 
	  }, function (newValue, oldValue) {
		  	if (newValue !== oldValue){
		  		$scope.tituloWizard = newValue;
		  		//$rootScope.titulo = newValue;
		  	}
	  });
});

//App.directive('dynamic', function ($compile) {
//	  return {
//	    //restrict: 'A',
//	    //replace: true,
//		//scope de la directiva
//		//ele: elemento donde es declarada la directiva
//		//attrs: atributos del elemento, compartidos por todas las directivas enlazadas
//	    link: function (scope, ele, attrs) {
//		      scope.$watch(attrs.dynamic, function(html) {
////	      scope.$watch(ele.attr('dynamic'), function(html) {
//		      console.log('elemento: '+ele.attr('dynamic'));
//		      var htmlContenido=$('#contenedor1').html();
//		      var scopeContenido=scope.html;
////		      if(scopeContenido===htmlContenido)
////		    	  alert('scope y html iguales');
////		      else
////		    	  alert('scope y html diferentes');
//	    	  console.log('compilando $ html.......:'+$('#contenedor1').html());
//	    	  console.log('html: '+html);
//	    	  console.log('compilando $ dynamic.......:'+$('#contenedor1').attr('dynamic'));
//	    	  console.log('scope.html: '+scope.html);
//	        ele.html(html);
////	    	  ele.html(htmlContenido);
////	    	  scope.html=htmlContenido;
//	        $compile(ele.contents())(scope);
////		      if(scopeContenido===htmlContenido)
////		    	  inicializar1();
//	      });
//	    }
//	  };
//	});
//App.controller('MyController',function($scope){
//	  $scope.click = function(arg) {
//		    alert('Clicked ' + arg);
//		  }
//		  $scope.html = '<a ng-click="click(1)" href="#">Click me</a>';
//		  $scope.click2 = function(arg) {
//			    alert('Clicked 2222 ' + arg);
//			  }
//			  $scope.html = '<a ng-click="click(1)" href="#">Click me</a>';
//});


App.controller('ModalDemoCtrl', function ($scope, $uibModal, $log) {
	//paso de drools
	  $scope.items = 1;
	  $scope.animationsEnabled = true;
	  $scope.open = function (size) {

	    var modalInstance = $uibModal.open({
	      backdrop: 'static',
	      keyboard: false,
	      animation: $scope.animationsEnabled,
	      templateUrl: 'pages/template.html',
	      controller: 'ModalInstanceCtrl',
	      size: size,
	      windowTopClass: 'app-modal-window',
	      resolve: {
	        items: function () {
	          return $scope.items;
	        }
	      }
	    });

	    modalInstance.result.then(function () {
	      $log.info('ok');
	    }, function () {
	      $log.info('Modal dismissed at: ' + new Date());
	    });
	  };

//	  $scope.toggleAnimation = function () {
//	    $scope.animationsEnabled = !$scope.animationsEnabled;
//	  };

	});

	// Please note that $modalInstance represents a modal window (instance) dependency.
	// It is not the same as the $uibModal service used above.
	// pasar el rootScope para identificar el módulo y hacer las solicitudes a drools
	App.controller('ModalInstanceCtrl', function ($rootScope, $scope, $uibModalInstance,$http, servicioProgreso, items) {
	  $scope.pasoDrools=items;//"hola";
	    $scope.$watch('pasoDrools', function (newValue, oldValue) {
	        if (newValue !== oldValue) servicioProgreso.setProgreso(newValue);
	    });
	  $scope.ok = function () {
		  console.log('hola: '+$scope.pasoDrools);
			$http({
				  method: 'GET',
				  url: '/web-generales/droolsTest/consultarDrools/'+$scope.pasoDrools
				 // data: {id: 'hola', nombre: 'Dennys'}
				}).then(function successCallback(response) {
					$rootScope.html=response.data;
					console.log('Exitoso: '+response.data);
					$scope.pasoDrools++;
				    // this callback will be called asynchronously
				    // when the response is available
				  }, function errorCallback(response) {
					  console.log("error");
				    // called asynchronously if an error occurs
				    // or server returns response with an error status.
				  });
	    //$uibModalInstance.close();
	  };

	  $scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };
	});
	
	
	
	App.controller('paso1Controller',function(){
		var vm=this;
		vm.paso1=function(){
			alert('Hola paso1');
		}
	});
	App.controller('paso2Controller',function(){
		var vm=this;
		vm.paso1=function(){
			alert('Hola paso2');
		}
	});
	App.controller('progresoController',function($scope,servicioProgreso){
		var vm=this;
		vm.maximo=4;
		vm.valor=1;
	    $scope.$watch(function () { return servicioProgreso.getProgreso(); }, function (newValue, oldValue) {
	        if (newValue !== oldValue) vm.valor = newValue;
	    });
	});
	App.factory('servicioProgreso',function(){
		var progreso;
		setProgreso=function(mProgreso){
			progreso=mProgreso;
		};
		getProgreso=function(){return progreso};
		return {setProgreso: setProgreso, getProgreso: getProgreso};		
	});
	App.controller('formController',function(){
	    var vm = this;
	    vm.model = {};
	    vm.options = {};
	    vm.fields = [
//	                 {
//	                   key: 'firstName',
//	                   type: 'customInput',
//	                   templateOptions: {
//	                     required: true,
//	                     label: 'First Name',
//	                     foo: 'hi'
//	                   }
//	                 },
	                 {
	                   key: 'lastName',
	                   type: 'input',
	                   templateOptions: {
	                     label: 'Last Name'
	                   },
	                   expressionProperties: {
	                     'templateOptions.disabled': '!model.firstName'
	                   }
	                 },
	                 {
	                   key: 'knowIpAddress',
	                   type: 'checkbox',
	                   templateOptions: {
	                     label: 'I know what an IP address is...'
	                   }
	                 },
	                 {
	                   key: 'ipAddress',
	                   type: 'input',
	                   templateOptions: {
	                     label: 'IP Address',
	                     placeholder: '127.0.0.1'
	                   },
	                   hideExpression: '!model.knowIpAddress',
	                   validators: {
	                     ipAddress: {
	                       expression: function(viewValue, modelValue) {
	                         var value = modelValue || viewValue;
	                         return !value || /(\d{1,3}\.){3}\d{1,3}/.test(value);
	                       },
	                       message: '$viewValue + " is not a valid IP Address"'
	                     }
	                   }
	                 }
	               ];

	});