package com.resty.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.resty.domain.error.RestError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Global exception handling logic for all controllers
 *
 * Created by damianhagge on 5/16/17.
 */
@ControllerAdvice
@Slf4j
public class SharedExceptionHandler {

    public static final String UNKNOWN_ERROR = "unknown";
    public static final String INVALID_ARGUMENT = "invalidArgument";

    @ExceptionHandler({ IllegalArgumentException.class, UnsupportedOperationException.class, IOException.class,
            JsonProcessingException.class})
    ResponseEntity<RestError> handleException(Exception ex, HttpServletResponse response) throws IOException {
        log.debug("Returning rest error response due to [{}]", ex.getMessage());

        RestError error =  new RestError(getErrorCode(ex), ex.getMessage(), getDetail(ex));
        return new ResponseEntity<>(error, getErrorHttpStatus(ex));
    }

    private String getErrorCode(Exception ex) {
        if (ex instanceof IllegalArgumentException) {
            return INVALID_ARGUMENT;
        } else {
            return UNKNOWN_ERROR;
        }
    }

    private HttpStatus getErrorHttpStatus(Exception ex) {
        return HttpStatus.BAD_REQUEST;
    }

    private Map<String, Object> getDetail(Exception ex) {
        return null;
    }
}
