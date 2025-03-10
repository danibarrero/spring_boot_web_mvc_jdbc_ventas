package org.iesvdm.controlador;

import jakarta.validation.Valid;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.dao.PedidoDAOImpl;
import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Comparator;
import java.util.List;

@Controller
public class ComercialController {

    @Autowired
    private ComercialService comercialService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PedidoDAOImpl pedidoDAO;

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
    public String submitCrear(@Valid @ModelAttribute("comercial") Comercial comercial, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("comercial", comercial);
            return "crear-comerciales";
        }

        comercialService.newComercial(comercial);

        return "redirect:/comerciales";

    }

    // DETALLE
    @GetMapping("/comerciales/{id}")
    public String detalle(@PathVariable Integer id,Model model) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        List<Pedido> listaPedidos = pedidoDAO.filterByComercialId(id);
        model.addAttribute("listaPedidos", listaPedidos);

        Cliente cliente = clienteService.one(id);
        model.addAttribute("cliente", cliente);

        ComercialDTO comercialDTOTotal = comercialService.totalPedidos(id);
        model.addAttribute("comercialDTOTotal", comercialDTOTotal);

        ComercialDTO comercialDTOMedia = comercialService.mediaPedidos(id);
        model.addAttribute("comercialDTOMedia", comercialDTOMedia);

        List <PedidoDTO> listaPedidosDTO = pedidoDAO.filterByComercialIdDTO(id);
        model.addAttribute("listaPedidosDTO", listaPedidosDTO);

        PedidoDTO maxPedido = listaPedidosDTO.stream()
                .max(Comparator.comparingDouble(PedidoDTO::getTotal))
                .orElse(null);
        model.addAttribute("maxPedido", maxPedido);

        PedidoDTO minPedido = listaPedidosDTO.stream()
                .min(Comparator.comparingDouble(PedidoDTO::getTotal))
                .orElse(null);
        model.addAttribute("minPedido", minPedido);

        List<ClienteDTO> listaClienteDTO = comercialService.listaCuantia(id);
        model.addAttribute("listaClienteDTO", listaClienteDTO);

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
