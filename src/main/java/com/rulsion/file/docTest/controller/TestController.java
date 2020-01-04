package com.rulsion.file.docTest.controller;

import com.rulsion.file.docTest.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
    public String uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws InterruptedException {
        testService.uploadFile(file);
        return "success";
    }

    //处理多文件上传
    @PostMapping(value = "/testUploadFiles")
    public String multipleFilesUpload(HttpServletRequest request) throws InterruptedException {

        testService.uploadFiles(request);
        return "success";

    }
}
