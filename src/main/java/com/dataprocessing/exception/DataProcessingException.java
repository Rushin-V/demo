package com.dataprocessing.exception;

/**
 * @author Rushin
 * @since 20-03-21
 */
public class DataProcessingException extends Exception {

    /** Custom Exception with Default Constructor */
    public DataProcessingException() {}

    /**
     * Custom Exception with error Message
     * @param errorMessage
     */
    public DataProcessingException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Custom Exception with error message & Throwable
     * @param errorMessage
     * @param t
     */
    public DataProcessingException(String errorMessage, Throwable t){
        super(errorMessage,t);
    }
}
