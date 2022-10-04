package com.exercise.wordcounterlibrary.stub;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class Translator {
    private static final Map<String, String> TRANSLATION;

    static {
        TRANSLATION = new HashMap<>();
        TRANSLATION.put("flower", "flower");
        TRANSLATION.put("flor", "flower");
        TRANSLATION.put("blume", "flower");
    }

    public Optional<String> translate(String word) {
        return TRANSLATION.containsKey(word) ? Optional.of(TRANSLATION.get(word)) : Optional.empty();
    }

}
