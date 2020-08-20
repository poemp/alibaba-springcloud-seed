package org.poem.handler.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.poem.file.FileUploadRespVo;
import org.poem.handler.admin.impl.CAdminDemoService;
import org.poem.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/v1/demo")
@Api(tags = {
        "01-demo"
})
public class CAdminDemoController {

    private static final Logger logger = LoggerFactory.getLogger(CAdminDemoController.class);

    @Autowired
    private CAdminDemoService cAdminDemoService;

    /**
     * get请求
     *
     * @return
     */
    @ApiOperation(value = "get请求", notes = "get请求")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没有填好"), @ApiResponse(code = 404, message = "请求路径没有找到")})
    @GetMapping(value = "/get")
    public CommonResult<String> get() {
        logger.info("CAdminDemoController->get");
        return cAdminDemoService.get();
    }

    /**
     * get请求
     *
     * @return
     */
    @ApiOperation(value = "post请求", notes = "post请求")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没有填好"), @ApiResponse(code = 404, message = "请求路径没有找到")})
    @PostMapping(value = "/post")
    public CommonResult<String> post() {
        logger.info("CAdminDemoController->post");
        return cAdminDemoService.post();
    }

    @RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
    @ApiOperation(value = "2701_下载文件，图片直接打开", notes = "下载文件，图片直接打开", httpMethod = "GET")
    public ResponseEntity<byte[]> downloadImage(@PathVariable("fileId") String fileId, HttpServletResponse httpServletResponse) {
        if (StringUtils.isBlank(fileId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        ResponseEntity<byte[]> result = null;
        InputStream inputStream = null;
        try {
            CommonResult<String> nameResult = cAdminDemoService.getFileName(fileId);
            if (nameResult.getCode() == 1) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            // feign文件下载
            ResponseEntity<byte[]> response = cAdminDemoService.downloadImage(fileId);
            byte[] b = response.getBody();
            HttpHeaders heads = new HttpHeaders();
            heads.add("Content-Disposition", "attachment; filename=\"" + new String(nameResult.getData().getBytes(StandardCharsets.UTF_8), "ISO8859-1") + "\"");
            logger.info("Down File Name:" + nameResult);
            assert b != null;
            logger.info("Down File Size:" + b.length);
            result = new ResponseEntity<byte[]>(b, heads, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 上传文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "2702_上传文件", notes = "上传文件", httpMethod = "POST")
    @ResponseBody
    public CommonResult<List<FileUploadRespVo>> fileUpload(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (!multipartResolver.isMultipart(request)) {
            return new CommonResult<>(1, null, "");
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Collection<MultipartFile> files = multiRequest.getFileMap().values();
        try {
            return cAdminDemoService.fileUpload(files.toArray(new MultipartFile[0]));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
            return new CommonResult<>(1, null, e.getMessage());
        }
    }
}
