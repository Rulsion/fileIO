package com.rulsion.file.docTest.Factory;


import com.rulsion.file.docTest.exception.DocReaderInstanceException;
import com.rulsion.file.docTest.reader.DocReader;
import com.rulsion.file.docTest.reader.impl.*;

import java.io.IOException;

public class DocReaderFactory {
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";
    private static final String DOC = "doc";
    private static final String DOCX = "docx";
    private static final String PPT = "ppt";
    private static final String PPTX = "pptx";
    private static final String PDF = "pdf";


    public static DocReader getReaderInstance(String fileName) throws IOException {
        //分割路径
        String[] filePathSplit = fileName.split("\\\\");
        if (filePathSplit.length < 1)
            throw new DocReaderInstanceException();
        // 分割文件名
        String[] fileSplit = filePathSplit[filePathSplit.length - 1].split("\\.");
        if (fileSplit.length < 2)
            throw new DocReaderInstanceException();
        //获取文件后缀
        String suffix = fileSplit[1];

        switch (suffix) {
            case PPT:
                return new PPTReader(fileName);
            case PPTX:
                return new PPTXReader(fileName);
            case PDF:
                return new PDFReader(fileName);
            case DOC:
                return new DOCReader(fileName);
            case DOCX:
                return new DOCXReader(fileName);
            case XLS:
                return new XLSReader(fileName);
            case XLSX:
                return new XLSXReader(fileName);
            default:
                return null;
        }

    }
}
