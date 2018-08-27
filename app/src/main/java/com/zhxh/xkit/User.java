package com.zhxh.xkit;

/**
 * Created by zhxh on 2018/8/27
 */
public class User {
    public long id;
    public String name;

    public User(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
