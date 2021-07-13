package com.cenibee.book.springreactive.repository;

import com.cenibee.book.springreactive.domain.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CartRepository extends ReactiveCrudRepository<Cart, String> {
}
