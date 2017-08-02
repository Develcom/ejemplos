
App.constant('PARAMETROS', {
	BaseUrl: 'resources/json/',
	BaseUrlSarc: '/sarc/api/v1/',
	BaseUrlSeguridad: '/api/v1/sistema/usuarioAutenticado',
	BaseUrlSolNUI: ':8443/servicios-solicitudNui/Api/',
	BaseUrlSolNUIOld: ':8443/servicios-solicitudNui/rest/',
	RootPath: function(){
		var constructor = window.location;	
		return ''+ constructor.protocol +'//' + constructor.hostname + ((constructor.port != '' )?':'+ constructor.port:'') + '/' + constructor.pathname.split('/')[1];		
	}
});


App.service('registrarRutasService', registrarSolicitudNuiService);

registrarSolicitudNuiService.$inject=['$http','PARAMETROS','$q'];
function registrarSolicitudNuiService ($http,PARAMETROS,$q) {
	this.promise;
	var self = this;
	this.solicitanteTemp='';
	var deferred = $q.defer();

	$http({
		url: PARAMETROS.RootPath() + PARAMETROS.BaseUrlSarc + 'propiedades',
		method: 'GET',
		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	}).then(function(data){		  
		self.propiedades = data.data;
		deferred.resolve("procesado =" + data);

		//Metodo para consultar los detalles de la Oficina
		self.solicitarDetallesOficina = function($http, $scope, vm, datos){
			return $http({
				url: self.propiedades["sarc.web.sarci.servidor.url"] +'/web-generales/detallesOficina',
				headers: { 'Content-Type': 'application/json' },
				data:JSON.stringify(datos),        
				method: 'POST'        
			});
		}
	}, function(data){
		console.log("Error al cargar el archivo");
		deferred.resolve("error");
	}
	);		
	self.promise = deferred.promise;

};	
