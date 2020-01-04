package com.rulsion.file.docTest.reader;


import lombok.Data;

@Data
public abstract class ReaderForWord extends ReaderForOffice{
    public ReaderForWord(String fileName){
        super(fileName);
    }
}
