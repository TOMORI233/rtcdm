package com.zjubiomedit.entity.User;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class PatientUserAuths extends UserBaseEntity{

    @Column(length = 200)
    private String weChat;
}
