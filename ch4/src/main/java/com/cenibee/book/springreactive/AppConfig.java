package com.cenibee.book.springreactive;

import com.cenibee.book.springreactive.util.HttpTraceWrapper;
import com.cenibee.book.springreactive.util.HttpTraceWrapperRepository;
import com.cenibee.book.springreactive.util.SpringDataHttpTraceRepository;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Collections;

@Configuration
public class AppConfig {

    @Bean
    public HttpTraceRepository traceRepository(HttpTraceWrapperRepository repository) {
//        return new InMemoryHttpTraceRepository();
        return new SpringDataHttpTraceRepository(repository);
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoMappingContext context) {
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(NoOpDbRefResolver.INSTANCE, context);

        mappingMongoConverter.setCustomConversions(
                new MongoCustomConversions(Collections.singletonList(HttpTraceWrapper.CONVERTER)));

        return mappingMongoConverter;
    }

}
