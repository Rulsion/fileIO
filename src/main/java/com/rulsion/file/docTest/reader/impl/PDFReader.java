package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.reader.DocReader;
import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Data
public class PDFReader implements DocReader {

    private PDDocument document;

    public PDFReader(InputStream inputStream) throws IOException {

        PDDocument document = PDDocument.load(inputStream);
        this.document = document;
    }

    @Override
    public String Read() throws IOException {
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        // 设置是否排序
        stripper.setSortByPosition(false);
        // 设置起始页
        stripper.setStartPage(1);
        // 设置结束页
        stripper.setEndPage(document.getNumberOfPages());
       List<String> strs = stripper.getRegions();
        PDFTextStripper pdfStripper = new PDFTextStripper();

        String text = pdfStripper.getText(document);
        return text;
    }
}
