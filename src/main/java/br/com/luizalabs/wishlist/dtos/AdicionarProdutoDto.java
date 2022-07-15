package br.com.luizalabs.wishlist.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AdicionarProdutoDto {

	@NotBlank
	private String idProduto;
	@NotBlank
	private String nome;
	
	//O Produto selecionado está atrelado a um cliente
	@NotBlank
	private String clienteId;
}
