package com.rulsion.file.docTest.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface  TestService {

    String readFile(String fileName) throws IOException;

    void uploadFile(MultipartFile file) throws  IOException;

    void uploadFiles(List<MultipartFile> request) throws  IOException;
}
