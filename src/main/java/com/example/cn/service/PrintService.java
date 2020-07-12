package com.example.cn.service;


import com.example.cn.util.CreateOrDeleteFile;
import com.example.cn.util.QRCodeGenerator;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PrintService {

    public void printpdf() throws Exception {
        System.out.println("打印service");

        String path="print\\picture.png";
        String pdf="print\\报账单.pdf";

        //第一个参数为二维码的描述
        QRCodeGenerator.generateQRCodeImage("http://www.baidu.com", 50, 25, path);
        System.out.println("生成二维码成功!!");
        //打印pdf
       File file=new File(pdf);
       //1.创建文件
        CreateOrDeleteFile.doCreateFile(file);
        //创建一个PDF
        CreateOrDeleteFile createOrDeleteFile=new CreateOrDeleteFile(file);
        createOrDeleteFile.createPDFReport();
        System.out.println("生成pdf成功!");
    }
}
