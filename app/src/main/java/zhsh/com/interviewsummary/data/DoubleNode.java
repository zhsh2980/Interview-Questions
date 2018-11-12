package zhsh.com.interviewsummary.data;

/**
 * 模仿单链表的数据格式  (data|next):数据跟指向下一个数据的指针
 *
 * created by shi on 2018/10/11/011
 */
public class DoubleNode {

    /**
     * data域：存储数据元素信息的域称为数据域；　
     * next域：存储直接后继位置的域称为指针域，它是存放结点的直接后继的地址（位置）的指针域（链域）。
     * pre域：存储直接前继位置的域称为指针域，它是存放结点的直接前继的地址（位置）的指针域（链域）。
     * data域+ next域：组成数据ai的存储映射，称为结点；Node类就是一个结点
     */
    private int data ; //数据域
    private DoubleNode next ; //指针后置域
    private DoubleNode pre ; //指针前置域

    public DoubleNode(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public DoubleNode getNext() {
        return next;
    }

    public void setNext(DoubleNode next) {
        this.next = next;
    }

    public DoubleNode getPre() {
        return pre;
    }

    public void setPre(DoubleNode pre) {
        this.pre = pre;
    }
}
