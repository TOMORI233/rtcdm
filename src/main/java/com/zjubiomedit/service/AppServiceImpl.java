package com.zjubiomedit.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zjubiomedit.dao.App.AppVersionDao;
import com.zjubiomedit.domain.App.AppVersion;
import com.zjubiomedit.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/13
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    AppVersionDao appVersionDao;

    @Override
    public String checkUpdate(String patientId, int version) {
        Map<String, Object> map = new HashMap<>();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        List<AppVersion> list = appVersionDao.findByVersionCodeGreaterThanOrderByUpdateDateDesc(version);
        if (list.size() > 0) {
            AppVersion appVersion = list.get(0);
            map.put("flag", Utils.SUCCESS);
//            map.put("newestVersion", gson.toJson(appVersion));
            map.put("newestVersion", appVersion);

        } else {
            map.put("flag", Utils.FAILURE);
        }
        return gson.toJson(map);
    }
}
