package com.cenibee.book.springreactive.repository;

import com.cenibee.book.springreactive.domain.Item;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public interface ItemByExampleRepository extends ReactiveQueryByExampleExecutor<Item> {
}
