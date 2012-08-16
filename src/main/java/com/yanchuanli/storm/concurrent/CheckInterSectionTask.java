package com.yanchuanli.storm.concurrent;

import com.google.common.collect.Sets;
import com.yanchuanli.storm.memory.Conf;
import com.yanchuanli.storm.memory.Util;
import com.yanchuanli.storm.db.UserDao;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 12-8-14
 */

public class CheckInterSectionTask extends RecursiveAction {

    private List<Integer> pages;
    private static Logger log = Logger.getLogger(CheckInterSectionTask.class);
    private int totalPages;

    public CheckInterSectionTask(List<Integer> pages, int totalPages) {
        this.pages = pages;
        this.totalPages = totalPages;
    }

    @Override
    protected void compute() {
        if (pages.size() == 1) {
//            log.info("processing page:" + pages.get(0) + "/" + totalPages);
            Set<String> data = UserDao.querySourceUser(pages.get(0), Conf.PAGESIZE);
//            log.info(data.size() + " data loaded in page " + pages.get(0));
            Set<String> result = Sets.intersection(data, Util.getUsers());
            Util.addSet(result);
//            log.info("page " + pages.get(0) + " finished processing with " + result.size() + " data matched ...");
        } else {
            List<Integer> data1 = new ArrayList<>();
            data1.add(pages.get(0));
            pages.remove(0);
            invokeAll(new CheckInterSectionTask(data1, totalPages), new CheckInterSectionTask(pages, totalPages));
        }
    }
}
