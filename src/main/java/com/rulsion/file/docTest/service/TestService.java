package com.rulsion.file.docTest.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface  TestService {

    String readFile(String fileName) throws IOException;

    void uploadFile(MultipartFile file) throws InterruptedException;

    void uploadFiles(HttpServletRequest request) throws InterruptedException;
}
