package com.waston.annotaions;

/**
 * @Description: 预热
 * @Author: Waston
 * @Date: 2019/8/14 21:05
 */
public @interface WarmUp {
    int iterations() default 0;
}
