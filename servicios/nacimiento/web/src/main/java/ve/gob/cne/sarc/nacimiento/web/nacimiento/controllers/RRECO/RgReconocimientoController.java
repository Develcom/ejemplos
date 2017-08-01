package ve.gob.cne.sarc.nacimiento.web.nacimiento.controllers.RRECO;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.funcionario.FuncionarioServicio;
import ve.gob.cne.sarc.generales.solicitud.SolicitudServicio;
import ve.gob.cne.sarc.generales.util.Util;


@Controller
@RestController
public class RgReconocimientoController {

    @Autowired
    ServletContext context;

    Logger log = Logger.getLogger(getClass().getName());
    private final String rutaHtml = "/resources/site/RRECO/page/templates/";

    SolicitudServicio solicitud = new SolicitudServicio();

    FuncionarioServicio funcionario = new FuncionarioServicio();

    //metodo iniciar tramite
    @RequestMapping(value = "/iniciarTramiteReconocimiento", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public
    @ResponseBody
    String iniciar(@RequestBody String data, HttpServletRequest request,
                   HttpSession session, HttpServletResponse response) throws Exception {
        log.info("********************iniciarTramiteReconocimiento************");


        //TODO REMOVER EL COMENTARIO DE LAS SIGUIENTES 3 LINEAS CUANDO EXISTA INTEGRACION CON GPT
//		PaqueteBO paqueteBO = obtenerPaquete(Long.valueOf(data.get("id")));
//		Solicitud result = new ObjectMapper().readValue(paqueteBO.getPayload()
//				.toJSONString(), Solicitud.class);
        //TODO ELIMINAR LAS SIGUIENTES LINEAS CUANDO EXISTA INTEGRACION CON GPT
        /*SolicitudServicioCliente cSol= new SolicitudServicioCliente();
          log.info("********Consultando el ID: "+data.get("id"));
          Solicitud result = cSol.consultarDetalleSolicitud(data.get("id"));*/
        JSONObject modelo = new JSONObject(data);
        String htmlInicio = null;

        Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));

        log.info("entro: " + sol.getCodigoEstadoSolicitud());


        htmlInicio = buscarHtml(sol.getCodigoEstadoSolicitud());
        String vista = Util.leerArchivo(context.getRealPath(rutaHtml + htmlInicio + ".html"));
        log.info("******Contenido Html************" + vista);

        JSONObject respuesta = new JSONObject();
        modelo.put("titulo", "Validar recaudos");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);

        return respuesta.toString();
    }


    public String buscarHtml(String estatus) {
        String html = null;
        //if("AB".equals(estatus)){
        // html = "validar_recaudo";
        // //	}
        switch (estatus) {
            case "PV":
                //html = "";
                html = "validar_recaudo";
                break;
            case "AB":
                //html="datos_reconocido_residencia";
                html = "validar_recaudo";
                break;
            default:
                html = "validar_recaudo";
                break;
        }
        return html;

    }


    @RequestMapping(value = "/pantalla2", method = RequestMethod.POST)
    public
    @ResponseBody
    String consultarTipoPermiso(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws IOException, JSONException {
        log.info("*************Tipo de permiso************" + sesion.getId());

        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml + "tipo_permiso.html"));

        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);

        return respuesta.toString();
    }


}