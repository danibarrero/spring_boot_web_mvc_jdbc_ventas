package org.iesvdm.modelo;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comercial {

	private int id;

	@NotBlank(message = "{msg.customer.nameBlank}")
	@Size(max = 30, message = "{msg.customer.nameMax}")
	private String nombre;

	@NotBlank(message = "{msg.customer.surname1Blank}")
	@Size(max = 30, message = "{msg.customer.surname1Max}")
	private String apellido1;

	// Opcional
	private String apellido2;

	@DecimalMin(value = "0.276", message = "{msg.customer.comisionMin}")
	@DecimalMax(value = "0.946", message = "{msg.customer.comisionMax}")
	private BigDecimal comision;

}
