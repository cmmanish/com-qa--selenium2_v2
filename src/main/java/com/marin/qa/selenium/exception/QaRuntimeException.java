package com.marin.qa.selenium.exception;

/**
 * @author mmadhusoodan
 */
public class QaRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public QaRuntimeException(Throwable e) {
        super(e);
    }

    public QaRuntimeException(String msg, Throwable e) {
        super(msg, e);
    }

    public QaRuntimeException(String msg) {
        super(msg);
    }

   
}

