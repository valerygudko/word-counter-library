package com.exercise.wordcounterlibrary.service;

import com.exercise.wordcounterlibrary.cache.WordsCache;
import com.exercise.wordcounterlibrary.exception.EmptyOrBlankInputException;
import com.exercise.wordcounterlibrary.exception.NonAlphabeticInputException;
import com.exercise.wordcounterlibrary.exception.NotFoundInputException;
import com.exercise.wordcounterlibrary.stub.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WordCounterService {

    @Autowired
    private WordValidationService wordValidationService;

    @Autowired
    private Translator translator;

    @Autowired
    private WordsCache wordsCache;

    public void addWords(String... words) throws EmptyOrBlankInputException, NonAlphabeticInputException, NotFoundInputException {
        wordValidationService.validate(words);
        for (String word : words) {
            final Optional<String> translatedWord = getTranslatedWord(word);
            int count = wordsCache.retrieveMap().containsKey(translatedWord.get()) ? wordsCache.getCount(translatedWord.get()) : 0;
            wordsCache.put(translatedWord.get(), ++count);
        }
    }

    public int getCount(String word) {
        final Optional<String> translatedWord = getTranslatedWord(word);
        return isAddedWord(translatedWord) ? wordsCache.getCount(translatedWord.get()) : 0;
    }

    private boolean isAWord(Optional<String> translatedWord) {
        return translatedWord.isPresent();
    }

    private boolean isAddedWord(Optional<String> translatedWord) {
        return isAWord(translatedWord) && wordCount(translatedWord.get()) != null;
    }

    private Integer wordCount(String word) {
        return wordsCache.getCount(word);
    }

    private Optional<String> getTranslatedWord(String word) {
        return translator.translate(word);
    }

}
