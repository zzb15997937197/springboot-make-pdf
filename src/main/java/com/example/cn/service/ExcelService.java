package com.example.cn.service;


import com.aspose.cells.License;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @ClassesName ExcelService
 * @Author ShilinMao
 * @DATE 2019/10/28
 * @Desc
 * @Version 1.0
 **/
@Service
@Slf4j
public class ExcelService {

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
     * @Description excel转为html
     * @Date 2019/10/28
     * @Param [file, path]
     **/
    public void excelToHtml(InputStream file, String path) throws Exception {
       getLicense();
        Workbook workbook = new Workbook(file);
        workbook.save(path, SaveFormat.HTML);
    }

    /**
     * @return void
     * @Author ShilinMao
     * @Description excel转为Pdf
     * @Date 2019/11/27
     * @Param [file, path]
     **/
    public void excelToPdf(InputStream file, String path) throws Exception {
        getLicense();
        Workbook workbook = new Workbook(file);
        workbook.save(path, SaveFormat.PDF);
    }
}
