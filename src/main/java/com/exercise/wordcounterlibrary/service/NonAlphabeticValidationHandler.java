package com.exercise.wordcounterlibrary.service;

import com.exercise.wordcounterlibrary.exception.NonAlphabeticInputException;
import org.springframework.stereotype.Service;

@Service
public class NonAlphabeticValidationHandler extends Handler<String[]> {

    private final WordValidator wordValidator;

    public NonAlphabeticValidationHandler(WordValidator wordValidator) {
        this.wordValidator = wordValidator;
    }

    @Override
    public void handle(String[] request) {
        if (wordValidator.notAlphabetic(request))
            throw new NonAlphabeticInputException();
        super.handle(request);
    }


}