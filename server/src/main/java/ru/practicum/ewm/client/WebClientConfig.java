package ru.practicum.ewm.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class WebClientConfig {
    @Value("${EXPLORE_WITH_ME_URL}")
    private String serviceUrl;


    @Bean
    public EventClient eventClient(RestTemplateBuilder builder) {
        RestTemplateBuilder restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serviceUrl));
        return new EventClient(serviceUrl, restTemplate);
    }
}
