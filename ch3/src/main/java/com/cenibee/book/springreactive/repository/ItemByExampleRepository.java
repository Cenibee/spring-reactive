package com.cenibee.book.springreactive.repository;

import com.cenibee.book.springreactive.domain.Item;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface ItemByExampleRepository extends ReactiveQueryByExampleExecutor<Item> {
}
