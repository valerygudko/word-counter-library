package com.exercise.wordcounterlibrary.service;

import com.exercise.wordcounterlibrary.exception.NotFoundInputException;
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
class WordExistenceValidationHandlerTest {

    @InjectMocks
    private WordExistenceValidationHandler wordExistenceValidationHandler;

    @Mock
    private WordValidator wordValidator;

    static Stream<List<String>> generateNotExistingWordInInputData() {
        return WordCounterTestData.generateNonAWordInputData();
    }

    @ParameterizedTest(name = "#{index} - Run test with args={0}")
    @DisplayName("When word or list of words contains word which doesn't exist is passed then NotFoundInputException thrown")
    @MethodSource("generateNotExistingWordInInputData")
    void handle_whenNotExistingWordInInputPassed_thenNotFoundInputExceptionThrown(List<String> words) {
        //given
        when(wordValidator.notAWord(any())).thenReturn(true);

        //when
        assertThrows(NotFoundInputException.class, () -> {
            wordExistenceValidationHandler.handle(words.toArray(new String[0]));
        });

        //then
        verify(wordValidator).notAWord(words.toArray(new String[0]));
    }

    @Test
    void handle_whenValidInputPassed_thenNoExceptionThrown() {
        //given
        when(wordValidator.notAWord(any())).thenReturn(false);

        //when
        wordExistenceValidationHandler.handle(VALID_WORDS);

        //then
        verify(wordValidator).notAWord(VALID_WORDS);
    }
}