package com.apiumhub.articles.gitsubmodules.api.client;

import lombok.Getter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static com.apiumhub.articles.gitsubmodules.api.client.RestTemplateHelper.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Service
public class ApiClient {

    private final RestTemplate template;

    public ApiClient(Environment env, RestTemplate template) {
        this.template = template == null ? new RestTemplate() : template;
    }

    public ApiClient setBasicAuth(String usr, String pass) {
        BasicAuthenticationInterceptor interceptor = new BasicAuthenticationInterceptor(usr, pass);
        template.getInterceptors().add(interceptor);
        return this;
    }

    public <T> T get(String url, Class<T> responseType) {
        URI uri = fromUriString(url).build().toUri();
        return catchException(() -> template.exchange(uri, GET, null, responseType).getBody());
    }

    public <T> T get(String language, String url, Class<T> responseType) {
        URI uri = fromUriString(url).build().toUri();
        return catchException(() -> template.exchange(uri, GET, getHttpEntity(language), responseType).getBody());
    }

    public <T> T get(String url, HttpHeaders headers, Class<T> responseType) {
        URI uri = fromUriString(url).build().toUri();
        return catchException(() -> template.exchange(uri, GET, getHttpEntity(headers), responseType).getBody());
    }

    public <T> T getQueryParams(String language, String url, Map<String, Object> parameters, Class<T> type) {
        UriComponentsBuilder uriBuilder = fromUriString(url);
        parameters.forEach(uriBuilder::queryParam);
        return catchException(() -> template.exchange(uriBuilder.build().toUri(), GET, getHttpEntity(language), type).getBody());
    }

    public <T> T getPathParams(String lang, String url, Map<String, Object> params, Class<T> type) {
        URI uriBuilder = fromUriString(url).buildAndExpand(params).toUri();
        return catchException(() -> template.exchange(uriBuilder, GET, getHttpEntity(lang), type).getBody());
    }

    public <R, T> T post(String uri, R body, String lang, Class<T> clazz) {
        return catchException(() ->
                template.exchange(uri, POST, new HttpEntity<>(body, createHttpHeaders(lang)), clazz).getBody());
    }

    public <R, T> T post(String uri, R body, Class<T> clazz) {
        return catchException(() -> template.exchange(uri, POST, new HttpEntity<>(body), clazz).getBody());
    }

}
