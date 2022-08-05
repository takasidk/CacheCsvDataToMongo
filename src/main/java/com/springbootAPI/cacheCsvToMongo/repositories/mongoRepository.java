package com.springbootAPI.cacheCsvToMongo.repositories;

import com.springbootAPI.cacheCsvToMongo.helpers.Constants;
import com.springbootAPI.cacheCsvToMongo.models.responseDTO;
import com.mongodb.client.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
@Slf4j
@Repository
public class mongoRepository {

    public void MongoInsert(List<Map<String, Object>> list, String Tb_Name) {
        // Creating a Mongo client
        MongoClient mongoClient = MongoClients.create(Constants.MongoUrl);
        MongoDatabase database = mongoClient.getDatabase(Constants.MongoDatabase);

        // Get the collection
        MongoCollection<Document> collection = database.getCollection(Tb_Name);
        List<Document> MongoList = new ArrayList<>();
        for (var i : list) {
            Document document = new Document(i);
            MongoList.add(document);
        }
        collection.insertMany(MongoList);
        log.info("Inserted Documents");
        mongoClient.close();
        System.out.println("Documents inserted.");
    }

    public List<responseDTO> MongoSearchByState(String state_code) {
        List<responseDTO> mylist = new ArrayList<>();
        Bson filter = eq("STATE_CODE", state_code);
        Bson sort = eq("DEATH", -1L);

        MongoClient mongoClient = MongoClients.create(Constants.MongoUrl);
        MongoDatabase database = mongoClient.getDatabase(Constants.MongoDatabase);
        MongoCollection<Document> collection = database.getCollection(Constants.MongoCollection);
        FindIterable<Document> result = collection.find(filter)
                .sort(sort);

        for (Document d : result) {
            Object o;
            responseDTO obj = new responseDTO();
            o=d.get("STATE_CODE");
            if(o==null) break;
            obj.setState_code(d.get("STATE_CODE").toString());
            o=d.get("DATE");
            obj.setDate((o != null) ? o.toString() : "null");
            o=d.get("DEATH");
            obj.setDeath((o != null) ? o.toString() : "null");
            o=d.get("POSITIVE");
            obj.setPositive((o != null) ? o.toString() : "null");
            o=d.get("NEGATIVE");
            obj.setNegative((o != null) ? o.toString() : "null");
            o=d.get("HOSPITALIZED");
            obj.setHospitalized((o != null) ? o.toString() : "null");
            o=d.get("TOTALTESTRESULTS");
            obj.setTotalTestResults((o != null) ? o.toString() : "null");
            o=d.get("ONVENTILATORCURRENTLY");
            obj.setOnVentilatorCurrently((o != null) ? o.toString() : "null");
            o=d.get("INICUCURRENTLY");
            obj.setInICICurrently((o != null) ? o.toString() : "null");
            mylist.add(obj);
        }
        log.info("Document Search in mongo Completed");
        mongoClient.close();
        return mylist;
    }
}
