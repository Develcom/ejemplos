#Servicio: Ciudadano

##Requerimientos: 

* Java 7 (jdk)


##Operaciones:

1. **consultarPorNumeroDocumento:** 
    * ***Descripcion:*** Operación que permite consultar los datos de un ciudadano segun nun numero de documento.
    * ***Endpoint:*** /consultarPorNumeroDocumento/{numeroDocIdentidad}
    * ***Método:*** GET
	* ***Entrada:*** numeroDocIdentidad
	* ***Salida:*** Ciudadano

##Artefactos Generados:
* servicios-ciudadano.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia