package com.cenibee.book.springreactive.controller;

import com.cenibee.book.springreactive.domain.Item;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class SpringAmqpItemController {

    private static final Logger log = LoggerFactory.getLogger(SpringAmqpItemController.class);

    private final AmqpTemplate template;

    @PostMapping("/items")
    public Mono<ResponseEntity<?>> addNewItemUsingSpringAmqp(@RequestBody Mono<Item> item) {
        return item
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(content -> Mono
                        .fromCallable(() -> {
                            this.template.convertAndSend(
                                    "hacking-spring-boot", "new-items-spring-amqp", content);
                            return ResponseEntity.created(URI.create("/items")).build();
                        }));
    }

}
