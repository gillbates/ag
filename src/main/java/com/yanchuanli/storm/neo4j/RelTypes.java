package com.yanchuanli.storm.neo4j;

import org.neo4j.graphdb.RelationshipType;

/**
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 8/25/12
 */
public enum RelTypes implements RelationshipType {
    USERS_REFERENCE,
    USER
}
