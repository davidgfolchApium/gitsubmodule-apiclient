package com.apiumhub.articles.gitsubmodules.api.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class RestTemplateHelper {

    private static final String PARAMETERS = "parameters";

    private RestTemplateHelper() {}

    static <T> T catchException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (RestClientResponseException e) {
            throw new ApiClientException(e);
        }
    }

    static HttpEntity<String> getHttpEntity(String language) {
        return new HttpEntity<>(PARAMETERS, createHttpHeaders(language));
    }

    static HttpEntity<String> getHttpEntity(HttpHeaders headers) {
        return new HttpEntity<>(PARAMETERS, headers);
    }

    static HttpHeaders createHttpHeaders(String language) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(APPLICATION_JSON));
        headers.set(ACCEPT_LANGUAGE, language);
        return headers;
    }

    public static HttpHeaders createHttpHeaders(String language, String authToken) {
        HttpHeaders headers = createHttpHeaders(language);
        headers.set(AUTHORIZATION, authToken);
        return headers;
    }

    public static RestTemplate create(int connectionTimeout, int readTimeout, List<HttpMessageConverter<?>> converters) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectionTimeout);
        factory.setReadTimeout(readTimeout);
        RestTemplate template = new RestTemplate(factory);
        template.setMessageConverters(converters);
        return template;
    }

}
