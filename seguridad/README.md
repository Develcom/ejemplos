#Modulo Seguridad SARC 


##Contenido


###Repositorio

Para obtener el repositorio es necesario usar [**git**](https://git-scm.com/), y tener una cuenta bitbucket con los permisos necesarios.

Copiar del repositorio
> git clone https://soaint_sarc@bitbucket.org/hisoftreps/sarc-seguridad.git

cambiar a la rama de desarrollo
> cd sarc-seguridad
> git checkout desarrollo


actualizar la rama
> git pull

###Autorizar
Este servicio usa Spring Security con la implementacion de OAUTH 2.0; se encarga de autorizar e iniciar sesion de un usuario, una vez validado el retonar un json con toda la informacion necesaria para su autorizacion en el sistema SARC:
por ejemplo.

```json
{
   "access_token": "3cd15966-b676-4b0a-9be5-dbd608858bc1",
   "token_type": "bearer",
   "refresh_token": "ef0a2772-73d8-47e5-9416-721e795fa081",
   "expires_in": 78460,
   "scope": "read write trust"
}
```
En el standalone.xml del jboss se deben crear los jndi y datasource para SARC y OAUTH, algo parecido a este ejemplo, solo que se debe colocar la direccion ip y credenciales de los servidores de base de datos.

Apuntando a SARC
```xml

<datasource jta="false" jndi-name="java:jboss/datasources/sarcDS" pool-name="sarcDS" enabled="true" spy="true" use-ccm="false">
    <connection-url>jdbc:oracle:thin:@{server}:1521/cneProyecto</connection-url>
    <driver-class>oracle.jdbc.OracleDriver</driver-class>
    <driver>oracle</driver>
    <pool>
        <min-pool-size>2</min-pool-size>
        <max-pool-size>200</max-pool-size>
        <prefill>true</prefill>
        <flush-strategy>IdleConnections</flush-strategy>
    </pool>
    <security>
        <user-name>{user}</user-name>
        <password>{password}</password>
    </security>
    <validation>
        <validate-on-match>true</validate-on-match>
        <background-validation>true</background-validation>
        <background-validation-millis>500</background-validation-millis>
    </validation>
    <timeout>
        <blocking-timeout-millis>5000</blocking-timeout-millis>
        <idle-timeout-minutes>1</idle-timeout-minutes>
        <query-timeout>60</query-timeout>
    </timeout>
    <statement>
        <share-prepared-statements>false</share-prepared-statements>
    </statement>
</datasource>
```

Apuntando a OAUTH
```xml
<datasource jta="false" jndi-name="java:jboss/datasources/sarcSecurity" pool-name="sarcSecurity" enabled="true" spy="true" use-ccm="false">
    <connection-url>jdbc:oracle:thin:@{server}:1521/cneProyecto</connection-url>
    <driver-class>oracle.jdbc.OracleDriver</driver-class>
    <driver>oracle</driver>
    <pool>
        <min-pool-size>2</min-pool-size>
        <max-pool-size>200</max-pool-size>
        <prefill>true</prefill>
        <flush-strategy>IdleConnections</flush-strategy>
    </pool>
    <security>
        <user-name>{user}</user-name>
        <password>{password}</password>
    </security>
    <validation>
        <validate-on-match>true</validate-on-match>
        <background-validation>true</background-validation>
        <background-validation-millis>500</background-validation-millis>
    </validation>
    <timeout>
        <blocking-timeout-millis>5000</blocking-timeout-millis>
        <idle-timeout-minutes>1</idle-timeout-minutes>
        <query-timeout>60</query-timeout>
    </timeout>
    <statement>
        <share-prepared-statements>false</share-prepared-statements>
    </statement>
</datasource>
```
Tambien es importante destacar que este servicio usa la capa de persistencia, asi que a la hora de compilar y desplegar se debe con anterioridad tener compilado esta capa.



###Proteger Recursos
Este es un jar que debe ser colocado como dependencia en los diferentes modulos de SARC, se debe colocar en el pom la siguiente dependencia:
```xml
<dependency>
    <groupId>ve.gob.cne.sarc.proteger</groupId>
    <artifactId>protegerRecursos</artifactId>
    <version>1.0</version>
</dependency>
```
Este jar toma el nombre del contexto del modulo (war) e invoca un servicio web que le retorna las url (end point) propios del modulo con los roles a proteger.