package com.exercise.wordcounterlibrary.service;

import com.exercise.wordcounterlibrary.stub.Translator;
import com.exercise.wordcounterlibrary.utility.WordCounterTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.exercise.wordcounterlibrary.utility.WordCounterTestConstants.VALID_ENGLISH_WORD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordValidatorTest {

    @InjectMocks
    private WordValidator wordValidator;

    @Mock
    private Translator translator;

    static Stream<List<String>> generateNonAlphabeticalInputData() {
        return WordCounterTestData.generateNonAlphabeticalInputData();
    }

    static Stream<List<String>> generateAlphabeticalInputData() {
        return WordCounterTestData.generateAlphabeticalInputData();
    }

    static Stream<List<String>> generateNonAWordInputData() {
        return WordCounterTestData.generateNonAWordInputData();
    }

    static Stream<List<String>> generateEmptyOrBlankInputData() {
        return WordCounterTestData.generateEmptyOrBlankInputData();
    }

    @ParameterizedTest
    @MethodSource("generateNonAlphabeticalInputData")
    @DisplayName("When word or a list of words with a word with non alphabetic characters is passed then true returned")
    void notAlphabetic_whenNotAlphabeticPassed_returnsTrue(List<String> words) {
        //given & when & then
        assertTrue(wordValidator.notAlphabetic(words.toArray(new String[0])));
    }

    @ParameterizedTest
    @MethodSource("generateAlphabeticalInputData")
    @DisplayName("When word or a list of words with all alphabetic characters is passed then false returned")
    void notAlphabetic_whenAlphabeticPassed_returnsFalse(List<String> words) {
        //given & when & then
        assertFalse(wordValidator.notAlphabetic(words.toArray(new String[0])));
    }

    @ParameterizedTest
    @MethodSource("generateNonAWordInputData")
    @DisplayName("When word or a list of words with not a word is passed then true returned")
    void notAWord_whenNotAWordPassed_returnsTrue(List<String> words) {
        //given
        when(translator.translate(any())).thenReturn(Optional.empty());
        //when & then
        assertTrue(wordValidator.notAWord(words.toArray(new String[0])));
    }

    @ParameterizedTest
    @MethodSource("generateAlphabeticalInputData")
    @DisplayName("When word or a list of words is passed then false returned")
    void notAWord_whenValidWordPassed_returnsFalse(List<String> words) {
        //given
        when(translator.translate(any())).thenReturn(Optional.of(VALID_ENGLISH_WORD));
        //when & then
        assertFalse(wordValidator.notAWord(words.toArray(new String[0])));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("When null is passed then true returned")
    void isEmptyOrBlank_whenNullPassed_returnsTrue(String[] words) {
        //given & when & then
        assertTrue(wordValidator.isEmptyOrBlank(words));
    }

    @ParameterizedTest
    @MethodSource("generateEmptyOrBlankInputData")
    @DisplayName("When blank or empty word is passed then true returned")
    void isEmptyOrBlank_whenEmptyOrBlankWordPassed_returnsTrue(List<String> words) {
        //given & when & then
        assertTrue(wordValidator.isEmptyOrBlank(words.toArray(new String[0])));
    }

    @ParameterizedTest
    @MethodSource("generateAlphabeticalInputData")
    @DisplayName("When blank or empty word is passed then false returned")
    void isEmptyOrBlank_whenValidWordPassed_returnsFalse(List<String> words) {
        //given & when & then
        assertFalse(wordValidator.isEmptyOrBlank(words.toArray(new String[0])));
    }

}