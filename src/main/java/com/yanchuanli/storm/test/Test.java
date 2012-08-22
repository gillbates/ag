package com.yanchuanli.storm.test;

import com.google.common.collect.Sets;
import com.yanchuanli.storm.concurrent.CheckInterSectionTask;
import com.yanchuanli.storm.concurrent.ComputeThread;
import com.yanchuanli.storm.db.GADao;
import com.yanchuanli.storm.db.RelationDao;
import com.yanchuanli.storm.db.UserDao;
import com.yanchuanli.storm.memory.Conf;
import com.yanchuanli.storm.memory.Generator;
import com.yanchuanli.storm.memory.Util;
import com.yanchuanli.storm.model.Czrk;
import com.yanchuanli.storm.model.Lgtrynb;
import com.yanchuanli.storm.model.Relation;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 12-8-14
 */

public class Test {

    private static Logger log = Logger.getLogger(Test.class);
    private static long basecount = 510105198310170510L;
    private static long startTime;


    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                elapsedTime = TimeUnit.NANOSECONDS.toMillis(elapsedTime);
                log.info(elapsedTime + " milli passed ...");
            }
        });
    }


    public static void main(String[] args) {
//        insertUsers();
//        insertSourceUsers();

        startTime = System.nanoTime();


//        forkJoinCheck();
//        sequentialCheck();
        insertUsers();
//        insertSourceUsers();
//        multiThreadedCheck();

//        testRefelection();
//        insertCzrk();
//          insertLgtrynb();

//        compareThreadedLoading();
//        insertRelation();
    }

    public static void testInterSectionOfUserAndRelation() {
        Set<String> data1 = RelationDao.getRelationsWithMultithread();
        log.info(data1.size() + " relations loaded ...");
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        elapsedTime = TimeUnit.NANOSECONDS.toMillis(elapsedTime);
        log.info(elapsedTime + " milli passed ...");
        Set<String> data2 = UserDao.getUsersWithMultithread();
        log.info(data1.size() + " users loaded ...");
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        elapsedTime = TimeUnit.NANOSECONDS.toMillis(elapsedTime);
        log.info(elapsedTime + " milli passed ...");


    }

    public static void insertRelation() {
        for (int i = 0; i < 10000000; i++) {
            long sfhma = basecount + i;

            for (int j = 0; j < 20; j++) {
                Relation r = new Relation(String.valueOf(sfhma), String.valueOf(sfhma + j));
                RelationDao.insertRelation(r);
            }
            if (i % 1000 == 0) {
                log.info(String.valueOf(i / 1000) + "/10000");
            }
        }
    }

    public static void compareThreadedLoading() {
        log.info("started ...");


//        Set<String> data1 = GADao.getCzrksWithMultithread();
//        log.info(data1.size() + " czrk loaded ...");

//        data1.clear();
        Set<String> data1 = GADao.getCzrkWithSingleThread();
        log.info(data1.size() + " czrk loaded ...");

    }


    public static void insertLgtrynb() {
        List<Lgtrynb> list = new ArrayList<>();
        for (int i = 0; i < 20000000; i++) {
            list.add(Generator.generateLgtrynb());
            if (i % 1000 == 0) {
                GADao.insertLgtrynb(list);
                list.clear();
                log.info(String.valueOf(i / 1000) + "/20000");
            }
        }
    }


    public static void insertCzrk() {
        List<Czrk> list = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            list.add(Generator.generateCzrk());
            if (i % 1000 == 0) {
                GADao.insertCzrk(list);
                list.clear();
                log.info(String.valueOf(i / 1000) + "/10000");
            }
        }
    }

    public static void testRefelection() {
        Czrk c = Generator.generateCzrk();
        Class clazz = c.getClass();
        Field[] fields = clazz.getDeclaredFields();
        log.info(fields.length + " fields found ...");
        for (Field f : fields) {
//            f.setAccessible(true);
            try {
                log.info(f.getName() + ":" + f.get(c));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    public static void multiThreadedCheck() {
        log.info("started ...");


        long czrkCount = GADao.gerCzrkCount();
        long lgtrynbCount = GADao.gerLgtrynbCount();


        ExecutorService pool;
        if (czrkCount <= lgtrynbCount) {
            Set<String> users = GADao.getCzrksWithMultithread();
            log.info(users.size() + " czrk found ...");
            Util.setUsers(users);
            int pageNum = (int) Math.ceil((double) lgtrynbCount / (double) Conf.PAGESIZE);
            pool = Executors.newFixedThreadPool(20);
            log.info("processing " + lgtrynbCount + " Lgtrynb in " + pageNum);
            for (int i = 1; i <= pageNum; i++) {
                ComputeThread ct = new ComputeThread(i, pageNum);
                pool.submit(ct);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Set<String> users = GADao.getLgtrynbs();
            log.info(users.size() + " zjhm found ...");
            Util.setUsers(users);


            int pageNum = (int) Math.ceil((double) czrkCount / (double) Conf.PAGESIZE);
            pool = Executors.newFixedThreadPool(pageNum);
            log.info("processing " + czrkCount + " Czrks in " + pageNum);
            for (int i = 1; i <= pageNum; i++) {
                ComputeThread ct = new ComputeThread(i, pageNum);
                pool.submit(ct);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        pool.shutdown();

    }

    public static void forkJoinCheck() {
        log.info("started ...");

        Set<String> users = UserDao.getUserSet();
        log.info(users.size() + " users loaded ...");
        Util.setUsers(users);
        long srcUserCount = UserDao.gerSourceUsersCount();
        int pageNum = (int) Math.ceil((double) srcUserCount / (double) Conf.PAGESIZE);
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= pageNum; i++) {
            pages.add(i);
        }


        ForkJoinTask sort = new CheckInterSectionTask(pages, pageNum);
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(sort);
        pool.shutdown();
        try {
            pool.awaitTermination(300, TimeUnit.SECONDS);
//            log.info(Util.getResult().size() + " users matched ...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info(Util.getResult().size() + " users matched ...");

    }

    public static void sequentialCheck() {
        log.info("started ...");
        Set<String> srcUsers = UserDao.getSourceUsers();
        log.info(srcUsers.size() + " src users loaded ...");
        Set<String> users = UserDao.getUserSet();
        log.info(users.size() + " users loaded ...");
        int result = 0;
        Set<String> resultSet = Sets.intersection(srcUsers, users);
        result = resultSet.size();
        log.info(result + " users matched ...");
    }

    public static void insertUsers() {
        log.info("started ...");
        for (int i = 0; i < 10000000; i++) {
            long result = basecount + i;
            if (i % 100000 == 0) {
                log.info(String.valueOf(i / 100000) + "/99");
            }
            UserDao.insertUser(String.valueOf(result), "用户" + String.valueOf(i));
        }
    }

    public static void insertSourceUsers() {
        log.info("started ...");
        for (int i = 0; i < 10000000; i++) {
            if (i % 100000 == 0) {
                log.info(String.valueOf(i / 100000) + "/99");
            }
            long result = basecount + i;
            if (i % 2 == 0) {
                UserDao.insertSourceUser(String.valueOf(result), "用户" + String.valueOf(result));
            }

        }
    }
}
