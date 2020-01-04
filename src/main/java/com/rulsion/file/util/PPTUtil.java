package com.rulsion.file.util;


import com.rulsion.file.docTest.reader.ReaderForPPT;
import org.apache.poi.sl.usermodel.*;

import java.awt.*;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PPTUtil {

    //将单张幻灯片存为图片

    /**
     * @param slide 幻灯片
     * @throws IOException
     */
    public static void toImage(Slide slide, ReaderForPPT readerForPPT) throws IOException {
        List<Shape> shapes = slide.getShapes();
        //防止乱码
        for (Shape e : shapes) {
            if (e instanceof TextShape) {
                TextShape sh = (TextShape) e;
                List<TextParagraph> textParagraphs = sh.getTextParagraphs();
                for (TextParagraph textParagraph : textParagraphs) {
                    List<TextRun> textRuns = textParagraph.getTextRuns();
                    for (TextRun textRun : textRuns) {
                        textRun.setFontFamily("宋体");
                    }
                }
            }
        }

        SlideShow slideShow = readerForPPT.getSlideShow();
        String filePath = readerForPPT.getFilePath();
        String fileName = readerForPPT.getFileName();
        Dimension pgsize = slideShow.getPageSize();
        slide.getTitle();
        String saveImagePathName = filePath + fileName + readerForPPT.getSuffix() + "_IMG" + readerForPPT.getSuffix();
        FileUtil.mkdir(saveImagePathName);

        BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();
        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
        slide.draw(graphics);
        FileOutputStream out = new FileOutputStream(saveImagePathName + File.separator + slide.getSlideName() + ".JPG");
        javax.imageio.ImageIO.write(img, "JPG", out);
        out.close();
    }
}
