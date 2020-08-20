package org.poem.file;


import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author sangfor
 */
@ApiModel(value = "文件存储对象")
@Data
public class FileVO {

    private String fileId;

    private String path;

    private String name;

    private Long size;

    private String createTime;

    private String createUser;

    private byte[] data = null;


}

