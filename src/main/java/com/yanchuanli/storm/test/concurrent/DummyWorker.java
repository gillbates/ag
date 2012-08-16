package com.yanchuanli.storm.test.concurrent;

import org.apache.log4j.Logger;
import org.joda.time.Duration;

import java.util.concurrent.Callable;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 8/16/12
 */
public class DummyWorker implements Callable<String> {

    private static Logger log = Logger.getLogger(DummyWorker.class);

    private int id;

    public DummyWorker(int id) {
        this.id = id;
    }



    @Override
    public String call() throws Exception {
        log.info("worker " + id + " is working ...");
        try {
            Thread.sleep(Duration.standardSeconds(5).getMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("worker " + id + " is finished ...");
        return String.valueOf(id);
    }
}
