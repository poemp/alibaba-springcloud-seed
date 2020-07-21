package org.poem.config.exception;

/**
 * @author sangfor
 */
public class ApiCenterProviderException extends Exception{

    public ApiCenterProviderException(String message) {
        super(message);
    }



    public ApiCenterProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
