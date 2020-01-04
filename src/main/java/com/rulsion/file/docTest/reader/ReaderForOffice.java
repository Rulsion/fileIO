package com.rulsion.file.docTest.reader;

import lombok.Data;

@Data
public abstract class ReaderForOffice implements DocReader {
    protected String filePath;
    protected String fileName;
    protected String suffix;

    public ReaderForOffice(String fileName){
        //分割路径
        this.filePath = fileName.substring(0, fileName.lastIndexOf("\\"));

        // 分割文件名
        this.fileName = fileName.substring(fileName.lastIndexOf("\\"), fileName.lastIndexOf("."));

        // 分割后缀
        this.suffix = fileName.substring(fileName.lastIndexOf("."));
    }
}
