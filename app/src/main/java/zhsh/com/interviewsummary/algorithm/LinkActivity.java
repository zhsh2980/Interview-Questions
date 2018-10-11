package zhsh.com.interviewsummary.algorithm;

import android.view.View;
import android.widget.Button;

import zhsh.com.interviewsummary.R;
import zhsh.com.interviewsummary.activity.BaseActivity;
import zhsh.com.interviewsummary.data.Node;

/**
 * 链表算法的面试题
 * created by shi on 2018/10/11/011
 */
public class LinkActivity extends BaseActivity {

    private Button bt_link_reversal;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_link;
    }

    @Override
    protected void initView() {

        bt_link_reversal = findViewById(R.id.bt_link_reversal);

    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        bt_link_reversal.setOnClickListener((v) -> startReversal());


    }

    /**
     * 单链表反转的实现方法
     */
    private void startReversal() {

        Node head = new Node(0);
        Node head1 = new Node(1);
        Node head2 = new Node(2);
        Node head3 = new Node(3);

        head.setNext(head1);
        head1.setNext(head2);
        head2.setNext(head3);

        //反转之前的数据
        Node h = head;
        while (h != null) {
            System.out.println("反转之前的数据为:" + h.getData());
            h = h.getNext();
        }

        Node rehead;
//        rehead = recursionReversal(head); //第一种方法:递归反转
        rehead = ergodicReversal(head); //第二种方法

        //反转之后的数据
        while (rehead != null) {
            System.out.println("反转之后的数据为:" + rehead.getData());
            rehead = rehead.getNext();
        }



    }

    /**
     * 遍历反转法:从前往后反转各个结点的指针域的指向。
     * 将当前节点cur的下一个节点 cur.getNext()缓存到temp后，
     * 然后更改当前节点指针指向上一结点pre。也就是说在反转当前结点指针指向前，
     * 先把当前结点的指针域用tmp临时保存，以便下一次使用，其过程可表示如下：
     * @param head
     */
    private Node ergodicReversal(Node head) {

        if (head == null){
            return head;
        }
        //首次进入设置当前节点第二个数据,第一个数据为pre,
        Node pre = head ;
        Node cur = head.getNext();
        Node temp ; //临时节点用于存储当前节点的下一个节点的位置
        while (cur != null){
            //第二个节点不为null就把指向的下一个节点设置为temp临时节点
            temp = cur.getNext();
            cur.setNext(pre); //把当前节点设置成前一个节点的位置

            //一次将值向后移动,pre有0->1; cur有1->2;
            pre = cur ;
            cur = temp ;
        }
        head.setNext(null); //将第一个节点设置为null
        return pre; //由于最后一次temp跟cur均为null,所以最后一个节点,也就是反转后的第一个节点为pre

    }

    /**
     * 递归反转法:在反转当前节点之前先反转后续节点,层层深入直到尾节点才开始反转指针域的指向
     * head:是前一结点的指针域（PS：前一结点的指针域指向当前结点）
     * head.getNext()：是当前结点的指针域（PS：当前结点的指针域指向下一结点）
     * reHead：是反转后新链表的头结点（即原来单链表的尾结点）
     */
    private Node recursionReversal(Node head) {

        // head看作是前一结点，head.getNext()是当前结点，reHead是反转后新链表的头结点
        if (head == null || head.getNext() == null) {
            return head;  //若为空链或者当前结点在尾结点，则直接还回
        }
        //rehead查找到链表元素中的最后一位,依次返回,并没有参与后续的计算:比如第一次从head2开始运算是返回head3,第二次从head1运算还是返回head2返回的值
        //只是标记反转后头结点的,不参与后续计算,只是一直随递归返回而已
        Node rehead = recursionReversal(head.getNext());

        System.out.println("最后一个节点为:" + rehead.getData()); //打印数据一直为 3

        head.getNext().setNext(head); //将后一个指针域设置成前一个的,然后将自己的设置成null,则递归后前一个指针域指向了null,将再次反转,直到第一个
        head.setNext(null);
        return rehead;
    }
}
