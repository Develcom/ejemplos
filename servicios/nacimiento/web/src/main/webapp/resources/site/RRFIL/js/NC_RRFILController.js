App.controller('NC_RRFILControlador', function ($scope, $http, $uibModal, $rootScope, validaciones) {
    console.log("*********controler---");
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
    vm.actaPrimigenia = false;
    //$scope.pais1={};
    //$scope.estado1={};
    //$scope.parroquia1={};
    //$scope.municipio1={};
    vm.estado = "iniciarTramite";
    //estado del proceso
    // vm.estado = 'iniciarTramiteEDPIC';
    /**
     *identifica el titulo de la etapa del proceso y el indice activo para
     *resaltarlo con el color azul
     **/
	$scope.vPatron = {
	        AlfaNumerico: /^[A-Za-z0-9]*$/,
			Alfa: /^[a-zA-Z]*$/,
			FormatoNombres: /^((([a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
			//FormatoNombres:  /^[a-zA-Z ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ'-]*$/,
			//FormatoCementerio:  /^[a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ'-]*$/,
			FormatoCementerio: /^((([a-zA-Z0-9 ñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ]))|([-'])\1?(?!\4))+$/,
			AlfaText: 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones y acentos',
			Pcementerio: 'Este campo solo permite letras, di&eacute;resis, ap&oacute;strofes, guiones, acentos y n&uacute;meros',
			FnumeroCertf: 'Este campo admite solo n&uacute;meros',
			TipoCed: /^[V|v|E|e]/,
			Cedula: /^[0-9]/,
			cedulaMaxLength: "9",
			cedulaMinLength: "6",
			nombreMaxlength: "50",
			nombreMinlength: "2",
			FormatoFolio: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 5 caracteres y un m&aacute;ximo de 5 caracteres",
			FormatoActa: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 20 caracteres y un m&aacute;ximo de 20 caracteres",
			FormatoTomo: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 2 caracteres y un m&aacute;ximo de 2 caracteres",
			CedulaText: "Este campo permite solo n&uacute;meros, con un m&iacute;nimo de 6 caracteres y un m&aacute;ximo de 9 caracteres",
			NumeroDocumento: /^[0-9]{6,11}$/,
			excepciones : ['Backspace', 'Tab', ' ', 'ArrowLeft', 'ArrowRight', 'Delete', 'Caps Lock', 'Shift', 'Control'],
			capTuraEvento : function(event, patron){
				
			}
		
	      };
    
    
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
    	console.log("---presentarVista1---"+ vm.actaPrimigenia);
    	vm.vista = response.data.vista;
        vm.modelo = response.data.modelo;

        vm.presentarTitulo(vm.modelo.titulo);
        vm.estado = "datosActa";

        

    }
    vm.presentarVista2 = function ($scope, vm, response) {
    	console.log("---presentarVista2---" +  vm.actaPrimigenia);
        vm.vista = response.data.vista;
        vm.modelo = response.data.modelo;
        vm.modelo.titulo = (response.data.modelo.titulo);
        console.log("-------FECHA MODELO----------> " + vm.modelo.fecha);
        
        $scope.funcionSeteoDataCombo(vm.modelo.nombreOficina);
        
        


        vm.presentarTitulo(vm.modelo.titulo);
        //$scope.buscarPais();
        vm.actaPrimigenia = true;
       
        
    }
    
    vm.presentarVista3 = function ($scope, vm, response) {
    	console.log("---presentarVista3---" +  vm.actaPrimigenia);
    	vm.vista = response.data.vista;
        vm.modelo = response.data.modelo;
        vm.modelo.titulo = (response.data.modelo.titulo);
        
        vm.actaPrimigenia = false;

        vm.presentarTitulo(vm.modelo.titulo);
        //$scope.buscarPais();
        vm.guardarDatosActa();
         
       
    }
    
    vm.guardarDatosActa = function(){
   	 console.log("----ACTUALIZAR ESTADO----");
   	 llamadaAjax2($http, $scope, vm, '/web-nacimiento/guardarDatosActaRRFIL', vm.modelo, vm.cambiarEstado, "POST");	 

    }
    vm.cambiarEstado = function(){
   	 vm.estado = "actualizarEstado";	 

    }
//

    vm.finalizar = function () {
        console.log("fin");
        vm.cancelar();
    }
//	 vm.resolve = function (){
//			return numCertificado;
//	 }
    
    
    
    vm.abrirModalActa =function(){ 
    	  var modalInstance = $uibModal.open({
    	   templateUrl: '/web-generales/pages/templates/imprimir/modalConfirmacion.html',
    	   controller: function($scope,$uibModalInstance){
    	    $scope.tipo = 's';
    	     $scope.mensaje='Los datos del acta no fueron encontrados. El acta primigenia ha sido cambiada a estatus “Sin Efecto“. ¿Desea editar la información suministrada? ';
    	    $scope.titulo = $scope.tituloWizard;;
    	   
    	     $scope.cancel = function () {
    	            $uibModalInstance.dismiss('cancel');
    	            
    	            vm.validarRecaudo();
    	           
    	        };
    	        $scope.ok = function () {
    	             $uibModalInstance.close(true);
//    	             llamadaAjax2($http, $scope, vm,
//    	         '/web-nacimiento/guardarActaPrim', vm.modelo, vm.f, "POST");
//    	             vm.otraModalActa();
    	            
    	        };
    	        $scope.cancelar = function () {
    	            $uibModalInstance.close(true);
    	           
    	           // vm.volverActa();
    	        }
    	   }
    	  });
    	  modalInstance.result.then(function () {
    	 
    	  }, function () {
    		  vm.validarRecaudo();
    		  console.log("************VENGO DE CANCELAR");
   
    	   
    	  });
    	  
    	 }
    
    
    vm.actaPrimigeniaNoEncontrada = function(){
   	 console.log("----MODAL----");
   	 
   	 vm.abrirModalActa();

   	// vm.modalOkError($scope,$uibModal,vm.okModalActa,vm.errorFuncion,'Los datos del acta no fueron encontrados. El acta primigenia ha sido cambiada a estatus “Sin Efecto“. ¿Desea editar la información suministrada?',$rootScope.tituloWizard,'s');
   	// vm.modalOkError('Los datos del acta no fueron encontrados. El acta primigenia ha sido cambiada a estatus “Sin Efecto“. ¿Desea editar la información suministrada?',$rootScope.tituloWizard,'s');
   	 	 
   	 }


	// vm.abrirModalConforme = function(){presentarModal($scope,$uibModal, vm.iniciarTramite,'Los datos del acta no fueron encontrados.El acata primigenia Ha sido cambiada a Status "Sin Efecto" ¿Desea editar la informacion suministrada?',$rootScope.tituloWizard,'s');}
    
	 
	 
     vm.abrirModalActualizar = function(){ presentarModal($scope,$uibModal,vm.okActualizar,'Verificaci?n de oficio exitoso',$rootScope.tituloWizard,'c'); }
    
     vm.okActualizar = function(){ llamadaAjax($http, $scope, vm, '/web-nacimiento/actualizarEstadoRRFIL', vm.modelo, vm.finalizar, "POST");}
     
     vm.validarRecaudo = function(){ llamadaAjax($http, $scope, vm, '/web-nacimiento/RRFIL_validarRecaudo', vm.modelo, vm.presentarVista3, "POST");}
   
     vm.finalizar = function () {$rootScope.cancelar1();}
   
     vm.confirmarSalidaModulo = function () {presentarModal($scope, $uibModal, vm.salirModulo, '¿Desea cancelar la solicitud?', $rootScope.tituloWizard, 's');  }
   
     vm.salirModulo = function () { $rootScope.cancelar1();   }

    /**
     * Carga los datos iniciales del proceso
     */
    vm.estados = {
        iniciarTramite:{ruta:'/web-nacimiento/iniciarRRFIL', funcion:vm.presentarVista, metodo:'POST'},
        datosActa:{ruta:'/web-nacimiento/RRFIL_datosActa', funcion:vm.presentarVista2, metodo:'POST'},
        actualizarEstado: {ruta : '/web-nacimiento/actualizarEstadoRRFIL', funcion : vm.finalizar, metodo : "POST"}
        //validarRecaudo:{ruta:'/web-nacimiento/RRFIL_validarRecaudo', funcion:vm.presentarVista2, metodo:'POST'}
    };

    //Datos a ser enviados al momento de cargar el modal
    vm.modelo = {id:$rootScope.objectSelected.numeroSolicitud, paso:0, statu:$rootScope.objectSelected.codigoEstadoSolicitud};
    llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);

    vm.siguiente = function () {
        console.log(vm.paso);
        vm.paso++;
        //guarda en la pila la vista actual
        vm.vistas.push(vm.vista);
        vm.modelos.push(vm.modelo);
        
        
        if(vm.actaPrimigenia){
           vm.consultaActa();
             // vm.presentarVista =true;
          
        	//vm.abrirModalActualizar();
           return;
        }
        
        llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
    }


    //cierra wizard desde el boton x
    vm.cancel = function () {
        vm.confirmarSalidaModulo();
    }
    //cierra wizard desde el boton cancelar
    vm.cancelar = function () {
        vm.confirmarSalidaModulo();
    }
    
    vm.consultaActa = function(){
   	 
   	 console.log("------ENTRO A CONSULTAR ACTA----");
   	   $http({
   	    method: 'GET',
   	    url: '/web-nacimiento/existeActaRRFIL/'+vm.modelo.n_acta
   	   }).then(function successCallback(n_acta) {
   		   
   		   console.log("----n_acta---- " + n_acta.data);
   		   
   		   if(n_acta.data == "0"){
   			   vm.actaPrimigeniaNoEncontrada();
   		   }else{
   			   console.log("-----NO ES CERO----");
   		   }
   	    
   	   
   	   }, function errorCallback(error) {
   	    console.log("error: "+error.data); 
   	   });
   	  }

    //boton regresar
    vm.regresar = function () {
        vm.confirmarDatosForm = false;
        $scope.campoObservacion = false;


        vm.vista = vm.vistas[vm.vistas.length - 1];
        vm.modelo = vm.modelos[vm.modelos.length - 1];
        vm.modelos.splice(vm.modelos.length - 1, 1);
        vm.vistas.splice(vm.vistas.length - 1, 1);
        vm.disabled = vm.vistas.length == 0;
        vm.disabled = vm.vistas.length == 0;
        vm.paso--;
        console.log("****before: " + vm.activo);
        vm.activo = vm.titulos.indexOf(vm.modelo.titulo);
        if (vm.activo < vm.titulos.length) {
            vm.titulos.splice(vm.activo + 1, vm.titulos.length
                - vm.activo - 1);
        }
        vm.activo = vm.titulos.indexOf(vm.modelo.titulo);
    }

    vm.onKeyDown = function (event, validacion, id) {
        var excepciones = validaciones[validacion].excepciones;
        for (var i = 0; i < excepciones.length; i++) {
            if (event.key === excepciones[i])
                return;
        }
        var patron = new RegExp(validaciones[validacion].expReg);
        if (!patron.test(event.key)) {
            event.preventDefault();
            vm.errorTecla[id] = true;
            return;
        }
        vm.errorTecla[id] = false;
    }

    vm.onBlur = function () {
        vm.errorTecla[id] = false;
    }

    $scope.buscarPais = function () {

        $http({
            method:'POST',
            url:'/web-generales/consultarPaisTodos',
            data:{}
        }).then(function successCallback(pais) {
                $scope.paises = pais.data;
                $scope.estados = $scope.paises.estados;
            }, function errorCallback(error) {
                console.log("error: " + error.data);
            });

    }
    $scope.buscarPais();

    $scope.buscarEstado = function () {
        if ($scope.pais1.codigo == "VEN") {
            $http({
                method:'POST',
                url:'/web-generales/consultarEstadoPorPais',
                data:{codigo:$scope.pais1.codigo}

            }).then(function successCallback(estado) {
                    $scope.disabled = false;
                    $scope.estados = estado.data;
                    $scope.municipios = $scope.estados.municipios;
                }, function errorCallback(error) {
                    console.log("error: " + error.data);
                });
        } else {
            $scope.disabled = true;
        }
    }
    $scope.buscarMunicipio = function () {

        $http({
            method:'POST',
            url:'/web-generales/consultarMunicipioPorEstado',
            data:{codigo:$scope.estado1.codigo}
        }).then(function successCallback(municipio) {
                $scope.municipios = municipio.data;
                $scope.parroquias = $scope.municipios.parroquias;
            }, function errorCallback(error) {
                console.log("error: " + error.data);
            });

    }
    $scope.buscarParroquia = function () {

        $http({
            method:'POST',
            url:'/web-generales/consultarParroquiaPorMunicipio',
            data:{codigo:$scope.municipio1.codigo}
        }).then(function successCallback(parroquia) {
                $scope.parroquias = parroquia.data;
                //$scope.parroquias=$scope.municipios.parroquias;
            }, function errorCallback(error) {
                console.log("error: " + error.data);
            });

    }
    /*
    $scope.buscarOficinas = function () {

        $http({
            method:'POST',
            url:'/web-generales/consultarOficinasTodos',
            data:{}
        }).then(function successCallback(oficina) {
                $scope.oficinas = oficina.data;
                //$scope.estados=$scope.paises.estados;
            }, function errorCallback(error) {
                console.log("error: " + error.data);
            });

    }
    $scope.buscarOficinas();*/
    
    $scope.onKeyDownAlfa = function(event, validacion, id){
		   var excepciones = $scope.vPatron.excepciones;
		   for(var i = 0; i < excepciones.length; i++){
		    if (event.key === excepciones[i])
		    return;
		   }
		   var patron = $scope.vPatron.FormatoNombres;
		   if (!patron.test(event.key)) {
		    event.preventDefault();
		    $scope.errorTecla[id] = true;
		    return;
		   }
		   $scope.errorTecla[id]= false;
	}
	
	$scope.onKeyDownCedula = function(event, validacion, id){
		   var excepciones = $scope.vPatron.excepciones;
		   for(var i = 0; i < excepciones.length; i++){
		    if (event.key === excepciones[i])
		    return;
		   }
		   var patron = $scope.vPatron.Cedula;
		   if (!patron.test(event.key)) {
		    event.preventDefault();
		    $scope.errorTecla[id] = true;
		    return;
		   }
		   $scope.errorTecla[id]= false;
	}
	
	 $scope.buscarOficinas = function(){
			
			$http({
				method: 'POST',
				url: '/web-generales/consultarOficinasTodos',
				data:{}
			}).then(function successCallback(oficina) {
				$scope.oficinas = oficina.data;
				//$scope.estados=$scope.paises.estados;
			}, function errorCallback(error) {
				console.log("error: "+error.data);
			});

		}
	 $scope.buscarOficinas();
	 
	    $scope.funcionSeteoDataCombo = function (parametro) {
	        var nombreOficina = null;

	        nombreOficina = parametro;
	        console.log("------------>vm.modelo.nombreOficina "+vm.modelo.oficina);
	            $scope.nomOficina = vm.modelo.oficina;
	            $http({
	                method:'POST',
	                url:'/web-generales/consultarOficinasTodos'

	            }).then(function successCallback(oficina) {
	                    console.log(oficina.data);
	                    $scope.oficinas = oficina.data;
	                   
	                    for (var i = 0; i < $scope.oficinas.length; i++) {
	             
	                        if ($scope.oficinas[i].nombre == $scope.nomOficina) {

	                            $scope.nOficina = $scope.oficinas[i].nombre;
	                            console.log("-------->$scope.nOficina "+$scope.nOficina);
	                            $scope.cOficina = $scope.oficinas[i].codigo;
	                            console.log("-------->$scope.cOficina "+$scope.cOficina);
	                            //vm.modelo.PermisoInhCre.municipio = $scope.mMunicipio;
	                            vm.modelo.oficina = $scope.nOficina;
	                            //console.log("---municipioooo seteado pal combo---- " + mun.cMunicipio);
	                            //console.log("---codigo mun seteado pal combo---- " + cMunicipio);
	                            break;
	                        }

	                    }
	                    

	                }, function errorCallback(error) {
	                    
	                });

	        }
	    


})