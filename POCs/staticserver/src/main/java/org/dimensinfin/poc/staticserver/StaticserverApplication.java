package org.dimensinfin.poc.staticserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class StaticserverApplication extends SpringBootServletInitializer {

	public static void main( String[] args ) {
		SpringApplication.run(StaticserverApplication.class, args);
	}

}
