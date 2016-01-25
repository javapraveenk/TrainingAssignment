package com.training.exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Application class for the beer example
 *
 * @author Praveen Kaujalgikar
 * @since 1.0
 */
@EnableAutoConfiguration
@ComponentScan
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
