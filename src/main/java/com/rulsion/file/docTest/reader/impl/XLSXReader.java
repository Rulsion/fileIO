package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.reader.ReaderForExcel;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XLSXReader extends ReaderForExcel {
    public XLSXReader(InputStream fileName) throws IOException {
        this.workbook = new XSSFWorkbook(fileName);
    }
}
