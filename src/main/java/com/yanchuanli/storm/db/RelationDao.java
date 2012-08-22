package com.yanchuanli.storm.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.yanchuanli.storm.memory.Conf;
import com.yanchuanli.storm.model.Relation;

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
 * Date: 8/22/12
 */
public class RelationDao {
    public static void insertRelation(Relation r) {
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_RELATION);

        DBObject doc = new BasicDBObject();
        doc.put("sfhma", r.getSfhma());
        doc.put("sfhmb", r.getSfhmb());
        coll.insert(doc);
    }


    public static Set<String> queryRelation(int pageNow, int pageSize) {
        Set<String> users = new HashSet<>();

        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_RELATION);

        BasicDBObject query = new BasicDBObject();
        BasicDBObject key = new BasicDBObject("sfhma", 1);

        DBCursor cur = coll.find(query,key).skip((pageNow - 1) * pageSize).limit(pageSize);
        while (cur.hasNext()) {
            DBObject obj = cur.next();
            users.add((String) obj.get("sfhma"));
        }
        return users;
    }

    public static Set<String> getRelationsWithMultithread() {
        long count = gerRelationCount();
        int pageCount = (int) Math.ceil((double) count / (double) Conf.PAGESIZE);
        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<LoadRelationWorker> workers = new ArrayList<>();
        for (int i = 1; i <= pageCount; i++) {
            LoadRelationWorker dw = new LoadRelationWorker(i, Conf.PAGESIZE);
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


    public static long gerRelationCount() {
        long result = 0;
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME, MongoDB.COLL_RELATION);
        result = coll.getCount();
        return result;
    }
}
