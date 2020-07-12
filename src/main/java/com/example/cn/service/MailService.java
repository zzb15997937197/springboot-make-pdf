package com.example.cn.service;


import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.email.MailMessage;
import com.aspose.email.SaveOptions;
import com.aspose.words.SaveFormat;
import com.sun.activation.registries.MailcapFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@Service
@Slf4j
public class MailService {


    @Autowired
    private LicenseService licenseService;


    /**
     * @return void
     * @Author ShilinMao
     * @Description //鉴权
     * @Date 2019/10/28
     * @Param []
     **/
    private void getLicense() {
        License license = new License();
        try {
            license.setLicense(licenseService.getLicense());
        } catch (Exception e) {
            log.error("鉴权发生错误: [{}]", e);
        }
    }

    /**
     * @return void
     * @Author ShilinMao
     * @Description //eml转为html
     * @Date 2020/1/6
     * @Param [file, path]
     **/
    public void emlToHtml(InputStream file, String path) {
        MailMessage messageEML = MailMessage.load(file);
        messageEML.save(path, SaveOptions.getDefaultHtml());
    }



    public void emlToPdf(InputStream file,String path) throws Exception {

        //ByteArrayInputStream 转换为inputstream
//        Presentation doc = new Presentation(file);
//        doc.save(path, SaveFormat.Pdf);
        emlToHtml(file,path);
        //再转换为PDF
        getLicense();
        Document doc = new Document(path);
        doc.save(path, SaveFormat.PDF);

//        getLicense();
//        Workbook workbook = new Workbook(file);
//        workbook.save(path, com.aspose.cells.SaveFormat.PDF);
    }




}
