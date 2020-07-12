package com.example.cn.util;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeGenerator {


    public static void generateQRCodeImage(String content, int weight, int height, String picturePath) throws Exception {
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        BitMatrix bitMatrix;

        try {
            bitMatrix=qrCodeWriter.encode(content, BarcodeFormat.QR_CODE,weight,height);
        }catch (Exception e){
            throw new Exception("生成二维码出错:"+e.getMessage());
        }

        Path path= FileSystems.getDefault().getPath(picturePath);
        System.out.println("路径为:"+path);
        try {
            MatrixToImageWriter.writeToPath(bitMatrix,"PNG",path);
        }catch (IOException e){
            throw new Exception("二维码写入错误!"+e.getMessage());
        }
    }
}
