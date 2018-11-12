package zhsh.com.interviewsummary.contract;

import zhsh.com.interviewsummary.data.DoubleNode;
import zhsh.com.interviewsummary.data.Node;

/**
 * created by shi on 2018/11/12/012
 */
public interface LinkActyContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        DoubleNode deleteDoubleNode(DoubleNode node1, int n);
    }
}
