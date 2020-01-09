package com.rulsion.file.docTest.controller;

import com.rulsion.file.docTest.entity.Webuploader;
import com.rulsion.file.docTest.service.TestService;
import com.rulsion.file.util.JsonResponseResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
public class TestController {

    private TestService testService;

    @GetMapping("/test")
    public String test(@RequestParam String fileName) throws IOException {
        return testService.readFile(fileName);
    }

    @GetMapping("/fileTest")
    public ModelAndView fileTest() {
        return new ModelAndView("demo");
    }


    //处理文件上传
    @PostMapping(value = "/testUploadFile")
    public JsonResponseResult uploadImg(Webuploader uploader, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return testService.uploadFile(uploader, request, response);
    }

    //处理多文件上传
    @PostMapping(value = "/testUploadFiles")
    public String multipleFilesUpload(@RequestParam("file") List<MultipartFile> files) throws IOException {

        testService.uploadFiles(files);
        return "success";

    }
}
