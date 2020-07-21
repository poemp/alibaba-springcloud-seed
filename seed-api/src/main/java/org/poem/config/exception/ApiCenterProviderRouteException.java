package org.poem.config.exception;

/**
 * 404 not found
 * @author sangfor
 */
public class ApiCenterProviderRouteException extends Exception {
    public ApiCenterProviderRouteException(String message) {
        super(message);
    }



    public ApiCenterProviderRouteException(String message, Throwable cause) {
        super(message, cause);
    }
}
