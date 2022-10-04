package com.exercise.wordcounterlibrary.cache;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;

@Component
public class WordsCache {

    public static final String WORDS = "words";

    @Autowired
    private HazelcastInstance hazelcastInstance;

    public ConcurrentMap<String, Integer> retrieveMap() {
        return hazelcastInstance.getMap(WORDS);
    }

    public Integer put(String word, Integer count) {
        return retrieveMap().put(word, count);
    }

    public Integer getCount(String key) {
        return retrieveMap().get(key);
    }

}
