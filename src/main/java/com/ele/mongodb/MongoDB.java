package com.ele.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * mongodb链接
 *
 * @author oukailiang
 * @create 2016-09-29 下午4:52
 */

public class MongoDB {
    static MongoDatabase mongoDatabase = null;
    static MongoClient mongoClient = null;

    public static MongoDatabase connnect() {
        try {
            // 连接到 mongodb 服务
            mongoClient = new MongoClient("localhost", 27017);

            // 连接到数据库
            if (mongoDatabase == null) {
                mongoDatabase = mongoClient.getDatabase("mydb");
            }
            System.out.println("Connect to database successfully");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return mongoDatabase;
    }

    public static void main(String[] arg) {
        MongoDB.connnect();

    }
}
