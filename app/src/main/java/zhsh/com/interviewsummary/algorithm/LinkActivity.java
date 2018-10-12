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
    private Button bt_has_ring;
    private Button bt_conbine_list;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_link;
    }

    @Override
    protected void initView() {

        bt_link_reversal = findViewById(R.id.bt_link_reversal);
        bt_has_ring = findViewById(R.id.bt_has_ring);
        bt_conbine_list = findViewById(R.id.bt_conbine_list);

    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        bt_link_reversal.setOnClickListener((v) -> startReversal());
        bt_has_ring.setOnClickListener((v)-> whetherRing());
        bt_conbine_list.setOnClickListener((v)-> conbineList());


    }

    /**
     * 合并两个有序链表
     * 假设为 1->3->5
     *       2->4->6
     *  则首先判断1跟2的值大的做表头,在将1.next->max( 2, 3)大的值,递归调用即可
     */
    private void conbineList() {

        Node node1 = new Node(1);
        Node node3 = new Node(3);
        Node node5 = new Node(5);

        node1.setNext(node3);
        node3.setNext(node5);

        Node node2 = new Node(2);
        Node node4 = new Node(4);
        Node node6 = new Node(6);

        node2.setNext(node4);
        node4.setNext(node6);

        Node h = startConbinedList(node1, node2);
        //反转之前的数据
        while (h != null) {
            System.out.println("合并之后的数据为:" + h.getData());
            h = h.getNext();
        }
    }

    /**
     * 使用递归调排序两个有序列表并将排序好的链表头返回
     * @param node1 第一个链表头
     * @param node2 第二个链表头
     * @return 返回排序好的链表头,只能是node1或者node2中最小的值
     */
    private Node startConbinedList(Node node1, Node node2) {

        //判空操作
        if (node1 == null){
            return node2;
        }

        if (node2 == null){
            return node1;
        }

        if (node1.getData() < node2.getData()){
            //将1的下一个指针指向min(2,3)最小的一个值
            node1.setNext(startConbinedList(node1.getNext() , node2));
            return node1; //最终返回的是链表的总头
        }else{

            node2.setNext(startConbinedList(node1 , node2.getNext()));
            return node2; //最终返回的是链表的总头
        }
    }

    /**
     * 判断是否有环:使用步长法判断: 思路,从起点开始分别以2x,1x速度出发两个指针,当遇到null停止,相遇点为null时说明没有环
     * 如果相遇点不为null,说明有环,注意有两个地方:1.首次相遇点速度为1的指针是进入环的第一圈,切记切记
     * 证明一下呗:  设 圆环总长为: R , 指针分别设置为 1, 2吧,当1指针首次进入圆环交点时,2指针在圆环位置为 x ,则 2->1 步长为 (R - x)
     * 此时开始走i步以后相遇则: (R - x) + i = 2i; (原来相距位置加上1走的距离等于 2倍的1走距离)
     * 则 i = R - x ; (X <= R) 的,所以 i < R (肯定在第一圈相遇)
     *
     * 由上可得:
     * 在第一圈相遇的时候1走的距离为: 设 起始点至环入口位置为 L ,则 1路程为(L + i)
     * 2走的距离为 (L + nR + i) ,n为圈数
     * 则: (L + nR + i) = 2(L + i); (切记:n >= 1  -> 2比1先进圈)(2速度为1的2倍,1的总路程 * 2)
     * 即: L = nR - i = (n - 1)R + (R - i) ; (n >= 1) 我们不管(n - 1)R,只是简单的绕圈数
     * 所以 L = R - i ; 而首次进入圈相遇的位置为 i ,整个圈长为 R ,
     * 所以当首次相遇的时候,重新以1为步长,一个以相交点为起始,一个以首节点开始,首次相遇点即为环的起始点;
     *
     */
    private void whetherRing() {

        Node head = new Node(0);
        Node head1 = new Node(1);
        Node head2 = new Node(2);
        Node head3 = new Node(3);
        Node head4 = new Node(4);
        Node head5 = new Node(5);
        Node head6 = new Node(6);
        Node head7 = new Node(7);
        Node head8 = new Node(8);

        head.setNext(head1);
        head1.setNext(head2);
        head2.setNext(head3);
        head3.setNext(head4);
        head4.setNext(head5);
        head5.setNext(head6);
        head6.setNext(head7);
        head7.setNext(head8);
        head8.setNext(head4);

        //获取步长法在环中的相交点
        Node node = hasRind(head);

        //查找相交点
        if (node != null){
            System.out.println("当前第一次的相交点位: " + node.getData());
            Node cur1 = head.getNext();
            Node cur2 = node.getNext();

            while (cur1 != cur2){
                cur1 = cur1.getNext();
                cur2 = cur2.getNext();
            }
            System.out.println("当前环的起点为:" + cur1.getData());

            int sum = 1 ;
            Node next = cur1.getNext();
            while (next != cur1){
                sum ++ ;
                next = next.getNext();
            }
            System.out.println("环长为:" + sum);
        }else {
            System.out.println("此链表没有环");
        }
    }

    /**
     * 判断是否有环
     * @param head
     * @return
     */
    private Node hasRind(Node head) {
        //如果链表为null,或者只有一个元素则返回head
        if (head == null || head.getNext() == null || head.getNext().getNext() == null){
            return null;
        }

        //设置首次起点为 1 , 2
        Node cur1 = head.getNext() ;
        Node cur2 = head.getNext().getNext() ;
        while (cur2 != null && cur1 != null){
            if (cur1 == cur2){ //如果相等就证明是相交点
                return cur1;
            }else{  //否则增加1步及2步
                cur1 = cur1.getNext();
                if (cur2.getNext() != null){ //切记,2->next 一定要判空
                    cur2 = cur2.getNext().getNext();
                }else{
                    return null;
                }
            }
        }

        return null ;
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
