App.controller("formController",function($scope,$http,$rootScope,$uibModal, $log, registrarRutasService){
	var vm=this;
	//Lista de pasos del proceso
	vm.pasos=[];
	//Resaltador del paso actual del proceso
	vm.activo="0";
	$rootScope.habilitarRadio = true;
	vm.removerClaseParticipantes=false;
	//Presenta la barra de progreso
	vm.enProgreso=false;
	//campos del formulario
	vm.userFields =[];
	//modelo asociado al formulario
	vm.user={};
	//guarda el formulario y el modelo asociado para regresar a la vista anterior sin consultas ajax
	vm.formularios=[];
	//guarda los modelos asociados a cada formulario para presentar las opciones seleccionadas por el usuario
	//en caso de que se presione la tecla anterior
	vm.modelos=[];
	vm.varias=false;
	vm.permiteVarias=true;
	//variable que se utiliza en caso de que alguna de las preguntas de Drools dependa de la anterior inmediata
	vm.memoria=false
	$rootScope.falloAutenticacion=false;
	vm.enCC = false;
	//Numero de miembros del consejo comunal
	vm.nMCC = 0;
	$rootScope.nMCC = 0;
	
	//Carga la vista inicial al seleccionar la solicitud desde la lista
	$http({
		method: 'POST',
		//url: '/web-autenticarCiudadano/iniciarDeclaracion',//'/web-autenticarCiudadano/iniciarAutenticar',
		//url: '/web-autenticarCiudadano/iniciarTramite',
		url: registrarRutasService.propiedades['sarc.web.autenticarCiudadano.iniciarTramite.url'],
		data:{id:$rootScope.objectSelected.numeroSolicitud}
	}).then(function successCallback(response) {
		//verifica la existencia de funciones en el objeto
		vm.loadFunciones(response.data.fields);
		vm.userFields=response.data.fields;
		vm.user=response.data.modelo;
		vm.pasos.push(response.data.modelo.titulo);
		//vm.activo=response.data.modelo.activo;
	}, function errorCallback(response) {
		console.log("error");
	});

	vm.finalizarYactualizar = function(){
		llamadaAjax1($http, $scope, vm,registrarRutasService.propiedades['sarc.web.autenticarCiudadano.cambiarEstado.url'], {mEstado : "PENDIENTE"}, function($scope,vm,response){vm.userFields=response.data.fields;
		vm.user=response.data.modelo;}, "POST");
	}
	
	//función asociada al boton "Siguiente" 
	$scope.$root.submit=function(event){
		
		if(vm.user.TipoOtroDeclarante === 'CPNA'){
			presentarModal1($scope,vm,$uibModal,vm.finalizarYactualizar,'No se autenticar\u00e1 ning\u00fan participante. Se proceder\u00e1 a finalizar el proceso para realizar el registro de nacimiento \u00bfDesea continuar?',$rootScope.tituloWizard,'s');
			return;
		}
		$rootScope.habilitarRadio=true;
		if($rootScope.generarAlerta){
			//alert('Alerta');
			$rootScope.generarAlerta = false;
			presentarModal1($scope,vm,$uibModal,vm.finalizarYactualizar,'No se autenticar\u00e1 ning\u00fan participante. Se proceder\u00e1 a finalizar el proceso para realizar el registro de nacimiento \u00bfDesea continuar?',$rootScope.tituloWizard,'s');
//			$rootScope.openDroolsModal();
			return;
		}
		/**
		 * Se verifica el numero de consultas consecutivas a drools en caso de de que exista mas de una pregunta en la misma 
		 * vista
		 */
		var nConsultas=0;
		$rootScope.presentarAlerta;
		if(event!=undefined){
			for(i=0;i<vm.userFields.length;i++)
				if(vm.userFields[i].type === "radioSiNoAutoSubmitHorizontal"
					|| vm.userFields[i].type === "radioSiNoAutoSubmit")
					nConsultas++;
		}else{
			nConsultas=1;
		}
		if(nConsultas===0)
			nConsultas=1;
		vm.enProgreso=true;
		var datos={};
		datos=vm.user;
		if($rootScope.presentarAlerta){
			$rootScope.openDroolsModal();
			return;
		}
		//var urlConsulta='/web-autenticarCiudadano/consultarDrools/';
		var urlConsulta=registrarRutasService.propiedades['sarc.web.autenticarCiudadano.consultarDrools.url'];
		
		/**Inicia la verificación de la etapa en la cual se encuentra actualmente el proceso
		 * y se establece la ruta a la cual se realizara el llamado ajax se conocen tres etapas: 
		 * 1. Consultas a drools
		 * 2. Autenticacion de ciudadanos
		 * 3. Datos de declaracion jurada
		 */
		
		if(vm.user.guardar){
			//urlConsulta='/web-autenticarCiudadano/insertarParticipantes/';
			urlConsulta=registrarRutasService.propiedades['sarc.web.autenticarCiudadano.insertarParticipantes.url'];
			
			/*$http({
				method: 'POST',
				url: urlConsulta,
				data:datos
			}).then(
				//Inicia el procesamiento de la respuesta ajax	
					function successCallback(response) {
				//oculta la barra de progreso
				vm.enProgreso=false;
				//Si se presenta un error lo muestra en pantalla y termina el proceso
				if((response.data.modelo!=null)&&(response.data.modelo.error==true)){
					vm.userFields=response.data.fields;
					return
				}
				if(response.data.modelo.enCC !== undefined)
					vm.enCC = true;
				//Presenta un mensaje enviado durante la etapa de consulta a Drools
				if(response.data.modelo.errorDrools){
					$rootScope.openDroolsModal();
				}
				
				
				//Si no se ha producido un error en la etapa de autenticacion de ciudadanos
				//guarda los formularios que se han presentado en pantalla para poder mostrarlos
				//cuando se presione el botón anterior
				if(!response.data.modelo.falloAutenticacion && response.data.fields !== undefined){	
					var tempFields=[];
					for(i=0;i<vm.userFields.length;i++)
						tempFields.push(vm.userFields[i]);
					
					//guarda en la pila de formularios y modelos consultados
					vm.formularios.push(tempFields);
					tempFields=[];
					var tmpModel={};
					for(var key in vm.user){
						tmpModel[key]=vm.user[key];
					}
					vm.modelos.push(tmpModel);
					tmpModel={};
				}	
				
				//identifica el titulo de la etapa del proceso y el indice activo para 
				//resaltarlo con el color azul
				vm.activo = vm.pasos.indexOf(response.data.modelo.titulo);
				if(vm.activo == -1){
					vm.pasos.push(response.data.modelo.titulo);
					vm.activo=vm.pasos.length-1;
				}
				vm.user.ancho=response.data.modelo.ancho;
				vm.user.ocultarLapiz=response.data.modelo.ocultarLapiz;
				//identifica funciones como controllers y link en la respuesta ajax
				//if(!response.data.modelo.guardar)
				vm.loadFunciones(response.data.fields);
				
				//flag que indica si la pregunta recibida desde drools debe ser anexada 
				//a la anterior
				vm.varias=response.data.modelo.varias;
				vm.permiteVarias=vm.user.permiteVarias;
				//Verifica si se debe o no cargar varias preguntas a la vez
				if(vm.varias && vm.permiteVarias){
					for(i=0;i<response.data.fields.length;i++)
						vm.userFields.push(response.data.fields[i]);
					for(var key in response.data.modelo)
						vm.user[key]=response.data.modelo[key];
				}			
				//Verifica si la pregunta actual depende de la anterior
				else if(response.data.modelo.memoria){
					for(var key in response.data.modelo)
						vm.user[key]=response.data.modelo[key];				
					vm.userFields=response.data.fields;
				}
				else{
					vm.user=response.data.modelo;
					if(response.data.fields !== undefined)
						vm.userFields=response.data.fields;
				}			
				$rootScope.falloAutenticacion=response.data.modelo.falloAutenticacion;	
				vm.user.guardar = false;
				
			}, function errorCallback(response) {
				vm.enProgreso=false;
				console.log("error");
			});*/
		}
		else if(vm.user.autenticar){
			datos={};
			if(vm.user.final_etapa){

				urlConsulta=registrarRutasService.propiedades['sarc.web.autenticarCiudadano.iniciarAutenticar.url'];
			}else{
				//urlConsulta='/web-autenticarCiudadano/validarParticipante/';
				urlConsulta=registrarRutasService.propiedades['sarc.web.autenticarCiudadano.validarParticipante.url'];
				//carga los datos del formulario en forma de un array para enviarlo a la 
				vm.loadModel(datos);
			}
		}
		else if(vm.user.a_declaracion){
			datos={};
			if(vm.user.final_etapa){
				//urlConsulta='/web-autenticarCiudadano/iniciarDeclaracion/';
				urlConsulta=registrarRutasService.propiedades['sarc.web.autenticarCiudadano.iniciarDeclaracion.url'];
			}else{
				vm.loadModel(datos);
				//urlConsulta='/web-autenticarCiudadano/datosDeclaracion/';
				urlConsulta=registrarRutasService.propiedades['sarc.web.autenticarCiudadano.datosDeclaracion.url'];
			}
		}else if(vm.user.aCC){
			datos={};
			vm.loadModel(datos);
			datos.nMCC = vm.nMCC;
			if(vm.enCC === false || vm.enCC === undefined){
				//urlConsulta='/web-autenticarCiudadano/retornarAcc';
				urlConsulta=registrarRutasService.propiedades['sarc.web.autenticarCiudadano.retornarAcc.url'];
				//vm.user.enCC = true;
			}else{
				//urlConsulta='/web-autenticarCiudadano/datosCC/';
				urlConsulta=registrarRutasService.propiedades['sarc.web.autenticarCiudadano.datosCC.url'];
			}
		}
		//fin de la verificación de etapa
//fin de la verificación de etapa
		datos.nConsultas=nConsultas;		
		/**
		 * Se realiza la peticion ajax a la ruta definida en la identificacion de etapa
		 */
		$http({
			method: vm.user.guardar || urlConsulta === registrarRutasService.propiedades['sarc.web.autenticarCiudadano.retornarAcc.url'] ? 'GET' : 'POST',
			url: urlConsulta,
			data:datos
		}).then(
			//Inicia el procesamiento de la respuesta ajax	
				function successCallback(response) {
			//oculta la barra de progreso
			vm.enProgreso=false;
			//Si se presenta un error lo muestra en pantalla y termina el proceso
			if((response.data.modelo!=null)&&(response.data.modelo.error==true)){
				vm.userFields=response.data.fields;
				return
			}
			if(response.data.modelo.enCC !== undefined)
				vm.enCC = true;
			//Presenta un mensaje enviado durante la etapa de consulta a Drools
			if(response.data.modelo.errorDrools){
				$rootScope.openDroolsModal();
			}
			
			
			//Si no se ha producido un error en la etapa de autenticacion de ciudadanos
			//guarda los formularios que se han presentado en pantalla para poder mostrarlos
			//cuando se presione el botón anterior
			if(!response.data.modelo.falloAutenticacion && response.data.fields !== undefined){	
				var tempFields=[];
				for(i=0;i<vm.userFields.length;i++)
					tempFields.push(vm.userFields[i]);
				
				//guarda en la pila de formularios y modelos consultados
				vm.formularios.push(tempFields);
				tempFields=[];
				var tmpModel={};
				for(var key in vm.user){
					tmpModel[key]=vm.user[key];
				}
				vm.modelos.push(tmpModel);
				tmpModel={};
			}	
			
			//identifica el titulo de la etapa del proceso y el indice activo para 
			//resaltarlo con el color azul
			vm.activo = vm.pasos.indexOf(response.data.modelo.titulo);
			if(vm.activo == -1){
				vm.pasos.push(response.data.modelo.titulo);
				vm.activo=vm.pasos.length-1;
			}
			vm.user.ancho=response.data.modelo.ancho;
			vm.user.ocultarLapiz=response.data.modelo.ocultarLapiz;
			//identifica funciones como controllers y link en la respuesta ajax
			//if(!response.data.modelo.guardar)
			vm.loadFunciones(response.data.fields);
			
			//flag que indica si la pregunta recibida desde drools debe ser anexada 
			//a la anterior
			vm.varias=response.data.modelo.varias;
			vm.permiteVarias=vm.user.permiteVarias;
			//Verifica si se debe o no cargar varias preguntas a la vez
			if(vm.varias && vm.permiteVarias){
				for(i=0;i<response.data.fields.length;i++)
					vm.userFields.push(response.data.fields[i]);
				for(var key in response.data.modelo)
					vm.user[key]=response.data.modelo[key];
			}			
			//Verifica si la pregunta actual depende de la anterior
			else if(response.data.modelo.memoria){
				for(var key in response.data.modelo)
					vm.user[key]=response.data.modelo[key];				
				vm.userFields=response.data.fields;
			}
			else{
				vm.user=response.data.modelo;
				if(response.data.fields !== undefined)
					vm.userFields=response.data.fields;
			}			
			$rootScope.falloAutenticacion=response.data.modelo.falloAutenticacion;						
		}, function errorCallback(response) {
			vm.enProgreso=false;
			console.log("error");
		});
	}

	$scope.$root.anterior=function(event){
		if(event !== undefined){
			$rootScope.habilitarRadio = false;
		}
		
		vm.userFields=vm.formularios[vm.formularios.length-1];
		vm.user=vm.modelos[vm.modelos.length-1];
		//Elimina los titulos presentados en el menu
		//identifica el titulo de la etapa del proceso y el indice activo para 
		//resaltarlo con el color azul
		vm.activo = vm.pasos.indexOf(vm.user.titulo);
		if(vm.activo < vm.pasos.length-1){
			vm.pasos.splice(vm.activo+1, vm.pasos.length-vm.activo-1);
		}
		vm.activo = vm.pasos.indexOf(vm.user.titulo);		
		
		var formRemovido=vm.formularios.splice(vm.formularios.length-1,1);
		var datosRemovidos=[];
		datosRemovidos=vm.modelos.splice(vm.modelos.length-1,1);
		
		//TODO INCLUIR TODOS LOS CAMBIOS QUE INCLUYAN UN BOTON DE AUTOSUBMIT
		//Habilita nuevamente el avance a través delbotón sub mit
		//en esta seccion se corrije el error de quedar en true cuando se regresa a traves del 
		var tipo=vm.userFields[vm.userFields.length-2].type;
		if(vm.userFields[vm.userFields.length-2].type==="radioSiNoAutoSubmit" ||  vm.userFields[vm.userFields.length-2].type==="radioSiNoAutoSubmitHorizontal" || vm.userFields[vm.userFields.length-2].type==="validarParticipantes" || vm.userFields[vm.userFields.length-2].type==="validarConyugue"){
			vm.userFields[vm.userFields.length-2].data.habilitado=true;
			if(vm.userFields[vm.userFields.length-2].data.regresarEnFalso){
				vm.user[vm.userFields[vm.userFields.length-2].key]="false";				
			}
		}
		
		
		vm.user.guardar=false;
		if(!(datosRemovidos[0].autenticar) && !(datosRemovidos[0].aCC) && !(datosRemovidos[0].a_declaracion)){		
			//murl= '/web-autenticarCiudadano/consultarDroolsAtras';
			murl= registrarRutasService.propiedades['sarc.web.autenticarCiudadano.consultarDroolsAtras.url'];
			$http({
				method: 'GET',
				url: murl
			}).then(function successCallback(response){
			}, function errorCallback(response){
				console.log("error 2");
			});		 
		}
	}

	//modal de cancelar
	$rootScope.open = function (size) {
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: '/web-autenticarCiudadano/pages/myModalContent.html',
			controller: 'ModalInstanceCtrl2',
			size: size,
		});
	};

	//Inicia el Modal cuando no existe participante
	$rootScope.openDroolsModal = function (size) {
		var modalInstance2 = $uibModal.open({
			animation: true,
			templateUrl: '/web-autenticarCiudadano/pages/sinAutenticar.html',
			controller: 'ModalInstanceCtrlSinPart',
			size: size,
		});
	};

	/**
	 * Carga los datos del modelo en el objeto que recibe como parametro
	 */
	vm.loadModel=function(datos){
		for(var name in vm.user){
			var objeto = vm.user[name];
			if(typeof(objeto)=='object')
				for(var name2 in objeto){
					var valor = objeto[name2];
					if(valor!=undefined){
						datos[name+'_'+name2] = valor;
						console.log("clave: "+name+'_'+name2);
						console.log("Valor: "+valor);
					}
				}
			else
				datos[name]=objeto;
		}		
	}
	
	/**
	 * Convierte cadenas de texto a funciones
	 */
	vm.loadFunciones=function(campos){
		for(i=0;i<campos.length;i++){
			for(var key in campos[i]){
				console.log("campo "+i+" "+key);
				if(key==="controller" && campos[i].controller.indexOf('function')!==-1){
					myFunc=campos[i].controller;
					funcion = new Function("return " + myFunc)();
					campos[i].controller=funcion;
				}
				if(key==="parsers"){
					myFunc=campos[i].parsers[0];
					funcion = new Function("return " + myFunc)();
					campos[i].parsers[0]=funcion;
				}
				if(key==="templateOptions"){
					for(var key2 in campos[i].templateOptions){
						if(key2==="onKeypress"){
							myFunc=campos[i].templateOptions.onKeypress;
							funcion = new Function("return " + myFunc)();
							campos[i].templateOptions.onKeypress=funcion;								
						}
						if(key2==="onClick"){
							myFunc=campos[i].templateOptions.onClick;
							funcion = new Function("return " + myFunc)();
							campos[i].templateOptions.onClick=funcion;								
						}
					}
				}
				if(key==="watcher"){
					for(var key2 in campos[i].watcher){
						if(key2==="listener"){
							myFunc=campos[i].watcher.listener;
							funcion = new Function("return " + myFunc)();
							campos[i].watcher.listener=funcion;								
						}
					}
				}
			}
		}
		console.log('fin');
	}
	
	vm.cancelarModal=function(){
		vm.modalInstance2.dismiss();
	}
	
	$scope.agregarMiembro = function(){
		vm.nMCC++;
		$rootScope.nMCC++;
		$http({
			method: 'POST',
			//url: '/web-autenticarCiudadano/miembroCCadicional',
			url: registrarRutasService.propiedades['sarc.web.autenticarCiudadano.miembroCCadicional.url'],
			data : {nMCC : vm.nMCC}
		}).then(function successCallback(response) {
			vm.loadFunciones(response.data.fields);
			for(var i=0; i < response.data.fields.length; i++)
				vm.userFields.push(response.data.fields[i]);
		}, function errorCallback(response) {
			console.log("error");
		});
		
	}

	
});	


App.controller('controladorFormly',function($scope){
	console.log('activo desde el controlador Formly');
});

App.controller('ModalInstanceCtrl2', function ($rootScope,$scope, $uibModalInstance) {
	$scope.ok = function () {
		//modalFactory.setCancelar(true);
		$uibModalInstance.close($scope.selected);
		console.log('close');
		$rootScope.cancelar1();
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};

});

App.controller('ModalInstanceCtrlSinPart', function($scope,$uibModalInstance,$http,$rootScope,$uibModal,registrarRutasService) {
	$scope.ok = function (parametro){
		//resetea el flag de generacion de alerta
		$rootScope.generarAlerta = false;
			$http({
				method: 'GET',
				//url: '/web-autenticarCiudadano/cambiarStatus/'+parametro,
				url: registrarRutasService.propiedades['sarc.web.autenticarCiudadano.cambiarStatus.url']+parametro,
				data: {id:$rootScope.objectSelected.numeroSolicitud}
			}).then(function successCallback(response) {

				console.log(response.data);

			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});//Fin llamada ajax	
			$uibModalInstance.close($scope.selected);
			$rootScope.cancelar1();		
	}
	$scope.cancel = function (parametro) {
		$uibModalInstance.dismiss('cancel');
	}
});

