package com.cenibee.book.springreactive.rsocketclient.controller;

import com.cenibee.book.springreactive.domain.Item;
import io.rsocket.metadata.WellKnownMimeType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

import static io.rsocket.metadata.WellKnownMimeType.MESSAGE_RSOCKET_ROUTING;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;
import static org.springframework.util.MimeTypeUtils.parseMimeType;


@RestController
public class RSocketController {

    private final RSocketRequester requester;

    public RSocketController(RSocketRequester.Builder builder) {
        this.requester = builder
                .dataMimeType(APPLICATION_JSON)
                .metadataMimeType(parseMimeType(MESSAGE_RSOCKET_ROUTING.toString()))
                .tcp("localhost", 7000);
    }

    @PostMapping("/items/request-response")
    Mono<ResponseEntity<?>> addNewItemUsingRSocketRequestsResponse(@RequestBody Item item) {
        return requester
                .route("newItems.request-response")
                .data(item)
                .retrieveMono(Item.class)
                .map(savedItem -> ResponseEntity.created(
                        URI.create("/item/request-response")).body(savedItem));
    }

    @GetMapping(value = "/items/request-stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    Flux<Item> findItemsUsingRSocketRequestStream() {
        return this.requester
                .route("newItems.request-stream")
                .retrieveFlux(Item.class)
                .delayElements(Duration.ofSeconds(1));
    }

    @PostMapping("/items/fire-and-forget")
    Mono<ResponseEntity<?>> addNewItemUsingRSocketFireAndForget(@RequestBody Item item) {
        return requester
                .route("newItems.fire-and-forget")
                .data(item)
                .send()
                .then(Mono.just(ResponseEntity.created(URI.create("/items/fire-and-forget")).build()));
    }

    @GetMapping(value = "/items", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Item> liveUpdates() {
        return this.requester
                .route("newItems.monitor")
                .retrieveFlux(Item.class);
    }
}
