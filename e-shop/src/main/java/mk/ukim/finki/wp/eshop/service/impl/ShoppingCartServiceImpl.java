package mk.ukim.finki.wp.eshop.service.impl;

import mk.ukim.finki.wp.eshop.model.VideoGame;
import mk.ukim.finki.wp.eshop.model.ShoppingCart;
import mk.ukim.finki.wp.eshop.model.User;
import mk.ukim.finki.wp.eshop.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.wp.eshop.model.exceptions.GameAlreadyInShoppingCartException;
import mk.ukim.finki.wp.eshop.model.exceptions.GameNotFoundException;
import mk.ukim.finki.wp.eshop.model.exceptions.ShoppingCartNotFoundException;
import mk.ukim.finki.wp.eshop.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp.eshop.repository.jpa.ShoppingCartRepository;
import mk.ukim.finki.wp.eshop.repository.jpa.UserRepository;
import mk.ukim.finki.wp.eshop.service.VideoGamesService;
import mk.ukim.finki.wp.eshop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final VideoGamesService videoGamesService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository,
                                   VideoGamesService videoGamesService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.videoGamesService = videoGamesService;
    }

    @Override
    public List<VideoGame> listAllGamesInShoppingCart(Long cartId) {
        if(!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);
        return this.shoppingCartRepository.findById(cartId).get().getVideoGames();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository
                .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addGameToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        VideoGame videoGame = this.videoGamesService.findById(productId)
                .orElseThrow(() -> new GameNotFoundException(productId));
        if(shoppingCart.getVideoGames()
                .stream().filter(i -> i.getId().equals(productId))
                .collect(Collectors.toList()).size() > 0)
            throw new GameAlreadyInShoppingCartException(productId, username);
        shoppingCart.getVideoGames().add(videoGame);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
