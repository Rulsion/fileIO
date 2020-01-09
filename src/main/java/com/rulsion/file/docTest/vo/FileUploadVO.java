package com.rulsion.file.docTest.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileUploadVO {
   private Integer  sort;
   private String  fileName;
   private Byte[]  sendByte;
   private Integer  fileUploadId;
   private String  lastModified;
   private Long  fileSplitSize;
   private Long  fileSize;
}
