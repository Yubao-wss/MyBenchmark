package com.waston.cases;


import com.waston.BenchmarkCase;
import com.waston.annotaions.Benchmark;
import com.waston.annotaions.Measurement;

/**
 * @Description:
 * @Author: Waston
 * @Date: 2019/7/12 20:21
 */
public class StringConcat implements BenchmarkCase {
    @Measurement(iterations = 10,countPerGroup = 10)
    @Benchmark
    public static String addString() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;
        }
        return a;
    }

    @Measurement(iterations = 10, countPerGroup = 10)
    @Benchmark
    public static String addStringBuilder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        return sb.toString();
    }

}
