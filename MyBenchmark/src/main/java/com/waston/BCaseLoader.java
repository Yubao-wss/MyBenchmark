package com.waston;

import com.waston.BenchmarkCase;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/7/13 10:00
 */
class CaseRunner{
    public void run(){

    }
}
public class BCaseLoader {
    public CaseRunner load(){
        String pkg = "com.waston.cases";


        return new CaseRunner();
    }
}
//    List<BenchmarkCase> load(){
//        List<String> bCaseClassNameList = scanBCaseClassNameList();
//        List<BenchmarkCase> bCaseList = buildBCaseList(bCaseClassNameList);
//        System.out.printf("共加载 %d 个有效基准测试 case。%n%n", bCaseList.size());
//        return bCaseList;
//    }
//    private List<String> scanBCaseClassNameList() {
//        ClassLoader classLoader = this.getClass().getClassLoader();
//        List<String> classNameList = new ArrayList<String>();
//        try {
//            Enumeration<URL> urls = classLoader.getResources("com/waston/com.waston.Cases");
//            while (urls.hasMoreElements()) {
//                URL url = urls.nextElement();
//                if (!url.getProtocol().equals("file")) {
//                    continue;
//                }
//                classNameList.addAll(scanClassesFromFile(URLDecoder.decode(url.getPath(), "UTF-8")));
//            }
//        } catch (IOException ignored) {
//            return classNameList;
//        }
//        return classNameList;
//    }
//    private List<String> scanClassesFromFile(String path) {
//        File dir = new File(path);
//        List<String> classNameList = new ArrayList<String>();
//        File[] files = dir.listFiles();
//        if (files == null) {
//            return classNameList;
//        }
//        for (File file : files) {
//            if (file.isDirectory()) {
//                continue;
//            }
//            String filename = file.getName();
//            if (!filename.endsWith(".class")) {
//                continue;
//            }
//            if (filename.lastIndexOf("$") != -1) {
//                continue;
//            }
//// 去掉 .class
//            String name = filename.substring(0, filename.length() - 6);
//            classNameList.add("com.bittech.cases." + name);
//        }
//        return classNameList;
//    }
//    private List<BenchmarkCase> buildBCaseList(List<String> bCaseClassNameList) {
//        List<BenchmarkCase> bCaseList = new ArrayList<BenchmarkCase>(bCaseClassNameList.size());
//        for (String className : bCaseClassNameList) {
//            try {
//                Class<?> bCaseClass = Class.forName(className);
//                if (!isBCaseClass(bCaseClass)) {
//                    continue;
//                }
//                BenchmarkCase bCase = (BenchmarkCase)bCaseClass.newInstance();
//                bCaseList.add(bCase);
//            } catch (ClassNotFoundException | InstantiationException |
//                    IllegalAccessException ignored) {}
//        }
//        return bCaseList;
//    }
//    private boolean isBCaseClass(Class<?> bCaseClass) {
//        return BenchmarkCase.class.isAssignableFrom(bCaseClass);
//    }