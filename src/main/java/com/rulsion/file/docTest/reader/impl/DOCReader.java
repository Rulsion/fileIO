package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.entity.FileRecord;
import com.rulsion.file.docTest.entity.PageRecord;
import com.rulsion.file.docTest.entity.TextRecord;
import com.rulsion.file.docTest.reader.ReaderForWord;
import com.rulsion.file.util.SysUtil;
import lombok.Data;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Data
public class DOCReader extends ReaderForWord {

    private HWPFDocument document;
    private WordExtractor ex;

    public DOCReader(String fileName) throws IOException {
        super(fileName);
        document = new HWPFDocument(new FileInputStream(new File(fileName)));
        InputStream is = new FileInputStream(new File(fileName));
        ex = new WordExtractor(is);
    }

    @Override
    public String Read() {
        FileRecord fileRecord = new FileRecord();
        String[] strs = ex.getParagraphText();
        for (int i = 0; i < strs.length; i++) {
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
