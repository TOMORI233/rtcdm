package com.zjubiomedit.domain.rehabilitation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "PRManager")
@Data
public class PRManager {

    @Id
    @Column(columnDefinition = "varchar")
    private String patientIdentifier;
    @Column
    private Integer state;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column
    private Date startTime;
    @Column
    private Integer duration;
    @Column
    private String rank;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column
    private Date endTime;
    private String memo;
    private String weekPlan;

}
