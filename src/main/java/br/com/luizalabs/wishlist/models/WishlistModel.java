package br.com.luizalabs.wishlist.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("wishlist")
public class WishlistModel {

	@Id
	private String id;
    private List<String> produtosId;
    
    //A wishlist estará atrelada a um clientId
    private String clienteId;
	
}
