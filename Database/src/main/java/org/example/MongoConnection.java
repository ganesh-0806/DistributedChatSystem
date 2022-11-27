package org.example;

import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import java.util.Iterator;

public class MongoConnection {
    static MongoDatabase databaseObject;
    public static void main(String[] args) {
        String database="chatApplication";
        String connectionString="mongodb://34.236.156.141:27017";
       try (MongoClient mongoClient = (MongoClient) MongoClients.create(connectionString)) {

            databaseObject = mongoClient.getDatabase(database);
            System.out.println("Database connected");
            Document doc = new Document("username","manager").append("password", "manager");
            insertPersonalInfo(doc);
            doc = new Document("from","manager").append("to", "manager");
            insertFriends(doc);
            retrieveFriends();
            retrievePersonalInfo();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }


    private static void insertPersonalInfo(Document doc) {
        String collection="personalInfo";

        databaseObject.getCollection(collection).insertOne(doc);
    }
    private static void insertFriends(Document doc) {
        String collection="friends";
        databaseObject.getCollection(collection).insertOne(doc);
    }

    public static void retrieveFriends() {
        MongoCollection<Document> collection=databaseObject.getCollection("friends");
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
    public static void retrievePersonalInfo() {
        MongoCollection<Document> collection=databaseObject.getCollection("personalInfo");
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

}
