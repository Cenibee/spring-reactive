package com.cenibee.book.springreactive.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.bson.Document;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.annotation.Id;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

public class HttpTraceWrapper {

    private @Id String id;

    private final HttpTrace httpTrace;

    public HttpTraceWrapper(HttpTrace httpTrace) {
        this.httpTrace = httpTrace;
    }

    public HttpTrace getHttpTrace() {
        return httpTrace;
    }

    public static Converter<Document, HttpTraceWrapper> CONVERTER =
            new Converter<Document, HttpTraceWrapper>() {
                @Override
                public HttpTraceWrapper convert(Document document) {
                    Document httpTrace = document.get("httpTrace", Document.class);
                    Document request = httpTrace.get("request", Document.class);
                    Document response = httpTrace.get("response", Document.class);

                    return new HttpTraceWrapper(new HttpTrace(
                            new HttpTrace.Request(
                                    request.getString("method"),
                                    URI.create(request.getString("uri")),
                                    request.get("headers", Map.class),
                                    null),
                            new HttpTrace.Response(
                                    response.getInteger("status"),
                                    response.get("headers", Map.class)),
                            httpTrace.getDate("timestamp").toInstant(),
                            null,
                            null,
                            httpTrace.getLong("timeTaken")));
                }
            };

}
