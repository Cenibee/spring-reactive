package com.cenibee.book.springreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveView;
import reactor.blockhound.BlockHound;
import reactor.blockhound.integration.BlockHoundIntegration;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

@SpringBootApplication
public class Ch4Application {

    public static void main(String[] args) {

        BlockHound.Builder builder = BlockHound.builder();

        ServiceLoader<BlockHoundIntegration> serviceLoader = ServiceLoader.load(BlockHoundIntegration.class);
        StreamSupport.stream(serviceLoader.spliterator(), false)
                .sorted()
                .forEach(builder::with);
        builder.allowBlockingCallsInside(ThymeleafReactiveView.class.getCanonicalName(), "render");
        builder.allowBlockingCallsInside(TemplateEngine.class.getCanonicalName(), "process");
        builder.install();

        SpringApplication.run(Ch4Application.class, args);
    }

}
