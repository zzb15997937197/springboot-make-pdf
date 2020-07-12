package com.example.cn.util;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateOrDeleteFile {

    Document  document=new Document();
    // 设置字体大小
    static Font headfont;
    // 设置字体大小
    static Font keyfont;
    // 设置字体大小
    static Font linefont;
    // 设置字体大小
    static Font textfont;
    private  static  int maxWidth = 520;
    static {
        BaseFont bfChinese;
        try {
            bfChinese = BaseFont.createFont("STSong-Light",  "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            // 标题字体大小
            headfont = new Font(bfChinese, 20, Font.NORMAL);
            // 关键标题字体大小
            keyfont = new Font(bfChinese, 14, Font.NORMAL);
            // 行标题字体大小
            linefont = new Font(bfChinese, 12, Font.NORMAL);
            // 字段字体大小
            textfont = new Font(bfChinese, 10, Font.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  CreateOrDeleteFile(File file) {        // 设置页面大小
        document.setPageSize(PageSize.A4);
        document.setPageCount(3);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static PdfPCell createCell(String value, Font font, int align){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }
    public static PdfPCell createCell(String value, Font font){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value,font));
        return cell;
    }
    public static PdfPCell createCell(String value, Font font,boolean boderFlag){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value,font));
        if(boderFlag){
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthBottom(1);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }
        return cell;
    }
    public static PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value,font));
        cell.setPadding(3.0f);
        if(boderFlag){
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthBottom(1);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }else{
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }
        return cell;
    }
    public static PdfPCell createCell(Image image, int align, int colspan){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setImage(image);
        cell.setPadding(3.0f);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthBottom(1);
        return cell;
    }
    public static PdfPTable createTable(int colNumber){
        PdfPTable table = new PdfPTable(colNumber);
        try{
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return table;
    }

    public  boolean  createPDFReport(){
        document.open();
        PdfPTable table0 = createTable(7);
        table0.addCell(createCell("报账单",headfont,Element.ALIGN_CENTER,7,false));
        PdfPTable table1 = createTable(7);
        //addPicture(expenseReportPrintDto,QR_CODE_IMAGE_PATH,table,url);
        table1.addCell(createCell("单据编号：", textfont,Element.ALIGN_LEFT,7,false));

         PdfPTable table2 = createTable(7);

        table2.addCell(createCell("费用事项：", keyfont, Element.ALIGN_LEFT,7,true));

        PdfPTable table3 = createTable(4);

        table3.addCell(createCell("保单费用行：", keyfont, Element.ALIGN_LEFT,7,true));

        try{
           document.add(table0);
            document.add(table1);
            document.add(table2);
            document.add(table3);
        }catch (DocumentException E){
            throw  new RuntimeException("PDF写入文件报错");
        }
       document.close();
        return true;
    }


    public static void doCreateFile(File file)  {
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                //如果有自定义错误的话，可以使用BizException,比如创建文件出错！
            }
        }
    }
}
