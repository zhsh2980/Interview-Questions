package zhsh.com.interviewsummary.manager;

/**
 * created by shi on 2018/11/7/007
 */
public class QuickSortManage {


    // 快速排序，a是数组，n表示数组的大小
    public static void quickSort(int[] a, int n) {
        quickSortInternally(a, 0, n-1);
    }

    // 快速排序递归函数，p,r为下标
    private static void quickSortInternally(int[] a, int p, int r) {
        if (p >= r) return;

        int q = partition(a, p, r); // 获取分区点
        quickSortInternally(a, p, q-1);
        quickSortInternally(a, q+1, r);

    }

    /**
     *
     * @param a 待排序数组
     * @param p 数组的起始位置
     * @param r 数组的终止位置
     * @return 返回数组最后一位排序后所在数组位置的下标值
     */
    private static int partition(int[] a, int p, int r) {

        //获取p ,r的中位数取其中的中间值作为分区点
        int q = (p + r) / 2 ;
        //得到三个值的中位数,然后给末尾交换
        int inter = p < q ? (q < r ? q : p < r ? r : p) : (q > r ? q : p > r ? r : p);
        if (inter != r){ //交换q跟r
            a[inter] = a[inter] ^ a[r]; //一个数异或两次还是本身 x = x ^ y ^ y ;
            a[r] = a[inter] ^ a[r];
            a[inter] = a[inter] ^ a[r];
        }

        int pivot = a[r]; //将最后一位作为分区点
        int i = p;
        for(int j = p; j < r; j++) {
            /**
             * 判断所有小于分区点的值跟其后第一个大于分区点的值交换位置
             * 比如:数组 1,4,5,2,3
             * 第一次遍历i = j = 0 ;交换第一个i,可以判断一下不交换位置了
             * 第二次遍历 i = j = 1 ,此时 4 > 3 , i不变 j++ :i ->4 , j -> 5
             * 第三次遍历 i = 1 , j = 2 , 此时 5  > 3 , i不变,j++ : i->4 , j -> 2
             * 第四次遍历 i = 1 , j = 3 , 此时 2 < 3了,将i的值与j互换,即数组1,3位置元素互换后为 1,2,5,4,3 : i++ , j++ ;
             * 第五次遍历 i = 2 , j = 4 , 进入最后一位跳出for循环,则交换i跟r的值,排序为 1,2,3,4,5
             * 此时返回的是i=2.即以3为分区点,再次循环分为1,2 跟 4,5划分
             */

            if (a[j] < pivot) {
                if (i != j){ //判断角标一致不在交换位置了,只是i++ ,j++ 操作
                    int tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }

                ++i;
            }
        }
        //将排序后的第一个大于povit的跟最后一个交换位置后,i之前的均小于povit,之后的均大于povit
        int tmp = a[i];
        a[i] = a[r];
        a[r] = tmp;

        System.out.println("i=" + i);
        return i;
    }



}
