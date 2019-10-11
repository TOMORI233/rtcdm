package com.zjubiomedit.domain.rehabilitation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class PRData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @Column(columnDefinition = "varchar")
    private String patientIdentifier;
    @Column
    private String exerciseId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column
    private Date recordTime;
    @Column
    private String content;
}
