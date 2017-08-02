/**
 * 
 */
(function(){
	'use-strict';
	var app=angular.module('validaciones',[]);

	App.constant('validaciones',{
		'cedula':{
			maximo : '9',
			minimo : '6',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']			
		},
		'rif':{
			maximo : '12',
			minimo : '6',
			expReg : '^[0-9\-]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: J-123456-0',
			excepciones : ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']			
		},
		'codigorif':{
			expReg : '^[JjVvEeGg0-9\-]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'C-',
			excepciones : ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']			
		},
		'pasaporte':{
			maximo : '11', 
			minimo : '6',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']			
		},
		'primerNombre' : {
			maximo : '50',
			minimo : '2',
			expReg : '^[A-Za-z\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones y acentos',
			placeholder : 'Ingrese el nombre',
			excepciones : ['Backspace', ' ', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']						
		},
		'segundoNombre' : {
			maximo : '255',
			minimo : '2',
			expReg : '^[A-Za-z\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones y acentos',
			placeholder : 'Ingrese el nombre',
			excepciones : ['Backspace', ' ', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']						
		},
		'primerApellido' : {
			maximo : '50',
			minimo : '2',
			expReg : '^[A-Za-z\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones y acentos',
			placeholder : 'Ingrese el apellido',
			excepciones : ['Backspace', ' ', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']			
		},
		'segundoApellido' : {
			maximo : '50',
			minimo : '2',
			expReg : '^[A-Za-z\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones y acentos',
			placeholder : 'Ingrese el apellido',
			excepciones : ['Backspace', ' ', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']			
		},
		'telefono' : {
			maximo : '12',
			minimo : '12',
			expReg : '^[0-9\-]+$',
			mensaje : 'Este campo admite solo n\u00fameros y guiones',
			placeholder : 'Ejemplo: 0212-11111111',
			excepciones : ['Backspace', ' ', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']						
		},
		'direccion' : {
			maximo : '300',
			minimo : '2',
			expReg : '^((([a-zA-Z0-9 Ò—·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹]))|([-.])\1?(?!\4))',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y numeros',
			placeholder : 'Ejemplo: Av. Venezuela',
			excepciones : ['Backspace', ' ', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']			
		},
		'mail' : {
			maximo : '300',
			minimo : '2',
			expReg : '^[A-Za-z0-9\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y numeros',
			placeholder : 'Ejemplo: mail@dominio.com',
			excepciones : ['Backspace', ' ', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']			
		},
		'lugarNacimiento' : {
			maximo : '100',
			minimo : '2',
			expReg : '^[A-Za-z0-9\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y numeros',
			placeholder : 'Ejemplo: Barcelona',
			excepciones : ['Backspace', ' ', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']			
		},
		'tipoDocumento' : {
			maximo : '200',
			minimo : '2',
			expReg : '^[A-Za-z\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones y acentos',
			placeholder : 'Ejemplo: Licencia de conducir',
			excepciones : ['Backspace', ' ', 'Tab']						
		},
		'cargoMiembroCC' : {
			maximo : '200',
			minimo : '2',
			expReg : '^[A-Za-z0-9\-\'\x7f-\xff\s]$',
			titulo : 'Formato cargo',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y n\u00fameros',
			placeholder : 'Ejemplo: Secretario',
			excepciones : ['Backspace', 'Tab', ' ']			
		},
		'observaciones' : {
			maximo : '200',
			minimo : '2',
			expReg : '^[A-Za-z0-9\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y n\u00fameros',
			titulo : 'Formato de observaciones',
			placeholder : 'Ingresar motivo de la inconformidad',
			excepciones : ['Backspace', 'Tab', ' ', 'ArrowLeft', 'ArrowRight', 'Delete', 'Caps Lock', 'Shift', 'Control']
		},
		'numMPPS':{
			maximo : '6',
			minimo : '6',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab']
		},
		'acta':{
			maximo : '20',
			minimo : '5',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']
		},
		'folio':{
			maximo : '5',
			minimo : '5',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']
		},
		'tomo':{
			maximo : '2',
			minimo : '1',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 123456',
			excepciones : ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']
		},
		'numCert':{
			maximo : '8',
			minimo : '8',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 12345678',
			excepciones : ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']
		},
		'edad':{
			maximo : '3',
			minimo : '1',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 100',
			excepciones : ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']
		},
		'sentencia':{
			maximo : '200',
			minimo : '2',
			expReg : '^[A-Za-z0-9]',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y n\u00fameros',
			titulo : 'Formato de observaciones',
			placeholder : 'Ejemplo: Error',
			excepciones : ['Backspace', 'Tab', ' ', 'Delete']
		},
		'cementerio':{
			maximo : '200',
			minimo : '2',
			expReg : '^[A-Za-z0-9\-\'\x7f-\xff\s]$',
			mensaje : 'Este campo solo permite letras, di\u00e9resis, ap\u00f3strofes, guiones, acentos y n\u00fameros',
			placeholder : 'Ejemplo: Nombre del cementerio',
			excepciones : ['Backspace', 'Tab', 'Delete', ' ']
		},
		'cant':{
			maximo : '2',
			minimo : '1',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 100',
			excepciones : ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']
		},
		'ev14':{
			maximo : '8',
			minimo : '5',
			expReg : '^[0-9]',
			mensaje : 'Este campo admite solo n\u00fameros',
			placeholder : 'Ejemplo: 100',
			excepciones : ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight', 'Delete']
		}
	});
	
})();
