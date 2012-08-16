package com.yanchuanli.storm.test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.yanchuanli.storm.memory.Conf;
import com.yanchuanli.storm.db.MongoDB;
import com.yanchuanli.storm.db.MongoDBFactory;
import com.yanchuanli.storm.db.UserDao;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 12-8-14
 */
public class TestDB {

    private static Logger log = Logger.getLogger(TestDB.class);

    public static void main(String[] args) {
//        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME, MongoDB.COLL_PRODUCT);
        UserDao.querySourceUser(1, Conf.PAGESIZE);
    }


    public static Set<Integer> query(int pageNow, int pageSize) {
        Set<Integer> users = new HashSet<>();

        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_PRODUCT);

        DBCursor cur = coll.find(new BasicDBObject()).skip((pageNow - 1) * pageSize).limit(pageSize);
        while (cur.hasNext()) {
            DBObject obj = cur.next();
            users.add((Integer) obj.get("udid"));
        }
        return users;
    }
}
