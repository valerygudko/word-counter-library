package com.exercise.wordcounterlibrary.service;

import com.exercise.wordcounterlibrary.exception.NonAlphabeticInputException;
import com.exercise.wordcounterlibrary.utility.WordCounterTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static com.exercise.wordcounterlibrary.utility.WordCounterTestConstants.VALID_WORDS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NonAlphabeticValidationHandlerTest {

    @InjectMocks
    private NonAlphabeticValidationHandler nonAlphabeticValidationHandler;

    @Mock
    private WordValidator wordValidator;

    static Stream<List<String>> generateNonAlphabeticWordInInputData() {
        return WordCounterTestData.generateNonAlphabeticalInputData();
    }

    @ParameterizedTest(name = "#{index} - Run test with args={0}")
    @DisplayName("When word or list of words contains word which is non alphabetic is passed then NonAlphabeticInputException thrown")
    @MethodSource("generateNonAlphabeticWordInInputData")
    void handle_whenNonAlphabeticWordInInputPassed_thenNonAlphabeticInputExceptionThrown(List<String> words) {
        //given
        when(wordValidator.notAlphabetic(any())).thenReturn(true);

        //when
        assertThrows(NonAlphabeticInputException.class, () -> {
            nonAlphabeticValidationHandler.handle(words.toArray(new String[0]));
        });

        //then
        verify(wordValidator).notAlphabetic(words.toArray(new String[0]));
    }

    @Test
    void handle_whenValidInputPassed_thenNoExceptionThrown() {
        //given
        when(wordValidator.notAlphabetic(any())).thenReturn(false);

        //when
        nonAlphabeticValidationHandler.handle(VALID_WORDS);

        //then
        verify(wordValidator).notAlphabetic(VALID_WORDS);
    }
}