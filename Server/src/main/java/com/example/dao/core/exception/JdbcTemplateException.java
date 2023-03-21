package com.example.dao.core.exception;


public class JdbcTemplateException extends Exception {
    public JdbcTemplateException() {
        super();
    }

    public JdbcTemplateException(String message) {
        super(message);
    }

    public JdbcTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcTemplateException(Throwable cause) {
        super(cause);
    }

}
