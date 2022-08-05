package com.springbootAPI.cacheCsvToMongo.repositories;

import com.springbootAPI.cacheCsvToMongo.helpers.Constants;
import com.springbootAPI.cacheCsvToMongo.models.responseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class h2Repository {

    @Autowired
    private ApplicationContext appContext;

    private static Connection conn = null;
    private static Statement stmt = null;
    //String Templates
    static STGroup group = new STGroupFile(Constants.sampleSTGPath, '$', '$');

    public Connection connect(){
        Connection connect=null;
        Environment environment = appContext.getBean(Environment.class);
        String USER=environment.getProperty("spring.datasource.username");
        String PASS=environment.getProperty("spring.datasource.password");
        try{
            Class.forName(Constants.JDBC_DRIVER);
            System.out.println("Connecting to database...");
            //STEP 2: Open a connection
            connect = DriverManager.getConnection(Constants.DB_URL, USER, PASS);
            log.info("Connected to H2 Database");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return connect;
    }

    public void insertCsvToH2() {

        String table_name1="AllStatesHistory";
        String StatesData=Constants.StateCovidPath;
        String table_name2="AllStatesWithStateCodes";
        String Statenames=Constants.StatenamesPath;
        try{
            conn=connect();
            stmt= conn.createStatement();
            //String sql = "CREATE TABLE AllStatesHistory AS SELECT * FROM CSVREAD('C:\\Users\\mt1703\\Downloads\\Sprint-Web-ApiFinal\\src\\main\\resources\\all-states-history-Copy.csv');";
            ST x=group.getInstanceOf("getSQLCreateCSv");
            x.add("table",table_name1);
            x.add("CsvFile",StatesData);
            String sql=x.render();
            stmt.execute(sql);
            System.out.println("Created AllStateHistory table");
            log.info("Created AllStateHistory table");
            //sql = "CREATE TABLE AllStatesWithStateCodes AS SELECT * FROM CSVREAD('C:\\Users\\mt1703\\Downloads\\Sprint-Web-ApiFinal\\src\\main\\resources\\StatesWithStatecodes.csv');";
            ST y=group.getInstanceOf("getSQLCreateCSv");
            y.add("table",table_name2);
            y.add("CsvFile",Statenames);
            sql=y.render();
            stmt.execute(sql);
            System.out.println("Created StatesWithStateCodes Table");
            log.info("Created StatesWithStateCodes Table");
            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String mapState_code(String State_name) {
        String state_code = "";
        String Column = "STATE";
        String FieldValue = State_name;
        String Table = "ALLSTATESWITHSTATECODES";
        try{
            conn=connect();
            stmt = conn.createStatement();
            ST x = group.getInstanceOf("getSQLWhere");
            x.add("field", FieldValue);
            x.add("column", Column);
            x.add("table", Table);
            //SQL query to be generated String sql = "SELECT * FROM ALLSTATESWITHSTATECODES WHERE STATE='" + State_name + "'";
            ResultSet rs = stmt.executeQuery(x.render());
            while (rs.next()) {
                state_code = rs.getString("STATE_CODE");
            }
            log.info("Mapped State name with state code");
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return state_code;
    }

    public List<responseDTO> queryDataFromH2(String state_code) {
        String Column = "STATE_CODE";
        String FieldValue = state_code;
        String Table = "AllStatesHistory";
        List<Map<String, Object>> mylist = new ArrayList<>();
        List<responseDTO> objlist = new ArrayList<>();
        try{
            conn=connect();
            stmt = conn.createStatement();
            ST sql = group.getInstanceOf("getSQLWhere");
            sql.add("field", FieldValue);
            sql.add("column", Column);
            sql.add("table", Table);
            //Query to be generated String sql = "SELECT * FROM AllStatesHistory e WHERE e.STATE_CODE='"+state_name+"'";
            ResultSet rs = stmt.executeQuery(sql.render());
            System.out.println("Connection Successful :)");
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            System.out.println("Fetching data.........");
            while (rs.next()) {
                responseDTO obj = new responseDTO();
                Object o;
                o = rs.getObject("STATE_CODE");
                if (o == null) break;
                obj.setState_code(rs.getObject("STATE_CODE").toString());
                o = rs.getObject("DATE");
                obj.setDate((o != null) ? o.toString() : "null");
                o = rs.getObject("DEATH");
                obj.setDeath((o != null) ? o.toString() : "null");
                o=rs.getObject("POSITIVE");
                obj.setPositive((o != null) ? o.toString() : "null");
                o=rs.getObject("NEGATIVE");
                obj.setNegative((o != null) ? o.toString() : "null");
                o=rs.getObject("HOSPITALIZED");
                obj.setHospitalized((o != null) ? o.toString() : "null");
                o=rs.getObject("TOTALTESTRESULTS");
                obj.setTotalTestResults((o != null) ? o.toString() : "null");
                o=rs.getObject("ONVENTILATORCURRENTLY");
                obj.setOnVentilatorCurrently((o != null) ? o.toString() : "null");
                o=rs.getObject("INICUCURRENTLY");
                obj.setInICICurrently((o != null) ? o.toString() : "null");
                objlist.add(obj);
                Map<String, Object> row = new HashMap<>(columns);
                for (int x = 1; x <= columns; ++x) {
                    row.put(md.getColumnName(x), rs.getObject(x));
                }
                mylist.add(row);
            }
            log.info("Fetched Data from H2 Database");
            rs.close();
            stmt.close();
            conn.close();
            mongoRepository mongo = new mongoRepository();
            mongo.MongoInsert(mylist, Constants.MongoCollection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return objlist;
    }

}

