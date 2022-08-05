package com.springbootAPI.cacheCsvToMongo.controller;

import com.springbootAPI.cacheCsvToMongo.models.responseDTO;
import com.springbootAPI.cacheCsvToMongo.services.IstateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/modak/CsvToMongo")
@Api(value = "user", tags = "User API")
public class stateController {
    @Autowired
    private IstateService sv;

    @RequestMapping(value = "/feedCSVData",method= RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Inserts data into inmemory database H2")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    public void InsertCsvData()
    {
        log.info("inserting data into H2 database");
        sv.insertCsvData();
    }


    @PostMapping("/state/{type}")
    @ApiOperation(value = "Gets covid data of a state")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "resource not found")
    })
    public ResponseEntity<List<responseDTO>> getStateDataByStateName(@PathVariable("type") String type) {
        log.info("Getting state covid data by state name ");

        return new ResponseEntity<List<responseDTO>>(sv.getDataByState(type), HttpStatus.OK);
    }

    @GetMapping("health")
    @ApiOperation(value="Checks the health of the API")
    public boolean HealthCheck(){
        log.info("Checking Health");
        return sv.healthCheck();
    }
}