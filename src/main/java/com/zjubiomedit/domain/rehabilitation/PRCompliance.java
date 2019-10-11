package com.zjubiomedit.domain.rehabilitation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "PRComp")
@Data
public class PRCompliance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @Column(columnDefinition = "varchar")
    private String patientIdentifier;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column
    private Date recordTime;
    @Column
    private String compliance;
    @Column
    private Integer realtimes;
    private Integer plantimes;

    public Integer getRealtimes() {
        return realtimes;
    }

    public void setRealtimes(Integer realtimes) {
        this.realtimes = realtimes;
    }
}
