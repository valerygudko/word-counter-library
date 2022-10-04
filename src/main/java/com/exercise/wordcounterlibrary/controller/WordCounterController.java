package com.exercise.wordcounterlibrary.controller;

import com.exercise.wordcounterlibrary.service.WordCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/words")
public class WordCounterController {

    @Autowired
    private WordCounterService wordCounterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestParam(value = "word") String... words) {
        wordCounterService.addWords(words);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Integer get(@RequestParam(value = "word") String word) {
        return wordCounterService.getCount(word);
    }

}
