package mk.ukim.finki.wp.eshop.service;

import mk.ukim.finki.wp.eshop.model.VideoGame;
import mk.ukim.finki.wp.eshop.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    List<VideoGame> listAllGamesInShoppingCart(Long cartId);
    ShoppingCart getActiveShoppingCart(String username);
    ShoppingCart addGameToShoppingCart(String username, Long productId);
}
