package com.cenibee.book.springreactive;

import com.cenibee.book.springreactive.domain.Cart;
import com.cenibee.book.springreactive.domain.CartItem;
import com.cenibee.book.springreactive.domain.Item;
import com.cenibee.book.springreactive.repository.CartRepository;
import com.cenibee.book.springreactive.repository.ItemRepository;
import com.cenibee.book.springreactive.service.AltInventoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BlockHoundIntegrationTest {

    AltInventoryService altInventoryService;

    @MockBean ItemRepository itemRepository;
    @MockBean CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        Item sampleItem = new Item("item1", "TV tray", "Alf TV tray", 19.99);
        CartItem sampleCartItem = new CartItem(sampleItem);
        Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));

        when(cartRepository.findById(anyString())).thenReturn(Mono.<Cart>empty().hide());
        when(itemRepository.findById(anyString())).thenReturn(Mono.just(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));

        altInventoryService = new AltInventoryService(cartRepository, itemRepository);
    }

    @Test
    void blockHoundShouldTrapBlockingCall() {
        Mono.delay(Duration.ofSeconds(1))
                .flatMap(tick -> altInventoryService.addItemToCart("My Cart", "item1"))
                .as(StepVerifier::create)
                .verifyErrorSatisfies(throwable -> {
                    assertThat(throwable).hasMessageContaining(
                            "block()/blockFirst()/blockLast() are blocking");
                });
    }

}
