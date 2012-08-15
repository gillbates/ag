package com.yanchuanli.storm.db;

import com.mongodb.*;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class MongoDBFactory {
    protected static Logger logger = Logger.getLogger(MongoDBFactory.class);

    public static final String MONGO_HOST = "192.168.1.166";
    public static final int MONGO_PORT = 27017;
    private static Mongo mongo;

    private MongoDBFactory() {
    }

    public static Mongo getMongo() {
        if (mongo == null) {
            try {
                String url = "192.168.7.41:27017,192.168.7.44:27017";
                ArrayList<ServerAddress> addr = new ArrayList<ServerAddress>();
                for (String s : url.split(",")) {
                    addr.add(new ServerAddress(s));
                }
                mongo = new Mongo(addr);
                mongo.setReadPreference(ReadPreference.SECONDARY);
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
