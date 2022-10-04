package com.exercise.wordcounterlibrary.controller;

import com.exercise.wordcounterlibrary.service.WordCounterService;
import com.exercise.wordcounterlibrary.utility.WordCounterTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Stream;

import static com.exercise.wordcounterlibrary.utility.WordCounterTestConstants.*;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WordCounterControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
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

        MultiValueMap<String, String> body
                = new LinkedMultiValueMap<>();
        body.addAll(WORD_PARAM, input);

        mockMvc.perform(post("/words")
                .params(body))
                .andExpect(status().isUnprocessableEntity());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {EMPTY, BLANK})
    @DisplayName("When empty or blank word passed to add words then bad request error is returned")
    void post_emptyOrBlankWord_returns400(String input) throws Exception {

        mockMvc.perform(post("/words")
                .param(WORD_PARAM, input))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When not a word passed to add words then not found input error is returned")
    void post_notAWord_returns404() throws Exception {

        mockMvc.perform(post("/words")
                .param(WORD_PARAM, NOT_A_WORD))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @MethodSource("generateValidInputData")
    @DisplayName("When valid word passed to add words then it gets added")
    void post_aValidWord_returns201(List<String> input) throws Exception {

        MultiValueMap<String, String> body
                = new LinkedMultiValueMap<>();
        body.addAll(WORD_PARAM, input);

        mockMvc.perform(post("/words")
                .params(body))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @ValueSource(strings = {EMPTY, BLANK, NOT_AlPHABETIC_WORD, NOT_A_WORD})
    @DisplayName("When invalid or not added word passed to get word count then 0 is returned")
    void get_inValidOrNotAddedWordPassed_returns0(String input) throws Exception {

        mockMvc.perform(get("/words")
                .param(WORD_PARAM, input))
                .andExpect(status().isOk())
                .andExpect(content().string(valueOf(0)));
    }

    @ParameterizedTest
    @ValueSource(strings = {VALID_ENGLISH_WORD, VALID_DIFF_LANGUAGE_WORD})
    @DisplayName("When valid word passed to get word count then expected count is returned")
    void get_validWordPassed_returnsExpectedCount() throws Exception {

        MultiValueMap<String, String> body
                = new LinkedMultiValueMap<>();
        body.addAll(WORD_PARAM, asList(VALID_WORDS));

        int currentCount = Integer.parseInt(mockMvc.perform(get("/words")
                .param(WORD_PARAM, VALID_ENGLISH_WORD))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());

        mockMvc.perform(post("/words")
                .params(body))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/words")
                .param(WORD_PARAM, VALID_ENGLISH_WORD))
                .andExpect(status().isOk())
                .andExpect(content().string(valueOf(currentCount + 2)));
    }
}