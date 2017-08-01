#Servicio: Recaudo

##Requerimientos:
* Java 7 (jdk)

##Operaciones:

1. **consultarPorCodigo:**
    * ***Descripcion:*** operación responsable de buscar el recaudo dado su código.
    * ***Endpoint:*** /consultarPorCodigo/{codigo}
    * ***Método:*** GET
    * ***Entrada:*** codigo
	* ***Salida:*** Recaudo

2. **registrarRecaudos:**
    * ***Descripcion:*** operación que permite registrar los recaudos solicitados en una solicitud
    * ***Endpoint:*** /registrarRecaudos/{numeroSolicitud}/{recaudos}
    * ***Método:*** POST
    * ***Entrada:*** numeroSolicitud,recaudos
	* ***Salida:*** boolean
	
3. **consultarRecaudos:**
    * ***Descripcion:*** operación responsable de los recaudos dado el numero de solicitud.
    * ***Endpoint:*** /consultarRecaudos/{numSolicitud}
    * ***Método:*** GET
    * ***Entrada:*** numSolicitud
	* ***Salida:*** List<Recaudo>
	
##Artefactos Generados:
* servicios-recaudo.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia