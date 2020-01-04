package com.rulsion.file.async;

import com.rulsion.file.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileAsync {

    @Async("fileIOExecutor")
    public void saveFile(MultipartFile file) throws InterruptedException {

        log.info("保存文件开始，线程ID:[{}],文件名:[{}]",Thread.currentThread().getId(),file.getOriginalFilename());
        FileUtil.uploadFile(file);
        log.info("保存文件结束，线程ID:[{}],文件名:[{}]",Thread.currentThread().getId(),file.getOriginalFilename());

    }
}
