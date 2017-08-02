<%@page import="org.springframework.ui.Model"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.lang.String"%>
<%@ page import="java.util.logging.Logger"%>
<%!Logger log=Logger.getLogger(getClass().getName()); %>

 <div class="panel-group" id="atencion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="acordionTab" role="tab" id="head1">
                <span>Registro de Personas que Intervienen en el Acto o Hecho</span>
            </div>
            <div id="panel1" collapse="panel!=1" role="tabpanel" aria-labelledby="tipo">
                <div class="panel-body" ng-controller='participantesController'>
                    <div class="row" style='margin-left: 0px; margin-right: 0px;' id="datosDeclarante" ng-hide="entePublico">
                        <div class="row"><label>Declarantes:</label></div>
                        <div class="row acordionTab" style="border-radius: 4px;">Condici&oacute;n de los Declarantes</div>
                        <div class="row" style="overflow-y:auto;height: 250px;">
                            <div class='row' ng-repeat="item in lista">
                                <div class="col-sm-5">
                                    <input type="checkbox"  ng-model="marca" ng-click="almacenarMarcados(item,marca,$event)" />
                                    <label style="margin-left: 20px;">{{item}}</label>
                                </div>
                                <div class="col-sm-3" ng-show="marca">
                                    <input id="ident1" type="checkbox" ng-model="sinDoc"/>
                                    <label style="margin-left: 20px;">No posee identificaci&oacute;n</label>
                                </div>  
                                <div class="col-sm-4" ng-show="sinDoc">
                                    <span ng-repeat="doc in lista" ><label><input type="radio" name="tipoDocumento">{{doc}}</label><br/></span>
                                </div>
                            </div>
                        </div>
                    </div><!-- Fin Lista Declarantes, seoculta si la solicitud es de un Ente publico -->
                    
                    <div class="row"  id="datosEntePublico" ng-show="entePublico"> <!-- Inician Datos de la solicitud por ente -->
                        <div class="row" style='margin-left: 0px; margin-right: 0px;' ><label>&Oacute;rgano o Ente P&uacute;blico:</label></div>
                            <div class="row acordionTab" style="border-radius: 4px;margin-left: 0px; margin-right: 0px;" >&Oacute;rgano o Ente P&uacute;blico y Ciudadanos relacionados:</div>
                            <div class="row" style='margin-left: 0px; margin-right: 0px;' >
                                <div class="col-sm-6"><label>Nombre del &Oacute;rgano o Ente P&uacute;blico:</label></div>
                                <div class="col-sm-6">
                                    <label>{{solicitante}}</label>
                                </div>
                            </div>
                            <div class="row" ng-repeat="participante in lista">
                                <div class="col-sm-1"><input type="checkbox" ng-click=""/></div>
                                <div class="col-sm-10"><label>{{participante}}</label></div>
                            </div>
                    </div>          
                </div><!-- Fin panel-body -->
            </div><!-- Fin Panel1 -->
        </div><!-- Fin Panel Default -->
        <div class="panel panel-default">
            <div class="acordionTabOff" role="tab" id="head2"><span>Participantes del Acto o Hecho</span></div>
            <div id="panel2" collapse="panel!=2" role="tabpanel" aria-labelledby="tramite">
                <div class="panel-body">
                    Se autenticar&aacute;n los siguientes ciudadanos:
                    <div class="row datos" ng-repeat="xparticipante in marcados"><label>{{xparticipante}}</label></div>
                </div>
            </div>
        </div>
        
<!--    PLANILLA DE AUTENTICACION -->
        <div class="panel panel-default" ng-show="panel==3" id="planilla">
            <div ngClass="{acordionTabOff:panel!=3, acordionTab:panel==3}" class="acordionTabOff" role="tab" id="head3"><span>Declarantes a autenticar</span></div>
            <div id="panel3"  collapse="panel!=3" role="tabpanel" aria-labelledby="tramite">
                <div class="panel-body1" id="planillaCiudadano">
                <h1>Panel3</h1>
                </div>
            </div>
        </div>
        <div class="panel panel-default" id="autenticados">
            <div class="acordionTabOff" role="tab" id="head4"><span>Declarantes a autenticar</span></div>
            <div id="panel4" collapse="panel!=4" role="tabpanel" aria-labelledby="tramite">
                <div class="panel-body1" id="ciudadanosAutenticados">
                <h1>panel 4</h1>
                </div>
            </div>
        </div>
        <div class="panel panel-default" id="modeloOficio">
            <div class="acordionTabOff" role="tab" id="head5"><span>Formato del Oficio</span>
            </div>
            <div id="panel5" collapse="panel!=5" role="tabpanel" aria-labelledby="tramite">
                <div class="panel-body1" id="oficio">
                <h1>panel 5</h1>
                </div>
            </div>
        </div>
        <div class="panel panel-default" id="pdfOficio">
            <div class="acordionTabOff" role="tab" id="head6"><span>Oficio</span>
            </div>
            <div id="panel6" collapse="panel!=6" role="tabpanel" aria-labelledby="tramite">
                <div class="panel-body1" style="height:300px" id="oficioFinal">
                    <h1>panel 6</h1>
                </div>
            </div>
        </div>
        <!-- botones de navegacion -->
             <div class="row col-sm-10" id="opciones" style="display:none;" ng-controller="navegacionController">
                <!--<div class="col-sm-2 mensajeria">Acciones:</div>-->
                <div class="col-sm-4">&emsp;</div>
                <div class="col-sm-1 mensajeria" >
                    <button class="btn" id="anteriorBtn" ng-click='regresar()'>Regresar</button>
                </div>
                <div class="col-sm-1">&emsp;</div>
                <div class="col-sm-1 mensajeria" >
                    <button class="btn" id="cancelarBtn" ng-click=''>Cancelar</button>
                </div>
                <div class="col-sm-1">&emsp;</div>
                <div class="col-sm-1 mensajeria" >
                    <button class="btn" id="continuarBtn" ng-click='continuar()'>Continuar</button>
                    <button class="btn" id="finalizarBtn" ng-click=''>Finalizar</button>
                </div>
             </div>
</div><!-- Fin panel-group -->

<script type="text/javascript" src='<c:url value="/js/js.js"/>'></script>
