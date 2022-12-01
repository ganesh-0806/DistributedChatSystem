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
        /*const client = new MongoClient(Config.database.url, {
            connectTimeoutMS: 5000,
            serverSelectionTimeoutMS: 5000
})*/
        String mongoUri ="mongodb://34.224.32.12:27017/chatApplication";


        try {
            MongoClient mongoClient = (MongoClient) MongoClients.create(mongoUri);
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

        List<Document> docList = collection.find().into(new ArrayList<>());
        for(Document document: docList) {
            System.out.println(document);
            String uname=document.getString("username");
            if(uname.equals(username)) {
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
            if(uname.equals(username) && password.equals(pwd)) {
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
        System.out.println("Add friend mongo collection");
        Document doc= new Document("from",from).append("to", to);
        databaseObject.getCollection("friends").insertOne(doc);

        doc= new Document("from",to).append("to", from);
        databaseObject.getCollection("friends").insertOne(doc);
    }

    public ArrayList<User> retrieveFriends(String username) {
        System.out.println("Retrieve friends");
        MongoCollection<Document> collection=databaseObject.getCollection("friends");
        ArrayList<User> friends = new ArrayList<User>();
        FindIterable<Document> iterDoc = collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            Document doc= (Document) it.next();
            System.out.println(doc);
            String from=doc.getString("from");
            String to=doc.getString("to");
            if(username.equals(from)) {
                friends.add(new User(to));
            }
        }

        System.out.println("Friends size " + friends.size());

        return friends;
    }

}
