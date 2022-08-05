package com.springbootAPI.cacheCsvToMongo.services;

import com.springbootAPI.cacheCsvToMongo.models.responseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IstateService {
    public void insertCsvData();
    public List<responseDTO> getDataByState(String State_name);
    public List<responseDTO> getFromH2(String state);
    public boolean healthCheck();
}
