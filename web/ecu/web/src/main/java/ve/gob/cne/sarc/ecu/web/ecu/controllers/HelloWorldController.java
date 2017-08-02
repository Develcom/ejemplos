package ve.gob.cne.sarc.ecu.web.ecu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView helloWorld() {

        String message = "<br><div style='text-align:center;'>"
                + "<h3>Bienvenido</h3> Este mensaje viene de un controlador llamado HelloWorldController</div><br><br>";
        return new ModelAndView("welcome", "message", message);
    }
}