#Servicio: Nui

##Requerimientos:
* Java 7 (jdk)

##Operaciones:

1. **buscarProximoNui:** 
    * ***Descripcion:*** Operación que permite buscar el proximo número de Nui.
    * ***Endpoint:*** /nui/buscarProximoNui/{codigoOficina}
    * ***Método:*** GET
    * ***Entrada:*** codigoOficina
    * ***Salida:*** long
	
2. **buscarExistenciaNUI:** 
    * ***Descripcion:*** Operación que permite buscar el NUI.
    * ***Endpoint:*** /nui/buscarExistenciaNUI/{numeroDocIdentidad}
    * ***Método:*** GET
    * ***Entrada:*** numeroDocIdentidad
    * ***Salida:*** boolean
	
##Artefactos Generados:
* servicios-nui.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia