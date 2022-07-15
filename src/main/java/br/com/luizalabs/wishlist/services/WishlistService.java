package br.com.luizalabs.wishlist.services;

import java.util.Optional;

import javax.validation.Valid;

import br.com.luizalabs.wishlist.dtos.AdicionarProdutoDto;
import br.com.luizalabs.wishlist.dtos.RemoverProdutoDto;
import br.com.luizalabs.wishlist.dtos.ResponseWishlistDto;
import br.com.luizalabs.wishlist.dtos.WishlistDto;
import br.com.luizalabs.wishlist.models.WishlistModel;

public interface WishlistService {

	ResponseWishlistDto adicionarProduto(AdicionarProdutoDto produtoDto);

	ResponseWishlistDto criarWishlist(@Valid WishlistDto wishListDto);
	
	ResponseWishlistDto removerProduto(RemoverProdutoDto removerProdutoDto);
	
	Optional<WishlistModel> consultarTodosProdutos(String clienteId);

	String consultarUnicoProduto(String clienteId, String produtoId);
}
