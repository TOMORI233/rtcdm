package com.zjubiomedit.domain.rehabilitation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class PRWarning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @Column(columnDefinition = "varchar")
    private String patientIdentifier;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column
    private Date recordTime;
    @Column
    private String prewarning;
    @Column
    private Integer flag;
    @Column
    private Long dataSeq;

    public PRWarning(String patientIdentifier, Date recordTime, String prewarning, Integer flag, Long dataSeq) {
        this.patientIdentifier = patientIdentifier;
        this.recordTime = recordTime;
        this.prewarning = prewarning;
        this.flag = flag;
        this.dataSeq = dataSeq;
    }
}
