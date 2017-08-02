$( document ).ready(function() {
	preventDrop();
	preventDrag();
	preventRightClick();
});
function contador(segundos){
	setTimeout(function(){ $('#mensajes').html('<h4>&emsp;<span class="glyphicon glyphicon-info-sign infoSign" aria-hidden="true"></span> Expiro su tiempo</h4>'); }, segundos);
}

function stageActivo(stage){
	$('#'+stage).addClass('flujoStage');
}

function actualizarNombreProceso(nombre){
	$('#nombreProcesoActual').html(nombre);
}
function acordeonGo(numero){
	$('#panel' + numero).collapse('toggle');
	$('#panel' + (numero + 1)).collapse('toggle');

	$('#head' + numero).removeClass('acordionTab');
	$('#head' + numero).addClass('acordionTabOff');

	$('#head' + (numero + 1)).removeClass('acordionTabOff');
	$('#head' + (numero + 1)).addClass('acordionTab');

	ajuste('head' + (numero + 1));
}
function ajuste(div) {
	$('.acordionTabOff').css('line-height', '20px');
	$('#'+div).css('line-height', '35px');
}

function mensajeCampos() {
	mensaje('Debe completar correctamente el formulario para poder continuar');
}
function mensajeLibro(texto){
	if($("#body").find("#formularioLibro").length > 0){
		if (texto.length > 0) {
			$("#mensajeLibro").html('<div class="alert alert-info alertPerform alertPerformDesign col-sm-9" role="alert"> <img src="/web-atencionComunitaria/img/info_0.png" class="img-responsive img_info_1"><span syle="text-align:justify;">' + texto + '</span></div>');
			movementScale("mensajeLibro img", "#");
			$("#mensajeLibro .alertPerform").fadeIn();
		} else {
			$("#mensajeLibro .alertPerform").fadeOut();
			
		}
	}
}
function mensaje(texto) {
	if($("#body").find("#formularioEcu").length > 0){
		if (texto.length > 0) {
			$("#mensajesEcu").html('<div class="alert alert-warning alertPerform alertPerformDesign" role="alert"> <img src="/web-atencionComunitaria/img/sign_warning.png" class="img-responsive img_info_1">' + texto + '</div>');
			movementScale("mensajesEcu img", "#");
			$("#mensajesEcu .alertPerform").fadeIn();
		} else {
			$("#mensajesEcu .alertPerform").fadeOut();
			
		}
	}else{
		if (texto.length > 0) {
			$("#mensajes").html('<div class="alert alert-warning alertPerform alertPerformDesign" role="alert"> <img src="/web-atencionComunitaria/img/sign_warning.png" class="img-responsive img_info_1">' + texto + '</div>');
			movementScale("mensajes img", "#");
			$("#mensajes .alertPerform").fadeIn();
		} else {
			$("#mensajes .alertPerform").fadeOut();
			
		}
	}
	
}
function inhabilitarMenu(bln){
	if(bln){
		$("#menNavigator button").css('cursor','not-allowed').fadeTo(500,0.2);
	}else{
		
		$("#menNavigator button").css('cursor','').fadeTo(500,1);
	}
	
}
function fnUpperCase(valor){
	return valor.toUpperCase();
}

