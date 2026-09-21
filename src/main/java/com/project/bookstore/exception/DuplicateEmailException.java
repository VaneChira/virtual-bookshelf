package com.project.bookstore.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends BaseRestException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;
    private static final String ERROR_BODY_MESSAGE = "An account with this email already exists: ";
    private final String displayMessage;

    public DuplicateEmailException(String email) {
        super(ERROR_BODY_MESSAGE + email);
        this.displayMessage = ERROR_BODY_MESSAGE + email;
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
