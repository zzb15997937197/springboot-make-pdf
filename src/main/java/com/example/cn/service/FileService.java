package com.example.cn.service;

import com.example.cn.model.FileCO;
import com.example.cn.util.CommonCodeUtils;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * @ClassesName FileService
 * @Author ShilinMao
 * @DATE 2019/10/25
 * @Desc
 * @Version 1.0
 **/
@Service
public class FileService {


    @Autowired
    private WordService wordService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private PptService pptService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private MailService mailService;
    @Autowired
    private TxtService txtService;

    @Autowired
    private ImgService imgService;

    private static Map<String, Integer> pdfMap = new HashMap<>();

    @Value("${file.save-path}")
    private String path;
    @Value("${file.type}")
    private String fileType;
    @Value("${file.image-scale}")
    private double imageScale;


//    public String start(FileCO fileCO) throws Exception {
//        if ("pdf".equals(fileType)) {
//            return transformation(fileCO, CommonCodeUtils.TO_PDF);
//        } else {
//            return transformation(fileCO, CommonCodeUtils.TO_HTML);
//        }
//    }


    public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            // 判断文件目录是否存在
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);

            //输出流
            fos = new FileOutputStream(file);

            //缓冲流
            bos = new BufferedOutputStream(fos);

            //将字节数组写出
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static int parseDoubleToInt(double sourceDouble) {
        int result = 0;
        result = (int) sourceDouble;
        return result;
    }

    public String transformation(FileCO fileCO, String type,String lastPath) throws Exception {
        String pdf = "pdf";
        String txt = "txt";
        List<String> xls = Arrays.asList("xls", "xlt", "xml");
        List<String> doc = Arrays.asList("doc", "rtf", "dot","docx");
        List<String> ppt = Arrays.asList("pps", "pot", "ppt");
        List<String> img = Arrays.asList("jpg", "png", "tif", "bmp");
        List<String> eml = Arrays.asList("msg", "eml", "mht", "pst", "ost");
        File dateFile = new File(path + lastPath);
        String uuid = UUID.randomUUID().toString();
        if (!dateFile.exists()) {
            //创建日期目录
            dateFile.mkdirs();
        }
        String fileName = fileCO.getFileName();
        //取后缀名
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //只取三位
        if (img.contains(suffix)) {
                //1.压缩
                //先将该文件转换为本地文件
                getFileByBytes(fileCO.getFileContent(), "print", fileName);
                String filePath="print/" + fileName;
                File file = new File(filePath);
                BufferedImage bufferedImage = ImageIO.read(file);      // 构造Image对象
                int width = bufferedImage.getWidth();
                int height = bufferedImage.getHeight();

                width =700;
                height =550;

                BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB );
                image.getGraphics().drawImage(bufferedImage, 0, 0, width, height, null); // 绘制缩小后的图

                File destFile = new File("print/" + fileName);
                FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
                // 可以正常实现bmp、png、gif转jpg
//                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//                encoder.encode(image); // JPEG编码
              ImageIO.write(image,"jpeg",out);
                out.close();
               // ImageHelper.scaleImageWithParams();

                //使用fileInputStream读取压缩后的文件
                FileInputStream fis = new FileInputStream(destFile);
                //调用自己，只压缩一次
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                fis.close();
                //3.删除文件
                fileCO.setFileContent(bytes);
                fileCO.setFileName(fileName);
                destFile.delete();
        }

        InputStream file = new ByteArrayInputStream(fileCO.getFileContent());

        //图片只做压缩
        if (img.contains(suffix)) {
            lastPath = lastPath + uuid + ".pdf";
        } else if (xls.contains(suffix)) {
            lastPath = lastPath + uuid + ".html";
        } else {
            if (CommonCodeUtils.TO_PDF.equals(type)) {
                //添加一个pdf
                lastPath = lastPath + uuid + ".pdf";
            }
            if (CommonCodeUtils.TO_HTML.equals(type)) {
                lastPath = lastPath + uuid + ".html";
            }
        }


        String fullPath = path + lastPath;
        //支持pdf,xls,doc,ppt,img,eml,txt
        if (pdf.equals(suffix)) {
            if (CommonCodeUtils.TO_HTML.equals(type)) {
                pdfService.pdfToHtml(file, fullPath);
            }
            if (CommonCodeUtils.TO_PDF.equals(type)) {
                pdfService.pdfToPdf(file, fullPath);
            }
        } else if (xls.contains(suffix)) {
            //excel只压缩为html
            // if (CommonCodeUtils.TO_HTML.equals(type)) {
            // excelService.excelToHtml(file, fullPath);
            // }
            if (CommonCodeUtils.TO_PDF.equals(type)) {
                excelService.excelToPdf(file, fullPath);
            }
        } else if (doc.contains(suffix)) {
//            if (CommonCodeUtils.TO_HTML.equals(type)) {
//                wordService.wordToHtml(file, fullPath);
//            }
            if (CommonCodeUtils.TO_PDF.equals(type)) {
                wordService.wordToPdf(file, fullPath);
            }
        } else if (ppt.contains(suffix)) {
//            if (CommonCodeUtils.TO_HTML.equals(type)) {
//                pptService.pptToHtml(file, fullPath);
//            }
            if (CommonCodeUtils.TO_PDF.equals(type)) {
                pptService.pptToPdf(file, fullPath);
            }
        } else if (img.contains(suffix)) {
            //现在图片走压缩,统一转为jpg
            if (CommonCodeUtils.TO_PDF.equals(type)) {
                imgService.imgToPdf(file, fullPath);
            }

        } else if (eml.contains(suffix)) {
            //eml转为pdf
//            byte[] bytes=fileCO.getFileContent();
//            getFileByBytes(bytes,fullPath);
            if (CommonCodeUtils.TO_PDF.equals(type)) {
                // mailService.emlToHtml(file, fullPath);
                mailService.emlToPdf(file, fullPath);
            }
        } else if (txt.contains(suffix)) {

            if (CommonCodeUtils.TO_PDF.equals(type)) {
                txtService.txtToPdf(file, fullPath);
            }
            //txtService.txtToHtml(fileCO.getFileContent(), fullPath, fileName);
        } else {
            return null;
        }

        return lastPath;
    }


}
