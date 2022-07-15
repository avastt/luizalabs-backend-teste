package br.com.luizalabs.wishlist.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class WishlistDto {

	@NotBlank
	private String clienteId;
}
