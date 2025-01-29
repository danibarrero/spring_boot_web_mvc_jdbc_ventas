package org.iesvdm.service;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComercialService {

    @Autowired
    private ComercialDAO comercialDAO;


    public List<Comercial> listAll() {

        return comercialDAO.getAll();

    }

    public Comercial one(Integer id) {

        Optional<Comercial> optCom = comercialDAO.find(id);
        if (optCom.isPresent())
            return optCom.get();
        else
            return null;

    }

    public void newComercial(Comercial comercial) {

        comercialDAO.create(comercial);

    }

    public void replaceComercial(Comercial comercial) {

        comercialDAO.update(comercial);

    }

    public void deleteComercial(int id) {

        comercialDAO.delete(id);

    }

    public ComercialDTO totalPedidos(int id) {

        return comercialDAO.totalPedidos(id);

    }

    public ComercialDTO mediaPedidos(int id) {

        return comercialDAO.mediaPedidos(id);

    }

    public List<ClienteDTO>listaCuantia(int id) {

        return comercialDAO.listaCuantia(id);

    }

}
