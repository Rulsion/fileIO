package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.reader.ReaderForExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class XLSReader extends ReaderForExcel {

    public XLSReader(String fileName) throws IOException {
        super(fileName);
        this.workbook = new HSSFWorkbook(new FileInputStream(fileName));
    }


}
