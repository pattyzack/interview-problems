/*
 * Copyright (c) 2019. copyright text here
 */

package com.disney.studios.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImageResourceNotFoundException extends RuntimeException{

    public ImageResourceNotFoundException() {
        super();
    }
}

