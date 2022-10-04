package com.exercise.wordcounterlibrary.service;

import com.exercise.wordcounterlibrary.stub.Translator;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@NoArgsConstructor
public class WordValidator {

    @Autowired
    private Translator translator;

    public boolean notAlphabetic(String[] words) {
        for (String word : words) {
            if (!word.matches("[a-zA-Z]+"))
                return true;
        }
        return false;
    }

    public boolean notAWord(String[] words) {
        for (String word : words) {
            if (translator.translate(word).isPresent()) {
                continue;
            }
            return true;
        }
        return false;
    }

    public boolean isEmptyOrBlank(String[] words) {
        if (words == null || words.length == 0)
            return true;
        else for (String word : words) {
            if (isBlank(word))
                return true;
        }
        return false;
    }
}
