package org.dimensinfin.poc.staticserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Collections;

//@EnableAutoConfiguration
@SpringBootApplication
public class StaticserverApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StaticserverApplication.class);
//        app.setDefaultProperties(Collections
//                .singletonMap("server.port", "8080"));
        app.run(args);
    }

}
