package com.rulsion.file.docTest.reader;


import com.alibaba.fastjson.JSONObject;
import com.rulsion.file.docTest.entity.FileRecord;
import com.rulsion.file.docTest.entity.PageRecord;
import com.rulsion.file.docTest.entity.TextRecord;
import com.rulsion.file.util.FileUtil;
import com.rulsion.file.util.PPTUtil;
import lombok.Data;
import org.apache.poi.sl.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Data
public abstract class ReaderForPPT extends ReaderForOffice {

    protected SlideShow slideShow;

    @Override
    public String Read() throws IOException {

        StringBuilder sb = new StringBuilder();

        List<Slide> slides = slideShow.getSlides();
        FileRecord fileRecord = new FileRecord();
        for (int pointIdx = 0; pointIdx < slides.size(); pointIdx++) {
            Slide each = slides.get(pointIdx);
            PageRecord pageRecord = new PageRecord();
            fileRecord.add(pageRecord);
            List<Shape> hslfShapes = each.getShapes();
            PPTUtil.toImage(each, this);
            for (int idx = 0; idx < hslfShapes.size(); idx++) {
                Shape e = hslfShapes.get(idx);
                if (e instanceof PictureShape) {
                    PictureShape pict = (PictureShape) e;
                    PictureData pictData = pict.getPictureData();
                    byte[] data = pictData.getData();
                    PictureData.PictureType type = pictData.getType();
                    String imgFilePath = "D:\\img";
                    FileUtil.mkdir(imgFilePath);
                    FileOutputStream out = new FileOutputStream(imgFilePath + File.separator + idx + type.extension);
                    out.write(data);
                    out.close();
                } else if (e instanceof AutoShape) {
                    TextRecord textRecord = new TextRecord();
                    pageRecord.add(textRecord);
                    textRecord.setRow(idx).setColumn(0).setText(((AutoShape) e).getText());
                    sb.append(((AutoShape) e).getText()).append("\r\n");
                }
            }

        }

        return JSONObject.toJSON(fileRecord).toString();
    }

}
