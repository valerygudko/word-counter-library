package com.exercise.wordcounterlibrary.service;

import com.exercise.wordcounterlibrary.exception.EmptyOrBlankInputException;
import com.exercise.wordcounterlibrary.exception.NonAlphabeticInputException;
import com.exercise.wordcounterlibrary.exception.NotFoundInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordValidationService {

    @Autowired
    private WordValidator wordValidator;

    public void validate(String... words) throws EmptyOrBlankInputException, NonAlphabeticInputException, NotFoundInputException {
        IHandler<String[]> handler = new EmptyOrBlankValidationHandler(wordValidator);
        handler
                .setNext(new NonAlphabeticValidationHandler(wordValidator))
                .setNext(new WordExistenceValidationHandler(wordValidator));
        handler.handle(words);
    }

}
