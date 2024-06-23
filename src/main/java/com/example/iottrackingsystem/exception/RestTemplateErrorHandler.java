package com.example.iottrackingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();

    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is5xxServerError()) {

            if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                throw new RestTemplateCustomException("ERROR: The server is temporarily unable to handle requests.");
            }
            if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                throw new RestTemplateCustomException("ERROR: A technical exception occurred.");
            }
            if (response.getStatusCode() == HttpStatus.BAD_GATEWAY) {
                throw new RestTemplateCustomException(
                        "ERROR: The server received an invalid response from a server that it accessed in attempting to fulfill the request.");
            }
            if (response.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT) {
                throw new RestTemplateCustomException(
                        "ERROR: The server did not receive a timely response from an upstream server it accessed in attempting to complete the request.");
            }
        } else if (response.getStatusCode().is4xxClientError()) {
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RestTemplateCustomException("ERROR: no data file found");
            }
            if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new RestTemplateCustomException("ERROR: Authentication failed or was not provided.");
            }
            if (response.getStatusCode() == HttpStatus.CONFLICT) {
                throw new RestTemplateCustomException(
                        "ERROR: The request could not be completed due to a conflict with the current state of the resource.");
            }
            if (response.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
                throw new RestTemplateCustomException(
                        "ERROR: The server did not receive a complete request message within the time that it was prepared to wait.");
            }
            if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new RestTemplateCustomException("ERROR: The requested resource is forbidden.");
            }
            if (response.getStatusCode() == HttpStatus.METHOD_NOT_ALLOWED) {
                throw new RestTemplateCustomException("ERROR: The requested method not allowed for this request.");
            }

        } else {
            throw new RestTemplateCustomException("ERROR: Not able to access endpoint");
        }

    }
}
