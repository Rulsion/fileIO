package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.entity.FileRecord;
import com.rulsion.file.docTest.entity.PageRecord;
import com.rulsion.file.docTest.entity.TextRecord;
import com.rulsion.file.docTest.reader.ReaderForWord;
import com.rulsion.file.util.SysUtil;
import lombok.Data;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.Picture;

import java.io.*;
import java.util.List;
import java.util.UUID;

@Data
public class DOCReader extends ReaderForWord {

    private HWPFDocument document;
    private WordExtractor ex;

    public DOCReader(InputStream inputStream) throws IOException {

        document = new HWPFDocument(inputStream);
        ex = new WordExtractor(inputStream);
    }

    @Override
    public String Read() throws IOException {
        // 文档图片内容
        PicturesTable picturesTable = document.getPicturesTable();
        List<Picture> pictures = picturesTable.getAllPictures();

        int i = 0;
        for (Picture picture : pictures) {
            // 输出图片到磁盘
            OutputStream out = new FileOutputStream( new File("C:\\Users\\Administrator\\Desktop\\test\\"  + i++ + "." + picture.suggestFileExtension()));//输出图片文件到磁盘
            picture.writeImageContent(out);
            out.close();
        }
        FileRecord fileRecord = new FileRecord();
        String[] strs = ex.getParagraphText();
        for (i = 0; i < strs.length; i++) {
            String text = strs[i];
            if (SysUtil.isEmpty(text)) continue;

            text = text.replaceAll("\b|\n|\r", "");

            if (SysUtil.isEmpty(text)) continue;

            //创建文本对象
            PageRecord pageRecord = new PageRecord();
            fileRecord.add(pageRecord);
            pageRecord.setPageNo(i);
            TextRecord textRecord = new TextRecord();
            pageRecord.add(textRecord);
            textRecord.setText(text).setRow(0).setColumn(0);
        }

        return fileRecord.toString();
    }
}
