package br.com.luizalabs.wishlist.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ConsultarProduto {
	@NotBlank
	private String idProduto;
	
	//O Produto selecionado está atrelado a um cliente
	@NotBlank
	private String clienteId;
}
