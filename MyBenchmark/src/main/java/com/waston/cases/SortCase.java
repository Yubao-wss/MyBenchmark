package com.waston.cases;

/**
 * @Description: 测试用例：排序
 * @Author: Waston
 * @Date: 2019/7/13 10:02
 */

import com.waston.BenchmarkCase;
import com.waston.annotaions.Benchmark;

/**
 * 1.快速排序和归并排序的差别
 * 2.自己实现的归并排序和Array.sort对比
 * 3.TODO:自己实现并发排序（ForkJoin）和Array.parall
 */
public class SortCase implements BenchmarkCase {
    //快速排序

    static class QuickSort{
        public static void quickSort(int[] target){
            int n = target.length;
            if (n <= 1){
                return;
            }
            Internal(target,0,n-1);
        }

        private static void Internal(int[] target,int low,int high){
            int randomIndex = (int) (Math.random()*(high - low + 1) + low);
            swap(target,low,randomIndex);
            int v = target[low];
            // arr[low+1...lt] < v
            int lt = low;
            // arr[lt+1...i-1] == v
            int i = low + 1;
            // arr[gt...r] > v
            int gt = high + 1;
            while(i < gt){
                if(target[i] < v){
                    swap(target,i,lt+1);
                    lt++;
                    i++;
                }else if(target[i] > v){
                    swap(target,i,gt-1);
                    gt--;
                }else {
                    i++;
                }
            }
            //循环走完只需要将low位置的元素与lt交换即为分区点
            swap(target,low,lt);
            //递归时将相同元素隔离
            Internal(target,low,lt-1);
            Internal(target,gt,high);
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
    }

    //归并排序
    static class MergeSort{
        public static void mergeSort(int[] target){
            int n = target.length;
            if (n<=1){
                return;
            }
            int mid = n/2;
            mergeInternal(target,0,n-1);
        }

        /***
         * 分治排序
         * @param array 要排序的集合
         * @param low 开始位置
         * @param high 结束位置
         */
        private static void mergeInternal(int[] array,int low,int high){
            int mid = low + (high-low)/2;
            //左边小数组
            mergeInternal(array,low,mid);
            //右边小数组
            mergeInternal(array,mid+1,high);
            //将array[p...mid]与array[mid+1...r]合并为array[p...r]
            //优化：当左边数组最大元素都小于右边数组最小元素，说明整个数组有序，直接结束排序
            if (array[mid] >= array[mid+1]) {
                merge(array,low,mid,high);
            }
        }

        /***
         * 合并函数
         * @param array
         * @param p 开始位置
         * @param mid 中间位置
         * @param r 结束位置
         */
        private static void merge(int[] array,int p,int mid,int r){
            int i = p;
            int j = mid+1;
            int k = 0;
            int[] temp = new int[r-p+1];
            //两部分数组都还有数据
            while (i <= mid && j <= r){
                //小于等于保证了有序性
                if (array[i] <= array[j]){
                    temp[k++] = array[i++];
                }else {
                    temp[k++] = array[j++];
                }
            }
            //判断两个数组中哪个还有元素
            int start = i;
            int end = mid;
            //如果剩下第二个数组
            if(j <= r){
                start = j;
                end = r;
            }
            //将剩余数据拷贝回temp数组
            while (start <= end){
                temp[k++] = array[start++];
            }
            //将temp中的数组拷贝回原数组a[p...r]
            for(i = 0;i <= r-p;i++){
                array[p+i] = temp[i];
            }
        }
    }

    @Benchmark
    public void testQuickSort(){
//        int[] a = new int[100000];
//        Random random = new Random(20190713);
//        for()
    }

}
