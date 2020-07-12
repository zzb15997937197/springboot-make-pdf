package com.example.cn.web;


import com.example.cn.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class  PrintController {


    @Autowired
    PrintService printService;

    @GetMapping("/make/pdf")
    public String makePdf() throws Exception {
        System.out.println("开始打印pdf文件.....");
        printService.printpdf();
        return "生成成功!";
    }
}
