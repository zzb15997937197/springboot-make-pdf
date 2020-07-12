package com.example.cn.service;

import com.aspose.imaging.Image;
import com.aspose.imaging.License;
import com.aspose.imaging.imageoptions.PdfOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@Slf4j
public class ImgService {

    @Autowired
    private LicenseService licenseService;


    private void getLicense() {
        License license = new License();
        try {
            license.setLicense(licenseService.getLicense());
        } catch (Exception e) {
            log.error("鉴权发生错误: [{}]", e);
        }
    }


    public void imgToPdf(InputStream file, String path) throws IOException {
        getLicense();
//        //重新获取到文件
//        File file1=new File(path);
//        //使用fileInputStream读取文件
//        FileInputStream fis=new FileInputStream(file1);
//        byte[]bytes= new byte[fis.available()];
//        //将path后缀换为pdf
//        String  resultPath=path.substring(0,path.lastIndexOf(".")+1)+"pdf";
//        //重新转换为字节流
//        InputStream inputStreamFile=new ByteArrayInputStream(bytes);
//        Image image = Image.load(inputStreamFile);
//        file1.delete();
//        image.save(resultPath, new PdfOptions());

        Image image=Image.load(file);
        image.save(path,new PdfOptions());

    }



}
