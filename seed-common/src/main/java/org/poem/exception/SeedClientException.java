package org.poem.exception;

/**
 * @author poem
 * 异常
 */
public class SeedClientException extends Exception {


    /**
     * Constructs a {@code CanalClientException} with no detail message.
     */
    public SeedClientException() {
        super();
    }

    /**
     * Constructs a {@code CanalClientException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public SeedClientException(String s) {
        super(s);
    }



    /**
     * Constructs a {@code CanalClientException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public SeedClientException(String s, Throwable throwable) {
        super(s,throwable);
    }
}
