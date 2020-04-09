package com.zjubiomedit.service.impl;

import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.service.AutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AutoServiceImpl implements AutoService {

    @Autowired
    DoctorUserAuthsRepository doctorUserAuthsRepository;

    @Override
    public void autoInsertDocUser(Integer count) {
        log.info("autoInsertDocUser " + count.toString());
//        DoctorUserAuths newDoc = new DoctorUserAuths();
//        newDoc.setUserName("doc" + count.toString());
//        newDoc.setAuth(0);
//        newDoc.setName("医生" + count.toString() + "姓名");
//        newDoc.setOrgCode("A1");
//        newDoc.setPassword("1");
//        doctorUserAuthsRepository.save(newDoc);
//        log.info(newDoc.toString());
    }
}