function validarCedula(nacionalidad, campo){
	var result = false;
	if(nacionalidad.indexOf("ced") != -1){
		var valor = $(campo).val().split('');
		result = validarNac(valor[0]);
		if(!result && valor.length == 1){
			$(campo).val($(campo).val() + "-");
		}
		if(result){
			$(campo).val("");
			$(campo).css('border','2px solid #efd76b');//efd76b
			$("#sign_" + campo.id).fadeIn();
			mensaje("Debe contemplar el formato ej. V-12345678 ó E-12345678 para Número de Cédula");
		}else if(valor.length >= 2){
			result= validarGuion(valor[1]);
			if(result){
				$(campo).val("");
				$(campo).css('border','2px solid #efd76b');
				$("#sign_" + campo.id).fadeIn();
				mensaje("Debe contemplar el formato ej. V-12345678 ó E-12345678 para Número de Cédula");
			}else if(valor.length > 2){
				var num = $(campo).val().split('-');
				if(num.length == 2){
					result = validarNumero(num[1]);
					if(result){
						$(campo).val("");
						$(campo).css('border','2px solid #efd76b');
						$("#sign_" + campo.id).fadeIn();
						mensaje("Debe contemplar el formato ej. V-12345678 ó E-12345678 para Número de Cédula");
					}
				}else{
					$(campo).val("");
						$(campo).css('border','2px solid #efd76b');
						$("#sign_" + campo.id).fadeIn();
						mensaje("Debe contemplar el formato ej. V-12345678 ó E-12345678 para Número de Cédula");
				}
			}
		}
	}else if(nacionalidad.indexOf("nui") != -1){
		result = validarNUI($(campo).val());
		if(result){
			$(campo).val("");
			$(campo).css('border','2px solid #efd76b');
			$("#sign_" + campo.id).fadeIn();
			mensaje("Debe contemplar el formato ej. 100000000");
		}
	}else if(nacionalidad.indexOf("pas") != -1){
		result = validarAlfaNumerico($(campo).val());
		if(result){
			$(campo).val("");
			$("#sign_" + campo.id).fadeIn();
			mensaje("Debe contemplar el formato ej. PAS");
		}
	}else if(nacionalidad == ""){
		$(campo).val("");
		mensaje("Debe seleccionar el Tipo de Documento de Identificación");
		if(campo.id == "nroDoc"){
			$("#tipoDocumento").css('border','2px solid #efd76b');
			$("#sign_tipoDocumento").fadeIn();
		}else if(campo.id == "nroDocumentoSolicitud"){
			$("#tipoDocumentoSolicitud").css('border','2px solid #efd76b');
			$("#sign_tipoDocumento").fadeIn();
		}
	}
}
/*valida la entrada numérica*/
function validarNumero(p) {
	console.log("presion de las teclas: " + p);
	var patron = /[^0-9]/;
	var resultado = patron.test(p);
	if (!resultado){
		console.log("valor numerico aceptado");
	}else{
		console.log("valor numerico no aceptado");
	}
	return resultado;
}
function validarNUI(p){
	console.log("presion de las teclas: " + p);
	var patron = /[^0-9]/;
	var resultado = patron.test(p);
	if (!resultado){
		console.log("valor numerico aceptado");
	}else{
		console.log("valor numerico no aceptado");
	}
	return resultado;
}
function validarNac(p) {
	console.log("presion de las teclas: " + p);
	var patron = /^[V|v|E|e] + \- + [0-9]/;
	var resultado = patron.test(p);
	if (!resultado){
		console.log("valor nacionalidad aceptado");
	}else{
		console.log("valor nacionalidad no aceptado");
	}
	return resultado;
}	
function validarGuion(p) {

	var patron = /[^-]/;
	var resultado = patron.test(p);
	if (!resultado){
		console.log("valor guion aceptado");
	}else{
		console.log("valor guion no aceptado");
	}
	return resultado;
}	
	/*valida la entrada sea solo alfabetica*/
