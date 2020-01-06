package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.reader.ReaderForPPT;
import lombok.Data;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Data
public class PPTXReader extends ReaderForPPT {


    public PPTXReader(InputStream inputStream) throws IOException {

        this.slideShow = new XMLSlideShow(inputStream);

    }


}
