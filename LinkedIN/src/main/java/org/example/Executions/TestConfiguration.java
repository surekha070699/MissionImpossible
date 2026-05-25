package org.example.Executions;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Lazy
@Getter
@Configuration
@ComponentScan(basePackages = {"org.example"})
public class TestConfiguration {
    @Configuration
    @PropertySource("classpath:properties/uat.properties")
    @Profile({"uat"})
    static class UAT {}

    @Configuration
    @PropertySource("classpath:properties/test.properties")
    @Profile({"test"})
    static class Test {}

    @Value("${app.baseUri}")
    private String baseUri;

    @Bean
    public static <T> Object getInstance(Class<T> classType) {
        // Placeholder for ApplicationContext logic
        return null;
    }

    public String getUserName() {
        return System.getProperty("userName");
    }

    public String getPassword() {
        return System.getProperty("password");
    }

    public String getActiveProfile() {
        return System.getProperty("spring.profiles.active");
    }
}

