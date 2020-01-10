package com.rulsion.file.util;

import com.rulsion.file.docTest.entity.UploadInfo;
import com.rulsion.file.docTest.entity.Webuploader;
import com.rulsion.file.docTest.exception.MkdirException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.fit.pdfdom.BoxStyle;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFDomTreeConfig;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class FileUtil {

    private final static List<UploadInfo> uploadInfoList = new ArrayList<>();


    public synchronized static boolean mkdir(String filePath) {

        File path = new File(filePath);
        //判断是否存在目录
        if (path.exists()) {
            return true;
        }
        if (!filePath.contains(File.separator)) {
            return false;
        }

        if (filePath.lastIndexOf(File.separator) + 1 == filePath.length()) {
            filePath = filePath.substring(0, filePath.length() - 1);
        }
        //创建父级文件夹
        if (mkdir(filePath.substring(0, filePath.lastIndexOf(File.separator))))
            //文件夹不存在则创建文件夹
            return path.mkdir();
        else
            return false;
    }

    /**
     * @param request 请求
     * @return String
     * @Description: 取得tomcat中的webapps目录 如： /home/gzxiaoi/apache-tomcat-8.0.45/webapps
     */
    public static String getRealPath(HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath(File.separator);
        realPath = realPath.substring(0, realPath.length() - 1);
        int aString = realPath.lastIndexOf(File.separator);
        realPath = realPath.substring(0, aString);
        return realPath;
    }

    public static void saveFile(String savePath, String fileFullName, MultipartFile file) throws IOException {
        byte[] data = readInputStream(file.getInputStream());
        // new一个文件对象用来保存图片，默认保存当前工程根目录
        File uploadFile = new File(savePath + fileFullName);
        if (!mkdir(savePath)) {
            throw new MkdirException("文件夹创建失败！路径为：" + savePath);
        }
        // 创建输出流
        try (FileOutputStream outStream = new FileOutputStream(uploadFile)) {// 写入数据
            outStream.write(data);
            outStream.flush();
        }
    }

    private static byte[] readInputStream(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        // 关闭输入流
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }


    public static void Uploaded(Webuploader uploader, String uploadFolderPath, String fileName, String ext) throws IOException {
        String md5 = uploader.getMd5value();
        synchronized (uploadInfoList) {
            if ((md5 != null && !md5.equals("")) &&
                    (uploader.getChunks() != null &&
                            !uploader.getChunks().equals("")) &&
                    !isNotExist(md5, uploader.getChunk())) {
                uploadInfoList.add(new UploadInfo(md5, uploader.getChunks(), uploader.getChunk(), uploadFolderPath, fileName, ext));
            }
        }
        boolean allUploaded = isAllUploaded(md5, uploader.getChunks());
        int chunksNumber = Integer.parseInt(uploader.getChunks());

        if (allUploaded) {
            mergeFile(uploader,chunksNumber, ext, uploadFolderPath);
            // fileService.save(new
            // com.zhangzhihao.FileUpload.Java.Model.File(guid + ext, md5, new
            // Date()));
        }
    }

    //判断在uploadInfoList是否有存在MD5和chunk都相同的元素
    private static boolean isNotExist(final String md5, final String chunk) {
        boolean flag = false;
        for (UploadInfo uploadInfo : uploadInfoList) {
            if (uploadInfo.getChunk().equals(chunk) && uploadInfo.getMd5().equals(md5)) {
                //若md5和chunk都相同，则认为两条记录相同，返回true
                flag = true;
            }
        }
        return flag;
    }

    private static boolean isAllUploaded(final String md5, String chunks) {
        int size = uploadInfoList.stream().filter(item -> item.getMd5().equals(md5)).distinct().collect(Collectors.toList()).size();
        boolean bool = (size == Integer.parseInt(chunks));
        if (bool) {
            synchronized (uploadInfoList) {
                uploadInfoList.removeIf(item -> Objects.equals(item.getMd5(), md5));
            }
        }
        return bool;
    }

    private static void mergeFile(Webuploader uploader,int chunksNumber, String ext, String uploadFolderPath) throws IOException {
        SimpleDateFormat year = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String pathOfFile = uploader.getPathName().substring(0,uploader.getPathName().lastIndexOf(File.separator));

        String destPath = uploadFolderPath +File.separator+"fileDate"+File.separator+ year.format(date)+File.separator+pathOfFile ;// 文件路径

        String newName = uploader.getName();// 文件新名称

        Vector<InputStream> v = new Vector<>();
        for (int i = 0; i < chunksNumber; i++) {
            String tempFilePath = uploadFolderPath + i + ext;
            InputStream s1 = new FileInputStream(tempFilePath);
            v.addElement(s1);
        }
        try (SequenceInputStream s = new SequenceInputStream(v.elements())) {
            // 通过输出流向文件写入数据(转移正式文件到目标目录)
            saveStreamToFile(s, destPath, newName);
        }

        // 删除保存分块文件的文件夹
        deleteFolder(destPath);


    }

    private static void deleteFolder(String mergePath) {
        File dir = new File(mergePath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        dir.delete();
    }

    private static void saveStreamToFile(SequenceInputStream inputStream, String filePath, String newName) throws IOException {
        if (!mkdir(filePath)) {
            throw new MkdirException("文件夹创建失败！路径为：" + filePath);
        }

        /* 创建输出流，写入数据，合并分块 */
        byte[] buffer = new byte[1024];

        try (OutputStream outputStream = new FileOutputStream(filePath + newName)) {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }

        }
    }

    public static void pdftohtml(PDDocument document, String htmlPath) throws IOException, ParserConfigurationException {
        //加载PDF文档

        // 输出pdf文本
//        readText(document);
        //将字节流转换成字符流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(htmlPath)), "UTF-8"));
        //实例化pdfdom树对象
        PDFDomTree pdfDomTree = new PDFDomTree(PDFDomTreeConfig.createDefaultConfig()){
            //字体大小不一致，重写方法重新设置字体大小
            @Override
            protected void updateStyle(BoxStyle bstyle, TextPosition text) {
                super.updateStyle(bstyle, text);
                bstyle.setFontSize(bstyle.getFontSize() - 7);
            }
        };

        //开始写入html文件
        pdfDomTree.writeText(document, out);
        out.write("<script src=\"/static/js/jquery.min.js\"");
        out.flush();
        out.close();
        document.close();
    }

    public static String readText(PDDocument document) throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        return text;
    }


}
