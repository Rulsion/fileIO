package com.rulsion.file.docTest.service.impl;

import com.rulsion.file.async.FileAsync;
import com.rulsion.file.docTest.Factory.DocReaderFactory;
import com.rulsion.file.docTest.entity.FileUpload;
import com.rulsion.file.docTest.entity.User;
import com.rulsion.file.docTest.entity.Webuploader;
import com.rulsion.file.docTest.mapper.FileMapper;
import com.rulsion.file.docTest.reader.DocReader;
import com.rulsion.file.docTest.service.TestService;
import com.rulsion.file.docTest.vo.FileUploadVO;
import com.rulsion.file.util.FileUtil;
import com.rulsion.file.util.JsonResponseResult;
import com.rulsion.file.util.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@EnableAsync
public class TestServiceImpl implements TestService {
    @Autowired
    private FileAsync fileAsync;

    @Resource
    private FileMapper fileMapper;

    @Override
    public String readFile(String fileName) throws IOException, ParserConfigurationException {
        fileName.replaceAll("[/]", File.separator);
        String fileName1 = fileName.substring(fileName.lastIndexOf(File.separator)+1);
        String suffix = fileName1.substring(fileName1.lastIndexOf(".")+1);
        DocReader docReader = DocReaderFactory.getReaderInstance(new FileInputStream(fileName), suffix);
        if (SysUtil.isEmpty(docReader)) {
            return null;
        }
        String str = docReader.Read();
        return str;
    }

    @Override
    public JsonResponseResult uploadFile(Webuploader uploader, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String uploadFolderPath = FileUtil.getRealPath(request);

        String mergePath = uploadFolderPath + "\\fileDate\\" + uploader.getId() + "\\";

        String ext = uploader.getName().substring(uploader.getName().lastIndexOf("."));


        // 判断文件是否分块
        if (uploader.getChunks() != null && uploader.getChunk() != null) {

            int index = Integer.parseInt(uploader.getChunk());

            String fileName = String.valueOf(index) + ext;

            // 将文件分块保存到临时文件夹里，便于之后的合并文件
            FileUtil.saveFile(mergePath, fileName, uploader.getFile());

            // 验证所有分块是否上传成功，成功的话进行合并
            FileUtil.Uploaded(uploader, mergePath, fileName, ext);

        } else {

            SimpleDateFormat year = new SimpleDateFormat("yyyyMMdd");

            Date date = new Date();

            String pathOfFile = uploader.getPathName().substring(0,uploader.getPathName().lastIndexOf(File.separator));

            String destPath = uploadFolderPath +File.separator+"fileDate"+File.separator+ year.format(date)+File.separator+pathOfFile ;// 文件路径

            String newName = uploader.getName();// 文件新名称

            // 上传文件没有分块的话就直接保存目标目录
            FileUtil.saveFile(destPath, newName, uploader.getFile());

        }

        MultipartFile file = uploader.getFile();
        String chunk = uploader.getChunk();
        String realname = uploader.getName();
        User user = new User(); //session获取用户信息
        String username = user.getAccount();
        Date now = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd");
        String path = "D:\\data\\";
        path += formater.format(now) + File.separator + username + File.separator;
        String newName = now.getTime() + realname;

        if (file == null) {
            return JsonResponseResult.createFailed("上传失败");
        }
        // 判断上传的文件是否被分片
        if (null == uploader.getChunks() && null == chunk) {
            File destTempFile = new File(path, newName);
            if (!destTempFile.exists()) {
                destTempFile.mkdirs();
            }
            file.transferTo(destTempFile);
            destTempFile.createNewFile();
            path += File.separator + newName;
            //					log.info("上传成功："+realname+" 服务务器存放位路径："+path);
            return JsonResponseResult.createSuccess("上传成功");
        }
        path = path + File.separator + "part" + File.separator;
        File parentFileDir = new File(path);
        if (!parentFileDir.exists()) {
            parentFileDir.mkdirs();
        }
        File f = new File(path + File.separator + realname + "_" + chunk + ".part");
        if (!f.exists()) {
            file.transferTo(f);
            f.createNewFile();
        }
        path = "D:\\data\\" + formater.format(now) + File.separator + username + File.separator + realname;
        return JsonResponseResult.createSuccess("上传成功");
    }

    @Override
    public void uploadFiles(List<MultipartFile> files) throws IOException {
        //遍历处理文件
        for (MultipartFile file : files) {
            fileAsync.saveFile(file);
        }

    }

    public void addFileUpload(FileUploadVO fileUploadVO, User user) throws IOException {
        String serverPath = "D:\\TEST\\";//此处路径应使用前台传递路径，转换成服务器相对路径

        File file = new File(serverPath + fileUploadVO.getFileName());
        if (file.exists()) {
            log.info("文件已存在");
            //执行文件存在操作
            return;
        }
        String tmpFileName = createTmpFile(serverPath, fileUploadVO.getFileSize());
        FileUpload fileUpload = new FileUpload()
                .setServerName(fileUploadVO.getFileName())
                .setServerPath(serverPath)
                .setStatus(0)
                .setTmpName(tmpFileName)
                .setUserid(user.getId())
                .setLastModifiedDate(fileUploadVO.getLastModified())
                .setLocalName(fileUploadVO.getFileName());

    }

    private String createTmpFile(String path, Long size) throws IOException {
        long tmpFileName = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String fileName = path + String.valueOf(tmpFileName);
        File tmpFile = new File(fileName);//在同一个目录下创建临时文件
        while (tmpFile.exists()) {
            log.info("临时文件已存在");
            fileName = path + String.valueOf(++tmpFileName);
            tmpFile = new File(fileName);
        }
        tmpFile.createNewFile();
        try (RandomAccessFile r = new RandomAccessFile(fileName, "rw")) {
            r.setLength(size);
        }

        return fileName;
    }

}
