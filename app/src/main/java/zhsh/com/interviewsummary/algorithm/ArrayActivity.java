package zhsh.com.interviewsummary.algorithm;

import zhsh.com.interviewsummary.R;
import zhsh.com.interviewsummary.activity.BaseActivity;
import zhsh.com.interviewsummary.manager.MergerSortManage;
import zhsh.com.interviewsummary.manager.QuickSortManage;

/**
 * 排序算法
 * created by shi on 2018/10/18/018
 */
public class ArrayActivity extends BaseActivity{
    @Override
    protected int setContentViewId() {
        return R.layout.activity_array;
    }

    @Override
    protected void initView() {


        findViewById(R.id.bt_bubble_sort).setOnClickListener((v)-> bubbleSort());

        findViewById(R.id.bt_insert_sort).setOnClickListener((v)-> insertSort());
        findViewById(R.id.bt_merger_sort).setOnClickListener((v)-> mergerSort());
        findViewById(R.id.bt_quick_sort).setOnClickListener((v)-> quickSort());

    }

    private void quickSort() {
        int [] arr = new int[]{9,4,3,5,2,7,1,8};
        int n = arr.length;

        QuickSortManage.quickSort(arr , n);

        for(int i : arr){
            System.out.println("快速算法排序以后的数据为: " + i);
        }


    }

    /**
     * 归并算法
     */
    private void mergerSort() {

        int [] arr = new int[]{1,4,3,5,2,7,9,8};
        int n = arr.length;

        MergerSortManage.merge_sort(arr , 0 , n-1);

        for(int i : arr){
            System.out.println("归并算法排序以后的数据为: " + i);
        }

    }


    //冒泡排序
    private void bubbleSort() {
        int [] arr = new int[]{1,4,3,5,2,7,9,8};
        if (arr == null || arr.length == 1){
            return;
        }


        int n = arr.length;
        for (int i = 0; i < n; i++) {

            boolean isFlag = false ; //是否有更改

            for (int j = 0; j < n - i - 1; j++) {

                if (arr[j] > arr[j + 1]){
                    int temp = arr[j];
                    arr[j] =  arr[j + 1];
                    arr[j + 1] = temp;
                    isFlag = true ;
                }
            }

            if (!isFlag){
                break;
            }
        }

        for(int i : arr){
            System.out.println("冒泡排序排序以后的数据为: " + i);
        }

    }

    /**
     * 插入算法
     */
    private void insertSort() {
        int[] arr = new int[]{1, 4, 3, 0, 5, 2, 7, 9, 8};
        if (arr == null || arr.length == 1) {
            return;
        }
        int n = arr.length;


        for (int i = 1; i < n; i++) {
            int value = arr[i];
            int j = i - 1 ;
            for(; j >= 0 ; j--){
                if (arr[j] > value){
                    arr[j + 1] = arr[j] ;
                }else{
                    break;
                }
            }
            arr[j + 1] = value ;
        }


        for(int i : arr){
            System.out.println("插入算法排序以后的数据为: " + i);
        }

    }





    @Override
    protected void initDate() {

    }
}
