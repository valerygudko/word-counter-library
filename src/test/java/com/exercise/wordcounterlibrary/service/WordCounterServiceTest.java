package com.exercise.wordcounterlibrary.service;

import com.exercise.wordcounterlibrary.cache.WordsCache;
import com.exercise.wordcounterlibrary.exception.EmptyOrBlankInputException;
import com.exercise.wordcounterlibrary.exception.NonAlphabeticInputException;
import com.exercise.wordcounterlibrary.exception.NotFoundInputException;
import com.exercise.wordcounterlibrary.stub.Translator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static com.exercise.wordcounterlibrary.utility.WordCounterTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordCounterServiceTest {

    @InjectMocks
    private WordCounterService wordCounterService;

    @Mock
    private Translator translator;

    @Mock
    private WordValidationService wordValidationService;

    @Mock
    private WordsCache wordsCache;

    @Mock
    private ConcurrentMap<String, Integer> mockedMap;

    @Test
    @DisplayName("When a list of words with a word with non alphabetic characters is passed then NonAlphabeticInputException thrown")
    void addWords_whenInvalidInputPassed_thenNonAlphabeticInputExceptionThrown() {
        //given
        doThrow(new NonAlphabeticInputException()).when(wordValidationService).validate(WORDS_WITH_NOT_AlPHABETIC_WORD);
        //when & then
        assertThrows(NonAlphabeticInputException.class, () -> {
            wordCounterService.addWords(WORDS_WITH_NOT_AlPHABETIC_WORD);
        });
        verifyNoInteractions(translator);
        verifyNoInteractions(wordsCache);
    }

    @Test
    @DisplayName("When empty or blank word is passed then EmptyOrBlankInputException thrown")
    void addWords_whenEmptyInputPassed_thenEmptyOrBlankInputExceptionExceptionThrown() {
        //given
        doThrow(new EmptyOrBlankInputException()).when(wordValidationService).validate(WORDS_WITH_EMPTY_WORD);
        //when & then
        assertThrows(EmptyOrBlankInputException.class, () -> {
            wordCounterService.addWords(WORDS_WITH_EMPTY_WORD);
        });
        verifyNoInteractions(translator);
        verifyNoInteractions(wordsCache);
    }

    @Test
    @DisplayName("When not a word or list which contains not a word is passed then NotFoundInputException thrown")
    void addWords_whenNotAWordInputPassed_thenNotFoundInputExceptionThrown() {
        //given
        doThrow(new NotFoundInputException()).when(wordValidationService).validate(WORDS_WITH_NOT_A_WORD);

        //when & then
        assertThrows(NotFoundInputException.class, () -> {
            wordCounterService.addWords(WORDS_WITH_NOT_A_WORD);
        });

        verifyNoInteractions(translator);
        verifyNoInteractions(wordsCache);
    }

    @Test
    @DisplayName("When a list of valid words is passed then it gets processed")
    void addWords_whenValidInputPassed_thenItGetsProcessed() {
        //given
        when(translator.translate(anyString())).thenReturn(Optional.of(VALID_ENGLISH_WORD));
        int expectedCount = VALID_WORDS.length;
        when(wordsCache.retrieveMap()).thenReturn(mockedMap);
        when(mockedMap.containsKey(anyString())).thenReturn(false);

        //when
        wordCounterService.addWords(VALID_WORDS);

        //then
        verify(wordsCache, times(expectedCount)).put(eq(VALID_ENGLISH_WORD), anyInt());
        verify(translator, times(expectedCount)).translate(anyString());
    }

    @Test
    @DisplayName("When an existing word gets added then its' count increased by 1")
    void addWords_whenExistingWordPassed_thenItGetsProcessedAndCountIncreasedByOne() {
        //given
        when(translator.translate(anyString())).thenReturn(Optional.of(VALID_ENGLISH_WORD));
        when(wordsCache.retrieveMap()).thenReturn(mockedMap);
        when(mockedMap.containsKey(anyString())).thenReturn(true);
        when(wordsCache.getCount(VALID_ENGLISH_WORD)).thenReturn(EXPECTED_WORD_COUNT);

        //when
        wordCounterService.addWords(VALID_ENGLISH_WORD);

        //then
        verify(wordsCache).put(VALID_ENGLISH_WORD, EXPECTED_WORD_COUNT + 1);
        verify(translator).translate(anyString());
    }

    @Test
    @DisplayName("When not a word passed then 0 returned")
    void getCount_whenNotAWordPassed_thenReturned0() {
        //given
        when(translator.translate(anyString())).thenReturn(Optional.empty());

        //when
        int actualCount = wordCounterService.getCount(NOT_A_WORD);

        //then
        verify(translator).translate(NOT_A_WORD);
        verifyNoInteractions(wordsCache);
        assertEquals(0, actualCount);
    }

    @Test
    @DisplayName("When not a word or not added word passed then 0 returned")
    void getCount_whenNotAddedWordPassed_thenReturned0() {
        //given
        when(translator.translate(anyString())).thenReturn(Optional.of(VALID_ENGLISH_WORD));
        when(wordsCache.getCount(VALID_ENGLISH_WORD)).thenReturn(null);

        //when
        int actualCount = wordCounterService.getCount(NOT_A_WORD);

        //then
        verify(translator).translate(NOT_A_WORD);
        verify(wordsCache).getCount(VALID_ENGLISH_WORD);
        assertEquals(0, actualCount);
    }

    @Test
    @DisplayName("When a valid added word passed then expected count is returned")
    void getCount_whenAnAddedWordPassed_thenExpectedCountReturned() {
        //given
        when(translator.translate(anyString())).thenReturn(Optional.of(VALID_ENGLISH_WORD));
        when(wordsCache.getCount(VALID_ENGLISH_WORD)).thenReturn(EXPECTED_WORD_COUNT);

        //when
        int actualCount = wordCounterService.getCount(VALID_ENGLISH_WORD);

        //then
        verify(translator).translate(VALID_ENGLISH_WORD);
        verify(wordsCache, times(2)).getCount(VALID_ENGLISH_WORD);
        assertEquals(EXPECTED_WORD_COUNT, actualCount);
    }

}