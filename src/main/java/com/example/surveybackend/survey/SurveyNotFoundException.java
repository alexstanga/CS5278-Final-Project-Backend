package com.example.surveybackend.survey;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SurveyNotFoundException extends RuntimeException {

    public SurveyNotFoundException(String message) {
        super(message);
    }

}
