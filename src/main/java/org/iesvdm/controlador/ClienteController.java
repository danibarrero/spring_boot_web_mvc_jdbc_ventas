package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.service.ClienteService;
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