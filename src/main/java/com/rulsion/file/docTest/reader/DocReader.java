package com.rulsion.file.docTest.reader;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface DocReader {
    String Read() throws IOException, ParserConfigurationException;
}
