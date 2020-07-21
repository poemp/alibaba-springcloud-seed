package org.poem.config;

import java.util.Map;

/**
 * 微服务请求全局变量
 *
 * @author sangfor
 */
public class CenterContextHolder {

    /**
     * 当前请求的请求路径
     */
    private static ThreadLocal<String> requestPath = new ThreadLocal<>();


    /**
     * 当前的微服务请求微服务名
     */
    private static ThreadLocal<String> providerName = new ThreadLocal<>();

    /**
     * 请求的方式
     */
    private static ThreadLocal<String> method = new ThreadLocal<>();

    /**
     * 当前请求的数据
     */
    private static ThreadLocal<Map<String, Object>> params = new ThreadLocal<>();

    /**
     * 头信息
     */
    private static ThreadLocal<Map<String, Object>> header = new ThreadLocal<>();

    public static String getRequestPath() {
        return requestPath.get();
    }


    public static void setRequestPath(String requestPath) {
        CenterContextHolder.requestPath.set(requestPath);
    }


    public static String getProviderName() {
        return providerName.get();
    }

    public static void setProviderName(String providerName) {
        CenterContextHolder.providerName.set(providerName);
    }

    public static Map<String, Object> getParams() {
        return params.get();
    }

    public static void setParams(Map<String, Object> params) {
        CenterContextHolder.params.set(params);
    }


    public static Map<String, Object> getHeader() {
        return header.get();
    }

    public static void setHeader(Map<String, Object> header) {
        CenterContextHolder.header.set(header);
    }


    public static String getMethod() {
        return method.get();
    }

    public static void setMethod(String method) {
        CenterContextHolder.method.set(method);
    }

    public static void remove() {
        requestPath.remove();
        method.remove();
        params.remove();
        header.remove();
    }
}
