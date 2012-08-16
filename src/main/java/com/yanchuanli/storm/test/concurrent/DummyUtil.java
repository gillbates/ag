package com.yanchuanli.storm.test.concurrent;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 8/16/12
 */
public class DummyUtil {

    private static List<String> data = new ArrayList<>();
    private static Logger log = Logger.getLogger(DummyUtil.class);

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
        startTime = System.nanoTime();
        List<String> mydata = loadDataFromDB();
        log.info(mydata);
    }

    public static void addString(String s) {
        data.add(s);
    }


    public static List<String> loadDataFromDB() {
        log.info("loading data from database ...");
        ExecutorService pool = Executors.newFixedThreadPool(3);
        List<DummyWorker> workers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DummyWorker dw = new DummyWorker(i);
            workers.add(dw);
        }

        try {
            List<Future<String>> result = pool.invokeAll(workers);
            for (Future<String> future : result) {
                data.add(future.get());
            }
        } catch (InterruptedException |ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        return data;
    }
}
