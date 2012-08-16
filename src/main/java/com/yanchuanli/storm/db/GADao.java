package com.yanchuanli.storm.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.yanchuanli.storm.memory.Conf;
import com.yanchuanli.storm.model.Czrk;
import com.yanchuanli.storm.model.Lgtrynb;

import java.lang.reflect.Field;
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
 * Date: 12-8-15
 */
public class GADao {

    public static Set<String> getLgtrynbs() {
        Set<String> users = new HashSet<>();
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_Lgtrynb);

        BasicDBObject query = new BasicDBObject();
        BasicDBObject key = new BasicDBObject("zjhm", 1);

        DBCursor cur = coll.find(query, key);

        while (cur.hasNext()) {
            DBObject obj = cur.next();
            users.add((String) obj.get("zjhm"));
        }
        return users;
    }

    public static Set<String> getCzrkWithSingleThread() {
        Set<String> users = new HashSet<>();
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_CZRK);

        BasicDBObject query = new BasicDBObject();
        BasicDBObject key = new BasicDBObject("gmsfhm", 1);

        DBCursor cur = coll.find(query, key);

        while (cur.hasNext()) {
            DBObject obj = cur.next();
            users.add((String) obj.get("gmsfhm"));
        }
        return users;
    }

    public static Set<String> getCzrksWithMultithread() {
        long count = gerCzrkCount();
        int pageCount = (int) Math.ceil((double) count / (double) Conf.PAGESIZE);
        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<LoadCzrkWorker> workers = new ArrayList<>();
        for (int i = 1; i <= pageCount; i++) {
            LoadCzrkWorker dw = new LoadCzrkWorker(i, Conf.PAGESIZE);
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

    public static void insertLgtrynb(List<Lgtrynb> list) {

        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_Lgtrynb);
        List<DBObject> result = new ArrayList<>();
        for (Lgtrynb c : list) {
            DBObject doc = new BasicDBObject();
            Class clazz = c.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field f : fields) {
                try {
                    doc.put(f.getName(), f.get(c));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            result.add(doc);
        }
        coll.insert(result);
    }

    public static void insertCzrk(List<Czrk> list) {
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_CZRK);
        List<DBObject> result = new ArrayList<>();
        for (Czrk c : list) {
            DBObject doc = new BasicDBObject();
            Class clazz = c.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field f : fields) {
                try {
                    doc.put(f.getName(), f.get(c));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            result.add(doc);
        }
        coll.insert(result);
    }

    public static void insertCzrk(Czrk c) {


        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_CZRK);

        DBObject doc = new BasicDBObject();


        Class clazz = c.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            try {
                doc.put(f.getName(), f.get(c));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        coll.insert(doc);

    }

    public static Set<String> queryCzrk(int pageNow, int pageSize) {
        Set<String> users = new HashSet<>();

        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_CZRK);

        BasicDBObject query = new BasicDBObject();
        BasicDBObject key = new BasicDBObject("gmsfhm", 1);

        DBCursor cur = coll.find(query, key).skip((pageNow - 1) * pageSize).limit(pageSize);
        while (cur.hasNext()) {
            DBObject obj = cur.next();
            users.add((String) obj.get("gmsfhm"));
        }
        return users;
    }

    public static Set<String> queryLgtrynb(int pageNow, int pageSize) {
        Set<String> users = new HashSet<>();

        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_Lgtrynb);

        DBCursor cur = coll.find(new BasicDBObject()).skip((pageNow - 1) * pageSize).limit(pageSize);
        while (cur.hasNext()) {
            DBObject obj = cur.next();
            users.add((String) obj.get("zjhm"));
        }
        return users;
    }

    public static long gerCzrkCount() {
        long result = 0;
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME, MongoDB.COLL_CZRK);
        result = coll.getCount();
        return result;
    }

    public static long gerLgtrynbCount() {
        long result = 0;
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME, MongoDB.COLL_Lgtrynb);
        result = coll.getCount();
        return result;
    }
}
