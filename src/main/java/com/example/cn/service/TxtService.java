package com.example.cn.service;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.example.cn.util.EncodingDetect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @Author: shouting.cheng@hand-china.com
 * @Date: 2020-01-08
 * @Description:
 */
@Service
@Slf4j
public class TxtService {

    private static String TARGET_CHARSET = "UTF-8";


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
     * @Author shouting.cheng
     * @Description txt转为html
     * @Date 2020-01-08
     * @Param [bytes, path]
     **/
    public void txtToHtml(byte[] bytes, String path, String fileName) {

        byte[] convertedBytes = convertBytesEncodingTo(bytes, TARGET_CHARSET, fileName);


        try(FileOutputStream out = new FileOutputStream(path)) {
            out.write(convertedBytes);
        } catch (IOException e) {
            log.error("写入文件失败", e);
            throw new RuntimeException("写入失败!");
        }
    }

    public void txtToPdf(InputStream file,String path) throws Exception {
        getLicense();
        Document doc = new Document(file);
        doc.save(path, SaveFormat.PDF);
    }




    /**
     * @return byte[]
     * @Author shouting.cheng
     * @Description //txt编码转换
     * @Date 2020-01-08
     * @Param [bytes]
     **/
    public byte[] convertBytesEncodingTo(byte[] bytes, String charsetName, String fileName) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String inputFileEncode = EncodingDetect.getJavaEncode(bytes);
        log.info("inputFileEncode=====" + inputFileEncode);
        try (
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(new ByteArrayInputStream(bytes), inputFileEncode)
                );
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, charsetName))
        ){
            String line = null;
            bufferedWriter.write("<!DOCTYPE html>");
            bufferedWriter.write("<html>");
            bufferedWriter.write(" <head>");
            bufferedWriter.write("<meta charset=\"utf-8\">");
            bufferedWriter.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, " +
                    "maximum-scale=1.0, user-scalable=0\">");
            bufferedWriter.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
            bufferedWriter.write("<title>" + fileName + "</title>");
            bufferedWriter.write("</head>");
            bufferedWriter.write("<body><pre>");
            while (true) {
                if ((line = bufferedReader.readLine()) == null) {
                    break;
                }
                bufferedWriter.write(line + "\r\n");
            }
            bufferedWriter.write("</pre></body></html>");
        } catch (UnsupportedEncodingException e) {
            log.error("文件格式不支持", e);
            throw  new RuntimeException("文件格式不支持!");
        } catch (IOException e) {
            log.error("写入文件失败", e);
            throw  new RuntimeException("写入文件失败!");
        }
        return outputStream.toByteArray();
    }
}
