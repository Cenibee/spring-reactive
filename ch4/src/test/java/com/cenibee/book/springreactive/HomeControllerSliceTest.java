package com.cenibee.book.springreactive;

import com.cenibee.book.springreactive.domain.Cart;
import com.cenibee.book.springreactive.domain.Item;
import com.cenibee.book.springreactive.repository.CartRepository;
import com.cenibee.book.springreactive.repository.ItemRepository;
import com.cenibee.book.springreactive.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebFluxTest(HomeController.class)
class HomeControllerSliceTest {

    @Autowired private WebTestClient webTestClient;

    @MockBean InventoryService inventoryService;

    @MockBean ItemRepository itemRepository;
    @MockBean CartRepository cartRepository;

    @Test
    void homePage() {
        when(itemRepository.findAll()).thenReturn(Flux.just(
                new Item("id1", "name1", "desc1", 1.99),
                new Item("id2", "name2", "desc2", 9.99)
        ));
        when(cartRepository.findById("My Cart"))
                .thenReturn(Mono.just(new Cart("My Cart")));

        webTestClient.get().uri("/").exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(exchange -> {
                    assertThat(exchange.getResponseBody()).contains("action=\"/add/id1\"");
                    assertThat(exchange.getResponseBody()).contains("action=\"/add/id2\"");
                });
    }

}