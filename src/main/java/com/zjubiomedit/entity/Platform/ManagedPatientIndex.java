package com.zjubiomedit.entity.Platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
@Entity
@Data
public class ManagedPatientIndex extends PlatformBaseEntity {
    @Column(length = 100)
    private String patientFeature; //如有多个，之间用逗号分隔，例如：老年人，肥胖，残疾人
    @Column(nullable = false)
    private Integer manageClass = 0; //哪种疾病，0-copd
    @Column(nullable = false)
    private Integer manageStatus = 0; // 0-管理中，1-向上转诊，2-向下转诊已，9-终止管理
    @Column(nullable = false)
    private Long hospitalID; // 关联所属医院集体账号
    @Column(nullable = false)
    private Long doctorID; // 关联所属医生/管理师个人账号
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date manageStartDateTime; // 最近一次管理的开始时间，如果是刚注册，则默认为审核通过时间
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastFollowupDate;
    private Integer followupTimes = 0; //本次管理的累计随访次数
    private Integer complianceRate = 0; //值为0至5，0表示依从度未知
    @Column(nullable = false)
    private Integer activeDegree = 1; //用户参与管理的活跃度，0-沉睡，1-活跃，默认值为1
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date terminationDateTime; //该字段只有当ManageStatus字段为9时才有意义
    @Column(length = 100)
    private String terminationReason;
    private String ext; // 保存不同管理分类的扩展信息

}
