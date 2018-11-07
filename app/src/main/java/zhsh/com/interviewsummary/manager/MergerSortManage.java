package zhsh.com.interviewsummary.manager;

/**
 * 归并算法的解答思路:排序一个数组,先将数组从中间分成前后两部分,在对前后两部分分辨排序,再将排序号的两部分合并在一起,这样整个数组就是有序的了
 *
 * merge_sort(p...r) = merge(merge_sort(p...q) , merge_sort(q+1...r));
 终止条件为
 p >= r 不用在继续分解
 * created by shi on 2018/11/7/007
 */
public class MergerSortManage {

    /**
     *
     * @param a 需要排序的数组
     * @param p 数组的起始位置
     * @param r 数组的结束位置
     *          终止条件为p >= r的时候
     */
    public static void merge_sort(int[] a , int p , int r){

        if (p >= r){
            return; //结束递归的终止条件
        }

        //获取中间位置的q
        int q = p + (r - p) /2 ;

        //分别递归调用 :分成两个部分
        merge_sort(a , p , q);
        merge_sort(a , q + 1 , r);

        //合并上方两部分的代码到数组a中
        merge(a , p , q , r);




    }

    private static void merge(int[] a, int p, int q, int r) {

        //将两个数组a[p , q]跟a[q+1 ,r]合并成一个数组 a
        int i = p ;
        int j = q + 1 ;
        int k = 0 ; //初始化新的数组的下标,存放a[p , q]跟a[q+1 ,r]合并后的数组
        int[] temp = new int[r - p + 1]; //初始化数组的长度为 p ->r

        while (i <= q && j <= r) { //判断两个数组均有值的情况
            if (a[i] <= a[j]) { //判断两个数组存储那个数据在前,值相等时存储第一个数组维持稳定性;
                temp[k++] = a[i++]; //先赋值在增加变量的操作
            }else{
                temp[k++] = a[j++];
            }
        }

        //判断哪个数组还有剩余的数据:初始化默认第一个数组有数据
        int start = i ;
        int end = q ;
        if (j <= r){ //判断是不是第二个数组有数据
            start = j;
            end = r;
        }

        //将剩余的数据存储到temp数组中区
        while (start <= end){
            temp[k++] = a[start++] ;
        }

        //将数据拷贝到a[p , r]中
        for (i = 0; i <= r - p; i++) {
            a[p+i] = temp[i]; //不能使用p++ ,否则上面的r-p也跟着变化的
        }
    }
}
