/*
 * Copyright (c) 2019. copyright text here
 */

package com.disney.studios.rest.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;
    private int statusCode;
    private String statusReason;

    public ExceptionResponse(Date timestamp, String message, String details, HttpStatus httpStatus) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.statusCode = httpStatus.value();
        this.statusReason = httpStatus.getReasonPhrase();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }
}