function validarNombre(p) {
	console.log("presion de las teclas: " + p);
	var patron1 = /[^a-zA-Z\s]/;
	var resultado = patron1.test(p);
	if (!resultado)
		console.log("valor de solo letras aceptado");
	else
		console.log("valor de solo letras no aceptado");
	return resultado;
}
function validarAlfaNumerico(p) {
	console.log("presion de las teclas: " + p);
	var patron1 = /[^A-Za-z0-9]/;
	var resultado = patron1.test(p);
	if (!resultado)
		console.log("valor alfanumerico aceptado");
	else
		console.log("valor alfanumerico no aceptado");
	return resultado;
}
function validarFormatoSolicitud(p) {
	console.log("presion de las teclas: " + p);
	var patron1 = /[^-A-Za-z0-9]/;
	var resultado = patron1.test(p);
	if (!resultado)
		console.log("valor alfanumerico aceptado");
	else
		console.log("valor alfanumerico no aceptado");
	return resultado;
}
function validarNacionalidad(valor, campo){
	if(valor.indexOf("ced") != -1){
		$("#" + campo).attr("placeholder", "ej.: V-12345678 | ej.: E-12345678");
	}else if(valor.indexOf("nui") != -1){
		$("#" + campo).attr("placeholder", "ej.: NUI");
	}else if(valor.indexOf("pas") != -1){
		$("#" + campo).attr("placeholder", "ej.: 12345QW45");
	}else{
		$("#" + campo).attr("placeholder", "Número de Documento");
	}
}
function testingX(){
	alert("TEST");
}
function movementRotate(id, tag, time){
	setInterval(function(){ 
		setTimeout(function(){
			$(tag + id).transition({ rotate: '45deg' });
		}, 500);
		setTimeout(function(){
			$(tag + id).transition({ rotate: '-45deg' });
		}, 500);
		setTimeout(function(){
			$(tag + id).transition({ rotate: '0deg' });
		}, 500);
	}, time);
}
function movementScale(id, tag){
/*	setInterval(function(){ 
		setTimeout(function(){
			$(tag + id).transition({ scale: 1.3 });
		}, 1000);
		setTimeout(function(){
			$(tag + id).transition({ scale: 1 });
		}, 1000);
	}, 3000);*/
}
function initializeButtons(){
	$('#opciones_modulo').displayOn();
	$('#finalizarBtn').displayOff();
	$('#cancelarBtn').deshabilitar();
	$('#cancelarBtn').esconderElemento();
	$('#anteriorBtn').deshabilitar();
	$('#anteriorBtn').esconderElemento();
	$('#finCreacionSol').esconderElemento();
	$("#tSolicitud").esconderElemento();
	$('#continuarBtn').aparecerElemento();
}
(function($){
    $.fn.disableCopyPasted = function() {
    	 this.bind("cut copy paste",function(e) {
             e.preventDefault();
         });
    };
    
    $.fn.disableSelectionContentDiv = function() {
    	$("#" + this[0].id + " input").each(function(){
        	if(this.type != null && this.type.toLowerCase() == "text"){
        		$("#" + this.id)
	        	.attr('unselectable', 'on')
	            .css('user-select', 'none')
	            .on('selectstart', false);
        	}
        }); 
    };
    $.fn.esconderElemento = function(){
    	try{
        	$(this).fadeOut();
    	}catch(err){
    		if(this.selector!= null){
    			$(this.selector).fadeOut();
    			$(this).fadeOut();
    		}else{
    			alert("Error con " + $(this));
    		}
    	}
    };
    $.fn.aparecerElemento = function(){
    	try{
        	$(this).fadeIn();
    	}catch(err){
    		if(this.selector!= null){
    			$(this.selector).fadeIn();
    			$(this).fadeIn();
    		}else{
    			alert("Error con " + $(this));
    		}
    		
    	}
    };
    $.fn.displayOn = function(){
    	$(this).removeClass('displayOff');
    	$(this).css('display','');
    };
    $.fn.displayOff = function(){
    	$(this).addClass('displayOff');
    	$(this).css('display','none');
    };
    $.fn.deshabilitar = function(){
    	$(this).attr("disabled", "disabled");
    };
    $.fn.habilitar = function(){
    	$(this).removeAttr("disabled");
    };
    $.fn.desAlertarInpt = function(){
    	try{
    		$(this).css("border","1px solid #ccc");
        	$("#sign_" + this[0].id).esconderElemento();
    	}catch(err){
    		if(this.selector!= null){
    			$(this.selector).css("border","1px solid #ccc");
            	$("#sign_" + this.selector.replace("#","").replace(".","")).esconderElemento();
    		}else{
    			alert("Error con " + $(this));
    		}
    		
    	}
    	
    };
   
    $.fn.AlertarInpt = function(){
    	try{
    		$(this).css("border","2px solid #efd76b");
        	$("#sign_" + this[0].id).aparecerElemento();
    	}catch(err){
    		if(this.selector!= null){
    			$(this.selector).css("border","2px solid #efd76b");
            	$("#sign_" + this.selector.replace("#","").replace(".","")).aparecerElemento();
    		}else{
    			alert("Error con " + $(this));
    		}
    	}
    };
    $.fn.showDesabilitado = function(){
    	$(this).aparecerElemento();
    	$(this).habilitar();
    };
    $.fn.AsyncronusFunc = function(urlV, methodV, dataTypeV, dataV, successFuncGeneral, errorFuncGeneral){
    	$.ajax({
    		url : url,
    		method : methodV,
    		dataType : dataTypeV,
    		data : dataV,
    		success : successFuncGeneral(response),
    		error : errorFuncGeneral(err)
    	});
    };
})(jQuery);
function AsyncronusFunc(urlV, methodV, dataTypeV, dataV, successFuncGeneral, errorFuncGeneral, jqueryRender){
	$("#body #waitingDiv").aparecerElemento();
	$.ajax({
		url : urlV,
		method : methodV,
		dataType : dataTypeV,
		data : dataV,
		success : function (response){
			successFuncGeneral(response, jqueryRender);
		},
		error : function (err){
			errorFuncGeneral(err);
		}
	});
}
function successFuncGeneral(response, jqueryRender){
	$("#body #waitingDiv").esconderElemento();
	inhabilitarMenu(false);
	if(response.indexOf("alert-danger") != -1 || response.indexOf("alert-warning") != -1){
		$("#subCabecera").html(response);
		$("#subCabecera .alert").each(function(){
			esconderBotonesNavegacion();
		});
		$("#body #waitingDiv").remove();
		if($("input:radio[name ='solicitud']:checked").attr('id') == "tramiteRadio"){
			$("#continuarBtn").esconderElemento();
			$("#finalizarBtn").aparecerElemento();
		}
		return false;
	}
	$("#body div:first-child").remove();
	$(jqueryRender).html(response);
	$("#waitingDivTop").esconderElemento();
}
function successFuncCiudadanoAcordeon(response){
	$("#body #waitingDiv").esconderElemento();
	inhabilitarMenu(false);
	if(response.indexOf("alert-danger") != -1 || response.indexOf("alert-warning") != -1){
		$("#subCabecera").html(response);
		$("#subCabecera .alert").each(function(){
			esconderBotonesNavegacion();
		});
		$("#body #waitingDiv").remove();
		if($("input:radio[name ='solicitud']:checked").attr('id') == "tramiteRadio"){
			$("#continuarBtn").esconderElemento();
			$("#finalizarBtn").aparecerElemento();
		}
		return false;
	}
	$("#body div:first-child").remove();
	$("#ciudadanoDiv").html(response);
	$("#waitingDivTop").esconderElemento();
}
function httpGetFunc($http, metodo, urlContext, datos, contenedor){
	$http({
			method: metodo,
			url: urlContext,
			params: {firstName: 'Dairene'}
		}).then(
		function(response) {
			console.log('consulta satisfactoria');
			angular.element(document.querySelector('#' + contenedor)).html(response.data);
			console.log(response);
		},
		function(response) {
			console.log('consulta errada');
		});
}
function routeFunctionPerform($http, response, $state, $stateParams, $scope){
	if(response.data != null){
		var path = response.data.state;
		delete response.data.state;
		$stateParams = response.data;
		$state.transitionTo(path, $stateParams);
	}
}
function functionListadoPaquetes($http, response, $state, $stateParams, $scope){
	$scope.dataLoaded = "70%";
	if(response.data != null && response.data.length > 0){
		$scope.ContentListSolicitudes = response.data;
		$scope.listSolicitudes = response.data;
		$scope.totalItems = response.data.length;
		$scope.itemsPerPage = 10;
		$scope.dataLoaded = "100%";
		
	}else{
		$scope.listSolicitudes = null;
		$stateParams.errorMsg="No hay solicitudes para ese Rol"
		$stateParams.errorSol = "solicitud_001";
		$state.transitionTo('errorSol', $stateParams);
	}
}
function modalCancelar($http, response, $state, $stateParams, $scope){
	if(response.data != null){
		$scope.masterHtml = response.data;
		$scope.modalActive = true;
		$scope.BlnModal = true;
	}
}
function procesoCrearDetSolicitud($http, response, $state, $stateParams, $scope){
	if(response.data != null){
		$http({
			method: "POST",
			url: "/web-atencionComunitaria/atComun/scrmDetalle"
		}).then(
		function(responseMap) {
			$scope.masterHtml = responseMap.data;
			$scope.masterForm = response.data[1];
		},
		function(response) {
			console.log('consulta errada');
		});
	}
}
function procesoInitAtencion($http, response, $location, $scope){
	
		$http({
			method: "POST",
			url: "/web-atencionComunitaria/atComun/init"
		}).then(
		function(responseMap) {
			$scope.masterHtml = responseMap.data;
			if(response == null){
				$scope.masterForm = "init";
			}else{
				$scope.masterForm = response.data[1];
			}
			
		},
		function(response) {
			console.log('consulta errada');
		});
}
function genericAjax($http, $state, $stateParams, $scope, metodo, urlContext, datos, performFunction){
	$http({
		method: metodo,
		url: urlContext,
		data: datos
	}).then(
	function(response) {
		console.log('consulta satisfactoria');
		console.log(response);
		if(response.data.errCode != null){
			$stateParams.errorSol = response.data.errCode;
			$stateParams.errorMsg = response.data.errMsg;
			$stateParams.current = $state.current.name;
			$state.transitionTo('errorSol', $stateParams);
		}else{
			performFunction($http, response, $state, $stateParams, $scope);
		}
	},
	function(response) {
		console.log('consulta errada');
	});
}

