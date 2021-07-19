package com.cenibee.book.springreactive.service;

import com.cenibee.book.springreactive.domain.Cart;
import com.cenibee.book.springreactive.domain.CartItem;
import com.cenibee.book.springreactive.domain.Item;
import com.cenibee.book.springreactive.repository.CartRepository;
import com.cenibee.book.springreactive.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.byExample;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Service
public class InventoryService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    private final ReactiveFluentMongoOperations fluentOperations;

    public Flux<Item> searchByFluentExample(String name, String description) {
        return fluentOperations.query(Item.class)
                .matching(query(where("TV tray").is(name).and("Smurf").is(description)))
                .all();
    }

    public Flux<Item> searchByFluentExample(String name, String description, boolean useAnd) {
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (useAnd
                ? ExampleMatcher.matchingAll()
                : ExampleMatcher.matchingAny())
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnorePaths("price");

        return fluentOperations.query(Item.class)
                .matching(query(byExample(Example.of(item, matcher))))
                .all();
    }

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

    public Flux<Item> getInventory() {
        return null;
    }

    public Mono<Cart> getCart(String cartId) {
        return null;
    }
}
