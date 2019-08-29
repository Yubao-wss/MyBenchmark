package com.waston.cases;

/**
 * @Description: 测试用例：排序
 * @Author: Waston
 * @Date: 2019/7/13 10:02
 */

import com.waston.BenchmarkCase;
import com.waston.annotaions.Benchmark;
import com.waston.annotaions.Measurement;

import java.util.Arrays;
import java.util.Random;

/**
 * 1.快速排序和归并排序的差别
 * 2.自己实现的归并排序和Array.sort对比
 * 3.TODO:自己实现并发排序（ForkJoin）和Array.parall
 */
@Measurement(iterations = 10,countPerGroup = 3)
public class SortCase implements BenchmarkCase {
    //快速排序

    //@Measurement(iterations = 10,countPerGroup = 10)

        public static void quickSort(int[] target){
            int n = target.length;
            if (n <= 1){
                return;
            }
            quickSortInternal(target,0,n-1);
        }

        private static void quickSortInternal(int[] target,int low,int high){
            if(low >= high){
                return;
            }
            int q = partition(target,low,high);
            quickSortInternal(target,low,q-1);
            quickSortInternal(target,q+1,high);
        }

        private static int partition(int[] target,int low,int high){
            int v =  target[low];
            int j = low;
            for(int i = low + 1;i <= high;i++){
                if(target[i] < v){
                    swap(target,j+1,i);
                    j++;
                }
            }
            swap(target,low,j);
            return j;
        }

        /***
         * 交换数组中的两个元素的位置
         * @param target 目标数组
         * @param indexA 元素A下标
         * @param indexB 元素B下标
         */
        private static void swap(int[] target,int indexA,int indexB){
            int temp = target[indexA];
            target[indexA] = target[indexB];
            target[indexB] = temp;
        }








    //归并排序
        //@Benchmark
        public static void mergeSort(int[] target){
            int n = target.length;
            if (n<=1){
                return;
            }
            mergeSortInternal(target,0,n-1);
        }

        /***
         * 分治排序
         * @param target 要排序的集合
         * @param low 开始位置
         * @param high 结束位置
         */
        private static void mergeSortInternal(int[] target,int low,int high){
            if(low >= high){
                return;
            }

            int mid = (low + high)/2;
            //左边小数组
            mergeSortInternal(target,low,mid);
            //右边小数组
            mergeSortInternal(target,mid+1,high);
            //将array[p...mid]与array[mid+1...r]合并为array[p...r]
            merge(target,low,mid,high);
        }

        /***
         * 合并函数
         * @param target
         * @param low 开始位置
         * @param mid 中间位置
         * @param high 结束位置
         */
        private static void merge(int[] target,int low,int mid,int high){
            int i = low;
            int j = mid+1;
            int k = 0;
            int[] temp = new int[high - low + 1];
            //两部分数组都还有数据
            while (i <= mid && j <= high){
                //小于等于保证了有序性
                if (target[i] <= target[j]){
                    temp[k++] = target[i++];
                }else {
                    temp[k++] = target[j++];
                }
            }
            //判断两个数组中哪个还有元素
            int start = i;
            int end = mid;
            //如果剩下第二个数组
            if(j <= high){
                start = j;
                end = high;
            }
            //将剩余数据拷贝回temp数组
            while (start <= end){
                temp[k++] = target[start++];
            }
            //将temp中的数组拷贝回原数组a[p...r]
            for(i = 0;i <= high-low;i++){
                target[low +i] = temp[i];
            }
        }


        @Benchmark
        public void testQuickSort(){
            int[] a = new int[10000];
            Random random = new Random(2013466);
            for(int i = 0;i < a.length;i++){
                a[i] = random.nextInt(10000);
            }

            mergeSort(a);
        }

    @Benchmark
    public void testArraysSort(){
        int[] a = new int[10000];
        Random random = new Random(2013466);
        for(int i = 0;i < a.length;i++){
            a[i] = random.nextInt(10000);
        }

        Arrays.sort(a);
    }

}
