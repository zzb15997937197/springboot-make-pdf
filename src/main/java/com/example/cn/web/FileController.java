package com.example.cn.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassesName FileController
 * @Author ShilinMao
 * @DATE 2020/01/13
 * @Desc
 * @Version 1.0
 **/
@Api(tags = "文件转换接口")
@RestController
@RequestMapping("/api/aspose/file")
public class FileController {


    @ApiOperation(value = "文件转换接口-常用文件转为html列表", notes = "文件转换接口-常用文件转为html列表 开发:Shilin.Mao")
    @GetMapping(value = "/list")
    public ResponseEntity<List<String>> fileUpload() {
        List<String> fileList = Arrays.asList("xls", "xlt", "xml", "pdf", "txt",
                "doc", "rtf", "dot", "pps", "pot", "ppt",
                "jpg", "png", "tif", "msg", "eml", "mht",
                "pst", "ost");
        return ResponseEntity.ok(fileList);
    }
}
