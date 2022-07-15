package br.com.luizalabs.wishlist.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.luizalabs.wishlist.dtos.AdicionarProdutoDto;
import br.com.luizalabs.wishlist.dtos.RemoverProdutoDto;
import br.com.luizalabs.wishlist.dtos.ResponseWishlistDto;
import br.com.luizalabs.wishlist.dtos.WishlistDto;
import br.com.luizalabs.wishlist.exception.DadosIncorretosException;
import br.com.luizalabs.wishlist.exception.DadosNaoExistenteException;
import br.com.luizalabs.wishlist.exception.OperacaoInvalidaException;
import br.com.luizalabs.wishlist.models.WishlistModel;
import br.com.luizalabs.wishlist.repository.WishlistRepository;
import br.com.luizalabs.wishlist.services.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService {

	private static final int LIMITE = 20;

	@Autowired
	private WishlistRepository wishlistRepository;

	@Transactional
	public ResponseWishlistDto criarWishlist(WishlistDto wishListDto) {

		Optional<WishlistModel> optionalwishlistModel = wishlistRepository.findByClienteId(wishListDto.getClienteId());

		if (!optionalwishlistModel.isEmpty()) {
			throw new OperacaoInvalidaException("Error: A Wishlist já existe!");
		}

		var wishlistModel = new WishlistModel();
		BeanUtils.copyProperties(wishListDto, wishlistModel);
		wishlistModel.setProdutosId(List.of());

		wishlistRepository.save(wishlistModel);

		var retorno = new ResponseWishlistDto();
		BeanUtils.copyProperties(wishlistModel, retorno);
		
		return retorno;
	}

	@Transactional
	public ResponseWishlistDto adicionarProduto(AdicionarProdutoDto adicionarProdutoDto) {

		Optional<WishlistModel> optionalWishlistModel = wishlistRepository
				.findByClienteId(adicionarProdutoDto.getClienteId());
		if (optionalWishlistModel.isEmpty()) {
			throw new DadosNaoExistenteException("Error: A Wishlist não existe!");
		}

		var wishlistModel = optionalWishlistModel.get();
		if (wishlistModel.getProdutosId().size() >= LIMITE) {
			throw new OperacaoInvalidaException("Error: A Wishlist está cheia!");
		}

		wishlistModel.getProdutosId().add(adicionarProdutoDto.getIdProduto());
		wishlistRepository.save(wishlistModel);
		
		var retorno = new ResponseWishlistDto();
		BeanUtils.copyProperties(wishlistModel, retorno);

		return retorno;
	}

	@Transactional
	public ResponseWishlistDto removerProduto(RemoverProdutoDto removerProdutoDto) {

		Optional<WishlistModel> optionalWishlistModel = wishlistRepository
				.findByClienteId(removerProdutoDto.getClienteId());
		if (optionalWishlistModel.isEmpty()) {
			throw new DadosNaoExistenteException("Error: A Wishlist não existe!");
		}

		var wishlistModel = optionalWishlistModel.get();

		if (!optionalWishlistModel.get().getProdutosId().contains(removerProdutoDto.getIdProduto())) {
			throw new DadosNaoExistenteException("Error: O produto não existe nessa lista!");
		}

		wishlistModel.getProdutosId().remove(removerProdutoDto.getIdProduto());
		wishlistRepository.save(wishlistModel);
		
		var retorno = new ResponseWishlistDto();
		BeanUtils.copyProperties(wishlistModel, retorno);

		return retorno;
	}

	public Optional<WishlistModel> consultarTodosProdutos(String clienteId) {

		Optional<WishlistModel> optionalWishlistModel = wishlistRepository.findByClienteId(clienteId);
		return optionalWishlistModel;
	}

	public String consultarUnicoProduto(String clienteId, String produtoId) {

		if (!StringUtils.hasLength(clienteId))
			throw new DadosIncorretosException("É obrigatório informar a wishlist(clienteId).");
		if (!StringUtils.hasLength(produtoId))
			throw new DadosIncorretosException("É obrigatório informar o produtoId.");

		String produto = wishlistRepository.findByClienteIdAndProdutosIdIn(clienteId, produtoId);

		return produto;
	}

}
