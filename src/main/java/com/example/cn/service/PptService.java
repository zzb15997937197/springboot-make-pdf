package com.example.cn.service;

import com.aspose.slides.License;
import com.aspose.slides.Presentation;
import com.aspose.slides.SaveFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @ClassesName PptService
 * @Author ShilinMao
 * @DATE 2019/10/28
 * @Desc
 * @Version 1.0
 **/
@Service
@Slf4j
public class PptService {


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
            log.error("鉴权发生错误: [{}]",e);
        }
    }

    /**
     * @return void
     * @Author ShilinMao
     * @Description ppt转为html
     * @Date 2019/10/28
     * @Param [file, path]
     **/
    public void pptToHtml(InputStream file, String path) {
        getLicense();
        Presentation doc = new Presentation(file);
        doc.save(path, SaveFormat.Html);
    }


    /**
     * @return void
     * @Author ShilinMao
     * @Description ppt转为Pdf
     * @Date 2019/11/27
     * @Param [file, path]
     **/
    public void pptToPdf(InputStream file, String path) {
        getLicense();
        Presentation doc = new Presentation(file);
        doc.save(path, SaveFormat.Pdf);
    }

}
