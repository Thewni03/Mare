package backend.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/user/**") // Apply to paths starting with /user/
                .allowedOrigins("http://localhost:3000") // Allow specific origin
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
                .allowedHeaders("*") // Allow all headers (or specify specific ones)
                .allowCredentials(true) // Allow sending credentials like cookies
                .maxAge(3600); // Cache preflight requests for 1 hour
    }


}
