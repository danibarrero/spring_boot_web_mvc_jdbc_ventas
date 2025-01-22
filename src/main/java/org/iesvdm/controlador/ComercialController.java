package org.iesvdm.controlador;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ComercialController {

    @Autowired
    private ComercialService comercialService;

    // LISTAR
    @GetMapping("/comerciales")
    public String listar(Model model) {

        List<Comercial> listaComerciales =  comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);

        return "comerciales";

    }

    //CREAR
    @GetMapping("/comerciales/crear")
    public String crear (Model model) {

        Comercial comercial = new Comercial();
        model.addAttribute("comercial", comercial);

        return "crear-comerciales";
    }

    @PostMapping("/comerciales/crear")
    public RedirectView submitCrear(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.newComercial(comercial);

        return new RedirectView("/comerciales") ;

    }

    // DETALLE
    @GetMapping("/comerciales/{id}")
    public String detalle(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        return "detalle-comerciales";

    }

    // EDITAR
    @GetMapping("/comerciales/editar/{id}")
    public String editar (Model model , @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        return "editar-comerciales";

    }

    @PostMapping("/comerciales/editar/{id}")
    public RedirectView editarSubmit(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.replaceComercial(comercial);

        return new RedirectView("/comerciales");

    }

    // BORRAR
    @PostMapping("/comerciales/borrar/{id}")
    public RedirectView submitBorrar ( @PathVariable Integer id) {

        comercialService.deleteComercial(id);

        return new RedirectView("/comerciales") ;

    }

}
