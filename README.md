# XKit
android kit for developer
逐渐替换原来的XTools和XUtils项目


## RxBus

非粘性事件
注册事件

public class YourActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册 String 类型事件
        RxBus.getDefault().subscribe(this, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                Log.e("eventTag", s);
            }
        });
        // 注册带 tag 为 "my tag" 的 String 类型事件
        RxBus.getDefault().subscribe(this, "my tag", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                Log.e("eventTag", s);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销
        RxBus.getDefault().unregister(this);
    }
}
发送事件

// 发送 String 类型事件
RxBus.getDefault().post("without tag");
// 发送带 tag 为 "my tag" 的 String 类型事件
RxBus.getDefault().post("with tag", "my tag");
粘性事件（也就是先发送事件，在之后注册的时候便会收到之前发送的事件）
发送事件

// 发送 String 类型的粘性事件
RxBus.getDefault().postSticky("without tag");
// 发送带 tag 为 "my tag" 的 String 类型的粘性事件
RxBus.getDefault().postSticky("with tag", "my tag");
注册事件

public class YourActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册 String 类型事件
        RxBus.getDefault().subscribeSticky(this, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                Log.e("eventTag", s);
            }
        });
        // 注册带 tag 为 "my tag" 的 String 类型事件
        RxBus.getDefault().subscribeSticky(this, "my tag", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                Log.e("eventTag", s);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销
        RxBus.getDefault().unregister(this);
    }
}


## Version 1.6

加上了


    //version 1.6加上了删除消息的函数
    public <T> boolean removeStickyEvent(final String tag, final Class<T> eventType) {
        Utils.requireNonNull(eventType, tag);
        return CacheUtils.getInstance().removeStickyEvent(tag, eventType);
    }

    //version 1.6加上了删除消息的函数
    public <T> boolean removeStickyEvent(final Class<T> eventType) {
        return removeStickyEvent(EMPTY_TAG, eventType);
    }

    //version 1.6加上了删除消息的函数
    public void removeStickyEvent() {
        CacheUtils.getInstance().removeAllStickyEvents();
    }


## Version 1.7 计划加上延时或定时功能，并且加上自动发送信息功能

postDelay
postInterval

## Version 1.8 计划加上延时或定时功能，并且加上自动发送信息功能

postStickyDelay

## Version 1.9

优化了 日志输出

com.zhxh.xkit D/defaultLog: [MainActivity.kt onEvent:31]->
    postMain 1

在application的onCreate中加入该语句可以自动实现debug包输出日志，release包不输出，（如果不加直接使用，release包也会有日志输出）

LogUtil.initDebugConfig(BuildConfig.DEBUG)
