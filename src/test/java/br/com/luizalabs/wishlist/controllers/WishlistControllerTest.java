package br.com.luizalabs.wishlist.controllers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.luizalabs.wishlist.dtos.AdicionarProdutoDto;
import br.com.luizalabs.wishlist.dtos.RemoverProdutoDto;
import br.com.luizalabs.wishlist.dtos.ResponseWishlistDto;
import br.com.luizalabs.wishlist.dtos.WishlistDto;
import br.com.luizalabs.wishlist.models.WishlistModel;
import br.com.luizalabs.wishlist.services.WishlistService;

@ExtendWith(MockitoExtension.class)
public class WishlistControllerTest {

	@InjectMocks
	private WishlistController wishlistController;

	@Mock
	private WishlistService wishlistService;

	private WishlistDto wishlistDto;
	private ResponseWishlistDto wishlistModel;
	private AdicionarProdutoDto adicionarProdutoDto;
	private ResponseWishlistDto wishlistModelComLista;
	private RemoverProdutoDto removerProdutoDto;

	private String clienteId = "passandoClienteId";
	private Optional<ResponseWishlistDto> optionalWishlistModel;

	private String produtoId = "passandoProdutoId";
	private String retornoProduto = "retornouProduto";
	
	@BeforeEach
	void setup() {
		wishlistDto = new WishlistDto();
		wishlistDto.setClienteId("testeFulano");

		wishlistModel = new ResponseWishlistDto();
		wishlistModel.setClienteId("testeFulano");
		wishlistModel.setProdutosId(List.of());
		wishlistModel.setId("3543afvb1251id");

		adicionarProdutoDto = new AdicionarProdutoDto();
		adicionarProdutoDto.setClienteId("testeFulano");
		adicionarProdutoDto.setIdProduto("testeTeclado");
		adicionarProdutoDto.setNome("Teclado mecânico");

		wishlistModelComLista = new ResponseWishlistDto();
		wishlistModelComLista.setClienteId("testeFulano");
		wishlistModelComLista.setProdutosId(List.of("testeTeclado"));
		wishlistModelComLista.setId("3543afvb1251id");

		removerProdutoDto = new RemoverProdutoDto();
		removerProdutoDto.setClienteId("testando");
		removerProdutoDto.setIdProduto("testeRemoção");

		optionalWishlistModel = Optional.of(wishlistModelComLista);
		
	}

	@Test
	void criarWishlist() {

		when(wishlistService.criarWishlist(wishlistDto)).thenReturn(wishlistModel);

		var response = assertDoesNotThrow(() -> wishlistController.criarWishlist(wishlistDto));

		assertNotNull(response);
		assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(wishlistModel), response);
	}

	@Test
	void adicionarProduto() {

		when(wishlistService.adicionarProduto(adicionarProdutoDto)).thenReturn(wishlistModelComLista);

		var response = assertDoesNotThrow(() -> wishlistController.adicionarProduto(adicionarProdutoDto));

		assertNotNull(response);
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(wishlistModelComLista), response);
	}

	@Test
	void removerProduto() {

		when(wishlistService.removerProduto(removerProdutoDto)).thenReturn(wishlistModel);

		var response = assertDoesNotThrow(() -> wishlistController.removerProduto(removerProdutoDto));

		assertNotNull(response);
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(wishlistModel), response);
	}

	@Test
	void consultarTodosProdutosRetornoOk() {
		
		var wishlistModel1 = new WishlistModel();
		wishlistModel1.setClienteId("testeFulano");
		wishlistModel1.setProdutosId(List.of("testeTeclado"));
		wishlistModel1.setId("3543afvb1251id");
		var optionalWishlist = Optional.of(wishlistModel1);

		when(wishlistService.consultarTodosProdutos(clienteId)).thenReturn(optionalWishlist);

		var response = assertDoesNotThrow(() -> wishlistController.consultarTodosProdutos(clienteId));

		assertNotNull(response);
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(optionalWishlist.get().getProdutosId()), response);

	}

	@Test
	void consultarTodosProdutosRetornoNaoEncontrado() {

		when(wishlistService.consultarTodosProdutos(clienteId)).thenReturn(Optional.empty());

		var response = assertDoesNotThrow(() -> wishlistController.consultarTodosProdutos(clienteId));

		assertNotNull(response);
		assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(), response);

	}

	@Test
	void consultarUnicoProduto() {

		when(wishlistService.consultarUnicoProduto(clienteId, produtoId)).thenReturn(retornoProduto);

		var response = assertDoesNotThrow(() -> wishlistController.consultarUnicoProduto(clienteId, produtoId));

		assertNotNull(response);
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(true), response);

	}

	@Test
	void consultarUnicoProdutoNãoEncontrado() {

		when(wishlistService.consultarUnicoProduto(clienteId, produtoId)).thenReturn("");

		var response = assertDoesNotThrow(() -> wishlistController.consultarUnicoProduto(clienteId, produtoId));

		assertNotNull(response);
		assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(), response);

	}
	
	@Test
	void consultarUnicoProdutoNull() {

		when(wishlistService.consultarUnicoProduto(clienteId, produtoId)).thenReturn(null);

		var response = assertDoesNotThrow(() -> wishlistController.consultarUnicoProduto(clienteId, produtoId));

		assertNull(response.getBody());
		assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(), response);

	}
}
