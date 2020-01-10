package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.reader.DocReader;
import com.rulsion.file.util.FileUtil;
import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

@Data
public class PDFReader implements DocReader {

    private PDDocument document;

    public PDFReader(InputStream inputStream) throws IOException {

        PDDocument document = PDDocument.load(inputStream);
        this.document = document;
    }

    @Override
    public String Read() throws IOException, ParserConfigurationException {

        FileUtil.pdftohtml(document, "C:\\Users\\Administrator\\Desktop\\demo.html");
//        FileUtil.readText(document);
        return "success";

    }
}
