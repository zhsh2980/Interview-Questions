package zhsh.com.interviewsummary.activity;


import android.view.View;
import android.widget.Button;

import zhsh.com.interviewsummary.R;
import zhsh.com.interviewsummary.algorithm.LinkActivity;

public class MainActivity extends BaseActivity {


    private Button bt_link;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        bt_link = findViewById(R.id.bt_link);

    }

    @Override
    protected void initDate() {

    }


    @Override
    protected void initListener() {

        bt_link.setOnClickListener((v)->startActivity(LinkActivity.class));

    }
}
