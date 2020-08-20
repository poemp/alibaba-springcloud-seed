package org.poem.controller;


import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.poem.file.FileUploadRespVo;
import org.poem.file.FileVO;
import org.poem.result.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author sangfor
 */
@RestController
@RequestMapping("/v1/file")
@Api(tags = {"01-文件操作"})
public class FileController {
    private static Logger logger = LoggerFactory.getLogger(FileController.class);


    @RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
    @ApiOperation(value = "0101_下载文件，图片直接打开", notes = "下载文件，图片直接打开", httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<byte[]> downloadImage(@PathVariable("fileId") String fileId, HttpServletResponse response) {
        if (StringUtils.isBlank(fileId)) {
            return null;
        }
        return null;
    }


    /**
     * 上传文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "0102_上传文件", notes = "上传文件", httpMethod = "POST")
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
        List<FileVO> fileVoList = new ArrayList<>();
        Collection<MultipartFile> files = multiRequest.getFileMap().values();
        try {

            for (MultipartFile file : files) {
                if (file == null || file.isEmpty() || StringUtils.isEmpty(file.getOriginalFilename())) {
                    continue;
                }

                FileVO fileVo = new FileVO();
                fileVo.setData(file.getBytes());
                fileVo.setName(file.getOriginalFilename());
                fileVo.setSize(file.getSize());
                fileVoList.add(fileVo);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
            return new CommonResult<>(1, null, e.getMessage());
        }
        return new CommonResult<>(1, Lists.newArrayList() );
    }


    /**
     * 获取文件的名字
     *
     * @param fileId
     * @return
     */
    @GetMapping(value = "/getFileName/{fileId}")
    @ApiOperation(value = "0104_获取文件的名字", notes = "获取文件的名字", httpMethod = "POST")
    public CommonResult<String> getFileName(@PathVariable("fileId") String fileId) {
        return new CommonResult<>(1, null, fileId);
    }
}
