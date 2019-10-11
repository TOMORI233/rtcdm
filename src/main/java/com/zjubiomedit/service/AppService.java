package com.zjubiomedit.service;

import org.springframework.stereotype.Service;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/13
 */
@Service
public interface AppService {
    String checkUpdate(String patientId, int version);
}
