package com.waston;

import com.waston.annotaions.Benchmark;
import com.waston.annotaions.Measurement;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private static final int DEFAULT_ITERATIONS = 10;
    private static final int DEFAULT_GROUP = 5;
    private final List<BenchmarkCase> caseList;

    public CaseRunner(List<BenchmarkCase> caseList){
        this.caseList = caseList;
    }
    public void run() throws InvocationTargetException, IllegalAccessException {
        for(BenchmarkCase bCase : caseList){
            int iterations = DEFAULT_ITERATIONS;
            int group = DEFAULT_GROUP;

            //现获取类级别的配置
            Measurement classMeasurement = bCase.getClass().getAnnotation(Measurement.class);
            if(classMeasurement != null){
                iterations = classMeasurement.iterations();
                group = classMeasurement.countPerGroup();
            }

            //找到对象中那些方法是需要测试的方法
            Method[] methods = bCase.getClass().getMethods();
            for(Method method : methods){
                Benchmark benchmark = method.getAnnotation(Benchmark.class);
                if(benchmark == null){
                    continue;
                }
                //方法级别的配置
                Measurement methodMeasurement = method.getAnnotation(Measurement.class);
                    if(methodMeasurement != null){
                        iterations = methodMeasurement.iterations();
                        group = methodMeasurement.countPerGroup();
                    }

                    runCase(bCase,method,iterations,group);
                }
            }

    }

    private void runCase(BenchmarkCase bCase, Method method, int iterations, int group) throws InvocationTargetException, IllegalAccessException {
        System.out.println(method.getName());
        for(int i = 1;i < iterations+1;i++){
            System.out.printf("第%d次实验，",i);
            long t1 = System.nanoTime();
            for(int j = 0;j < group;j++){
                method.invoke(bCase);
            }
            long t2 = System.nanoTime();
            System.out.printf("耗时：%d 纳秒%n",t2 - t1);
        }
    }
}
public class BCaseLoader {
    public CaseRunner load(){
        String pkg = "com/waston/cases";
        String pkgDot = "com.waston.cases";
        List<String> classNameList = new ArrayList<>();

        //1、根据一个固定类，找到类加载器
        //2、根据加载器找到类文件所在的路径
        //3、扫描路径下的所有类文件
        ClassLoader classLoader = this.getClass().getClassLoader();
        try {
            Enumeration<URL> urls = classLoader.getResources(pkg);
            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(!url.getProtocol().equals("file")){
                    //如果不是*.class文件，暂时不支持
                    continue;
                }
                String dirname = URLDecoder.decode(url.getPath(),"UTF-8");
                File dir = new File(dirname);
                if(!dir.isDirectory()){
                    //不是目录
                    continue;
                }

                //扫描该目录下的所有*.class文件，作为所有的类文件
                File[] files = dir.listFiles();
                if(files == null){
                    continue;
                }

                for(File file : files){
                    //没有判断后缀是否是.class
                    String filename = file.getName();
                    String classname = filename.substring(0,filename.length() - 6);
                    //获取所有类的名称
                    classNameList.add(classname);
                }

            }
            List<BenchmarkCase> caseList = new ArrayList<>();
            for (String className : classNameList){
                Class<?> cls = Class.forName(pkgDot + "." + className);
                if(hasInterface(cls, BenchmarkCase.class)){
                    caseList.add((BenchmarkCase) cls.newInstance ());
                }
            }

            return new CaseRunner(caseList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        return null;
    }

    private boolean hasInterface(Class<?> cls,Class<?> intf){
        Class<?>[] intfs = cls.getInterfaces();
        for(Class<?> i : intfs){
            if(i == intf){
                return true;
            }
        }

        return false;
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