# README #

## Descripcion ##

Arquetipo Maven para la generacion de proyectos multimodulo con Spring MVC 4 para SARC Fase 2. Este arquetipo esta basado en el proyecto [Jboss Spring MVC Application 2.7.0.Final](http://www.jboss.org/archetypes/eap/jboss-spring-mvc-archetype-wfk/) por lo que las librerias base utilizadas estan certificadas para Jboss AS 7.

### Requerimientos ###

* JDK 7 o superior.
* Maven 3 (alternativo: IDE con soporte Maven).

### Proyectos generados con el arquetipo ###

#### Estructura ####

```
#!

+ proyecto (directorio raiz)
|--integration-tests (pruebas de integracion)
|  |--src
|  |--test
|  `--pom.xml
|--servicios (proyecto de servicios)
|  |--src
|  |--test
|  `--pom.xml
`--pom.xml
```

#### Librerias ####

* Spring (core, mvc, aop, etc) 4.1.4.RELEASE
* Jackson 2.4.3
* Servlet 3.0
* JSP 2.2
* JPA 2.0
* Hibernate Entity Manager 4.2.14.SP4-redhat-1

## Instalacion y uso del Arquetipo ##

### Desde consola ###

* Clonamos el repositorio
* Cambiamos al directorio donde clonamos el repositorio. Ej. `cd /home/git/repositorios/sarc_persistencia`
* Compilamos e instalamos con `mvn install`
* Cambiamos al directorio donde queremos crear el nuevo proyecto a partir del arquetipo. Ej: `cd /home/git/repositorios`
* Generamos un nuevo proyecto con `mvn archetype:generate -DarchetypeCatalog=local`
* Seleccionamos el numero del arquetipo `ve.gob.cne.sarc:sarc_persistencia` (numero 2 en el siguiente ejemplo)

```
#!bash

> mvn archetype:generate -DarchetypeCatalog=local
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] >>> maven-archetype-plugin:2.3:generate (default-cli) > generate-sources @ standalone-pom >>>
[INFO]
[INFO] <<< maven-archetype-plugin:2.3:generate (default-cli) < generate-sources @ standalone-pom <<<
[INFO]
[INFO] --- maven-archetype-plugin:2.3:generate (default-cli) @ standalone-pom ---
[INFO] Generating project in Interactive mode
[INFO] No archetype defined. Using maven-archetype-quickstart (org.apache.maven.archetypes:maven-archetype-quickstart:1.0)
Choose archetype:
1: local -> com.github.spring-mvc-archetypes:spring-mvc-quickstart (Archetype for creating Spring MVC 4 web application)
2: local -> ve.gob.cne.sarc:sarc_persistencia (sarc_persistencia)
Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): : 2
```

* Suministramos el `groupId` y `artifactId` cuando lo solicite (version y package son opcionales)

```
#!bash

Define value for property 'groupId': : ve.gob.cne.sarc
Define value for property 'artifactId': : prueba
Define value for property 'version':  1.0-SNAPSHOT: :
Define value for property 'package':  ve.gob.cne.sarc: :
Confirm properties configuration:
groupId: ve.gob.cne.sarc
artifactId: prueba
version: 1.0-SNAPSHOT
package: ve.gob.cne.sarc
 Y: : Y
```

* Cambios al directorio del proyecto recien creado. Ej: `cd /home/git/repositorios/prueba`
* Compilamos y probamos el proyecto con `mvn install`
* En el directorio `servicios/target` encontraremos el archivo war de servicios listo para ser desplago en el contenedor.
* En el directorio `web/target` encontraremos el archivo war del proyecto web listo para ser desplago en el contenedor.

### Desde Eclipse ###

* Importamos el proyecto git con `Import ... > Projects from Git > Clone URI` ![Clonando el repositorio]
* Instalamos el arquetipo con `mvn install` ![Instalando el arquetipo]
* Vamos a `Preferences > Maven > Archetypes` y seleccionamos `Add Local Catalog`
* Navegamos el directorio del proyecgo y seleccionamos el archivo de catalago suministrado `archetype-catalog.xml` ![Agregando el catalogo]
* Creamos un nuevo proyecto Maven y seleccionamos el arquetipo `ve.gob.cne.sarc:sarc_persistencia` (recuerden marcar el checkbox `Include snapshot archetypes`) ![Generando un nuevo proyecto]