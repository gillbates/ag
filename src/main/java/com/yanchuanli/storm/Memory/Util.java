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

    public static Set<String> getInterSection(Set<String> set1, Set<String> set2) {
        Set<String> a;
        Set<String> b;
        Set<String> res = new HashSet<String>();
        if (set1.size() <= set2.size()) {
            a = set1;
            b = set2;
        } else {
            a = set2;
            b = set1;
        }

        for (String e : a) {
            if (b.contains(e)) {
                res.add(e);
            }
        }
        return res;
    }
}
