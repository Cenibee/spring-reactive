package com.cenibee.book.springreactive.ch1;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@Service
public class KitchenService {

    private final List<Dish> menu = List.of(
            new Dish("Sesame chicken"),
            new Dish("Lo mein noodles, plain"),
            new Dish("Sweet & sour beef")
    );

    private final Random picker = new Random();

    /**
     * 요리 스트림 생성
     */
    public Flux<Dish> getDishes() {
        return Flux.<Dish> generate(sink -> sink.next(randomDish()))
                .delayElements(Duration.ofMillis(250));
    }

    /**
     * 요리 무작위 선택
     */
    private Dish randomDish() {
        return menu.get(picker.nextInt(menu.size()));
    }
}
