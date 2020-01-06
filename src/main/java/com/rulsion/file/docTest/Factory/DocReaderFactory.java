package com.rulsion.file.docTest.Factory;


import com.rulsion.file.docTest.reader.DocReader;
import com.rulsion.file.docTest.reader.impl.*;

import java.io.IOException;
import java.io.InputStream;

public class DocReaderFactory {
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";
    private static final String DOC = "doc";
    private static final String DOCX = "docx";
    private static final String PPT = "ppt";
    private static final String PPTX = "pptx";
    private static final String PDF = "pdf";

    public static DocReader getReaderInstance(InputStream inputStream, String suffix) throws IOException {

        switch (suffix) {
            case PPT:
                return new PPTReader(inputStream);
            case PPTX:
                return new PPTXReader(inputStream);
            case PDF:
                return new PDFReader(inputStream);
            case DOC:
                return new DOCReader(inputStream);
            case DOCX:
                return new DOCXReader(inputStream);
            case XLS:
                return new XLSReader(inputStream);
            case XLSX:
                return new XLSXReader(inputStream);
            default:
                return null;
        }

    }
}
