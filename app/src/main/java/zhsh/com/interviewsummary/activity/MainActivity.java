package zhsh.com.interviewsummary.activity;

import zhsh.com.interviewsummary.R;
import zhsh.com.interviewsummary.algorithm.ArrayActivity;
import zhsh.com.interviewsummary.algorithm.LinkActivity;

public class MainActivity extends BaseActivity {



    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        findViewById(R.id.bt_link).setOnClickListener((v)->startActivity(LinkActivity.class));
        findViewById(R.id.bt_sort).setOnClickListener((v)->startActivity(ArrayActivity.class));

    }

    @Override
    protected void initDate() {

    }


    @Override
    protected void initListener() {

    }
}
