package com.waston.annotaions;

import com.waston.BenchmarkCase;
import com.waston.cases.StringConcat;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/7/13 10:29
 */
public class Runner {
    private static List<BenchmarkCase> initCases() {
        List<BenchmarkCase> cases = new ArrayList<BenchmarkCase>();
        cases.add(new StringConcat());
        return cases;
    }
    private static void runMethodCase(BenchmarkCase bCase, Method method, int
            iterations, int countPerGroup) {
        for (int i = 0; i < iterations; i++) {
            System.out.printf("第 %d 次测试: ", i + 1);
            long t1 = System.nanoTime();
            for (int j = 0; j < countPerGroup; j++) {
                try {
                    method.invoke(bCase);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            long t2 = System.nanoTime();
            System.out.printf("耗时: %d%n", t2 - t1);
        }
    }
    private static void runCase(BenchmarkCase bCase) {
        Method[] methods = bCase.getClass().getMethods();
        for (Method method : methods) {
            Annotation benchmark = method.getAnnotation(Benchmark.class);
            if (benchmark == null) {
                continue;
            }
            int iterations = 10;
            int countPerGroup = 1000;
            Measurement measurement =
                    (Measurement)method.getAnnotation(Measurement.class);
            if (measurement != null) {
                iterations = measurement.iterations();
                countPerGroup = measurement.countPerGroup();
            }
            runMethodCase(bCase, method, iterations, countPerGroup);
        }
    }
    public static void main(String[] args) {
        List<BenchmarkCase> bCaseList = initCases();
        for (BenchmarkCase bCase : bCaseList) {
            runCase(bCase);
        }
    }
}
