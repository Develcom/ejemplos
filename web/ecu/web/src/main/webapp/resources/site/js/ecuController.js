App.controller("gestionECU", function ($rootScope, $scope, $http, $location, $state, $stateParams, $window, $route, $uibModal, registrarRutasService) {
    var vm = this;
    $scope.heightPerformPeticionDatos = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height()) / 1.4) + 'px;';
    $scope.panel = 0;
    $scope.blnEntidad = "ciudadanoDiv";
    var elm = "ciudadanoDiv";
    $scope.elm = elm;
    var metodo = "POST";
    var urlContext = "/web-atencionComunitaria/atComun/" + elm;
    var performFunction = functionDataSolicitante;
    var datos = {};
    //genericAjax($http, $state, $stateParams, $scope, metodo, urlContext, datos, performFunction);

    $scope.vPatron = {
        AlfaNumerico: /^[A-Za-z0-9]*$/,
        Alfa: /^[a-zA-Z]*$/,
        FormatoNombres: /^[A-Za-z0-9\-.'\x7f-\xff\s]{0,50}$/i,
        AlfaText: 'Este Campo Solo Permite Letras, Di&eacute;resis, Ap&oacute;strofes, Guiones y Acentos',
        TipoCed: /^[V|v|E|e]/,
        Cedula: /^[0-9]{6,9}$/,
        CedulaText: "Este Campo Permite Solo N&uacute;meros, con un M&iacute;nimo de 6 Caracteres y un M&aacute;ximo de 9 Caracteres <br/> Ejemplo: 12345678",
        NumeroDocumento: /^[0-9]{6,11}$/,
        NumeroDocumentoText: "Este Campo Permite Solo N&uacute;meros, con un M&iacute;nimo de 6 Caracteres y un M&aacute;ximo de 11 Caracteres <br/> Ejemplo: 12345678",
        NumeroDocumentoEnteText: "Este Campo no Permite Caracteres Especiales <br/> Ejemplo: 123ER678"
    };
    $scope.clearFields = function (form) {
        if (form.$name == "myFormEcu") {
            $scope.tipoDocumento = undefined;
            $scope.tipoDocCdano = undefined;
            $scope.nroDocCed = undefined;
            $scope.nroDoc = undefined;
        }
    }
    $scope.reloadPage = function (size) {
//			$route.reload();
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: "/web-ecu/site/pages/modalCancelar.html",
            controller: 'ModalInstanceCtrl',
            size: size,
            resolve: {}
        });
    }
    $scope.tipoDocumentoChange = function () {
        $scope.tipoDocCdano = undefined;
        $scope.nroDocCed = undefined;
        $scope.nroDoc = undefined;
    }
    $scope.triggerPopOver = function (elem) {
        $('input[name=' + elem + ']').trigger('popoverValid');
    }

    $scope.continuarFichaEcu = function () {
        var blnContinue = true;
        var datos = {};
        if ($scope.blnEntidad == 'ciudadanoDiv') {
            //var tipoDocumento = "CED";
            //datos.tipoDocumento = tipoDocumento;
            var tipoDocumento = $scope.myFormEcu.tipoDocumento_field.$modelValue;
            if (tipoDocumento == undefined) {
                blnContinue = false;
            } else {
                datos.tipoDocumento = $scope.myFormEcu.tipoDocumento_field.$modelValue;
            }
            datos.tipoDocumento = tipoDocumento;
            if ((tipoDocumento != undefined && tipoDocumento == 'CED')
                && (($scope.myFormEcu.tipoDocCdano_field.$modelValue == '' || $scope.myFormEcu.tipoDocCdano_field.$modelValue == undefined)
                || ($scope.myFormEcu.nroDocCed_field.$modelValue == '' || $scope.myFormEcu.nroDocCed_field.$modelValue == undefined))) {
                blnContinue = false;
            } else {
                datos.tipoDocCdano = $scope.myFormEcu.tipoDocCdano_field.$modelValue;
                datos.nroDocCed = $scope.myFormEcu.nroDocCed_field.$modelValue;
            }

            if ((tipoDocumento != undefined && tipoDocumento != 'CED')
                && ($scope.myFormEcu.nroDoc_field.$modelValue == '' || $scope.myFormEcu.nroDoc_field.$modelValue == undefined)) {
                blnContinue = false;
            } else {
                datos.nroDoc = $scope.myFormEcu.nroDoc_field.$modelValue;
            }
        }
        if (blnContinue) {
            var metodo = "POST";
            var urlContext = registrarRutasService.propiedades['sarc.web.generales.consultarFicha.url'];
            var performFunction = functionFichaECU;
            genericAjax1($http, $state, $stateParams, $scope, metodo, urlContext, datos, performFunction, 'errorSolECU');
            //$rootScope.panel = 1;
        }
    }
    $scope.cargarActas = function () {
        $scope.panel = 2;
        $scope.heightPerform = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height()) / 1.2) + 'px;';
    }
    $scope.backAcordeon = function () {
        var numero = $scope.panel;
        $scope.panel = numero - 1;
        /*if($rootScope.panel == 0){
         $scope.heightPerformPeticionDatos = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/1.2) + 'px;';
         $rootScope.heightPerformPeticionDatos = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/1.2) + 'px;';
         }
         if($rootScope.panel == 1){
         $scope.heightPerform = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/1.2) + 'px;';
         }*/
    }
    $scope.$watch("panel", function (newValue, oldValue) {
        if (newValue != null) {
            if (newValue == 0) {
                $scope.heightPerformPeticionDatos = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height()) / 1.4) + 'px;';
            }
            if (newValue == 1) {
                $scope.heightPerform = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height()) / 1) + 'px;';
                $scope.tablaFichaEcu = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height()) / 1.9) + 'px;';
                $scope.heightActas = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height()) / 1.6) + 'px;';
            }

        }
    });

    $scope.finalizarECU = function () {
//		$state.transitionTo('gestionECU');
        $state.forceReload();
    }
});

