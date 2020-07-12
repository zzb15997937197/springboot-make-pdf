package com.example.cn.service;

import com.aspose.pdf.Document;
import com.aspose.pdf.License;
import com.aspose.pdf.SaveFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @ClassesName PdfService
 * @Author ShilinMao
 * @DATE 2019/10/28
 * @Desc
 * @Version 1.0
 **/
@Service
@Slf4j
public class PdfService {

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
     * @Description //pdf转为html
     * @Date 2019/10/28
     * @Param [file, path]
     **/
    public void pdfToHtml(InputStream file, String path) {
        getLicense();
        Document pdf = new Document(file);
        pdf.save(path, SaveFormat.Html);
    }

    /**
     * @return void
     * @Author ShilinMao
     * @Description //pdf转为Pdf
     * @Date 2019/10/28
     * @Param [file, path]
     **/
    public void pdfToPdf(InputStream file, String path) {
        getLicense();
        Document pdf = new Document(file);
        pdf.save(path, SaveFormat.Pdf);
    }
}
