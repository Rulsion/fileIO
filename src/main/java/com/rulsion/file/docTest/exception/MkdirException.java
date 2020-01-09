package com.rulsion.file.docTest.exception;

public class MkdirException extends RuntimeException {
    private String msg;
    public MkdirException(String msg) {
        this.msg = msg;
    }
}
