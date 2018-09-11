package com.zhxh.xkit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.zhxh.xlibkit.common.LogUtil
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
            startActivity(Intent(this@MainActivity, PostActivity::class.java))
        }

        RxBus.getDefault().subscribe(this, "postMain", RxBus.Callback<User> { s ->
            LogUtil.d("\npostMain ${s.id}")
            LogUtil.d("postMain", "\npostMain ${s.id}")
            tvBus.append("\npostMain ${s.id}")
        })

        RxBus.getDefault().subscribe(this, "postThread", RxBus.Callback<User> { s -> tvBus.append("\npostThread ${s.id}") })

        RxBus.getDefault().subscribeSticky(this, "postStickyMain", RxBus.Callback<User> { s ->
            tvBus.append("\npostStickyMain ${s.id}")
            RxBus.getDefault().removeStickyEvent("postStickyMain", User::class.java)

        })

        RxBus.getDefault().subscribeSticky(this, "postStickyThread", RxBus.Callback<User> { s -> tvBus.append("\npostStickyThread ${s.id}") })


    }

    override fun onDestroy() {
        super.onDestroy()
        // 注销
        RxBus.getDefault().unregister(this)
    }
}
