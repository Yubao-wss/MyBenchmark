package com.waston;

import com.waston.annotaions.Benchmark;
import com.waston.annotaions.Measurement;

import java.lang.reflect.InvocationTargetException;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/7/12 19:20
 */
@Measurement(iterations = 10,countPerGroup = 5)
public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        BCaseLoader loader = new BCaseLoader();
        loader.load().run();

    }
}


//}
//    public static void main(String[] args) {
//        //主方法中定义的默认配置(第一级)
//        int iterations = 10;
//        int group = 5;
//
//        //类级别配置（第二级）
//        BenchmarkCase sc = new StringConcat();
//        Class<?> cls = sc.getClass();
//        Annotation annotationMeasurement = cls.getAnnotation(Measurement.class);
//        if(annotationMeasurement != null){
//            Measurement measurement = (Measurement) annotationMeasurement;
//            iterations = measurement.iterations();
//            group = measurement.countPerGroup();
//        }
//
//        //如果方法中没有benchmark注解，不属于要测试的方法，直接跳过
//        Method[] methods = cls.getMethods();
//        for(Method method:methods){
//            Annotation annotationBenchmark = method.getAnnotation(Benchmark.class);
//            if(annotationBenchmark == null){
//                continue;
//            }
//
//            int methodIterations = iterations;
//            int methodGroup = group;
//
//            System.out.println(method.getName());
//            //方法配置（三级）
//            Annotation methodAnnotation = method.getAnnotation(Measurement.class);
//            if(methodAnnotation != null){
//                Measurement methodMeasurement = (Measurement)methodAnnotation;
//                methodIterations = methodMeasurement.iterations();
//                methodGroup = methodMeasurement.countPerGroup();
//            }
//
//            //调用对象的测试实例方法，进行真正的测试
//            for(int i = 0;i < methodGroup;i++){
//
//                long t1 = System.nanoTime();
//                for(int j = 0;j < methodIterations;j++){
//                    try {
//                        method.invoke(sc);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//                }
//                long t2 = System.nanoTime();
//
//                System.out.printf("第%d次实验，耗时：%d%n",i,t2 - t1);
//            }
//        }
//
//    }