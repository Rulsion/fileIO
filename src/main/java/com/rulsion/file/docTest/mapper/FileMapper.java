package com.rulsion.file.docTest.mapper;

import com.rulsion.file.docTest.entity.FileUpload;

import java.util.List;

public interface FileMapper {
    List<FileUpload> getFileUploadByUserIdAndFileName(FileUpload fileUpload);
}
