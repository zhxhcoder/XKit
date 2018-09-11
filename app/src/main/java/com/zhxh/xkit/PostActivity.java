package com.zhxh.xkit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhxh.xlibkit.rxbus.RxBus;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        new Thread(new Runnable() {
            @Override
            public void run() {
                RxBus.getDefault().post("postThread", new User(3));
                RxBus.getDefault().postSticky("postStickyThread", new User(4));
            }
        });


        findViewById(R.id.tvBus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, TestActivity.class));
            }
        });

        RxBus.getDefault().post("postMain", new User(1));
        RxBus.getDefault().postDelay("postDelay", new User(-1), 12000);
        RxBus.getDefault().postSticky("postStickyMain", new User(2));
        RxBus.getDefault().postStickyDelay("postStickyMain", new User(-2), 15000);

    }
}
