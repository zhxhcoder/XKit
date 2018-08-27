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