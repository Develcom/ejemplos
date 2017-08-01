#Servicio: Notificaciones

##Requerimientos:

* Java 7 (jdk)

##Operaciones:

1. **enviarCorreo:** 
    * ***Descripcion:*** Permite enviar notificaciones por mail a un destinatario
    * ***Endpoint:*** /enviarCorreo/{html}/{mailReceptor}/{asunto}
    * ***Método:*** POST
    * ***Entrada:*** html, mailReceptor,asunto,etiquetas
    * ***Salida:*** boolean
    

##Artefactos Generados:
* servicios-notificaciones.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia