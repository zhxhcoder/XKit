package com.zhxh.xkit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zhxh.xlibkit.parser.GsonParser;
import com.zhxh.xlibkit.rxbus.RxBus;

public class TestActivity extends AppCompatActivity {

    //{"CourseName":"","EndTime":"","UnitName":"元","CouponID":1044,"CouponCode":"ca575ace-317b-47f6-bdcf-d386714fbdbb",
    // "CouponName":"课程体验券","CouponType":0,"Type":0,"Cost":500,"LowerCost":9800,"Days":7,"LargeDes":"适用于所有课程，\n满9800可用","WeChatCode":"","CollectionTime":"0001-01-02","UserCashID":255263,"OrderID":0,"Status":1,"UseTime":"","ValidityTime":"2018-09-18","StockCode":"","StockName":"","State":2,"StateName":"已过期","CourseIDList":[]}
    //CouponID为 int类型
    //    "CouponID": 1044,
    //    "CouponName": "课程体验券",
    //   "CourseIDList": []


    private String gsonStr = "{\"CourseName\":\"\",\"EndTime\":\"\",\"UnitName\":\"元\",\"CouponID\":1044,\"CouponCode\":\"ca575ace-317b-47f6-bdcf-d386714fbdbb\",\"CouponName\":\"课程体验券\",\"CouponType\":0,\"Type\":0,\"Cost\":500,\"LowerCost\":9800,\"Days\":7,\"LargeDes\":\"适用于所有课程，\\n满9800可用\",\"WeChatCode\":\"\",\"CollectionTime\":\"0001-01-02\",\"UserCashID\":255263,\"OrderID\":0,\"Status\":1,\"UseTime\":\"\",\"ValidityTime\":\"2018-09-18\",\"StockCode\":\"\",\"StockName\":\"\",\"State\":2,\"StateName\":\"已过期\",\"CourseIDList\":[]}";

    TextView tvBus;
    TextView tvGsonValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvBus = findViewById(R.id.tvBus);
        tvGsonValue = findViewById(R.id.tvGsonValue);


        tvGsonValue.append(GsonParser.parseGsonValue("CouponID", gsonStr));
        tvGsonValue.append("\n");
        tvGsonValue.append(GsonParser.parseGsonValue("EndTime", gsonStr));
        tvGsonValue.append("\n");
        tvGsonValue.append(GsonParser.parseGsonValue("CouponName", gsonStr));
        tvGsonValue.append("\n");
        tvGsonValue.append(GsonParser.parseGsonValue("CourseIDList", gsonStr));
        tvGsonValue.append("\n");
        tvGsonValue.append(GsonParser.parseGsonValue("LargeDes", gsonStr));

        initTestSubscribe();


    }

    private void initTestSubscribe() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                RxBus.getDefault().subscribe(this, "postMain", new RxBus.Callback<User>() {
                    @Override
                    public void onEvent(final User user) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvBus.append("\npostMain " + user.id);
                            }
                        });
                    }
                });
                RxBus.getDefault().subscribe(this, "postThread", new RxBus.Callback<User>() {
                    @Override
                    public void onEvent(final User user) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvBus.append("\npostThread " + user.id);
                            }
                        });
                    }
                });
                RxBus.getDefault().subscribeSticky(this, "postStickyMain", new RxBus.Callback<User>() {
                    @Override
                    public void onEvent(final User user) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvBus.append("\npostStickyMain " + user.id);
                            }
                        });
                    }
                });
                RxBus.getDefault().subscribeSticky(this, "postStickyThread", new RxBus.Callback<User>() {
                    @Override
                    public void onEvent(final User user) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvBus.append("\npostStickyThread " + user.id);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        RxBus.getDefault().unregister(this);
    }
}
