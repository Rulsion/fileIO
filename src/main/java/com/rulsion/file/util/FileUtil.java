package com.rulsion.file.util;


import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class FileUtil {
    //    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FileUtil.class);
    public synchronized static void mkdir(String filePath) {
        if (filePath.indexOf(File.separator) < 0) {
            return;
        }
        File path = new File(filePath);
        //判断是否创建成功
        if (path.exists()) {
            return;
        }

        mkdir(filePath.substring(0, filePath.lastIndexOf(File.separator)));

        //文件夹不存在则创建文件夹
        path.mkdir();

    }


}
