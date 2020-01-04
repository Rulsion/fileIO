package com.rulsion.file.docTest.entity;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@ToString
@Data
public class FileRecord {
    private List<PageRecord> pageRecords;
    public FileRecord(){
        this.pageRecords = new ArrayList<>();
    }
    public void add(PageRecord pageRecord){
        this.pageRecords.add(pageRecord);
    }
}
