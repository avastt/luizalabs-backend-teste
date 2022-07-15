package br.com.luizalabs.wishlist.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.luizalabs.wishlist.models.WishlistModel;

public interface WishlistRepository extends MongoRepository<WishlistModel, String> {

	Optional<WishlistModel> findByClienteId(String clienteId);

	@Query(value = "{ 'clienteId' : ?0, 'produtosId' : {$in : [?1] }}")
	String findByClienteIdAndProdutosIdIn(String clienteId, String produtoId);

}
