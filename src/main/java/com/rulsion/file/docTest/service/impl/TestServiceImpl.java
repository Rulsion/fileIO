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

import java.io.File;
import java.io.FileInputStream;
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
        fileName.replaceAll("/|\\\\",File.separator);
        String fileName1  = fileName.substring(fileName.lastIndexOf(File.separator));
        String suffix = fileName1.substring(fileName1.lastIndexOf("."));
        DocReader docReader = DocReaderFactory.getReaderInstance(new FileInputStream(fileName),suffix);
        if (SysUtil.isEmpty(docReader)) {
            return null;
        }
        String str = docReader.Read();
        return str;
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {

        fileAsync.saveFile(file);
    }

    @Override
    public void uploadFiles(List<MultipartFile> files) throws IOException {
        //遍历处理文件
        for (MultipartFile file : files) {
            fileAsync.saveFile(file);
        }

    }

}
