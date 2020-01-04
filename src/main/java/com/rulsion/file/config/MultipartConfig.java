package com.rulsion.file.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class  MultipartConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //最大单个文件
        factory.setMaxFileSize( DataSize.ofMegabytes(100));
        /// 最大总上传数据
        factory.setMaxRequestSize( DataSize.ofGigabytes(2));
        return factory.createMultipartConfig();
    }
}
