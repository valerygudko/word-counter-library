package com.exercise.wordcounterlibrary.controller;

import com.exercise.wordcounterlibrary.exception.EmptyOrBlankInputException;
import com.exercise.wordcounterlibrary.exception.NonAlphabeticInputException;
import com.exercise.wordcounterlibrary.exception.NotFoundInputException;
import com.exercise.wordcounterlibrary.service.WordCounterService;
import com.exercise.wordcounterlibrary.utility.WordCounterTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Stream;

import static com.exercise.wordcounterlibrary.utility.WordCounterTestConstants.*;
import static java.lang.String.valueOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WordCounterController.class)
class WordCounterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordCounterService wordCounterService;

    static Stream<List<String>> generateNonAlphabeticalInputData() {
        return WordCounterTestData.generateNonAlphabeticalInputData();
    }

    static Stream<List<String>> generateValidInputData() {
        return WordCounterTestData.generateAlphabeticalInputData();
    }

    @ParameterizedTest
    @DisplayName("When non-alphabetic word passed to add a word then unprocessable entity error is returned")
    @MethodSource("generateNonAlphabeticalInputData")
    void post_nonAlphabeticWord_returns422(List<String> input) throws Exception {
        doThrow(NonAlphabeticInputException.class)
                .when(wordCounterService).addWords(input.toArray(new String[0]));
        MultiValueMap<String, String> body
                = new LinkedMultiValueMap<>();
        body.addAll(WORD_PARAM, input);

        mockMvc.perform(post("/words")
                .params(body))
                .andExpect(status().isUnprocessableEntity());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("When no word passed to add words then bad request error is returned")
    void post_emptyOrBlankWord_returns400(String input) throws Exception {
        doThrow(EmptyOrBlankInputException.class).when(wordCounterService).addWords(input);

        mockMvc.perform(post("/words")
                .param(WORD_PARAM, input))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When not a word passed to add words then not found input error is returned")
    void post_notAWord_returns404() throws Exception {
        doThrow(NotFoundInputException.class).when(wordCounterService).addWords(NOT_A_WORD);

        mockMvc.perform(post("/words")
                .param(WORD_PARAM, NOT_A_WORD))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @MethodSource("generateValidInputData")
    @DisplayName("When valid word passed to add words then it gets added")
    void post_aValidWord_returns201(List<String> input) throws Exception {
        doNothing().when(wordCounterService).addWords(input.toArray(new String[0]));

        MultiValueMap<String, String> body
                = new LinkedMultiValueMap<>();
        body.addAll(WORD_PARAM, input);

        mockMvc.perform(post("/words")
                .params(body))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {EMPTY, BLANK, NOT_AlPHABETIC_WORD, NOT_A_WORD, VALID_ENGLISH_WORD})
    @DisplayName("When invalid or not added word passed to get word count then 0 is returned")
    void get_inValidOrNotAddedWordPassed_returns0(String input) throws Exception {
        when(wordCounterService.getCount(input)).thenReturn(0);

        mockMvc.perform(get("/words")
                .param(WORD_PARAM, input))
                .andExpect(status().isOk())
                .andExpect(content().string(valueOf(0)));
    }

    @Test
    @DisplayName("When valid added word passed to get word count then expected count is returned")
    void get_validWordPassed_returnsExpectedCount() throws Exception {
        when(wordCounterService.getCount(VALID_ENGLISH_WORD)).thenReturn(1);

        mockMvc.perform(get("/words")
                .param(WORD_PARAM, VALID_ENGLISH_WORD))
                .andExpect(status().isOk())
                .andExpect(content().string(valueOf(1)));
    }
}