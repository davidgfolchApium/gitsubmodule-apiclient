package com.apiumhub.articles.gitsubmodules.api.client;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientResponseException;

import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.logging.log4j.util.Strings.EMPTY;

public class ApiClientException extends RestClientResponseException {

    public ApiClientException(String message, int statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(message, statusCode, statusText, responseHeaders, responseBody, responseCharset);
    }

    public ApiClientException(RestClientResponseException e) {
        super(e.getMessage() == null ? EMPTY : e.getMessage(), e.getRawStatusCode(), e.getStatusText(), e.getResponseHeaders(), e.getResponseBodyAsByteArray(), UTF_8);
    }

}
