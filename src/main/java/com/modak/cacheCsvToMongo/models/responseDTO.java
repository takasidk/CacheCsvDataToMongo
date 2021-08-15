package com.modak.cacheCsvToMongo.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

@Entity
//lombok annotations
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//spring annotation
@Component
public class responseDTO {
    String State_code;
    String Date;
    String Death;
    String TotalTestResults;
    String Positive;
    String Negative;
    String Hospitalized;
    String InICICurrently;
    String OnVentilatorCurrently;

}

