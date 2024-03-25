package com.api.store.utils.errors;

import org.springframework.http.HttpStatus;

import java.util.List;

public class InvalidParamError extends RuntimeException {

    public InvalidParamError(String field) {
        super("Invalid param " + field);
    }
}
