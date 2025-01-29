package org.iesvdm.dao;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Comercial;

public interface ComercialDAO {
	
	void create(Comercial comercial);
	List<Comercial> getAll();
	Optional<Comercial> find(int id);
	void update(Comercial comercial);
	void delete(int id);

	ComercialDTO totalPedidos (int id);
	ComercialDTO mediaPedidos (int id);
	List<ClienteDTO>listaCuantia(int id);

}
