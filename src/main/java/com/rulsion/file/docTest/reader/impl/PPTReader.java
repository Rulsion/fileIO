package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.reader.ReaderForPPT;
import lombok.Data;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;

import java.io.IOException;
import java.io.InputStream;

@Data
public class PPTReader extends ReaderForPPT {

    public PPTReader(InputStream inputStream) throws IOException {

        this.slideShow = new HSLFSlideShow(new HSLFSlideShowImpl(inputStream));
    }

}
