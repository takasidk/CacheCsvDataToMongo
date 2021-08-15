package com.modak.cacheCsvToMongo.services;

import com.modak.cacheCsvToMongo.models.responseDTO;
import com.modak.cacheCsvToMongo.repositories.h2Repository;
import com.modak.cacheCsvToMongo.repositories.mongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Service
public class stateService implements IstateService{
    @Autowired
    private mongoRepository mongorepo;

    @Autowired
    private h2Repository h2repo;

    public void insertCsvData() {
        h2repo.insertCsvToH2();
    }

    public List<responseDTO> getDataByState(String State_name) {
        String State_code=h2repo.mapState_code(State_name);
        List<responseDTO> mylist = new ArrayList<>();
        mylist = mongorepo.MongoSearchByState(State_code);
        if (mylist.size() == 0) {
            mylist = getFromH2(State_code);
        }
        return mylist;
    }

    public List<responseDTO> getFromH2(String State_code) {
        List<responseDTO> mylist = new ArrayList<>();
        mylist = h2repo.queryDataFromH2(State_code);
        return mylist;
    }

    public boolean healthCheck(){
        Connection conn=h2repo.connect();
        if(conn!=null){
            return true;
        }
        return false;
    }
}
