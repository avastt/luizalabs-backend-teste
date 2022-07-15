package br.com.luizalabs.wishlist.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizalabs.wishlist.dtos.AdicionarProdutoDto;
import br.com.luizalabs.wishlist.dtos.RemoverProdutoDto;
import br.com.luizalabs.wishlist.dtos.ResponseWishlistDto;
import br.com.luizalabs.wishlist.dtos.WishlistDto;
import br.com.luizalabs.wishlist.models.WishlistModel;
import br.com.luizalabs.wishlist.services.WishlistService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/wishlist")
@CrossOrigin(origins = "*", maxAge = 3600)
@ApiOperation("Wishlist API")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	@PostMapping("/criarWishlist")
	@ApiOperation(value = "Criar uma nova Wishlist do Cliente", notes = "Retorna uma nova lista criada")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Lista criada com sucesso!")
	})
	public ResponseEntity<ResponseWishlistDto> criarWishlist(@RequestBody @Valid WishlistDto wishListDto) {

		log.debug("POST criarWishlist WishlistDto recebido {} ", wishListDto.toString());
		var wishlistModel = wishlistService.criarWishlist(wishListDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(wishlistModel);
	}

	@PostMapping("/adicionarProduto")
	@ApiOperation(value = "Adicionar um novo Produto na Wishlist", notes = "Retorna a lista com o produto inserido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Produto adicionado com sucesso!")
	})
	public ResponseEntity<ResponseWishlistDto> adicionarProduto(@RequestBody @Valid AdicionarProdutoDto adicionarProdutoDto) {

		log.debug("POST adicionarProduto adicionarProdutoDto recebido {} ", adicionarProdutoDto.toString());
		var wishlistModel = wishlistService.adicionarProduto(adicionarProdutoDto);

		return ResponseEntity.status(HttpStatus.OK).body(wishlistModel);
	}

	@DeleteMapping("/removerProduto")
	@ApiOperation(value = "Remover um Produto na Wishlist", notes = "Retorna a lista com o produto removido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Produto removido com sucesso!")
	})
	public ResponseEntity<ResponseWishlistDto> removerProduto(@RequestBody @Valid RemoverProdutoDto removerProdutoDto) {

		log.debug("POST adicionarProduto adicionarProdutoDto recebido {} ", removerProdutoDto.toString());
		var wishlistModel = wishlistService.removerProduto(removerProdutoDto);

		return ResponseEntity.status(HttpStatus.OK).body(wishlistModel);
	}

	@GetMapping("/consultarTodosProdutos/{clienteId}")
	@ApiOperation(value = "Consulta todos os produtos de uma Wishlist", notes = "Retorna todos os produtos de uma Wishlist")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lista de produtos retornada com sucesso!"),
			@ApiResponse(code = 204, message = "Lista vazia de produtos!")
	})
	public ResponseEntity<List<String>> consultarTodosProdutos(@PathVariable("clienteId") String clienteId) {

		Optional<WishlistModel> optionalWishlistModel = wishlistService.consultarTodosProdutos(clienteId);
		if (optionalWishlistModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optionalWishlistModel.get().getProdutosId());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/consultar/{clienteId}/produto/{produtoId}")
	@ApiOperation(value = "Verifica se o produto existe na Wishlist", notes = "Retorna se o produto existe na Wishlist")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "O produto pesquisado existe nessa Wishlist!"),
			@ApiResponse(code = 204, message = "O produto pesquisado não existe nessa Wishlist!")
	})
	public ResponseEntity<Boolean> consultarUnicoProduto(@PathVariable("clienteId") String clienteId,
			@PathVariable("produtoId") String produtoId) {

		String produto = wishlistService.consultarUnicoProduto(clienteId, produtoId);

		if (produto == null || produto.isBlank()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(true);
	}
}
