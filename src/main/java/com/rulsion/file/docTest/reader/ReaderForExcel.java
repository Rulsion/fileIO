package com.rulsion.file.docTest.reader;


import com.alibaba.fastjson.JSONObject;
import com.rulsion.file.docTest.entity.FileRecord;
import com.rulsion.file.docTest.entity.PageRecord;
import com.rulsion.file.docTest.entity.TextRecord;
import com.rulsion.file.util.SysUtil;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

@Data
public abstract class ReaderForExcel extends ReaderForOffice {
    protected Workbook workbook;



    @Override
    public String Read() throws IOException {
        FileRecord fileRecord = new FileRecord();

        int sheetNum = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            PageRecord pageRecord = new PageRecord();
            pageRecord.setPageNo(i);
            fileRecord.add(pageRecord);
            for (int j = 0; j <= sheet.getLastRowNum(); j++) {//获取每行
                Row row = sheet.getRow(j);
                if (SysUtil.isEmpty(row)) continue;
                for (int k = 0; k <= row.getLastCellNum(); k++) {//获取每个单元格
                    Cell cell = row.getCell(k);
                    if (SysUtil.isEmpty(cell)) continue;
                    TextRecord textRecord = new TextRecord();
                    pageRecord.add(textRecord);
                    textRecord.setRow(j);
                    textRecord.setColumn(k);

                    textRecord.setText(cell.toString());

                }

            }
        }

        return JSONObject.toJSON(fileRecord).toString();
    }
}
