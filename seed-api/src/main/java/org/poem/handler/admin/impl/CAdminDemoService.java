package org.poem.handler.admin.impl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.poem.ProviderName;
import org.poem.config.error.ErrMsgConfiguration;
import org.poem.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Administrator
 */
@Service
@FeignClient(name = ProviderName.SEED_ADMIN, configuration = {ErrMsgConfiguration.class})
public interface CAdminDemoService {
    /**
     * get请求
     *
     * @return
     */
    @ApiOperation(value = "get请求", notes = "get请求")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没有填好"), @ApiResponse(code = 404, message = "请求路径没有找到")})
    @GetMapping(value = "/v1/demo/get")
    public CommonResult<String> get();
    /**
     * get请求
     *
     * @return
     */
    @ApiOperation(value = "post请求", notes = "post请求")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没有填好"), @ApiResponse(code = 404, message = "请求路径没有找到")})
    @PostMapping(value = "/v1/demo/post")
    public CommonResult<String> post()  ;

}
