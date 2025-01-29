package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.ClienteDAOImpl;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAOImpl;
import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PedidoDAOImpl pedidoDAO;

	@Autowired
	private ComercialService comercialService;
    @Autowired
    private ClienteDAOImpl clienteDAOImpl;


	// LISTAR
	@GetMapping("/clientes")
	public String listar(Model model) {

		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);

		return "clientes";

	}

	//CREAR
	@GetMapping("/clientes/crear")
	public String crear (Model model) {

		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);

		return "crear-clientes";

	}

	@PostMapping("/clientes/crear")
	public RedirectView submitCrear(@ModelAttribute("cliente") Cliente cliente) {

		clienteService.newCliente(cliente);

		return new RedirectView("/clientes") ;

	}

	// DETALLE
	@GetMapping("/clientes/{id}")
	public String detalle(Model model, @PathVariable Integer id) {

		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);

		List<Pedido> listaPedidos = pedidoDAO.filterByComercialId(id);
		model.addAttribute("listaPedidos", listaPedidos);

		List<Comercial> listaComerciales = comercialService.listAll();
		model.addAttribute("listaComerciales", listaComerciales);

		ClienteDTO clienteDTO = clienteDAOImpl.conteoComeriales(id);
		model.addAttribute("clienteDTO", clienteDTO);

		return "detalle-clientes";

	}

	// EDITAR
	@GetMapping("/clientes/editar/{id}")
	public String editar (Model model , @PathVariable Integer id) {

		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);

		return "editar-clientes";

	}

	@PostMapping("/clientes/editar/{id}")
	public RedirectView editarSubmit(@ModelAttribute("cliente") Cliente cliente) {
		clienteService.replaceCliente(cliente);

		return new RedirectView("/clientes");
	}

	// BORRAR
	@PostMapping("/clientes/borrar/{id}")
	public RedirectView submitBorrar ( @PathVariable Integer id) {
		clienteService.deleteCliente(id);

		return new RedirectView("/clientes") ;
	}

}