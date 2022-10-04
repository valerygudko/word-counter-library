package com.exercise.wordcounterlibrary.utility;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.exercise.wordcounterlibrary.utility.WordCounterTestConstants.*;
import static java.util.Collections.singletonList;

public class WordCounterTestData {

    public static Stream<List<String>> generateNonAlphabeticalInputData() {
        return Stream.of(
                singletonList(NOT_AlPHABETIC_WORD),
                Arrays.asList(WORDS_WITH_NOT_AlPHABETIC_WORD)
        );
    }

    public static Stream<List<String>> generateAlphabeticalInputData() {
        return Stream.of(
                singletonList(VALID_ENGLISH_WORD),
                singletonList(VALID_DIFF_LANGUAGE_WORD),
                Arrays.asList(VALID_WORDS)
        );
    }

    public static Stream<List<String>> generateNonAWordInputData() {
        return Stream.of(
                singletonList(NOT_A_WORD),
                Arrays.asList(WORDS_WITH_NOT_A_WORD)
        );
    }

    public static Stream<List<String>> generateEmptyOrBlankInputData() {
        return Stream.of(
                singletonList(EMPTY),
                singletonList(BLANK),
                Arrays.asList(WORDS_WITH_EMPTY_WORD)
        );
    }
}
