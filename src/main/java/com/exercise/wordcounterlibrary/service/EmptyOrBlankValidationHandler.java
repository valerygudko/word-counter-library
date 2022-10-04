package com.exercise.wordcounterlibrary.service;

import com.exercise.wordcounterlibrary.exception.EmptyOrBlankInputException;
import org.springframework.stereotype.Service;

@Service
public class EmptyOrBlankValidationHandler extends Handler<String[]> {

    private final WordValidator wordValidator;

    public EmptyOrBlankValidationHandler(WordValidator wordValidator) {
        this.wordValidator = wordValidator;
    }

    @Override
    public void handle(String[] request) {
        if (wordValidator.isEmptyOrBlank(request))
            throw new EmptyOrBlankInputException();
        super.handle(request);
    }


}