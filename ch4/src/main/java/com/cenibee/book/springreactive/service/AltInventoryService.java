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
public class AltInventoryService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    public Mono<Cart> addItemToCart(String cartId, String itemId) {
        Cart myCart = this.cartRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId))
                .block();

        return myCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                .findAny()
                .map(cartItem -> {
                    cartItem.increment();
                    return Mono.just(myCart);
                })
                .orElseGet(() -> this.itemRepository.findById(itemId)
                        .map(CartItem::new)
                        .map(cartItem -> {
                            myCart.getCartItems().add(cartItem);
                            return myCart;
                        }))
                .flatMap(this.cartRepository::save);
    }

}
