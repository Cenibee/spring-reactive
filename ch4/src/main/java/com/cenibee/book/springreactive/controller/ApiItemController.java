package com.cenibee.book.springreactive.controller;

import com.cenibee.book.springreactive.domain.Item;
import com.cenibee.book.springreactive.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class ApiItemController {

    private final ItemRepository repository;

    @GetMapping("/api/items")
    public Flux<Item> findAll() {
        return this.repository.findAll();
    }

    @GetMapping("/api/items/{id}")
    public Mono<Item> findOne(@PathVariable String id) {
        return this.repository.findById(id);
    }

    @PostMapping("/api/items")
    public Mono<ResponseEntity<?>> addNewItem(@RequestBody Mono<Item> item) {
        return item.flatMap(repository::save)
                .map(savedItem -> ResponseEntity
                        .created(URI.create("/api/items/" + savedItem.getId()))
                        .body(savedItem));
    }

    @PutMapping("/api/items/{id}")
    public Mono<ResponseEntity<?>> updateItem(@RequestBody Mono<Item> item, @PathVariable String id) {
        return item
                .map(content -> new Item(id, content.getName(), content.getDescription(), content.getPrice()))
                .flatMap(repository::save)
                .map(ResponseEntity::ok);
    }

}
