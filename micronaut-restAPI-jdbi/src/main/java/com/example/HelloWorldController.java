package com.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import jakarta.inject.Inject;
import org.jdbi.v3.core.Jdbi;

@Controller("/postgres")
public class HelloWorldController {

    @Inject
    Jdbi jdbi;

    @Get("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return "Hello World";
    }

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String getPostgresVersion() {
        return jdbi.onDemand(VersionRepository.class).getVersion();
    }
}
