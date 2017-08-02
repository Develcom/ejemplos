var app = angular.module("app", [ 'ngResource','ngRoute','ui.bootstrap'])
    .config(function($routeProvider){
        $routeProvider
            .when("/", {
                controller: "MainCtrl",
                controllerAs: "vm",
                templateUrl: "home.html"
            })
            .when("/descargas", {
                controller: "MainCtrl",
                controllerAs: "vm",
                templateUrl: "declaracionJurada.html"
            })
            .when("/opciones", {
                controller: "MainCtrl",
                controllerAs: "vm",
                templateUrl: "opciones.html"
            });
    })

.controller('MainCtrl', function ($scope) {
	
    $scope.mostrar=true;
	$scope.calcularEdad = function() {
		//alert('EL valor es: ');
		
		//var hours = moment.duration( $scope.dt2).asYears();
		var duration = moment.duration(moment().diff( $scope.dt2));
		var hours = duration.asYears()+0.01;
		if ($scope.myDropDown=="uno"){
			
			if (hours < 18 && hours > 7){
			$scope.mostrar=false;	
			
			}else{
			$scope.mostrar=true;
			}
			if (hours >= 25) {
				$scope.men_matrimonio=false;	
				$scope.buttonDisabled=true;
			}else{
				$scope.men_matrimonio=true;
				$scope.buttonDisabled=false;
			}
		}
		if ($scope.myDropDown=="cero"){
			
			
			if (hours < 18 && hours > 7){
			$scope.mostrar=false;	
			
			}else{
			$scope.mostrar=true;
			}
			
		}
		
		if ($scope.myDropDown=="dos"){
			
			
			if (hours < 18 && hours > 7){
			$scope.mostrar=false;	
			
			}else{
			$scope.mostrar=true;
			}
			
		}
		
		if ($scope.myDropDown=="cuatro"){
			
			if (hours < 18 && hours > 7){
			$scope.mostrar=false;	
			
			}else{
			$scope.mostrar=true;
			}
			if (hours >= 21) {
				$scope.men_matrimonio=false;	
				$scope.buttonDisabled=true;
			}else{
				$scope.men_matrimonio=true;
				$scope.buttonDisabled=false;
			}
		}
		$scope.edad=hours;

	};
	
	$scope.men_matrimonio=true;
	$scope.calcularMatrimonio = function() {
		//alert('EL valor es: ');
		
		//var hours = moment.duration( $scope.dt2).asYears();
		var duration = moment.duration(moment().diff( $scope.dt2));
		var hours = duration.asYears()+0.01;

		if (hours < 5){
			$scope.men_matrimonio=false;	
			$scope.buttonDisabled=true;
		
		}else{
			$scope.men_matrimonio=true;
			$scope.buttonDisabled=false;
		}

	};
	
		
	$scope.fechaResEsc1 = function() {
		
		var duration = moment.duration(moment().diff( $scope.dt1));
		$scope.hours = duration.asYears()+0.01;
		var fecha = $scope.edad-$scope.hours;
		
		
		if (fecha > 18){
			$scope.men_matrimonio=false;	
			$scope.buttonDisabled=true;
		
		}else{
			$scope.men_matrimonio=true;
			$scope.buttonDisabled=false;
		}

	};
	
	$scope.fechaNatPadresEsc1 = function() {
		
		var duration = moment.duration(moment().diff( $scope.dt3));
		$scope.hours = duration.asYears()+0.01;
		
		if ($scope.hours > $scope.edad){
			$scope.men_matrimonio=false;	
			$scope.buttonDisabled=true;
			
		}else{
			$scope.men_matrimonio=true;
			$scope.buttonDisabled=false;
		}

	};
	
	$scope.fechaResEsc2 = function() {
		
		var duration = moment.duration(moment().diff( $scope.dt0));
		$scope.hours = duration.asYears()+0.01;
		var fecha = $scope.edad-$scope.hours;
		
		
		if (fecha > 18){
			$scope.men_matrimonio=false;
			$scope.buttonDisabled=true;
		}else{
			$scope.men_matrimonio=true;
			$scope.buttonDisabled=false;
			

		}

	};
	
	$scope.sinEntrevista = function() {
		
		var duration = moment.duration(moment().diff( $scope.si6));
		$scope.hours = duration.asYears()+0.01;
		
		if ($scope.si6 == $scope.edad){
			$scope.men_matrimonio=false;	
			$scope.buttonDisabled=true;
		
		}else{
			$scope.men_matrimonio=true;
			$scope.buttonDisabled=false;
		}

	};	
	
	
	$scope.menorAusente = function() {
		var estado = $scope.checked;
		
		if (estado == "no"){
			$scope.buttonDisabled=true;
		
		
		}else{
			$scope.buttonDisabled=false;
		}	
	};
	
	$scope.menorInconforme = function() {
		var estado = $scope.regla3;
		
		if (estado == "no"){
			$scope.men_matrimonio=false;
			$scope.buttonDisabled=true;
		
		
		}else{
			$scope.buttonDisabled=false;
		}	
	};
	
	$scope.hora = new Date();

	  $scope.toggleMin = function() {
          $scope.maxDate = $scope.maxDate ? null : new Date();
       };
      $scope.toggleMin();
      $scope.open = function($event,opened) {
         
             $event.preventDefault();
             $event.stopPropagation();
             $scope[opened] = true;
             //$scopeFecha1Opened = true;
           };
        
        $scope.dateOptions = {
                formatYear: 'yy',
                startingDay: 1
              };

         $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
         $scope.format = $scope.formats[0];
         
         $scope.today = function() {
             $scope.dt = new Date();
           };
           $scope.today();

           $scope.clear = function () {
             $scope.dt = null;
          };
          
          $("[data-toggle='tooltip']").each(function (index, el) {
        	    $(el).tooltip({
        	        placement: $(this).data("placement") || 'top'
        	    });
          });
      	  
       
          
          $scope.mensajemat = "Solicitud Cancelada por no cumplir con la condicion constitucional para acoger la Nacionalidad Venezolana";
          $scope.paisSeleccion = 0;
          $scope.ciudadSeleccion = null;
          $scope.paises = [
                           {
                            pais: 'EspaÃ±a',
                            grupo: 1 
                           },
                           {
                            pais: 'Portugal',
                            grupo: 1 
                           },
                           {
                            pais: 'Italia',
                            grupo: 1 
                           },
                           {
                            pais: 'Antigua y Barbuda',
                            grupo: 1 
                           },
                           {
                            pais: 'Aruba',
                            grupo: 1 
                           },
                           {
                            pais: 'Bahamas',
                            grupo: 1 
                            },
                            {
                             pais: 'Barbados',
                               grupo: 1 
                            },
                            {
                             pais: 'Cuba',
                             grupo: 1 
                            },
                            {
                             pais: 'Dominica',
                             grupo: 1 
                            },
                            {
                             pais: 'Granada',
                             grupo: 1 
                            },
                            {
                             pais: 'Guadalupe',
                             grupo: 1 
                            },
                            {
                             pais: 'HaitÃ­',
                             grupo: 1 
                            },
                            {
                             pais: 'Islas Caiman',
                             grupo: 1 
                            },
                            {
                             pais: 'Islas Turcas y Caicos',
                             grupo: 1 
                            },
                            {
                            pais: 'Islas Virgenes',
                            grupo: 1 
                           },
                           {
                            pais: 'Jamaica',
                            grupo: 1 
                            },
                            {
                             pais: 'Martinica',
                               grupo: 1 
                            },
                            {
                             pais: 'Puerto Rico',
                             grupo: 1 
                            },
                            {
                             pais: 'Republica Dominicana',
                             grupo: 1 
                            },
                            {
                             pais: 'San BartolomÃ©',
                             grupo: 1 
                            },
                            {
                             pais: 'San Cristobal y Nieves',
                             grupo: 1 
                            },
                            {
                             pais: 'San Vicente y las Granadinas',
                             grupo: 1 
                            },
                            {
                             pais: 'Santa Lucia',
                             grupo: 1 
                            },
                            {
                             pais: 'Trinidad y Tobago',
                             grupo: 1 
                            },
                            {
                                pais: 'Belice',
                                grupo: 1 
                               },
                               {
                                pais: 'Costa Rica',
                                grupo: 1 
                               },
                               {
                                pais: 'El Salvador',
                                grupo: 1 
                               },
                               {
                                pais: 'Guatemala',
                                grupo: 1 
                               },
                               {
                                pais: 'Honduras',
                                grupo: 1 
                               },
                               {
                               pais: 'Panama',
                               grupo: 1 
                              },
                              {
                               pais: 'Argentina',
                               grupo: 1 
                               },
                               {
                                pais: 'Bolivia',
                                  grupo: 1 
                               },
                               {
                                pais: 'Brasil',
                                grupo: 1 
                               },
                               {
                                pais: 'Chile',
                                grupo: 1 
                               },
                               {
                                pais: 'Colombia',
                                grupo: 1 
                               },
                               {
                                pais: 'Ecuador',
                                grupo: 1 
                               },
                               {
                                pais: 'Guyana',
                                grupo: 1 
                               },
                               {
                                pais: 'Guyana Francesa',
                                grupo: 1 
                               },
                               {
                                pais: 'Paraguay',
                                grupo: 1 
                               },
                               {
                                   pais: 'Peru',
                                   grupo: 1 
                                  },
                                  {
                                   pais: 'Suriname',
                                   grupo: 1 
                                  },
                                  {
                                   pais: 'Uruguay',
                                   grupo: 1 
                                  },
                                  {
                                   pais: 'Mexico',
                                   grupo: 1 
                                  },
                                  {
                                   pais: 'Otros paises',
                                   grupo: 1 
                                  }
                          ];


});