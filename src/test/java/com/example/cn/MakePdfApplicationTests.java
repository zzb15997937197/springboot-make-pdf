package com.example.cn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MakePdfApplicationTests {

    @Test
    public void contextLoads() {


    }

    @Test
    public void deleteAllFileAndDirectory(){
      File file=new File("print/");
      deleteFileAll(file);
    }

        /**
         * 删除文件下所有文件夹和文件
         * file：文件名
         * */
        public static void deleteFileAll(File file) {
            if (file.exists()) {
                File files[] = file.listFiles();
                int len = files.length;
                for (int i = 0; i < len; i++) {
                    if (files[i].isDirectory()) {
                        deleteFileAll(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
                file.delete();
            }
        }


        @Test
    public void compareTime(){
            if ("2020-00-00 10:40:51".compareTo("2020-04-28 10:40:51")==1){
                System.out.println("1");
            }else{
                System.out.println("0");
            }

            System.out.println("2020-00-00 10:40:51".substring(0,7));

        }

      @Test
    public void time(){

            System.out.println(new Date());
          try {
              File path = new File(ResourceUtils.getURL("classpath:resources").getPath());
              System.out.println("path:"+path.getAbsolutePath());
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          }
      }


      @Test
      public void equals(){
            if ("Y".equals(null)){
                System.out.println("为空");
            }else{
                System.out.println("不为空");
            }

      }


      @Test
      public void  testArrayList(){
            List<String> list= Arrays.asList("01","02");
            if (list.contains("01")){
                System.out.println("111");
          }
      }

      @Test
    public void testContain(){

            String str="FinaLAFA-中文";
            System.out.println(str.toLowerCase());

      }







}
