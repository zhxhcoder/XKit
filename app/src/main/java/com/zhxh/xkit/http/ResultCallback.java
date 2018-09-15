package com.zhxh.xkit.http;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by zhxh on 2018/9/15
 */
public abstract class ResultCallback {

    public abstract void onError(Request request, Exception e);

    public abstract void onResponse(String str) throws IOException;
}
