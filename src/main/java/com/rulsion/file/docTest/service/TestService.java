package com.rulsion.file.docTest.service;

import com.rulsion.file.docTest.entity.Webuploader;
import com.rulsion.file.util.JsonResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface  TestService {

    String readFile(String fileName) throws IOException, ParserConfigurationException;

    JsonResponseResult uploadFile(Webuploader files, HttpServletRequest request, HttpServletResponse response) throws Exception;

    void uploadFiles(List<MultipartFile> request) throws  IOException;
}
