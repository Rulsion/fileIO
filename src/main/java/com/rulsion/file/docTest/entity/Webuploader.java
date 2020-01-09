package com.rulsion.file.docTest.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Data
@Accessors(chain = true)
public class Webuploader {
    //uploader自带参数
    private MultipartFile file;
    private String chunks;//总分片量
    private String chunk;//当前分片
    private String name;//文件名称
    private String size;//文件大小
    private String lastModifiedDate;//最后修改时间
    private String type;//文件类型
    private String id;//文件id
    private String guid;
    //自定义参数
    private String md5value;
    private String pathName;//目录结构

}
