package com.zhxh.xkit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhxh.xlibkit.rxbus.RxBus;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        RxBus.getDefault().post("haha", new User(2));
        RxBus.getDefault().postSticky("hehe", new User(1));
    }
}
