package com.exercise.wordcounterlibrary.service;

import com.exercise.wordcounterlibrary.exception.NotFoundInputException;
import org.springframework.stereotype.Service;

@Service
public class WordExistenceValidationHandler extends Handler<String[]> {

    private final WordValidator wordValidator;

    public WordExistenceValidationHandler(WordValidator wordValidator) {
        this.wordValidator = wordValidator;
    }

    @Override
    public void handle(String[] request) {
        if (wordValidator.notAWord(request))
            throw new NotFoundInputException();
        super.handle(request);
    }


}