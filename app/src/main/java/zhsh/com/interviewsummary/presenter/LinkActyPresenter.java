package zhsh.com.interviewsummary.presenter;

import zhsh.com.interviewsummary.contract.LinkActyContract;
import zhsh.com.interviewsummary.data.DoubleNode;
import zhsh.com.interviewsummary.data.Node;

/**
 * created by shi on 2018/11/12/012
 */
public class LinkActyPresenter implements LinkActyContract.Presenter {

    private LinkActyContract.View view ;

    public LinkActyPresenter(LinkActyContract.View view) {
        this.view = view;
    }

    @Override
    public DoubleNode deleteDoubleNode(DoubleNode node1, int n) {

        if (node1 == null || n <= 0){
         return null ;
        }
        int k = n ;

        DoubleNode  doubleNode = node1 ;
        while (doubleNode != null){ //从n开始倒序k--
            doubleNode = doubleNode.getNext();
            k-- ;
        }

        if (k > 0){
            return null ;
        }else if (k == 0){
            //将数据头设置成node1的下一位,并置空前置节点
            node1 = node1.getNext();
            node1.setPre(null);
            return node1 ;
        }

        System.out.println("当前K为:" + k);

        //k < 0的操作,重新遍历
        doubleNode = node1;
        while (++k != 0){ //从k值为符增加到首先先加1 -> 直到 0 的位置,
            doubleNode = doubleNode.getNext();
        }

        //k == 0 的时候删除操作
        if (doubleNode.getNext().getNext()!= null) {
            doubleNode.getNext().getNext().setPre(doubleNode);
        }
        doubleNode.setNext(doubleNode.getNext().getNext());

        return node1;
    }
}
