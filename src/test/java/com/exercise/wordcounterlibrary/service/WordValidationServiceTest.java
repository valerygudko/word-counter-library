package com.exercise.wordcounterlibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.exercise.wordcounterlibrary.utility.WordCounterTestConstants.VALID_WORDS;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class WordValidationServiceTest {

    @InjectMocks
    private WordValidationService wordValidationService;

    @Mock
    private WordValidator wordValidator;

    @Test
    @DisplayName("Validate validations are called in the right order")
    void validate_methodCallsALLValidationsCorrectly() {
        //given
        InOrder inOrderVerifier = inOrder(wordValidator);

        //when
        wordValidationService.validate(VALID_WORDS);

        //then
        inOrderVerifier.verify(wordValidator).isEmptyOrBlank(VALID_WORDS);
        inOrderVerifier.verify(wordValidator).notAlphabetic(VALID_WORDS);
        inOrderVerifier.verify(wordValidator).notAWord(VALID_WORDS);
    }
}