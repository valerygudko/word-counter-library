package com.exercise.wordcounterlibrary.service;

public abstract class Handler<T> implements IHandler<T> {

    private IHandler<T> next;

    public void handle(T request) {
        if (next != null)
            next.handle(request);
    }

    public IHandler<T> setNext(IHandler<T> next) {
        this.next = next;
        return this.next;
    }
}
