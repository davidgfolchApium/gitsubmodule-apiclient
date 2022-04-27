package com.apiumhub.articles.gitsubmodules.api.client;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Getter
@Configuration
public class ApiConfig {

    @Bean
    public RestTemplate apiClientRestTemplate() {
        return new RestTemplate();
    }

}
