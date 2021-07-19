package com.cenibee.book.springreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;
import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL_FORMS;

@SpringBootApplication
@EnableHypermediaSupport(type = { HAL, HAL_FORMS })
public class Ch4Application {

    public static void main(String[] args) {

//        BlockHound.Builder builder = BlockHound.builder();
//
//        ServiceLoader<BlockHoundIntegration> serviceLoader = ServiceLoader.load(BlockHoundIntegration.class);
//        StreamSupport.stream(serviceLoader.spliterator(), false)
//                .sorted()
//                .forEach(builder::with);
//        builder.allowBlockingCallsInside(ThymeleafReactiveView.class.getCanonicalName(), "render");
//        builder.allowBlockingCallsInside(TemplateEngine.class.getCanonicalName(), "process");
//        builder.install();

        SpringApplication.run(Ch4Application.class, args);
    }

}
