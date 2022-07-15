package br.com.luizalabs.wishlist.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import br.com.luizalabs.wishlist.dtos.AdicionarProdutoDto;
import br.com.luizalabs.wishlist.dtos.RemoverProdutoDto;
import br.com.luizalabs.wishlist.dtos.ResponseWishlistDto;
import br.com.luizalabs.wishlist.dtos.WishlistDto;
import br.com.luizalabs.wishlist.exception.DadosIncorretosException;
import br.com.luizalabs.wishlist.exception.DadosNaoExistenteException;
import br.com.luizalabs.wishlist.exception.OperacaoInvalidaException;
import br.com.luizalabs.wishlist.models.WishlistModel;
import br.com.luizalabs.wishlist.repository.WishlistRepository;
import br.com.luizalabs.wishlist.services.impl.WishlistServiceImpl;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceImplTest {

	@InjectMocks
	private WishlistServiceImpl wishlistService;

	@Mock
	private WishlistRepository wishlistRepository;

	private WishlistDto wishlistDto;
	private WishlistModel wishlistModel;
	private Optional<WishlistModel> optionalWishlistModel;
	private WishlistDto wishlistDtoNovo;
	private WishlistModel wishlistModelNovo;
	private WishlistModel wishlistModelRetorno;

	private AdicionarProdutoDto adicionarProdutoDto;
	private Optional<WishlistModel> optionalWishlistModelRetorno;
	private WishlistModel wishlistModelComLista;

	@BeforeEach
	void setup() {
		wishlistDto = new WishlistDto();
		wishlistDto.setClienteId("testeFulano");

		wishlistModel = new WishlistModel();
		wishlistModel.setClienteId("testeFulano");
		wishlistModel.setProdutosId(List.of());
		wishlistModel.setId("3543afvb1251id");

		optionalWishlistModel = Optional.of(wishlistModel);

		wishlistDtoNovo = new WishlistDto();
		wishlistDtoNovo.setClienteId("testandoClienteId");

		wishlistModelNovo = new WishlistModel();
		wishlistModelNovo.setClienteId("testandoClienteId");
		wishlistModelNovo.setId("99999999abc88888");
		wishlistModelNovo.setProdutosId(List.of());

		wishlistModelRetorno = new WishlistModel();
		wishlistModelRetorno.setClienteId("testandoClienteId");
		wishlistModelRetorno.setProdutosId(List.of());
		wishlistModelRetorno.setId(null);

		adicionarProdutoDto = new AdicionarProdutoDto();
		adicionarProdutoDto.setClienteId("clienteId");
		adicionarProdutoDto.setIdProduto("produtoId");
		adicionarProdutoDto.setNome("nome");

		List<String> list = new ArrayList<String>();
		list.add("primeiroItem");
		list.add("segundoItem");

		wishlistModelComLista = new WishlistModel();
		wishlistModelComLista.setClienteId("clienteId");
		wishlistModelComLista.setProdutosId(list);
		wishlistModelComLista.setId("3543afvb1251id");

		optionalWishlistModelRetorno = Optional.of(wishlistModelComLista);
	}

	@Test
	void criarWishlist() {
		when(wishlistRepository.findByClienteId(wishlistDto.getClienteId())).thenReturn(optionalWishlistModel);

		assertThrows(OperacaoInvalidaException.class, () -> wishlistService.criarWishlist(wishlistDto));

	}

	@Test
	void criarWishlistVazia() {
		when(wishlistRepository.findByClienteId("testandoClienteId")).thenReturn(Optional.empty());

		var response = wishlistService.criarWishlist(wishlistDtoNovo);

		var responseWishlistDto = new ResponseWishlistDto();
		BeanUtils.copyProperties(response, responseWishlistDto);
		
		assertEquals(responseWishlistDto, response);
	}

	@Test
	void adicionarProduto() {
		when(wishlistRepository.findByClienteId("clienteId")).thenReturn(optionalWishlistModelRetorno);

		var response = assertDoesNotThrow(() -> wishlistService.adicionarProduto(adicionarProdutoDto));
		assertNotNull(response);
	}

	@Test
	void adicionarProdutoEmListaNaoExistente() {
		when(wishlistRepository.findByClienteId(adicionarProdutoDto.getClienteId())).thenReturn(Optional.empty());

		assertThrows(DadosNaoExistenteException.class, () -> wishlistService.adicionarProduto(adicionarProdutoDto));
	}

	@Test
	void adicionarProdutoListaCheia() {

		List<String> produtosId = IntStream.range(0, 20).mapToObj(String::valueOf).collect(Collectors.toList());
		WishlistModel model = new WishlistModel();
		model.setProdutosId(produtosId);
		Optional<WishlistModel> optionalModel = Optional.of(model);

		when(wishlistRepository.findByClienteId("clienteId")).thenReturn(optionalModel);

		assertThrows(OperacaoInvalidaException.class, () -> wishlistService.adicionarProduto(adicionarProdutoDto));

	}

	@Test
	void removerProduto() {
		when(wishlistRepository.findByClienteId("clienteId")).thenReturn(optionalWishlistModelRetorno);

		var removerProdutoDto = new RemoverProdutoDto();
		removerProdutoDto.setClienteId("clienteId");
		removerProdutoDto.setIdProduto("segundoItem");

		var response = assertDoesNotThrow(() -> wishlistService.removerProduto(removerProdutoDto));
		assertNotNull(response);
	}

	@Test
	void removerProdutoEmListaNaoExistente() {
		var removerProdutoDto = new RemoverProdutoDto();
		removerProdutoDto.setClienteId("clienteId");
		removerProdutoDto.setIdProduto("segundoItem");

		when(wishlistRepository.findByClienteId("clienteId")).thenReturn(Optional.empty());

		assertThrows(DadosNaoExistenteException.class, () -> wishlistService.removerProduto(removerProdutoDto));
	}

	@Test
	void removerProdutoNaoExistenteNaLista() {
		var removerProdutoDto = new RemoverProdutoDto();
		removerProdutoDto.setClienteId("clienteId");
		removerProdutoDto.setIdProduto("terceiroItem");

		when(wishlistRepository.findByClienteId("clienteId")).thenReturn(optionalWishlistModelRetorno);

		assertThrows(DadosNaoExistenteException.class, () -> wishlistService.removerProduto(removerProdutoDto));
	}

	@Test
	void consultarTodosProdutos() {
		when(wishlistRepository.findByClienteId("clienteId")).thenReturn(optionalWishlistModelRetorno);

		var response = wishlistService.consultarTodosProdutos("clienteId");
		assertNotNull(response);
	}

	@Test
	void consultarUnicoProduto() {
		when(wishlistRepository.findByClienteIdAndProdutosIdIn("clienteId", "produtoId"))
				.thenReturn("retornoDoServico");

		String clienteId = "clienteId";
		String produtoId = "produtoId";

		var response = wishlistService.consultarUnicoProduto("clienteId", "produtoId");
		assertNotNull(response);

	}

	@Test
	void consultarUnicoProdutoComClienteIdInvalido() {
		assertThrows(DadosIncorretosException.class, () -> wishlistService.consultarUnicoProduto("", "produtoId"));

	}
	
	@Test
	void consultarUnicoProdutoComProdutoIdInvalido() {
		assertThrows(DadosIncorretosException.class, () -> wishlistService.consultarUnicoProduto("clienteId", ""));

	}
}