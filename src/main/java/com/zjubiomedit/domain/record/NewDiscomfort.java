package com.zjubiomedit.domain.record;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author leiyiSheng
 * @date 2019/4/9
 */

@Entity(name = "NewDiscomfortRecord")
public class NewDiscomfort extends GeneralRecord{

	private int isDiscomfort;
	@Column(columnDefinition = "nvarchar")
	private String symptom;
	Integer ExecuteMark = null;

	public NewDiscomfort() {
	}

	public NewDiscomfort(String patientIdentifier, Date measureTime, int isDiscomfort, String symptom) {
		super(patientIdentifier, measureTime);
		this.isDiscomfort = isDiscomfort;
		this.symptom = symptom;
	}

	public int getIsDiscomfort() {
		return isDiscomfort;
	}

	public void setIsDiscomfort(int isDiscomfort) {
		this.isDiscomfort = isDiscomfort;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

}
