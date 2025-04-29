package com.example.my_mod.utils;

public class NoStartBlockException extends RuntimeException {
    public NoStartBlockException() {}
    public NoStartBlockException(String message) {
        super(message);
    }
}
