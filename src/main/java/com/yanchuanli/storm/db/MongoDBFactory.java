package com.yanchuanli.storm.db;

import com.mongodb.*;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class MongoDBFactory {
    protected static Logger logger = Logger.getLogger(MongoDBFactory.class);

    public static final String MONGO_HOST = "192.168.7.47:30000";
    private static Mongo mongo;

    private MongoDBFactory() {
    }

    public static Mongo getMongo() {
        if (mongo == null) {
            try {

                ArrayList<ServerAddress> addr = new ArrayList<ServerAddress>();
                for (String s : MONGO_HOST.split(",")) {
                    addr.add(new ServerAddress(s));
                    logger.info(s);
                }
                mongo = new Mongo(addr);
//                mongo.setReadPreference(ReadPreference.SECONDARY);
//                mongo.setWriteConcern(WriteConcern.REPLICAS_SAFE);
            } catch (UnknownHostException | MongoException e) {
                logger.error(e);
            }
        }

        return mongo;
    }

    public static DB getDB(String dbname) {
        return getMongo().getDB(dbname);
    }

    public static DBCollection getCollection(String dbname, String collection) {
        return getDB(dbname).getCollection(collection);

    }
}
