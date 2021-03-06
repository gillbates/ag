package com.yanchuanli.storm.concurrent;


import com.yanchuanli.storm.db.GADao;
import com.yanchuanli.storm.memory.Conf;
import com.yanchuanli.storm.memory.Util;
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
        log.info("processing page:" + pageNum + "/" + totalPageCount);
        Set<String> data = GADao.queryLgtrynb(pageNum, Conf.PAGESIZE);
        log.info(data.size() + " data loaded in page " + pageNum);
        Set<String> result = Util.getInterSection(data, Util.getUsers());
        Util.addSet(result);
        log.info("page " + pageNum + " finished processing with " + result.size() + " data matched ...");
    }
}
