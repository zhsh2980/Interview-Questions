package zhsh.com.interviewsummary.manager;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * created by shi on 2018/11/14/014
 */
public class TestServer extends IntentService {

    public static final String ACTION_UPLOAD_DATA
            = "com.jlrc.upload";
    private JSONObject jsonObject;

    public TestServer() {
        super("thisTest");
    }

    public static void start(Context context, String action) {
        Intent intent = new Intent(context, TestServer.class);
        intent.setAction(action);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (ACTION_UPLOAD_DATA.equals(intent.getAction())) {

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //开始
            System.out.println("this is " + Thread.currentThread() + Looper.getMainLooper().getThread());
            if (Thread.currentThread() == Looper.getMainLooper().getThread())
                System.out.println("当前是主线程");
            else
                System.out.println("当前是子线程");

            JSONArray objects = new JSONArray();
            for (int i = 0; i < 100; i++) {

                if (jsonObject == null){
                    jsonObject = new JSONObject();
                }else{
                    jsonObject.fluentClear();
                }


                jsonObject.put("nihao", "shi" + i);
                jsonObject.put("age", "nianling" + i);
                objects.add(jsonObject);
            }

            Log.e("shiqiang", "我是json: " + objects.toJSONString());


        }

    }
}
