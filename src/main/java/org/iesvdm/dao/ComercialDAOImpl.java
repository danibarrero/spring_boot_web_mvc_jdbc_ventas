package org.iesvdm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO {

	private JdbcTemplate jdbcTemplate;

	/**
	 * Inserta en base de datos el nuevo Comercial, actualizando el id en el bean Cliente.
	 */
	@Override
	public synchronized void create(Comercial comercial) {

		String sqlInsert = """
                INSERT INTO comercial (nombre, apellido1, apellido2, comision) 
                VALUES  (     ?,         ?,         ?,       ?)
                """;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[]{"id"});
			int idx = 1;
			ps.setString(idx++, comercial.getNombre());
			ps.setString(idx++, comercial.getApellido1());
			ps.setString(idx++, comercial.getApellido2());
			ps.setDouble(idx, comercial.getComision());
			return ps;
		}, keyHolder);

		comercial.setId(keyHolder.getKey().intValue());

		log.info("Insertados {} registros.", rows);
	}

	/**
	 * Devuelve lista con todos los Comerciales.
	 */
	@Override
	public List<Comercial> getAll() {
		
		List<Comercial> listComercial = jdbcTemplate.query(
                "SELECT * FROM comercial",
                (rs, rowNum) -> new Comercial(rs.getInt("id"), 
                							  rs.getString("nombre"), 
                							  rs.getString("apellido1"),
                							  rs.getString("apellido2"), 
                							  rs.getFloat("comision"))
                						 	
        );
		
		log.info("Devueltos {} registros.", listComercial.size());
		
        return listComercial;
	}

	/**
	 * Devuelve Optional de Comercial con el ID dado.
	 */
	@Override
	public Optional<Comercial> find(int id) {

		Comercial comercial = jdbcTemplate
				.queryForObject("SELECT * FROM comercial WHERE id = ?"
						, (rs, rowNum) -> new Comercial(rs.getInt("id"),
								rs.getString("nombre"),
								rs.getString("apellido1"),
								rs.getString("apellido2"),
								rs.getFloat("comision"))
						, id
				);

		if (comercial != null) {
			return Optional.of(comercial);
		} else {
			log.info("Comercial no encontrado.");
			return Optional.empty();
		}

	}

	/**
	 * Actualiza Comercial según ID.
	 */
	@Override
	public void update(Comercial comercial) {

		int rows = jdbcTemplate.update("""
                        UPDATE comercial SET 
                        				nombre = ?, 
                        				apellido1 = ?, 
                        				apellido2 = ?,
                        				comision = ?
                        		WHERE id = ?
                        """, comercial.getNombre()
				, comercial.getApellido1()
				, comercial.getApellido2()
				, comercial.getComision()
				, comercial.getId());

		log.info("Update de Comercial con {} registros actualizados.", rows);

	}

	/**
	 * Borra Comercial con ID proporcionado.
	 */
	@Override
	public void delete(int id) {

		int rowsPedido = jdbcTemplate.update("DELETE FROM pedido WHERE id_comercial = ?", id);
		int rows = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);

		log.info("Delete de Comercial con {} registros eliminados.", rows);

	}

}
