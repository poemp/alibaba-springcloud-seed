package org.poem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.poem.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/demo")
@Api(tags = {
        "01-demo"
})
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);


    /**
     * get请求
     *
     * @return
     */
    @ApiOperation(value = "demo-get请求", notes = "get请求")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没有填好"), @ApiResponse(code = 404, message = "请求路径没有找到")})
    @GetMapping(value = "/get")
    public CommonResult<String> get() {
        logger.info("DemoController->get");
        return new CommonResult<>("demo-seed -> get");
    }

    /**
     * get请求
     *
     * @return
     */
    @ApiOperation(value = "demo-post请求", notes = "post请求")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没有填好"), @ApiResponse(code = 404, message = "请求路径没有找到")})
    @PostMapping(value = "/post")
    public CommonResult<String> post() {
        logger.info("DemoController->post");
        return new CommonResult<>("demo-seed -> post");
    }
}
