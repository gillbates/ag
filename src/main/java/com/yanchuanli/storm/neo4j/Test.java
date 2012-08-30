package com.yanchuanli.storm.neo4j;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.impl.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 8/25/12
 */
public class Test {
    private static Logger log = Logger.getLogger(Test.class);
    private static GraphDatabaseService graphDb;
    private static String USERNAME_KEY = "username";
    private static final String DB_PATH = "target/neo4j-hello-db";
    private static Index<Node> nodeIndex;

    public static void main(String[] args) {
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        registerShutdownHook(graphDb);
        nodeIndex = graphDb.index().forNodes("");
        addUser();
        findUser();
    }



    private static void findUser() {
        int idToFind = 45;
        Node foundUser = nodeIndex.get(USERNAME_KEY, idToUserName(idToFind)).getSingle();
        log.info("The username of user " + idToFind + " is " + foundUser.getProperty(USERNAME_KEY));
    }

    private static void addUser() {
        Transaction tx = graphDb.beginTx();
        Node usersReferenceNode = graphDb.createNode();
        graphDb.getReferenceNode().createRelationshipTo(usersReferenceNode, RelTypes.USERS_REFERENCE);

        for (int id = 0; id < 100; id++) {
            Node userNode = createAndIndexUser(graphDb, idToUserName(id));
            usersReferenceNode.createRelationshipTo(userNode, RelTypes.USER);
        }

    }

    private static String idToUserName(final int id) {
        return "user" + id + "@neo4j.org";
    }

    private static Node createAndIndexUser(GraphDatabaseService graphDb, final String username) {
        Node node = graphDb.createNode();
        node.setProperty(USERNAME_KEY, username);
        nodeIndex.add(node, USERNAME_KEY, username);
        return node;
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running example before it's completed)
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    private void clearDb() {
        try {
            FileUtils.deleteRecursively(new File(DB_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
