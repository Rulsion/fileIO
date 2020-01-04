package com.rulsion.file.docTest.reader.impl;


import com.rulsion.file.docTest.entity.FileRecord;
import com.rulsion.file.docTest.entity.PageRecord;
import com.rulsion.file.docTest.entity.TextRecord;
import com.rulsion.file.docTest.reader.ReaderForWord;
import com.rulsion.file.util.SysUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class DOCXReader extends ReaderForWord {
    private XWPFDocument document;
    public DOCXReader(String fileName) throws IOException {
        super(fileName);
        document = new XWPFDocument(new FileInputStream(new File(fileName)));
    }

    @Override
    public String Read() throws IOException {

        FileRecord fileRecord = new FileRecord();

        List<XWPFParagraph> xwpfParagraphs = document.getParagraphs();
        for (int i = 0; i < xwpfParagraphs.size(); i++) {

            XWPFParagraph xwpfParagraph = xwpfParagraphs.get(i);

            //取得文本
            String text = xwpfParagraph.getText();
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
