package com.cenibee.book.springreactive.service;

import com.cenibee.book.springreactive.domain.Cart;
import com.cenibee.book.springreactive.domain.CartItem;
import com.cenibee.book.springreactive.repository.CartRepository;
import com.cenibee.book.springreactive.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CartService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public Mono<Cart> addToCart(String cartId, String id) {
        return cartRepository.findById(cartId)
                .defaultIfEmpty(new Cart("My Cart"))
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem().getId().equals(id))
                        .findAny()
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        })
                        .orElseGet(() -> this.itemRepository.findById(id)
                                .map(CartItem::new)
                                .map(cartItem -> {
                                    cart.getCartItems().add(cartItem);
                                    return cart;
                                })))
                .flatMap(this.cartRepository::save);
    }

}
