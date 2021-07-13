package com.cenibee.book.springreactive.service;

import com.cenibee.book.springreactive.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static org.springframework.data.mongodb.core.query.Criteria.byExample;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
@Service
public class ItemService {

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

}
