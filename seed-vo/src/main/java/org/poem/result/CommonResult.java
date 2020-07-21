package org.poem.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author sangfor
 */
@ApiModel(description = "通用结果")
public class CommonResult<T> implements Serializable {

    @ApiModelProperty(value = "业务状态码")
    private Integer code;

    @ApiModelProperty(value = "有效数据")
    private T data;

    @ApiModelProperty(value = "消息")
    private String message;

    public CommonResult() {
        this.code = 0;
    }


    public CommonResult(T data) {
        this.data = data;
        this.code = 0;
    }

    public CommonResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public CommonResult(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
