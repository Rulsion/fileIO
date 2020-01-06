package com.rulsion.file.async;

import com.rulsion.file.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class FileAsync {

    /**
     * 第一步：判断文件是否为空   true：返回提示为空信息   false：执行第二步
     * 第二步：判断目录是否存在   不存在：创建目录
     * 第三部：通过输出流将文件写入硬盘文件夹并关闭流
     *
     * @param file
     * @return
     */
    public void saveFile(MultipartFile file) throws IOException {

        log.info("保存文件开始，线程ID:[{}],文件名:[{}]", Thread.currentThread().getId(), file.getOriginalFilename());
        String fileName = file.getOriginalFilename().replace("/", File.separator);
        String filePath = "D:\\repository\\";
        if (fileName.lastIndexOf(File.separator) != -1) {
            filePath = filePath + fileName.substring(0, fileName.lastIndexOf(File.separator) + 1);
            fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        }
        //MultipartFile接收到之后会生成临时文件。临时文件在生成流之后进行关闭，文件的操作会进行打开文件，所以文件的操作一定需要在生成文件流之前执行

        byte[] bytes = file.getBytes();
        //第一步：判断文件是否为空
        if (file.isEmpty()) {
            log.info("文件为空");
            return;
        }

        FileUtil.mkdir(filePath);
        //第三部：通过输出流将文件写入硬盘文件夹并关闭流

        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(filePath + fileName))) {
            stream.write(bytes);
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("文件写入异常,线程ID:[{}],文件名:[{}]", Thread.currentThread().getId(), fileName);
            return;
        }


        log.info("保存文件结束，线程ID:[{}],文件名:[{}]", Thread.currentThread().getId(), file.getOriginalFilename());

    }
}
