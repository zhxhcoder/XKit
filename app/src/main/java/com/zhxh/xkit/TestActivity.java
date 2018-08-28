package com.zhxh.xkit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zhxh.xlibkit.rxbus.RxBus;

public class TestActivity extends AppCompatActivity {

    TextView tvBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvBus = findViewById(R.id.tvBus);

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
