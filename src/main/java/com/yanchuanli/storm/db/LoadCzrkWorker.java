package com.yanchuanli.storm.db;

import org.apache.log4j.Logger;

import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 8/16/12
 */
public class LoadCzrkWorker implements Callable<Set<String>> {

    private int pageNo;
    private int pageSize;
    private static Logger log = Logger.getLogger(LoadCzrkWorker.class);

    public LoadCzrkWorker(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    @Override
    public Set<String> call() throws Exception {
        log.info("LoadCzrkWorker " + pageNo + " starting fetching page " + pageNo);
        Set<String> result = GADao.queryCzrk(pageNo, pageSize);
        log.info(result.size() + " data is loaded from page " + pageNo);
        return GADao.queryCzrk(pageNo, pageSize);
    }
}
