package com.exercise.wordcounterlibrary.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static com.exercise.wordcounterlibrary.utility.WordCounterTestConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class WordsCacheFunctionalTest {

    private final TestHazelcastInstanceFactory hazelcastFactory = new TestHazelcastInstanceFactory();
    private final HazelcastInstance hazelcastInstance = hazelcastFactory.newHazelcastInstance();
    @InjectMocks
    private WordsCache wordsCache;

    @BeforeEach
    private void setUp() {
        ReflectionTestUtils.setField(wordsCache, "hazelcastInstance", hazelcastInstance, HazelcastInstance.class);
    }

    @Test
    @DisplayName("The entity gets added to the expected words map")
    void put_addsEntityToTheExpectedMap() {
        //given & when
        IMap<Object, Object> mockedMap = hazelcastInstance.getMap(WORD_MAP);
        wordsCache.put(VALID_ENGLISH_WORD, DEFAULT_WORD_COUNT);

        //then
        assertThat(mockedMap.get(VALID_ENGLISH_WORD))
                .as(String.format("cache should contain expected value for key: %s", VALID_ENGLISH_WORD))
                .isEqualTo(DEFAULT_WORD_COUNT);
    }

    @Test
    @DisplayName("The entity gets expected count from words map")
    void getCount_getsValueFromTheExpectedMap() {
        //given
        IMap<Object, Object> mockedMap = hazelcastInstance.getMap(WORD_MAP);
        mockedMap.put(VALID_ENGLISH_WORD, EXPECTED_WORD_COUNT);

        // when
        int actualWordCount = wordsCache.getCount(VALID_ENGLISH_WORD);

        //then
        assertThat(actualWordCount)
                .as(String.format("cache should return expected count for key: %s", VALID_ENGLISH_WORD))
                .isEqualTo(EXPECTED_WORD_COUNT);
    }
}