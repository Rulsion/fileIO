package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.reader.ReaderForPPT;
import lombok.Data;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;

import java.io.IOException;

@Data
public class PPTReader extends ReaderForPPT {

    public PPTReader(String fileName) throws IOException {
        super(fileName);
        this.slideShow = new HSLFSlideShow(new HSLFSlideShowImpl(fileName));
    }

}
