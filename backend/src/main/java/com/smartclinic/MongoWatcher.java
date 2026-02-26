package com.smartclinic;

import com.mongodb.client.*;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.OperationType;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.bson.Document;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MongoWatcher {

    private MongoClient mongoClient;

    @PostConstruct
    public void initWatcher() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("clinicdb");
        MongoCollection<Document> patients = database.getCollection("patients");

        System.out.println("MongoDB watcher initiating");

        new Thread(() -> {
            try (MongoChangeStreamCursor<ChangeStreamDocument<Document>> cursor = patients.watch().cursor()) {
                while (cursor.hasNext()) {
                    ChangeStreamDocument<Document> change = cursor.next();
                    Instant now = Instant.now();

                    OperationType type = change.getOperationType();
                    switch (type) {
                        case INSERT:
                            System.out.println("[" + now + "] New patient insert: " + change.getFullDocument());
                            break;
                        case UPDATE:
                            System.out.println("[" + now + "] Patient updated: " + change.getUpdateDescription());
                            break;
                        case DELETE:
                            System.out.println("[" + now + "] Patient deleted: " + change.getDocumentKey());
                            break;
                        default:
                            System.out.println("[" + now + "] Other change: " + change);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error in MongoWatcher: " + e.getMessage());
            }
        }).start();
    }

    @PreDestroy
    public void closeClient() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("MongoClient close.");
        }
    }
}