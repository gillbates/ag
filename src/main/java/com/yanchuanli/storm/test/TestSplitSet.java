package com.yanchuanli.storm.test;

import com.yanchuanli.storm.concurrent.CheckInterSectionTask;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 12-8-14
 */
public class TestSplitSet {

    private static Logger log = Logger.getLogger(TestSplitSet.class);


    public static void main(String[] args) {

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(i);
        }
        ForkJoinTask sort = new CheckInterSectionTask(data,data.size());
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(sort);
        pool.shutdown();
        try {
            pool.awaitTermination(300, TimeUnit.SECONDS);
//            log.info(Util.getResult().size() + " users matched ...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
