#Servicio: Funcionario

##Requerimientos:

* Java 7 (jdk)

##Operaciones:

1. **buscarPorLogin:** 
    * ***Descripcion:*** Permite consultar los datos de un funcionario segun su login
    * ***Endpoint:*** /funcionario/buscarPorLogin/
    * ***Método:*** GET
    * ***Entrada:*** login
    * ***Salida:*** Funcionario

2. **buscarFuncionarioPorOficina:** 
    * ***Descripcion:*** Permite consultar los datos de un funcionario segun la oficina y el estatus
    * ***Endpoint:*** /funcionario/buscarFuncionarioPorOficina/{codOficina}/{codTipoFuncionario}/{codEstatusFuncionario}
    * ***Método:*** GET
    * ***Entrada:*** codOficina, codTipoFuncionario, codEstatusFuncionario
    * ***Salida:*** Funcionario	

##Artefactos Generados:
* servicios-funcionario.war

##Dependendencias
* sarc-comunes
* SARC II - Capa de Persistencia