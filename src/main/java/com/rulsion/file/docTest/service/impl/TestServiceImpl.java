package com.rulsion.file.docTest.service.impl;

import com.rulsion.file.async.FileAsync;
import com.rulsion.file.docTest.Factory.DocReaderFactory;
import com.rulsion.file.docTest.reader.DocReader;
import com.rulsion.file.docTest.service.TestService;
import com.rulsion.file.util.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@EnableAsync
public class TestServiceImpl implements TestService {
    @Autowired
   private  FileAsync fileAsync;
    @Override
    public String readFile(String fileName) throws IOException {
        DocReader docReader = DocReaderFactory.getReaderInstance(fileName);
        if (SysUtil.isEmpty(docReader)) {
            return null;
        }
        String str = docReader.Read();
        return str;
    }

    @Override
    public void uploadFile(MultipartFile file) throws InterruptedException {

        fileAsync.saveFile(file);
    }

    @Override
    public void uploadFiles(HttpServletRequest request) throws InterruptedException {
        //获取上传的文件数组
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        //遍历处理文件
        for (MultipartFile file : files) {
            fileAsync.saveFile(file);
        }

    }

}