function genericAjax1($http, $state, $stateParams, $scope, metodo, urlContext, datos, performFunction,Linkerror){
	$http({
		method: metodo,
		url: urlContext,
		data: datos
	}).then(
	function(response) {
		console.log('consulta satisfactoria');
		console.log(response);
		if(response.data.errCode != null){
			$stateParams.errorSol = response.data.errCode;
			$stateParams.errorMsg = response.data.errMsg;
			$stateParams.current = $state.current.name;
			if(Linkerror!=null){
				$state.transitionTo(Linkerror, $stateParams);
			}else{
			$state.transitionTo('errorSol', $stateParams);
			}
		}else{
			performFunction($http, response, $state, $stateParams, $scope);
		}
	},
	function(response) {
		console.log('consulta errada');
	});
}

function AsyncGenericAjax($http, $state, $stateParams, $scope, metodo, urlContext, datos, performFunction, async){
	$http({
		method: metodo,
		url: urlContext,
		data: datos,
		async: async
	}).then(
	function(response) {
		console.log('consulta satisfactoria');
		console.log(response);
		if(response.data.errCode != null){
			$stateParams.errorSol = response.data.errCode;
			$stateParams.errorMsg = response.data.errMsg;
			$stateParams.current = $state.current.name;
			$state.transitionTo('errorSol', $stateParams);
		}else{
			performFunction($http, response, $state, $stateParams, $scope);
		}
	},
	function(response) {
		console.log('consulta errada');
	});
}

