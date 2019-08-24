package com.waston;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/8/14 20:13
 */
public class TestMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = TestMain.class.getClassLoader();
        Enumeration<URL> urls = classLoader.getResources("com/waston/cases");
        while(urls.hasMoreElements()){
            URL url = urls.nextElement();

            File dir = new File(URLDecoder.decode(url.getPath(),"UTF-8"));
            if(!dir.isDirectory()){
                continue;
            }

            File[] files = dir.listFiles();
            if(files == null){
                continue;
            }

            for(File file : files){
                String filename = file.getName();
                String className = filename.substring(0,filename.length() - 6);


                Class<?> cls = Class.forName("com.waston.cases."+className );

                //利用BenchmarkCase接口，找出需要的class
                Class<?>[] interfaces = cls.getInterfaces();
                for(Class<?> interf : interfaces){
                    if(interf == BenchmarkCase.class){
                        System.out.println(className);
                    }
                }
            }

        }
    }

}
