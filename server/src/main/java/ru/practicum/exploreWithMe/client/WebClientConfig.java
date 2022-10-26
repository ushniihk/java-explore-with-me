package ru.practicum.exploreWithMe.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.exploreWithMe.client.EventClient;

@Configuration
public class WebClientConfig {
    @Value("${explore-with-me-stat.url}")
    private String serviceUrl;


    @Bean
    public EventClient eventClient(RestTemplateBuilder builder) {
        RestTemplateBuilder restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serviceUrl));
        return new EventClient(serviceUrl, restTemplate);
    }

/*    @Bean
    public BookingClient bookingClient(RestTemplateBuilder builder) {
        RestTemplateBuilder restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serviceUrl));
        return new BookingClient(serviceUrl, restTemplate);
    }

    @Bean
    public ItemClient itemClient(RestTemplateBuilder builder) {
        RestTemplateBuilder restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serviceUrl));
        return new ItemClient(serviceUrl, restTemplate);
    }

    @Bean
    public ItemRequestClient requestClient(RestTemplateBuilder builder) {
        RestTemplateBuilder restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serviceUrl));
        return new ItemRequestClient(serviceUrl, restTemplate);
    }*/

}
