package com.modak.cacheCsvToMongo.helpers;

public class Constants {
    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:mem:testdb";//"jdbc:h2:file:./data/demo";//"jdbc:h2:tcp://localhost/~/test";
    public static final String StateCovidPath="C:\\Users\\mt1703\\Downloads\\Sprint-Web-ApiFinal\\src\\main\\resources\\all-states-history-Copy.csv";
    public static final String StatenamesPath="C:\\Users\\mt1703\\Downloads\\Sprint-Web-ApiFinal\\src\\main\\resources\\StatesWithStatecodes.csv";
    public static final String sampleSTGPath="src/main/java/com/modak/cacheCsvToMongo/helpers/sample.stg";
    public static final String MongoCollection="stateCovidData1";
    public static final String MongoUrl="mongodb://w3.training5.modak.com:27017";
    public static final String MongoDatabase="training_dotnet_dt1703";
}
