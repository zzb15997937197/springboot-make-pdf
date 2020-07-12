package com.example.cn.service;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @ClassesName WordService
 * @Author ShilinMao
 * @DATE 2019/10/28
 * @Desc
 * @Version 1.0
 **/
@Service
@Slf4j
public class WordService {
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
     * @Author ShiLinMao
     * @Description word转为html
     * @Date 2019/10/28
     * @Param [file, path]
     **/
    public void wordToHtml(InputStream file, String path) throws Exception {
       getLicense();
        Document doc = new Document(file);
        doc.save(path, com.aspose.words.SaveFormat.HTML);
    }

    /**
     * @return void
     * @Author ShiLinMao
     * @Description word转为Pdf
     * @Date 2019/11/27
     * @Param [file, path]
     **/
    public void wordToPdf(InputStream file, String path) throws Exception {
        getLicense();
        Document doc = new Document(file);
        doc.save(path, SaveFormat.PDF);
    }
}
