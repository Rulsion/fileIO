package com.rulsion.file.docTest.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileUpload {


    private Integer id;//文件上传记录主键ID
    private Integer userid;//用户主键
    private String serverPath;//服务器文件路径（从用户文件目录开始）
    private String serverName;//上传到服务器文件名
    private String tmpName;//服务器临时文件名称
    private String localPath;//用户本地文件路径
    private String localName;//用户本地文件名
    private String lastModifiedDate;//本地文件最后更新时间，用以确认文件是否被更改
    private Long uploadedSize;//已上传大小 单位Byte
    private Integer status;//上传状态 0：未完成 1：已完成 2：已取消
    private String createTime;//任务创建时间
    private String finishTime;//任务完成时间


}
