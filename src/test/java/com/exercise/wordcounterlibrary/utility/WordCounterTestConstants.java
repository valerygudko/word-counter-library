package com.exercise.wordcounterlibrary.utility;

public class WordCounterTestConstants {

    public static final String EMPTY = "";
    public static final String BLANK = " ";
    public static final String WORD_PARAM = "word";
    public static final String WORD_MAP = "words";
    public static final String NOT_AlPHABETIC_WORD = "dg51!";
    public static final String[] WORDS_WITH_NOT_AlPHABETIC_WORD = new String[]{"dg", NOT_AlPHABETIC_WORD};
    public static final String NOT_A_WORD = "oo";
    public static final String VALID_ENGLISH_WORD = "flower";
    public static final String[] WORDS_WITH_NOT_A_WORD = new String[]{VALID_ENGLISH_WORD, NOT_A_WORD};
    public static final String[] WORDS_WITH_EMPTY_WORD = new String[]{VALID_ENGLISH_WORD, EMPTY};
    public static final String VALID_DIFF_LANGUAGE_WORD = "flor";
    public static final String[] VALID_WORDS = new String[]{VALID_ENGLISH_WORD, VALID_DIFF_LANGUAGE_WORD};
    public static final Integer DEFAULT_WORD_COUNT = 1;
    public static final Integer EXPECTED_WORD_COUNT = 3;
}
