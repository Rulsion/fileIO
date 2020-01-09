package com.rulsion.file.docTest.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class UploadInfo {
    private String md5;
    private String chunks;
    private String chunk;
    private String path;
    private String fileName;
    private String ext;

}
