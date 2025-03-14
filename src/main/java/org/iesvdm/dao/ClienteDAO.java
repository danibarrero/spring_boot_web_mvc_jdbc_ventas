package org.iesvdm.dao;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;

public interface ClienteDAO {

	void create(Cliente cliente);
	List<Cliente> getAll();
	Optional<Cliente>  find(int id);
	void update(Cliente cliente);
	void delete(int id);

}
