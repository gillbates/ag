package com.yanchuanli.storm.Memory;

import com.yanchuanli.storm.model.Czrk;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 12-8-15
 */
public class Generator {
    private static RandomData ran = new RandomDataImpl();
    private static RandomStringUtils rans = new RandomStringUtils();
    private static long gmsfhm = 100000000000000000L;

    public static Czrk generateCzrk() {
        Czrk c = new Czrk();
        c.setRynbid(generate19DigitsLong());
        c.setRyid(generate19DigitsLong());
        c.setHhnbid(generate19DigitsLong());
        c.setMlpnbid(generate19DigitsLong());
        c.setZpid(generate19DigitsLong());
        c.setNbsfzid(generate19DigitsLong());
        c.setGmsfhm(String.valueOf(gmsfhm++));
        c.setQfjg(RandomStringUtils.randomAlphabetic(100));
        c.setYxqxjzrq(RandomStringUtils.randomAlphabetic(8));
        c.setYxqxqsrq(RandomStringUtils.randomAlphabetic(8));
        c.setXm(RandomStringUtils.randomAlphabetic(20));
        c.setCym(RandomStringUtils.randomAlphabetic(20));
        c.setXmpy(RandomStringUtils.randomAlphabetic(50));
        c.setCympy(RandomStringUtils.randomAlphabetic(50));
        c.setXb(ran.nextInt(0, 1));
        c.setMz(RandomStringUtils.randomAlphabetic(2));
        c.setCsrq(RandomStringUtils.randomAlphabetic(8));
        c.setCssj(RandomStringUtils.randomAlphabetic(6));
        c.setCsdgjdq(RandomStringUtils.randomAlphabetic(3));
        c.setCsdssxq(RandomStringUtils.randomAlphabetic(6));
        c.setCsdxz(RandomStringUtils.randomAlphabetic(100));
        c.setDhhm(RandomStringUtils.randomNumeric(100));
        c.setJhryxm(RandomStringUtils.randomAlphabetic(20));
        c.setJhrygmsfhm(RandomStringUtils.randomAlphabetic(18));
        c.setJhryjhgx(RandomStringUtils.randomAlphabetic(2));
        c.setJhrexm(RandomStringUtils.randomAlphabetic(20));
        c.setJhregmsfhm(RandomStringUtils.randomAlphabetic(18));
        c.setJhrejhgx(RandomStringUtils.randomAlphabetic(2));
        c.setFqxm(RandomStringUtils.randomAlphabetic(20));
        c.setFqgmsfhm(RandomStringUtils.randomAlphabetic(18));
        c.setMqxm(RandomStringUtils.randomAlphabetic(20));
        c.setMqgmsfhm(RandomStringUtils.randomAlphabetic(18));
        c.setPoxm(RandomStringUtils.randomAlphabetic(20));
        c.setPogmsfhm(RandomStringUtils.randomAlphabetic(18));
        c.setJggjdq(RandomStringUtils.randomAlphabetic(3));
        c.setJgssxq(RandomStringUtils.randomAlphabetic(6));
        c.setZjxy(RandomStringUtils.randomAlphabetic(2));
        c.setWhcd(RandomStringUtils.randomAlphabetic(2));
        c.setHyzk(RandomStringUtils.randomAlphabetic(2));
        c.setByzk(ran.nextInt(0, 9));
        c.setSg(RandomStringUtils.randomAlphabetic(3));
        c.setXx(ran.nextInt(0, 9));
        c.setZy(RandomStringUtils.randomAlphabetic(20));
        c.setZylb(RandomStringUtils.randomAlphabetic(3));
        c.setFwcs(RandomStringUtils.randomAlphabetic(100));
        c.setXxjb(ran.nextInt(0, 9));
        c.setHsql(RandomStringUtils.randomAlphabetic(8));
        c.setHyql(RandomStringUtils.randomAlphabetic(4));
        c.setHgjdqql(RandomStringUtils.randomAlphabetic(3));
        c.setHssxqql(RandomStringUtils.randomAlphabetic(6));
        c.setHxzql(RandomStringUtils.randomAlphabetic(100));
        c.setHslbz(RandomStringUtils.randomAlphabetic(8));
        c.setHylbz(RandomStringUtils.randomAlphabetic(4));
        c.setHgjdqlbz(RandomStringUtils.randomAlphabetic(3));
        c.setHsssqlbz(RandomStringUtils.randomAlphabetic(6));
        c.setHxzlbz(RandomStringUtils.randomAlphabetic(100));
        c.setSwrq(RandomStringUtils.randomAlphabetic(8));
        c.setSwzxlb(RandomStringUtils.randomAlphabetic(4));
        c.setSwzxrq(RandomStringUtils.randomAlphabetic(8));
        c.setQcrq(RandomStringUtils.randomAlphabetic(8));
        c.setQczxlb(RandomStringUtils.randomAlphabetic(4));
        c.setQwdgjdq(RandomStringUtils.randomAlphabetic(4));
        c.setQwdssxq(RandomStringUtils.randomAlphabetic(6));
        c.setQwdxz(RandomStringUtils.randomAlphabetic(100));
        c.setCszmbh(RandomStringUtils.randomAlphabetic(20));
        c.setCszqfrq(RandomStringUtils.randomAlphabetic(8));
        c.setHylb(RandomStringUtils.randomAlphabetic(2));
        c.setQtssxq(RandomStringUtils.randomAlphabetic(6));
        c.setQtzz(RandomStringUtils.randomAlphabetic(100));
        c.setRylb(ran.nextInt(0, 9));
        c.setHb(ran.nextInt(0, 9));
        c.setYhzgx(RandomStringUtils.randomAlphabetic(2));
        c.setRyzt(ran.nextInt(0, 9));
        c.setRysdzt(ran.nextInt(0, 9));
        c.setLxdbid(generate19DigitsLong());
        c.setBz(RandomStringUtils.randomAlphabetic(100));
        c.setJlbz(ran.nextInt(0, 9));
        c.setYwnr(RandomStringUtils.randomAlphabetic(3));
        c.setCjhjywid(generate19DigitsLong());
        c.setCchjywid(generate19DigitsLong());
        c.setQysj(RandomStringUtils.randomAlphabetic(14));
        c.setJssj(RandomStringUtils.randomAlphabetic(14));
        c.setCxbz(ran.nextInt(0, 9));
        return c;
    }

    private static long generate19DigitsLong() {
        return ran.nextLong(1111111111111111111L, 9223372036854775807L);
    }
}
