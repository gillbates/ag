package com.yanchuanli.storm.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.yanchuanli.storm.memory.Conf;
import com.yanchuanli.storm.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 12-8-14
 */
public class UserDao {

    public static void insertUser(String udid, String name) {
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_USER);

        DBObject doc = new BasicDBObject();
        doc.put("udid", udid);
        doc.put("name", name);
        coll.insert(doc);
    }

    public static void insertSourceUser(String udid, String name) {
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.SOURCE_USER);

        DBObject doc = new BasicDBObject();
        doc.put("udid", udid);
        doc.put("name", name);
        coll.insert(doc);
    }


    public static Set<String> querySourceUser(int pageNow, int pageSize) {
        Set<String> users = new HashSet<>();

        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.SOURCE_USER);

        DBCursor cur = coll.find(new BasicDBObject()).skip((pageNow - 1) * pageSize).limit(pageSize);
        while (cur.hasNext()) {
            DBObject obj = cur.next();
            users.add((String) obj.get("udid"));
        }
        return users;
    }


    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_USER);

        BasicDBObject query = new BasicDBObject();

        DBCursor cur = coll.find(query);

        while (cur.hasNext()) {
            DBObject obj = cur.next();
            User user = new User((String) obj.get("udid"), (String) obj.get("name"));
            users.add(user);
        }
        return users;
    }

    public static Set<String> getUserSet() {
        Set<String> users = new HashSet<>();
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_USER);

        BasicDBObject query = new BasicDBObject();
        BasicDBObject key = new BasicDBObject("udid", 1);
//        query.put("udid", 1);
        DBCursor cur = coll.find(query, key);

        while (cur.hasNext()) {
            DBObject obj = cur.next();
            users.add((String) obj.get("udid"));
        }
        return users;
    }


    public static Set<String> getUsersWithMultithread() {
        long count = gerUsersCount();
        int pageCount = (int) Math.ceil((double) count / (double) Conf.PAGESIZE);
        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<LoadUserWorker> workers = new ArrayList<>();
        for (int i = 1; i <= pageCount; i++) {
            LoadUserWorker dw = new LoadUserWorker(i, Conf.PAGESIZE);
            workers.add(dw);
        }

        Set<String> data = new HashSet<>();
        try {
            List<Future<Set<String>>> result = pool.invokeAll(workers);
            for (Future<Set<String>> future : result) {
                data.addAll(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        return data;
    }

    public static Set<String> queryUser(int pageNow, int pageSize) {
        Set<String> users = new HashSet<>();

        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_USER);

        BasicDBObject query = new BasicDBObject();
        BasicDBObject key = new BasicDBObject("udid", 1);

        DBCursor cur = coll.find(query, key).skip((pageNow - 1) * pageSize).limit(pageSize);
        while (cur.hasNext()) {
            DBObject obj = cur.next();
            users.add((String) obj.get("udid"));
        }
        return users;
    }

    public static boolean containUser(int i) {
        boolean result = false;
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_USER);
        BasicDBObject query = new BasicDBObject();
//        query.put("udid", i);


        DBCursor cur = coll.find(query);

        while (cur.hasNext()) {
            result = true;
        }
        return result;
    }

    public static Set<String> getSourceUsers() {
        Set<String> users = new HashSet<>();
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.SOURCE_USER);

        BasicDBObject query = new BasicDBObject();
        BasicDBObject key = new BasicDBObject("udid", 1);
//        query.put("udid", 1);
        DBCursor cur = coll.find(query, key);

        while (cur.hasNext()) {
            DBObject obj = cur.next();
//            User user = new User((String) obj.get("udid"), (String) obj.get("name"));
            users.add((String) obj.get("udid"));
        }
        return users;
    }

    public static long gerUsersCount() {
        long result = 0;
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME, MongoDB.COLL_USER);
        result = coll.getCount();
        return result;
    }

    public static long gerSourceUsersCount() {
        long result = 0;
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME, MongoDB.SOURCE_USER);
        result = coll.getCount();
        return result;
    }



}
