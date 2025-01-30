package org.iesvdm.modelo;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
	
	private int id;

	@NotBlank(message = "{msg.client.nameBlank}")
	@Size(max = 30, message = "{msg.client.nameMax}")
	@Size(min = 3, message = "{msg.client.nameMin}")
	private String nombre;

	@NotBlank(message = "{msg.client.surname1Blank}")
	@Size(max = 30, message = "{msg.client.surname1Max }")
	private String apellido1;

	// Opcional
	private String apellido2;

	@NotBlank(message = "{msg.client.cityBlank}")
	@Size(max = 50, message = "{msg.client.cityMax}")
	private String ciudad;

	@DecimalMax(value="1000", message = "{msg.client.categoryMax}")
	@DecimalMin(value="100", message = "{msg.client.categoryMin}")
	private int categoria;

	@NotBlank(message = "{msg.client.emailBlank}")
	@Email(message = "Formato de email incorrecto", regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
	private String email;

	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	//@NotBlank(message = "Por favor, introduzca la fecha.")
	//private Date fecha;

}
