package com.example.cn.web;

import com.example.cn.model.FileCO;
import com.example.cn.service.FileService;
import com.example.cn.service.PdfMergeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassesName TestController
 * @Author ShilinMao
 * @DATE 2019/11/27
 * @Desc
 * @Version 1.0
 **/
@Api(tags = "文件转换接口-测试接口")
@RestController
@RequestMapping("/aspose/file")
public class TestController {

    @Autowired
    private PdfMergeService pdfMergeService;
    @Autowired
    private FileService fileService;


    @ApiOperation(value = "文件转换接口-常用文件转为html", notes = "文件转换接口-常用文件转为html 开发:Shilin.Mao")
    @GetMapping(value = "/toPdfAndMerge")
    public ResponseEntity<String> fileUpload(@RequestParam(name = "file1", required = false)
                                                     MultipartFile file1,
                                             @RequestParam(name = "file2", required = false)
                                                     MultipartFile file2) throws Exception {

        List<FileCO> fileCOs = new ArrayList<>();
        if (file1 != null) {
            FileCO fileCO = new FileCO();
            fileCO.setFileContent(file1.getBytes());
            fileCO.setFileName(file1.getOriginalFilename());
            fileCOs.add(fileCO);
        }
        if (file2 != null) {
            FileCO fileCO2 = new FileCO();
            fileCO2.setFileContent(file2.getBytes());
            fileCO2.setFileName(file2.getOriginalFilename());
            fileCOs.add(fileCO2);
        }
        return ResponseEntity.ok(pdfMergeService.testService(fileCOs));
    }

    /**
     * 批量合并文件
     * @param files
     * @return
     * @throws Exception
     */
    @GetMapping("/batch/mergetPdf")
    public ResponseEntity<String> fileBatchUpload(@RequestBody List<MultipartFile> files) throws Exception {
        List<FileCO> fileCOs = new ArrayList<>();

        if (files.size()>0 && !files.isEmpty()){
            for (MultipartFile e: files){
                FileCO fileCO2 = new FileCO();
                fileCO2.setFileContent(e.getBytes());
                fileCO2.setFileName(e.getOriginalFilename());
                fileCOs.add(fileCO2);
            }
        }
        return ResponseEntity.ok(pdfMergeService.testService(fileCOs));
    }







    @ApiOperation(value = "文件转换接口-常用文件转为html", notes = "文件转换接口-常用文件转为html 开发:Shilin.Mao")
    @GetMapping(value = "/test2")
    public ResponseEntity<String> fileUpload(@RequestParam(name = "file", required = false)
                                                     MultipartFile file) throws Exception {
        FileCO fileCO2 = new FileCO();
        fileCO2.setFileContent(file.getBytes());
        fileCO2.setFileName(file.getOriginalFilename());
       // fileService.start(fileCO2);
        return ResponseEntity.ok("true");
    }
}
