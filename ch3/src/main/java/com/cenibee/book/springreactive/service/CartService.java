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
                .log("foundCart")
                .defaultIfEmpty(new Cart("My Cart"))
                .log("emptyCart")
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem().getId().equals(id))
                        .findAny()
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        })
                        .orElseGet(() -> this.itemRepository.findById(id)
                                .log("fetchedItem")
                                .map(CartItem::new)
                                .log("cartItem")
                                .map(cartItem -> {
                                    cart.getCartItems().add(cartItem);
                                    return cart;
                                }).log("addedCartItem")
                        ))
                .log("cartWithAnotherItem")
                .flatMap(this.cartRepository::save)
                .log("savedCart");
    }

}
