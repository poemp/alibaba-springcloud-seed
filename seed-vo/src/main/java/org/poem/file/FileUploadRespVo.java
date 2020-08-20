package org.poem.file;


import lombok.Data;

@Data
public class FileUploadRespVo {
    private String fileId;

    private String filePath;

    private String name;

    private String fileType;
}
