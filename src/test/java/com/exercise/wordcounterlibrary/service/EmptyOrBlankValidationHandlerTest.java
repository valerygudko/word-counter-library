package com.exercise.wordcounterlibrary.service;

import com.exercise.wordcounterlibrary.exception.EmptyOrBlankInputException;
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
class EmptyOrBlankValidationHandlerTest {

    @InjectMocks
    private EmptyOrBlankValidationHandler emptyOrBlankValidationHandler;

    @Mock
    private WordValidator wordValidator;

    static Stream<List<String>> generateEmptyOrBlankWordInInputData() {
        return WordCounterTestData.generateEmptyOrBlankInputData();
    }

    @ParameterizedTest(name = "#{index} - Run test with args={0}")
    @DisplayName("When blank or empty value is passed then RInputException thrown")
    @MethodSource("generateEmptyOrBlankWordInInputData")
    void handle_whenEmptyOrBlankWordInInputPassed_thenEmptyOrBlankInputExceptionThrown(List<String> words) {
        //given
        when(wordValidator.isEmptyOrBlank(any())).thenReturn(true);

        //when
        assertThrows(EmptyOrBlankInputException.class, () -> {
            emptyOrBlankValidationHandler.handle(words.toArray(new String[0]));
        });

        //then
        verify(wordValidator).isEmptyOrBlank(words.toArray(new String[0]));
    }

    @Test
    void handle_whenValidInputPassed_thenNoExceptionThrown() {
        //given
        when(wordValidator.isEmptyOrBlank(any())).thenReturn(false);

        //when
        emptyOrBlankValidationHandler.handle(VALID_WORDS);

        //then
        verify(wordValidator).isEmptyOrBlank(VALID_WORDS);
    }
}