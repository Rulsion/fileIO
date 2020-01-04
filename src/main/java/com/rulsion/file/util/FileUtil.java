package com.rulsion.file.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    /**
     * 第一步：判断文件是否为空   true：返回提示为空信息   false：执行第二步
     * 第二步：判断目录是否存在   不存在：创建目录
     * 第三部：通过输出流将文件写入硬盘文件夹并关闭流
     *
     * @param file
     * @return
     */

    public static boolean uploadFile(MultipartFile file) throws InterruptedException {
        String fileName = file.getOriginalFilename().replace("/", File.separator);
        String filePath = "D:\\repository\\";
        if (fileName.lastIndexOf(File.separator) != -1) {
            filePath = filePath + fileName.substring(0, fileName.lastIndexOf(File.separator)+1);
            fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
        }
        //第一步：判断文件是否为空
        if (file.isEmpty()) {
//            log.info();
            System.out.println("文件为空");
            return false;
        }
        mkdir(filePath);
        //第三部：通过输出流将文件写入硬盘文件夹并关闭流

        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(filePath + fileName))) {

                stream.write(file.getBytes());
                stream.flush();

        } catch (IOException e) {
            log.info("文件写入异常,文件名:[{}]",fileName);
        }

        return true;
    }


}
