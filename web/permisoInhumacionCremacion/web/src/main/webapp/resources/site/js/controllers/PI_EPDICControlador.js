
App.controller('imprimirEPDIC', function($scope,$http,$uibModal,$rootScope, $sce) {
	console.log("****ENTRO A imprimirEPDIC *****");
	console.log("*****$rootScope.imprimirEPDIC*******_"+$rootScope.imprimirEPDIC);
	
	var vm = this;
	vm.estado = "iniciarTramite";
	//Guarda en un array las vistas presentadas durante el proceso
	vm.vistas = [];
	//arreglo de datos que han sido presentados en pantalla
	vm.modelos = [];
	//representa los datos actualmente presentes en pantalla
	vm.modelo = {};
	vm.titulos = [];
	vm.activo = -1;
	vm.impreso = false;
	

	
	
//	 var constructor = window.location;
//	 constructor.protocol +'//' + constructor.hostname + ((constructor.port != '' )?':'+ constructor.port:'') 
//	 
//	 constructor.protocol +'//' + constructor.hostname + ((constructor.port != '' )?':'+ constructor.port:'') + '/'

    //estado del proceso
    //vm.estado = 'inicializacion';

    /**
     * Maneja la etapa de verificacion de la declaracion jurada
     */
    vm.presentarPdf = function ($scope, vm, response) {
        vm.vista = response.data.vista;
        vm.modelo = response.data.modelo;
        vm.modelo.titulo = $sce.trustAsHtml(vm.modelo.titulo);
    }
//
//    vm.presentarPdf2 = function ($scope, vm, response) {
//        vm.vista = response.data.vista;
//        vm.modelo = response.data.modelo;
//        vm.modelo.mensaje = $sce.trustAsHtml(vm.modelo.titulo);
//
//    }

    vm.abrirmodalConfirmar = function () {
        presentarModal($scope, $uibModal, vm.okRespuesta, '¿Se confirma la impresión?', $rootScope.tituloWizard, 's');
        vm.impreso = false;
    }
    vm.okRespuesta = function () {
        console.log("confirmado");
        vm.estado = "finalizarTramite";
        llamadaAjax($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
    }
    vm.imprimir = function () {

        vm.windowPrintBrowser();

        presentarModal($scope, $uibModal, vm.okF, '¿La impresión es satisfactoria?', $rootScope.tituloWizard, 's');
    }
    vm.okF = function () {
       
        vm.impreso = true;
    }
    vm.finalizar = function () {

        llamadaAjax($http, $scope, vm, '/web-permisoInhumacionCremacion/actualizarEstado', vm.modelo, vm.respuestaActualizar, "POST");
    	
    	$rootScope.imprimirEPDIC = false;
    }

    vm.respuestaActualizar = function () {
        $rootScope.imprimirEPDIC = false;
    }

    vm.estados = {

        iniciarTramite:{ruta:'/web-permisoInhumacionCremacion/presentarImprimir', funcion:vm.presentarPdf, metodo:"POST"},
        finalizarTramite:{ruta:'/web-permisoInhumacionCremacion/presentarSatisfactorio', funcion:vm.presentarPdf, metodo:"POST"}//,

    };

    //Datos a ser enviados al momento de cargar el modal
    vm.modelo = {id:$rootScope.objectSelected.numeroSolicitud, tramite:$rootScope.objectSelected.tramite.codigo, statu:$rootScope.objectSelected.codigoEstadoSolicitud};

    if (vm.modelo.tramite == "EPDIC") {
        vm.modelo.tramite =  "Registrar permiso de inhumaci&#243n y/o cremaci&#243n";
    }
    if (vm.modelo.statu == "PI") {
        vm.modelo.statu = "Pendiente por imprimir";
    }

    llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);

    vm.siguiente = function () {

        if (vm.impreso) {
            vm.abrirmodalConfirmar();
            return;
        }
        llamadaAjax2($http, $scope, vm, vm.estados[vm.estado].ruta, vm.modelo, vm.estados[vm.estado].funcion, vm.estados[vm.estado].metodo);
    }


    //cierra wizard desde el boton x
    vm.cancel = function () {
        $rootScope.cancelar1();
    }
    //cierra wizard desde el boton cancelar
    vm.cancelar = function () {
        $rootScope.cancelar1();
    }

    vm.windowPrintBrowser = function () {

        document.getElementById("plugin").focus();

        document.getElementById("plugin").contentWindow.print();


    }


})
