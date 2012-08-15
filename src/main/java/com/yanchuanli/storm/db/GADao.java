package com.yanchuanli.storm.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.yanchuanli.storm.model.Czrk;
import com.yanchuanli.storm.model.Lgtrynb;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 12-8-15
 */
public class GADao {

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
}
