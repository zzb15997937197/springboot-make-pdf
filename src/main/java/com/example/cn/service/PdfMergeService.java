package com.example.cn.service;

import com.example.cn.model.FileCO;
import com.example.cn.util.CommonCodeUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassesName PdfMergeService
 * @Author ShilinMao
 * @DATE 2019/11/27
 * @Desc
 * @Version 1.0
 **/
@Service
@Slf4j
public class PdfMergeService {

    @Autowired
    private FileService fileService;

    @Value("${pdf.path}")
    private String path;


    /**
     * @return void
     * @Author ShilinMao
     * @Description 测试方法, 后续请删掉;;;
     * @Date 2019/11/27
     * @Param [fileCOs]
     **/
    public String testService(List<FileCO> fileCOs) throws Exception {
        return mergeFileToPdf(fileCOs);
    }

    /**
     * @return void
     * @Author ShilinMao
     * @Description 将多个文件转为pdf并且合并为一个pdf
     * @Date 2019/11/27
     * @Param [fileCOs]
     **/
    public String mergeFileToPdf(List<FileCO> fileCOs) throws Exception {
        List<String> files = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String commonPath=formatter.format(new Date()) + "/";

        String pdfPath = commonPath+UUID.randomUUID().toString() + ".pdf";
        for (FileCO fileCO : fileCOs) {
            files.add(path+ fileService.transformation(fileCO, CommonCodeUtils.TO_PDF,commonPath));
        }
        mergePdfFiles(files, path + pdfPath);
        return pdfPath;
    }

    /**
     * @return void
     * @Author ShilinMao
     * @Description pdf文件合并
     * @Date 2019/11/27
     * @Param [fileCOs]
     **/
    public void mergePdfFiles(List<String> files, String newFile) throws IOException, DocumentException {
        PdfReader pdfReader = new PdfReader(files.get(0));
        Document document = new Document(pdfReader.getPageSize(1));
        pdfReader.close();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(newFile));
        document.open();
        for (int i = 0; i < files.size(); i++) {
            PdfReader reader = new PdfReader(files.get(i));
            int n = reader.getNumberOfPages();
            for (int j = 1; j <= n; j++) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, j);
                copy.addPage(page);
            }
            reader.close();
        }
        copy.close();
        document.close();
        log.debug("正在删除单个文件");
        files.forEach(e -> {
            File dateFile = new File(e);
            if (dateFile.exists()) {
                dateFile.delete();
            }
        });
    }
}
