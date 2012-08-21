package com.yanchuanli.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import org.apache.log4j.Logger;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 12-8-14
 */
public class SimpleSumExample {
    private static Logger log = Logger.getLogger(SimpleSumExample.class);

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new SimpleSpout(), 5);
        builder.setBolt("bolt", new SimpleBolt(), 5).shuffleGrouping("spout");

        Config conf = new Config();
        conf.setDebug(false);
        conf.setNumWorkers(10);


        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", conf, builder.createTopology());
        Utils.sleep(10000);
        cluster.killTopology("test");
        cluster.shutdown();
    }
}
