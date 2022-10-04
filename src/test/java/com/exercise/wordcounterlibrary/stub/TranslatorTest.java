package com.exercise.wordcounterlibrary.stub;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.exercise.wordcounterlibrary.utility.WordCounterTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TranslatorTest {

    @InjectMocks
    private Translator translator;

    @Test
    @DisplayName("When not a word is passed then Optional of empty is returned")
    void translate_whenWordIsNotPresent_returnsOptionalOfEmpty() {
        //given & when
        Optional<String> translation = translator.translate(NOT_A_WORD);

        //then
        assertFalse(translation.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {VALID_ENGLISH_WORD, VALID_DIFF_LANGUAGE_WORD})
    @DisplayName("When a word is passed then English translation is returned")
    void translate_whenWordPresent_returnsEnglishTranslation(String input) {
        //given & when
        Optional<String> translation = translator.translate(input);

        //then
        assertTrue(translation.isPresent());
        assertEquals(VALID_ENGLISH_WORD, translation.get());
    }

}