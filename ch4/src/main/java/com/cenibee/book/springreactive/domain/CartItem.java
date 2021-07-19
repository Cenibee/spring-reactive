package com.cenibee.book.springreactive.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItem {

    private Item item;
    private int quantity;

    private CartItem() {}

    public CartItem(Item item) {
        this.item = item;
        this.quantity = 1;
    }

    public void increment() {
        quantity++;
    }
}
