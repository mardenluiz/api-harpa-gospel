package com.mardenluiz.harpa.api.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldErrorValidation {

    private String field;
    private String message;

}
