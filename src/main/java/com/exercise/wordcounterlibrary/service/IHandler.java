package com.exercise.wordcounterlibrary.service;

public interface IHandler<T> {
    IHandler<T> setNext(IHandler<T> next);

    void handle(T request);
}
