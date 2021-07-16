package com.cenibee.book.springreactive.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SpringDataHttpTraceRepository implements HttpTraceRepository {

    private final HttpTraceWrapperRepository repository;

    @Override
    public List<HttpTrace> findAll() {
        assert repository != null;
        assert repository.findAll() != null;
        return repository.findAll()
                .map(HttpTraceWrapper::getHttpTrace)
                .collect(Collectors.toList());
    }

    @Override
    public void add(HttpTrace trace) {
        repository.save(new HttpTraceWrapper(trace));
    }
}
