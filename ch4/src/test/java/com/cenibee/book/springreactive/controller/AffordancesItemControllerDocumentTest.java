package com.cenibee.book.springreactive.controller;

import com.cenibee.book.springreactive.domain.Item;
import com.cenibee.book.springreactive.repository.ItemRepository;
import com.cenibee.book.springreactive.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@WebFluxTest(AffordancesItemController.class)
@AutoConfigureRestDocs
class AffordancesItemControllerDocumentTest {

    @Autowired WebTestClient webTestClient;

    @MockBean InventoryService service;

    @MockBean ItemRepository repository;

    @Test
    void findSingleItemAffordances() {
        when(repository.findById("item-1")).thenReturn(Mono.just(
                new Item("item-1", "Alf alarm clock", "nothing I really need", 19.99)));

        webTestClient.get().uri("/affordances/items/item-1")
                .accept(MediaTypes.HAL_FORMS_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("single-item-affordances", preprocessResponse(prettyPrint())));
    }

}