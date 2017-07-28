package com.resty.domain.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Created by damianhagge on 5/16/17.
 */
@Data
@AllArgsConstructor
public class RestError {
    /**
     * A custom error code specific to this error.
     */
    private String code;

    /**
     * An (optional) customMessage indicating whu this error is being returned.
     */
    private String message;

    /**
     * Data that pertains to the exception.
     */
    private Map<String, Object> response;
}
