package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	private ClienteDAO clienteDAO;

	@Autowired
	private PedidoDAO pedidoDAO;


	public List<Cliente> listAll() {

		return clienteDAO.getAll();

	}

	public Cliente one(Integer id) {

		Optional<Cliente> optCli = clienteDAO.find(id);
		if (optCli.isPresent())
			return optCli.get();
		else
			return null;

	}

	public void newCliente(Cliente cliente) {

		clienteDAO.create(cliente);

	}

	public void replaceCliente(Cliente cliente) {

		clienteDAO.update(cliente);

	}

	public void deleteCliente(int id) {

		clienteDAO.delete(id);

	}

}
