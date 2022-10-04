package com.exercise.wordcounterlibrary.cache;

import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.exercise.wordcounterlibrary.utility.WordCounterTestConstants.WORD_MAP;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WordsCacheTest {

    @InjectMocks
    private WordsCache wordsCache;

    @Mock
    private HazelcastInstance hazelcastInstance;

    @Test
    @DisplayName("The expected words map gets retrieved")
    void retrieveMap_callsTheCorrectGetMap() {
        //given & when
        wordsCache.retrieveMap();

        //then
        verify(hazelcastInstance).getMap(WORD_MAP);
    }

}