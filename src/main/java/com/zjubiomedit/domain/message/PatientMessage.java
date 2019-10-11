package com.zjubiomedit.domain.message;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author leiyiSheng
 * @date 2019/4/9
 */
@Entity
public class PatientMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seq;
	@Column(columnDefinition = "nvarchar", nullable = false)
	private String patientIdentifier;
	@Column(columnDefinition = "nvarchar", nullable = false)
	private String message;
	@Column(nullable = false)
	private Date messageTime;
	@Column(columnDefinition = "nvarchar", nullable = false)
	private String doctor;
	@Column(columnDefinition = "int")
	private Integer mark;
	private Integer messageFrom;

	public PatientMessage(){}

	public PatientMessage(String patientIdentifier, String message, Date messageTime, String doctor, Integer mark, Integer messageFrom) {
		this.patientIdentifier = patientIdentifier;
		this.message = message;
		this.messageTime = messageTime;
		this.doctor = doctor;
		this.mark = mark;
		this.messageFrom = messageFrom;
	}

	public Integer getMessageFrom() {
		return messageFrom;
	}

	public void setMessageFrom(Integer messageFrom) {
		this.messageFrom = messageFrom;
	}
	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getPatientIdentifier() {
		return patientIdentifier;
	}

	public void setPatientIdentifier(String patientIdentifier) {
		this.patientIdentifier = patientIdentifier;
	}
	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}
}
