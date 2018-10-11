package zhsh.com.interviewsummary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * created by shi on 2018/10/11/011
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(setContentViewId());
        initView();
        initDate();
        initListener();


    }

    protected abstract int setContentViewId();

    protected abstract void initView();

    protected abstract void initDate();

    protected void initListener() {}

    protected void startActivity(Class clazz){
        Intent intent = new Intent(this , clazz);
        startActivity(intent);
    }
}
