package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.reader.ReaderForPPT;
import lombok.Data;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

import java.io.FileInputStream;
import java.io.IOException;

@Data
public class PPTXReader extends ReaderForPPT {


    public PPTXReader(String fileName) throws IOException {
        super(fileName);
        this.slideShow = new XMLSlideShow(new FileInputStream(fileName));

    }


}
