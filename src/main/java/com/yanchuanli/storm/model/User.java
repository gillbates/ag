package com.yanchuanli.storm.model;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 12-8-14
 */
public class User {
    private String udid;
    private String name;

    public User(String udid, String name) {
        this.udid = udid;
        this.name = name;
    }

    public String getUdid() {
        return udid;
    }

    public String getName() {
        return name;
    }
}
