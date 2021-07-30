package com.project.bookstore.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseRestException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private final String displayMessage;
    private static final String ERROR_BODY_MESSAGE = " not found";

    public ResourceNotFoundException(String message, String resourceName) {
        super(message);
        this.displayMessage = resourceName + ERROR_BODY_MESSAGE;
    }

    public ResourceNotFoundException(Throwable cause, String message, String resourceName) {
        super(cause, message);
        this.displayMessage = resourceName + ERROR_BODY_MESSAGE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }

    @Override
    public String getDisplayMessage() {
        return displayMessage;
    }
}
