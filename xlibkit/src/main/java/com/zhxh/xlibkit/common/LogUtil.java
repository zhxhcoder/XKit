package com.zhxh.xlibkit.common;

import android.util.Log;

import com.zhxh.xlibkit.BuildConfig;

/**
 * Created by zhxh on 2018/8/29
 */
public final class LogUtil {
    private static String TAG = "debugLog";
    /**
     * 日志输出级别 0为不输出
     */
    private static int logMode = BuildConfig.DEBUG ? 4 : 4;

    public static void e(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (Log.ERROR <= logMode)
            Log.e(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (Log.WARN <= logMode)
            Log.w(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (Log.INFO <= logMode)
            Log.i(tag, msg);
    }

    public static void i(String msg) {
        if (msg == null) {
            return;
        }
        if (Log.INFO <= logMode)
            Log.i(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (tag == null || msg == null) {
            return;
        }
        if (Log.DEBUG <= logMode)
            Log.d(tag, msg);
    }

    public static void d(String msg) {
        if (msg == null) {
            return;
        }
        if (Log.DEBUG <= logMode)
            Log.d(TAG, msg);
    }

    public static void e(Object object, String msg) {
        if (msg == null) {
            return;
        }
        if (Log.DEBUG <= logMode)
            Log.e(object.getClass().getSimpleName(), msg);
    }

    public static void e(String msg) {
        if (msg == null) {
            return;
        }
        if (Log.DEBUG <= logMode)
            Log.e(TAG, msg);
    }
}

