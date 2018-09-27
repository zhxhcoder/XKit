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

    private String tstStr="{\"Remark\":\"大神好眼光，再接再厉，成为首富！\",\"QuizLead\":\"猜大盘涨跌，赢海量牛宝\",\"Url\":\"https://h5.niuguwang.com/huodong/2018y/guess-updown/index.html\"}";
    TextView tvBus;
    TextView tvGsonValue;
    TextView tvTestValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvBus = findViewById(R.id.tvBus);
        tvGsonValue = findViewById(R.id.tvGsonValue);
        tvTestValue = findViewById(R.id.tvTestValue);


        tvGsonValue.append(GsonParser.parseGsonNumber("CouponID", gsonStr));
        tvGsonValue.append("\n");
        tvGsonValue.append(GsonParser.parseGsonValue("EndTime", gsonStr));
        tvGsonValue.append("\n");
        tvGsonValue.append(GsonParser.parseGsonValue("CouponName", gsonStr));
        tvGsonValue.append("\n");
        tvGsonValue.append(GsonParser.parseGsonValue("CourseIDList", gsonStr));
        tvGsonValue.append("\n");
        tvGsonValue.append(GsonParser.parseGsonValue("LargeDes", gsonStr));


        tvGsonValue.append(GsonParser.parseGsonValue("Remark", tstStr));
        tvGsonValue.append("\n");

        tvGsonValue.append(GsonParser.parseGsonValue("QuizLead", tstStr));
        tvGsonValue.append("\n");

        tvGsonValue.append(GsonParser.parseGsonValue("Url", tstStr));
        tvGsonValue.append("\n");


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
