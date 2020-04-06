package com.zjubiomedit.service;

import com.google.gson.JsonObject;
import com.zjubiomedit.entity.Dict.DivisionDict;
import com.zjubiomedit.entity.Dict.OrgDict;
import com.zjubiomedit.util.Result;

public interface DictService {
    /**
     * 添加行政区划
     */
    Result createDivision(DivisionDict divisionDict);
    /**
     * 修改行政区划
     */

    /**
     * 获取线上行政列表
     */
    Result getDivision();
    /**
     * 添加医院机构
     */
    Result createOrg(OrgDict orgDict);
    /**
     * 修改医院机构
     */

    /**
     * 根据行政区划代码获取该地区医疗机构列表
     */
    Result getOrgByDivision(JsonObject jsonObject);

    Result getHospitalList(String orgCode);

    Result getOrgNameByOrgCode(String orgCode);
}
