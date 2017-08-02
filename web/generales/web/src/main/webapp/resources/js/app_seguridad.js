App.constant('PARAMETROS', {
	BaseUrlSarc: '/sarc/api/v1/',
	RootPath: function(){
		var constructor = window.location;	
		return ''+ constructor.protocol +'//' + constructor.hostname + ((constructor.port != '' )?':'+ constructor.port:'') + '/' + constructor.pathname.split('/')[1];		
	}
});


App.service('UserService', function(store) {
    var service = this,
        currentUser = null;

    service.setCurrentUser = function(user) {
        currentUser = user;
        store.set('user', user);
        return currentUser;
    };

    service.getCurrentUser = function() {
        if (!currentUser) {
            currentUser = store.get('user');
        }
        return currentUser;
    };
});

App.factory('sessionInjector', ['UserService', function(UserService) {  
    var sessionInjector = {
        request: function(config) {
            if (!UserService.isAnonymus) {
                config.headers['x-session-token'] = UserService.token;
            }
            return config;
        }
    };
    return sessionInjector;
}]);
App.factory('sessionInjectorResponse', ['UserService', function(UserService) {  
    var sessionInjector = {
        response: function(config) {
        	if(config.data[1] != undefined){
        		config.headers['x-session-token'] = config.data[1];
        	}
            return config;
        }
    };
    return sessionInjector;
}]);

/**
 * Controlador de la vista de login
 */

App.service('loginService', ['$http','PARAMETROS','$q', function ($http,PARAMETROS,$q) {

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

		//Metodo para consultar en OURC el codigo de identificacion de la oficina y el codigo de usuario
		self.solicitarUsuarioOficinaPost = function(usuario){
			return $http({
				url: self.propiedades["sarc.web.sarci.servidor.url"] +'/servicios-snuiourc/Api/UsuarioOURC/NombreUsuario',
				headers: { 'Content-Type': 'application/json' },
				data: usuario,        
				method: 'POST'        
			});
		}
		
		//Metodo para ingresar
		self.ingresarUsuario = function(user){
			return $http({
				url: "/web-generales/ini/" + user.nombreusuario + "/" + user.clave,
				method: 'POST' 
			});
		}
		
	}, function(data){
		console.log("Error al cargar el archivo");
		deferred.resolve("error");
	}
	);		
	self.promise = deferred.promise;

}]);	


App.controller('registroUsuarios', 
		['cfCryptoHttpInterceptor', '$scope', '$http','$rootScope','loginService', 
		 function(cfCryptoHttpInterceptor, $scope, $http,$rootScope, loginService){
			sessionStorage.removeItem('usuario');
			sessionStorage.removeItem('oficina');
			$scope.user = null;
			$scope.profiles = new Object();

			$scope.validar = function() {
				$scope.profiles = new Object();
				var data = {};
				var method = "POST";
				
				// Metodos para codificar y decodificar datos del usuario
				var code = {

						_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

						// Metodo para codificar
						encode : function (input) {
						    var output = "";
						    var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
						    var i = 0;

						    input = code._utf8_encode(input);

						    while (i < input.length) {

						        chr1 = input.charCodeAt(i++);
						        chr2 = input.charCodeAt(i++);
						        chr3 = input.charCodeAt(i++);

						        enc1 = chr1 >> 2;
						        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
						        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
						        enc4 = chr3 & 63;

						        if (isNaN(chr2)) {
						            enc3 = enc4 = 64;
						        } else if (isNaN(chr3)) {
						            enc4 = 64;
						        }

						        output = output +
						        this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
						        this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

						    }

						    return output;
						},

						// Metodo para decodificar
						decode : function (input) {
						    var output = "";
						    var chr1, chr2, chr3;
						    var enc1, enc2, enc3, enc4;
						    var i = 0;

						    input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

						    while (i < input.length) {

						        enc1 = this._keyStr.indexOf(input.charAt(i++));
						        enc2 = this._keyStr.indexOf(input.charAt(i++));
						        enc3 = this._keyStr.indexOf(input.charAt(i++));
						        enc4 = this._keyStr.indexOf(input.charAt(i++));

						        chr1 = (enc1 << 2) | (enc2 >> 4);
						        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
						        chr3 = ((enc3 & 3) << 6) | enc4;

						        output = output + String.fromCharCode(chr1);

						        if (enc3 != 64) {
						            output = output + String.fromCharCode(chr2);
						        }
						        if (enc4 != 64) {
						            output = output + String.fromCharCode(chr3);
						        }

						    }

						    output = code._utf8_decode(output);

						    return output;

						},

						// Metodo para codificar en UTF8
						_utf8_encode : function (string) {
						    string = string.replace(/\r\n/g,"\n");
						    var utftext = "";

						    for (var n = 0; n < string.length; n++) {

						        var c = string.charCodeAt(n);

						        if (c < 128) {
						            utftext += String.fromCharCode(c);
						        }
						        else if((c > 127) && (c < 2048)) {
						            utftext += String.fromCharCode((c >> 6) | 192);
						            utftext += String.fromCharCode((c & 63) | 128);
						        }
						        else {
						            utftext += String.fromCharCode((c >> 12) | 224);
						            utftext += String.fromCharCode(((c >> 6) & 63) | 128);
						            utftext += String.fromCharCode((c & 63) | 128);
						        }

						    }

						    return utftext;
						},

						// Metodo para decodificar en UTF8
						_utf8_decode : function (utftext) {
						    var string = "";
						    var i = 0;
						    var c = 0;
						    var c1 = 0;
						    var c2 = 0;

						    while ( i < utftext.length ) {

						        c = utftext.charCodeAt(i);

						        if (c < 128) {
						            string += String.fromCharCode(c);
						            i++;
						        }
						        else if((c > 191) && (c < 224)) {
						            c2 = utftext.charCodeAt(i+1);
						            string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
						            i += 2;
						        }
						        else {
						            c2 = utftext.charCodeAt(i+1);
						            c3 = utftext.charCodeAt(i+2);
						            string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
						            i += 3;
						        }

						    }

						    return string;
						}

						}//Fin Codificacion
				
				$scope.user.code = code;
				if( $scope.user != null && ($scope.user.nombreusuario != null || $scope.user.nombreusuario != undefined) && ($scope.user.clave != null || $scope.user.clave != undefined)){
					loginService.promise.then(function(){
						loginService.ingresarUsuario($scope.user)
						.success(function(data, status, headers, config) {
							if("error" === data[0]){
								$scope.profiles.msj = "El nombre de usuario o clave son inv\u00e1lidos";
								return
							}else{
								loginService.promise.then(function(){
									loginService.solicitarUsuarioOficinaPost($scope.user.nombreusuario).success(function(data) {
										
										var cifrada = $scope.user.code.encode($scope.user.clave);
										var cifrada2 = $scope.user.code.encode(cifrada);
										var descifrada = $scope.user.code.decode(cifrada2);
										var descifrada2 = $scope.user.code.decode(descifrada);
										
										sessionStorage.setItem("usuario",data.usuario);
										sessionStorage.setItem("oficina",data.oficina);
										sessionStorage.setItem("nombre",$scope.user.nombreusuario);
										sessionStorage.setItem("datos",cifrada2);
										
										window.location.href = '/web-generales/resources/pages/index.html';
									})
									.error(function(data, status, headers, config) {
										$scope.profiles.msj =  "El nombre de usuario o clave son inv\u00e1lidos";
									});
								})
							}
						}).error(function(data, status, headers, config) {
							$scope.profiles.msj =  "El nombre de usuario o clave son inv\u00e1lidos";
						});
					});
				}
			}
			
}]);

