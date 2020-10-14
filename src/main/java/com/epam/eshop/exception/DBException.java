package com.epam.eshop.exception;

/**
 * Created by artch on 22.09.2020.
 */
public class DBException extends RuntimeException {

    public DBException(String reason) {
        super(reason);
    }

    public DBException(String reason, Exception e) {
        super(reason, e);
    }
}
