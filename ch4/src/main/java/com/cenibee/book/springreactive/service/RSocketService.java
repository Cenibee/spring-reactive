package com.cenibee.book.springreactive.service;

import com.cenibee.book.springreactive.domain.Item;
import com.cenibee.book.springreactive.repository.ItemRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Controller
public class RSocketService {

    private final ItemRepository repository;
    private final Sinks.Many<Item> itemSink;

    public RSocketService(ItemRepository repository) {
        this.repository = repository;
        this.itemSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @MessageMapping("newItems.request-response")
    public Mono<Item> processNewItemViaRSocketRequestResponse(Item item) {
        return this.repository.save(item)
                .doOnNext(itemSink::tryEmitNext);
    }

    @MessageMapping("newItems.request-stream")
    public Flux<Item> findItemViaRSocketRequestStream() {
        return this.repository.findAll()
                .doOnNext(itemSink::tryEmitNext);
    }

    @MessageMapping("newItems.fire-and-forget")
    public Mono<Void> processNewItemViaRSocketFireAndForget(Item item) {
        return this.repository.save(item)
                .doOnNext(itemSink::tryEmitNext)
                .then();
    }

    @MessageMapping("newItems.monitor")
    public Flux<Item> monitorItems() {
        return this.itemSink.asFlux();
    }
}
