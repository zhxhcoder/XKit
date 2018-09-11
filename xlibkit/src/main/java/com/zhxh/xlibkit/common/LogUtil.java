package com.zhxh.xlibkit.common;

import android.util.Log;

/**
 * Created by zhxh on 2018/8/29
 * 日志输出优化
 */
public final class LogUtil {
    private final static String defaultTag = "defaultLog";
    static String TAG;
    static String fileName;
    static String className;
    static String methodName;
    static int lineNumber;

    private static boolean isDebuggable = false;

    public static void initDebugConfig(boolean isDebuggable) {
        LogUtil.isDebuggable = isDebuggable;
    }

    private LogUtil() {
        /* Protect from instantiations */
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static boolean isLogIgnore() {
        return !isDebuggable;
    }

    private static String createLog(String log) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(fileName);
        stringBuilder.append(" ");
        //stringBuilder.append(className);
        //stringBuilder.append(" ");
        stringBuilder.append(methodName);
        stringBuilder.append(":");
        stringBuilder.append(lineNumber);
        stringBuilder.append("]");
        stringBuilder.append("->");
        stringBuilder.append(log);
        return stringBuilder.toString();
    }

    /**
     * debug info
     *
     * @param sElements
     */
    private static void getMethodNames(StackTraceElement[] sElements) {
        /**
         * 每输出一个日志前都要先别 TAG还原
         */
        TAG = defaultTag;
        fileName = sElements[1].getFileName();
        className = sElements[1].getClassName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (isLogIgnore())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(TAG, createLog(message));
    }

    public static void i(String message) {
        if (isLogIgnore())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.i(TAG, createLog(message));
    }

    public static void d(String message) {
        if (isLogIgnore())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.d(TAG, createLog(message));
    }

    public static void v(String message) {
        if (isLogIgnore())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.v(TAG, createLog(message));
    }

    public static void w(String message) {
        if (isLogIgnore())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.w(TAG, createLog(message));
    }

    public static void wtf(String message) {
        if (isLogIgnore())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(TAG, createLog(message));
    }

    /********************************************************/
    public static void e(String tag, String message) {
        if (isLogIgnore())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        /**
         * 默认从 getMethodNames里得到Tag
         */
        TAG = tag;
        Log.e(TAG, createLog(message));
    }

    public static void i(String tag, String message) {
        if (isLogIgnore())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        /**
         * 默认从 getMethodNames里得到Tag
         */
        TAG = tag;
        Log.i(TAG, createLog(message));
    }

    public static void d(String tag, String message) {
        if (isLogIgnore())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        /**
         * 默认从 getMethodNames里得到Tag
         */
        TAG = tag;
        Log.d(TAG, createLog(message));
    }

    public static void v(String tag, String message) {
        if (isLogIgnore())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        /**
         * 默认从 getMethodNames里得到Tag
         */
        TAG = tag;
        Log.v(TAG, createLog(message));
    }

    public static void w(String tag, String message) {
        if (isLogIgnore())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        /**
         * 默认从 getMethodNames里得到Tag
         */
        TAG = tag;
        Log.w(TAG, createLog(message));
    }

    public static void wtf(String tag, String message) {
        if (isLogIgnore())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        /**
         * 默认从 getMethodNames里得到Tag
         */
        TAG = tag;
        Log.wtf(TAG, createLog(message));
    }
}

