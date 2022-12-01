package org.distributed.db;

import com.mongodb.MongoClientOptions;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.distributed.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MongoConnection {
    static MongoConnection instance;
    private MongoDatabase databaseObject;

    MongoConnection()
    {
        String database="chatApplication";
        String connectionString="mongodb://34.227.7.28:27017";
        try (MongoClient mongoClient = (MongoClient) MongoClients.create(connectionString)) {
            databaseObject = mongoClient.getDatabase(database);
            System.out.println("Db object created");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static MongoConnection getInstance() {
        if(instance == null) {
            instance = new MongoConnection();
        }
        return instance;
    }

    public boolean isUserExists(String username) {
        MongoCollection<Document> collection=databaseObject.getCollection("personalInfo");
        //FindIterable<Document> iterDoc = collection.find();

        List<Document> docList = collection.find().into(new ArrayList<>());
        //Iterator it = iterDoc.iterator();
        HashMap<String,String> users=new HashMap<>();
        for(Document document: docList) {
            //System.out.println(doc);
            String uname=document.getString("username");
            if(uname == username) {
                return true;
            }
        }
        return false;
    }

    public boolean isUserValid(String username, String password) {
        MongoCollection<Document> collection=databaseObject.getCollection("personalInfo");
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        HashMap<String,String> users=new HashMap<>();
        while (it.hasNext()) {
            Document doc= (Document) it.next();
            System.out.println(doc);
            String uname=doc.getString("username");
            String pwd=doc.getString("password");
            if(uname == username && password == pwd) {
                return true;
            }
        }
        return false;
    }

    public boolean addUser(String userName, String password) {
        //TODO: Create the document out of these atrifacts and insert.
        Document doc= new Document("username",userName).append("password", password);
        databaseObject.getCollection("personalInfo").insertOne(doc);
        return true;
    }

    public void addFriend(String from, String to) {
        Document doc= new Document("from",from).append("to", to);
        databaseObject.getCollection("friends").insertOne(doc);
    }

    public ArrayList<User> retrieveFriends(String username) {
        MongoCollection<Document> collection=databaseObject.getCollection("friends");
        ArrayList<User> friends = new ArrayList<User>();
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document doc= (Document) it.next();
            String from=doc.getString("from");
            String to=doc.getString("to");
            if(username == from) {
                friends.add(new User(to));
            }
        }

        return friends;
    }

}
