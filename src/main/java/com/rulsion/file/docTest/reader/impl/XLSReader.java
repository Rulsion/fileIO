package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.reader.ReaderForExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XLSReader extends ReaderForExcel {

    public XLSReader(InputStream inputStream) throws IOException {

        this.workbook = new HSSFWorkbook(inputStream);
    }


}
