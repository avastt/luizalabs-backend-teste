package br.com.luizalabs.wishlist.dtos;

import java.util.List;

import lombok.Data;

@Data
public class ResponseWishlistDto {

	private String id;
    private List<String> produtosId;
    
    //A wishlist estará atrelada a um clientId
    private String clienteId;
}
