package com.yanchuanli.storm.Memory;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 12-8-14
 */
public class Util {
    private static Set<String> users;
    private static Set<String> result = new HashSet<>();

    public static Set<String> getUsers() {
        return users;
    }

    public static void setUsers(Set<String> users) {
        Util.users = users;
    }

    public static void addSet(Set<String> tmp) {
        result.addAll(tmp);
    }

    public static Set<String> getResult() {
        return result;
    }
}
