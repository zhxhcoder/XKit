package com.zhxh.xkit;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zhxh.xlibkit.rxbus.RxBus;

import java.util.TimerTask;

public class TestActivity extends AppCompatActivity {


    TextView tvBus;


    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvBus = findViewById(R.id.tvBus);


        new Thread(new Runnable() {
            @Override
            public void run() {
                RxBus.getDefault().subscribeSticky(this, "thread", new RxBus.Callback<User>() {
                    @Override
                    public void onEvent(final User user) {

                        Log.d("rx-thread", "id " + user.id);

                        runOnUiThread(new TimerTask() {
                            @Override
                            public void run() {
                                tvBus.append("id " + user.id);
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
