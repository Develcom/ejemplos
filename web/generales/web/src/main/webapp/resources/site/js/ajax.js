function validarMenu(variable){
	var cursor = $(variable).css("cursor")
	if(cursor.indexOf("not-allowed") != -1){
		return false;
	}else{
		return true;
	}
}
function nuevaSolicitud(variable){
	if(!$("#waitingDiv").is(":visible")){
		if(validarMenu(variable)){
			$("#waitingDivTop").fadeIn();
			if(!solicitudEnProceso()){
				$("#cancelarBtn").trigger("click");
			}else{
				ajaxNuevaSolicitud();
			}
		}
	}
}
//LIBRO
function gestionLibros(variable){
	if(!$("#waitingDiv").is(":visible")){
		if(validarMenu(variable)){
			inhabilitarMenu(true);
			$("#waitingDivTop").fadeIn();
			$.ajax({
				url: "gestionarLibros",
				method: "GET",
				dataType: 'text',
				success: function(data) {
					$("#waitingDivTop").fadeOut();
					inhabilitarMenu(false);
					if(data.indexOf("alert-danger") == -1){
						if(data != null && $.trim(data) != ""){
							$("#body").html(data);
						}
						$('#opciones').css('display','');
						$("#continuarBtn").fadeIn();
						$("#cancelarBtn").fadeOut();
						$("#anteriorBtn").fadeOut();
						$("#finalizarBtn").fadeOut();
					}else{
						$("#subCabecera").html(data);
						$("#anteriorBtn").fadeOut();
						$("#subCabecera .alert").each(function(){
							$("#continuarBtn").fadeOut();
							$("#cancelarBtn").fadeOut();
							//$("#finalizarBtn").fadeIn();
						});
						$("#anteriorBtn").fadeOut();
						//$("#finalizarBtn").fadeIn();
						$("#continuarBtn").fadeIn();
					}
				}
			});	
		}
	}
}
function ajaxNuevaSolicitud(){
	console.log('ejecutando ajax nueva solicitud');
	$.ajax({
//		url: "flujo",
//		method: "POST",
		url: "/web-atencionComunitaria/welcomeAtencion",
		method: "GET",
		dataType: 'text',
		success: function(data) {
			$("#body").html(data);  
			$("#continuarBtn").fadeIn();
			$("#waitingDivTop").fadeOut();
		}
	});
}
function autenticarCiudadano(variable){
	if(!$("#waitingDiv").is(":visible")){
		if(validarMenu(variable)){
			if(!$("#waitingDiv").is(":visible")){
				$("#waitingDivTop").fadeIn();
				$.ajax({
					url: "autenticarCiudadano",
					method: "GET",
					dataType: 'text',
					success: function(data) {
						$("#waitingDivTop").fadeOut();
						$("#body").html(data);
						
					}
				});
			}
		}
	}
}

//ECU
function gestionEcu(variable){
	if(!$("#waitingDiv").is(":visible")){
		if(validarMenu(variable)){
			$("#waitingDivTop").fadeIn();
			$.ajax({
				url: "gestionEcu",
				method: "GET",
				dataType: 'text',
				success: function(data, status, xhttp) {
					$("#waitingDivTop").fadeOut();
					$("#body").html(data);
					
				}
			});
		}
	}
}

//NACIMIENTO
function registrarNacimiento(){
	$.ajax({
		url: "getOficio",
		method: "POST",
		dataType: 'text',
		success: function(data) {
			if(data != null){
				var params = {
					url: $.trim(data),
					pdfOpenParams: {
						navpanes: 1,
						toolbar: 1,
						statusbar: 0,
						zoom:150,
						view: "Fit"
					}
				};	
				var myPDF = new PDFObject(params).embed('body');
			}else{console.log("no se encuentra data");}
		}
	});
}


function crearSolicitud(datos){
	$("#body #waitingDiv").fadeIn();
	$.ajax({
		url : "crearSolicitud",
		method : "POST",
		dataType : 'text',
		data : datos,
		success : function(data) {
			if(data != ""){
				$("#subCabecera").html(data);
				$("#body #waitingDiv").fadeOut();
				$("#anteriorBtn").fadeOut();
				$("#subCabecera .alert").each(function(){
					$("#continuarBtn").fadeOut();
					$("#cancelarBtn").fadeOut();
					$("#finalizarBtn").fadeIn();
				});
				$("#body #waitingDiv").remove();
				$("#anteriorBtn").fadeOut();
				if($("input:radio[name ='solicitud']:checked").attr('id') == "tramiteRadio"){
					$("#continuarBtn").fadeOut();
					$("#finalizarBtn").fadeIn();
				}
			}
		},
		error: function (err) {
			$("#body #waitingDiv").remove();
			$("#continuarBtn").fadeOut();
			$("#cancelarBtn").fadeOut();
			$("#anteriorBtn").fadeOut();
			$("#finalizarBtn").fadeIn();
			mensaje("Surgio un error al realizar la operación, intentelo mas tarde.");
	    }
	});
}
function acceso(){
	$.ajax({
		url: "acceso",
		method: "POST",
		dataType: 'text',
		success: function(data) {
			   
		},
		error: function (err) {
	        
	    }
	})
	.done(function( data, textStatus, jqXHR ) {
	     
	 });
}

function solicitudEnProceso(){
	return (!$("#cancelarBtn").is(":visible"));
//	
//	{
//		return false;
//	
//	if($("#cancelarBtn").is(":visible")){
//		return false;
//	}else{
//		return true;
//	}
}
function mapAction(){
	if($("#body").find("#formularioAtencion").length > 0){
		//$("#gestionSol").trigger("click");
		ajaxNuevaSolicitud();
	}else if($("#body").find("#autenticarCiudadano").length > 0){
		$("#autenticarCiu").trigger("click");
	}else if($("#body").find("#formularioEcu").length > 0){
		$("#ecu").trigger("click");
	}else{
		$("#accesoAct").submit();
	}
}
function atencionCiudadanoContinuar(data){
	$.ajax({
		url : "atencionComunitaria",
		method : "POST",
		dataType : 'text',
		data : data,
		success : function(data) {
			$("#body #waitingDiv").fadeOut();
			inhabilitarMenu(false);
			if(data.indexOf("alert-danger") == -1){
				$("#body").remove("formularioAtencion");
				$("#body").html(data);	
			}else{
				$("#subCabecera").html(data);
				$("#anteriorBtn").fadeOut();
				$("#subCabecera .alert").each(function(){
					$("#continuarBtn").fadeOut();
					$("#cancelarBtn").fadeOut();
					$("#finalizarBtn").fadeIn();
				});
				$("#body #waitingDiv").remove();
				$("#anteriorBtn").fadeOut();
				if($("input:radio[name ='solicitud']:checked").attr('id') == "tramiteRadio"){
					$("#continuarBtn").fadeOut();
					$("#finalizarBtn").fadeIn();
				}
			}
		}
	});
}
function openEcu(){
	$.ajax({
		url: "loadPDF",
		method: "POST",
		dataType: 'text',
		success: function(data) {
			if(data != null){
				var params = {
					url: $.trim(data),
					pdfOpenParams: {
						navpanes: 1,
						toolbar: 0,
						statusbar: 0,
						view: "FitV"
					}
				};	
				var myPDF = new PDFObject(params).embed("contentEcuView");
				if(myPDF){
					$("#viewEcu").modal("show");
				}
			}
		}
	});
}