function httpGetFuncResponse($http, $location, metodo, urlContext, datos){
	$http({
			method: metodo,
			url: urlContext,
			data: datos
		}).then(
		function(response) {
			console.log('consulta satisfactoria');
			console.log(response);
			if(response.data != null){
				var path = "/";
				for(var i = 0; i < response.data.length; i++){
					if(i == 0){
						path+= response.data[i];
					}
					if(i > 0){
						path+= "/:" + response.data[i];
					}
				}
				$location.path(path);
			}
			return response.data;
		},
		function(response) {
			console.log('consulta errada');
		});
}
function httpGetFuncAdop($http, metodo, urlContext, madre, padre, contenedor){
	$http({
			method: metodo,
			url: urlContext,
			data: $.param({ 'madre_check': madre, 'padre_check': padre}),
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(
		function(response) {
			console.log('consulta satisfactoria');
			angular.element(document.querySelector('#' + contenedor)).html(response.data);
			console.log(response);
		},
		function(response) {
			console.log('consulta errada');
		});
}
function errorFuncGeneral(err){
	$("#body #waitingDiv").remove();
	$("#continuarBtn").fadeOut();
	$("#cancelarBtn").fadeOut();
	$("#anteriorBtn").fadeOut();
	$("#finalizarBtn").fadeIn();
	mensaje("Surgio un error al realizar la operación, intentelo mas tarde.");
}
function esconderBotonesNavegacion(){
	$("#continuarBtn").esconderElemento();
	$("#cancelarBtn").esconderElemento();
	$("#finalizarBtn").esconderElemento();
	$("#anteriorBtn").esconderElemento();
}
function preventDrop(){
	window.addEventListener("drop",function(e){
	  e = e || event;
	  e.preventDefault();
	},false);
}
function preventDrag(){
	window.addEventListener("dragover",function(e){
	  e = e || event;
	  e.preventDefault();
	},false);
}
function preventRightClick(){
	window.addEventListener("contextmenu",function(e){
	  e = e || event;
	  e.preventDefault();
	},false);
}
function anterior(numero){
	var panel = $('.in').attr('id');

	if(numero > 1){
		$('#panel'+numero).collapse('toggle');
		$('#panel'+(numero-1)).collapse('toggle');


		$('#head'+numero).removeClass('acordionTab');
		$('#head'+numero).addClass('acordionTabOff');

		$('#head'+(numero-1)).removeClass('acordionTabOff');
		$('#head'+(numero-1)).addClass('acordionTab');

		ajuste('head'+(numero-1));	
		
		$('#fin').val('false');
	}
	
}


function getURLParameter(name) {
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||Znull
}