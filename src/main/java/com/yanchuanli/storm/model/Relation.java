package com.yanchuanli.storm.model;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 8/22/12
 */
public class Relation {
    private String sfhma;
    private String sfhmb;

    public Relation(String sfhma, String sfhmb) {
        this.sfhma = sfhma;
        this.sfhmb = sfhmb;
    }

    public String getSfhma() {
        return sfhma;
    }

    public void setSfhma(String sfhma) {
        this.sfhma = sfhma;
    }

    public String getSfhmb() {
        return sfhmb;
    }

    public void setSfhmb(String sfhmb) {
        this.sfhmb = sfhmb;
    }
}
