package org.poem.handler.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.poem.handler.auth.impl.CAuthService;
import org.poem.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/v1/auth")
@Api(tags = {
        "02-auth"
})
public class CAuthController {

    private static final Logger logger = LoggerFactory.getLogger(CAuthController.class);


    @Autowired
    private  CAuthService cAuthService;
    /**
     * get请求
     *
     * @return
     */
    @ApiOperation(value = "auth-get请求", notes = "get请求")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没有填好"), @ApiResponse(code = 404, message = "请求路径没有找到")})
    @GetMapping(value = "/get")
    public CommonResult<String> get() {
        logger.info("CAuthController->post");
        return cAuthService.get();
    }

    /**
     * get请求
     *
     * @return
     */
    @ApiOperation(value = "auth-post请求", notes = "post请求")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没有填好"), @ApiResponse(code = 404, message = "请求路径没有找到")})
    @PostMapping(value = "/post")
    public CommonResult<String> post() {
        logger.info("CAuthController->post");
        return cAuthService.post();
    }

}
