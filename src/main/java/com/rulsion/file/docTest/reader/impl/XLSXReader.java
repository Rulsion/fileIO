package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.reader.ReaderForExcel;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class XLSXReader extends ReaderForExcel {
    public XLSXReader(String fileName) throws IOException {
        super(fileName);
        this.workbook = new XSSFWorkbook(new FileInputStream(fileName));
    }
}
