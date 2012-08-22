package com.yanchuanli.storm.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.yanchuanli.storm.model.Relation;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 8/22/12
 */
public class RelationDao {
    public static void insertRelation(Relation r) {
        DBCollection coll = MongoDBFactory.getCollection(MongoDB.DBNAME,
                MongoDB.COLL_USER);

        DBObject doc = new BasicDBObject();
        doc.put("sfhma", r.getSfhma());
        doc.put("sfhmb", r.getSfhmb());
        coll.insert(doc);
    }
}
