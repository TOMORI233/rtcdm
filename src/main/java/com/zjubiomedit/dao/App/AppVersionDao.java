package com.zjubiomedit.dao.App;

import com.zjubiomedit.domain.App.AppVersion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/13
 */
public interface AppVersionDao extends CrudRepository<AppVersion, Long> {

    List<AppVersion> findByVersionCodeGreaterThanOrderByUpdateDateDesc(int versionCode);
}
