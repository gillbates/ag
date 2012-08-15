package com.yanchuanli.storm.concurrent;

import com.google.common.collect.Sets;
import com.yanchuanli.storm.Memory.Conf;
import com.yanchuanli.storm.Memory.Util;
import com.yanchuanli.storm.db.UserDao;
import org.apache.log4j.Logger;

import java.util.Set;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 12-8-14
 */
public class ComputeThread implements Runnable {

    private int pageNum;
    private int totalPageCount;
    private static Logger log = Logger.getLogger(ComputeThread.class);

    public ComputeThread(int pageNum, int totalPageCount) {
        this.pageNum = pageNum;
        this.totalPageCount = totalPageCount;
    }

    @Override
    public void run() {
//        log.info("processing page:" + pageNum + "/" + totalPageCount);
        Set<String> data = UserDao.querySourceUser(pageNum, Conf.PAGESIZE);
//        log.info(data.size() + " data loaded in page " + pageNum);
        Set<String> result = Sets.intersection(data, Util.getUsers());
        Util.addSet(result);
        log.info("page " + pageNum + " finished processing with " + result.size() + " data matched ...");
    }
}
