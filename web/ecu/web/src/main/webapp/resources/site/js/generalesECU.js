function functionFichaECU($http, response, $state, $stateParams, $scope) {
    if (response.data != null) {
    	var na='N/A';
        $scope.panel = 1;
        //$scope.tablaFichaEcu = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/1.9) + 'px;';
        //$scope.heightActas = (($window.outerHeight - $("#header").outerHeight() - $('nav.navbar.navbar-inverse.navbar-sarc').outerHeight() - $('footer').height())/1.6) + 'px;';
        $scope.fichaECU = {};
        $scope.ciudadano = response.data;
        var documentos = $scope.ciudadano.documentoIdentidad;
        if (documentos.length > 0) {
            for (var i = 0; i < documentos.length; i++) {
                var doc = documentos[i];
                if (doc.tipoDocumento != undefined && doc.tipoDocumento.codigo != undefined && doc.tipoDocumento.codigo == "CED" || doc.tipoDocumento.codigo == "PAS") {
                    $scope.fichaECU.tipoDoc = doc.tipoDocumento.nombre;
                    $scope.fichaECU.nroDoc = doc.numero;
                    //var num = doc.numero;
                    //var numero = num.split("-");
                    //if(numero.length > 0){
                    //	$scope.fichaECU.tipoDoc = numero[0];
                    //	$scope.fichaECU.nroDoc = numero[1];
                    //}
                } else if (doc.tipoDocumento != undefined && doc.tipoDocumento.codigo != undefined && doc.tipoDocumento.codigo == "NUI") {
                	$scope.fichaECU.tipoDoc = doc.tipoDocumento.nombre;
                    $scope.fichaECU.nroDoc = doc.numero;
                    $scope.fichaECU.NUI = doc.numero;

                }
            }
        }
        $scope.fichaECU.nombres = $scope.ciudadano.primerNombre + " " + $scope.ciudadano.segundoNombre;
        $scope.fichaECU.apellidos = $scope.ciudadano.primerApellido + " " + $scope.ciudadano.segundoApellido;
        $scope.fichaECU.fechaNac = $scope.ciudadano.fechaNacimiento;
        var direcciones = $scope.ciudadano.direccion;
        if (direcciones.length > 0) {
            for (var i = 0; i < direcciones.length; i++) {
                var tipoDireccion = direcciones[i].tipoDireccion;
                if (tipoDireccion.codigo == 'NAC') {
                    $scope.fichaECU.lugarNac = (direcciones[i].ubicacion==undefined || direcciones[i].ubicacion==null)?na:direcciones[i].ubicacion;
                } else if (tipoDireccion.codigo == 'RES') {
                    $scope.fichaECU.direccionResi = (direcciones[i].ubicacion==undefined || direcciones[i].ubicacion==null)?na:direcciones[i].ubicacion;
                }                
            }
        	            
        }
        $scope.fichaECU.estadoCivil = $scope.ciudadano.estadoCivil;
        $scope.fichaECU.sexo = $scope.ciudadano.sexo;
        $scope.fichaECU.nacionalidad = $scope.ciudadano.nacionalidad
        $scope.fichaECU.condicionNacionalidad = $scope.ciudadano.condicionNacionalidad;
        $scope.fichaECU.fechaResidencia = $scope.ciudadano.fechaResidencia;
        $scope.fichaECU.estadoCiudadano = $scope.ciudadano.estadoCiudadano;
        $scope.fichaECU.numeroHijosRegistrados = $scope.ciudadano.numeroHijosRegistrados;

        $scope.fichaECU.uehRegistrada = $scope.ciudadano.uehRegistrada;
        $scope.fichaECU.capacidadRegistrada = ($scope.ciudadano.capacidadRegistrada==undefined || $scope.ciudadano.capacidadRegistrada==null)?na:$scope.ciudadano.capacidadRegistrada;
        $scope.fichaECU.comunidadIndigena = ($scope.ciudadano.comunidadIndigena==undefined || $scope.ciudadano.comunidadIndigena==null)?na:$scope.ciudadano.comunidadIndigena;
        $scope.fichaECU.aParticipante = $scope.ciudadano.aParticipante;
        $scope.fichaECU.aVinculadas = $scope.ciudadano.aVinculadas;

    }

}