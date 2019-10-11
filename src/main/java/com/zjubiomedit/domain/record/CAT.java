package com.zjubiomedit.domain.record;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/8
 */
@Entity(name = "CATRecord")
public class CAT extends GeneralRecord {
    @Column(name = "scroe")
    private Integer score;
    private Integer duration = 0;
    @Column(columnDefinition = "nvarchar")
    private String answers;
    @Column(nullable = true)
    private Integer flag = null;

    public CAT(){}

    public CAT(String patientId, Date measureTime, int score, String answers, int duration) {
        super(patientId, measureTime);
        this.score = score;
        this.answers = answers;
        this.duration = duration;
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
}
