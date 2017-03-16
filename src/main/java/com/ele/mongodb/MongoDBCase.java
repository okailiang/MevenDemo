package com.ele.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.BSON;
import org.bson.Document;

import java.util.*;

/**
 * mongodb的基本操作
 *
 * @author oukailiang
 * @create 2016-09-29 下午6:07
 */

public class MongoDBCase {
    private static long id = 9000000;

    public static void createCollection() {
        try {
            MongoDatabase mongoDatabase = MongoDB.connnect();
            mongoDatabase.createCollection("operate_log");
            System.out.print("createCollection success!");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static MongoCollection<Document> getCollection() {
        MongoCollection<Document> mongoCollection = null;
        try {
            MongoDatabase mongoDatabase = MongoDB.connnect();
            mongoCollection = mongoDatabase.getCollection("operate_log");
            System.out.print("mongoCollection=" + mongoCollection.toString());
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return mongoCollection;
    }

    /**
     * 插入文档
     */
    public static void insertDocument() {
        try {
            MongoDatabase mongoDatabase = MongoDB.connnect();
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("operate_log");
            //Map<String, Object> map = new HashMap<String, Object>();
            // OperationLog operationLog = getOperationLog();
            // map = CollectionUtls.objectToMap1(operationLog);
            List<Document> documents = new ArrayList<Document>();
            for (int i = 0; i < 1000000; i++) {
                Document document = getDocument(getOperationLog());
                documents.add(document);
            }

            mongoCollection.insertMany(documents);
            System.out.print("insertDocument ok!");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * 获得文档
     */
    public static void getDocuments() {
        MongoCursor<Document> mongoCursor = null;
        try {
            MongoDatabase mongoDatabase = MongoDB.connnect();
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("operate_log");
            FindIterable<Document> findIterable = mongoCollection.find();
            mongoCursor = findIterable.iterator();
            while ((mongoCursor.hasNext())) {
                //System.out.println("1=" + mongoCursor.next().get("operationLog"));
                System.out.println("2=" + mongoCursor.next().toJson());
                //OperationLog operationLog = mongoCursor.next().get("57ed04a55f2cf211c401e703", OperationLog.class);
                // System.out.println("3=" + operationLog);
            }
            mongoCursor.close();

            System.out.print("get Document ok!");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            mongoCursor.close();
        }
    }

    /**
     * 获得一个文档
     */
    public static void getOneDocument() {
        MongoCursor<Document> mongoCursor = null;
        try {
            MongoDatabase mongoDatabase = MongoDB.connnect();
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("operate_log");
            FindIterable<Document> findIterable = mongoCollection.find(Filters.eq("id", 1));
            mongoCursor = findIterable.iterator();
            while ((mongoCursor.hasNext())) {
                //System.out.println("1=" + mongoCursor.next().get("operationLog"));
                Document d = mongoCursor.next();
                System.out.println("2=" + d.toJson());
                OperationLog operationLog = d.get("57ed04a55f2cf211c401e703", OperationLog.class);
                System.out.println("3=" + operationLog);
            }
            mongoCursor.close();

            System.out.print("get one Document ok!");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            mongoCursor.close();
        }
    }

    /**
     *
     */
    public static void deleteDocument() {
        MongoDatabase mongoDatabase = MongoDB.connnect();
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("operate_log");
        DeleteResult deleteResult = mongoCollection.deleteMany(Filters.exists("id"));

        System.out.println("delete success!" + deleteResult.getDeletedCount());
    }

    public static long getCount() {
        MongoDatabase mongoDatabase = MongoDB.connnect();
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("operate_log");
        return mongoCollection.count();
    }

    /**
     * 创建索引
     */
    public static void createIndex() {
        try {
            MongoDatabase mongoDatabase = MongoDB.connnect();
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("operate_log");


            mongoCollection.createIndex(new Document("id", 1));
            System.out.print("create index ok!");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static Document getDocument(OperationLog operationLog) {
        Document document = new Document("id", operationLog.getId()).append("className", operationLog.getClassName())
                .append("costTime", operationLog.getCostTime()).append("createTime", operationLog.getCreateTime())
                .append("creator", operationLog.getCreator()).append("ipAddress", operationLog.getIpAddress())
                .append("createId", operationLog.getCreatorId()).append("levelOneModule", operationLog.getLevelOneModule())
                .append("levelTwoModule", operationLog.getLevelTwoModule()).append("methodName", operationLog.getMethodName());
        return document;
    }

    public static OperationLog getOperationLog() {
        OperationLog operationLog = new OperationLog();
        operationLog.setClassName("class");
        operationLog.setCostTime(123l);
        operationLog.setCreateTime(new Date());
        operationLog.setCreator("adminact");
        operationLog.setCreatorId(123l);
        operationLog.setId(++id);
        System.out.println(id);
        operationLog.setIpAddress("127.0.0.1");
        operationLog.setLastModified(new Date());
        operationLog.setLevelOneModule(1);
        operationLog.setLevelTwoModule(2);
        operationLog.setMethodName("save");
        return operationLog;
    }

    public static void main(String[] args) {
        //
        //createCollection();
        //getCollection();
//        for (int i = 0; i < 10; i++) {
//            id++;
//            insertDocument();
//        }
        long start = System.currentTimeMillis();
        // insertDocument();
        System.out.println("insert many time=" + (System.currentTimeMillis() - start));
        //deleteDocument();
        //getDocuments();
        //createIndex();
        Map map = new HashMap();
        getOneDocument();
        // System.out.println(getCount());
    }
}
