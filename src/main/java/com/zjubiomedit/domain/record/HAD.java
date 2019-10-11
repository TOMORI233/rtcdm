package com.zjubiomedit.domain.record;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/8
 */
@Entity(name = "HADRecord")
public class HAD extends GeneralRecord {
    @Column(name = "scroe")
    private Integer score;
    private Integer duration = 0;
    @Column(columnDefinition = "nvarchar")
    private String answers;
    private Integer flag = null;
    private Integer had1;
    private Integer had2;

    public HAD(){}

    public HAD(String patientId, Date measureTime, int score, String answers, int duration, int had1, int had2) {
        super(patientId, measureTime);
        this.score = score;
        this.answers = answers;
        this.duration = duration;
        this.had1 = had1;
        this.had2 = had2;
    }

    public int getDuration() {
        return duration;
    }

    public int getFlag() {
        return flag;
    }

    public String getAnswers() {
        return answers;
    }

    public int getScore() {
        return score;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Integer getHad1() {
        return had1;
    }

    public void setHad1(Integer had1) {
        this.had1 = had1;
    }

    public Integer getHad2() {
        return had2;
    }

    public void setHad2(Integer had2) {
        this.had2 = had2;
    }
}
