package com.zhxh.xkit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.zhxh.xlibkit.rxbus.RxBus


class MainActivity : AppCompatActivity() {

    lateinit var tvBus: TextView
    lateinit var tvButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvBus = findViewById<TextView>(R.id.tvBus)
        tvButton = findViewById<TextView>(R.id.tvButton)


        tvButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, PostActivity::class.java
            ))
        }

        RxBus.getDefault().subscribe(this, RxBus.Callback<User> { s -> tvBus.append("eventTag ${s.id}") })

        RxBus.getDefault().subscribe(this, "haha", RxBus.Callback<User> { s -> tvBus.append("eventTag ${s.id}") })

        RxBus.getDefault().subscribeSticky(this, "hehe", RxBus.Callback<User> { s -> tvBus.append("eventTag ${s.id}") })

    }

    override fun onDestroy() {
        super.onDestroy()
        // 注销
        RxBus.getDefault().unregister(this)
    }
}
