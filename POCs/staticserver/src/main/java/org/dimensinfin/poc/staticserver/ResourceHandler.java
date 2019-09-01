package org.dimensinfin.poc.staticserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ResourceHandler implements WebMvcConfigurer {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // When overriding default behavior, you need to add default(/) as well as added static paths(/webapp).
//
//        // src/main/resources/static/...
//        registry
//                //.addResourceHandler("/**") // « /css/myStatic.css
//                .addResourceHandler("/static/**") // « /static/css/myStatic.css
//                .addResourceLocations("classpath:/static/") // Default Static Loaction
//                .setCachePeriod(3600)
//                .resourceChain(true) // 4.1
//                .addResolver(new GzipResourceResolver()) // 4.1
//                .addResolver(new PathResourceResolver()); //4.1
//    }
}