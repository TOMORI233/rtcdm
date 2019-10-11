package com.zjubiomedit.domain.App;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/13
 */
@Entity(name = "AppVersionHistory")
public class AppVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;
    private Integer versionCode;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String versionName;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String updateContent;
    @Column(columnDefinition = "TINYINT(1)")
    private Integer isForced;

    AppVersion(){}

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public long getSeq() {
        return seq;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Integer getIsForced() {
        return isForced;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setIsForced(Integer isForced) {
        this.isForced = isForced;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
