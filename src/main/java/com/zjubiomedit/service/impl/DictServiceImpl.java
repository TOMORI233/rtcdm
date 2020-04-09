package com.zjubiomedit.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.zjubiomedit.config.exception.CommonJsonException;
import com.zjubiomedit.dao.Dict.DivisionDictRepository;
import com.zjubiomedit.dao.Dict.OrgDictRepository;
import com.zjubiomedit.entity.Dict.DivisionDict;
import com.zjubiomedit.entity.Dict.OrgDict;
import com.zjubiomedit.service.DictService;
import com.zjubiomedit.util.Result;
import com.zjubiomedit.util.Utils;
import com.zjubiomedit.util.enums.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    DivisionDictRepository divisionDictRepository;
    @Autowired
    OrgDictRepository orgDictRepository;

    @Override
    public Result createDivision(DivisionDict divisionDict) {
        try {
            Object save = divisionDictRepository.save(divisionDict);
            return new Result(save);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result getDivision() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        List<DivisionDict> divisionDicts = divisionDictRepository.findByIsValid(Utils.VALID);
        ArrayList<DivisionDict> res = new ArrayList<>();
        HashMap<String, DivisionDict> map = new HashMap<>();
        for (DivisionDict obj : divisionDicts) {
            map.put(obj.getDivisionCode(), obj);
        }
        for (DivisionDict val : divisionDicts) {
            String parentCode = "";
            if (val.getParentDivisionCode() != null) {
                parentCode = val.getParentDivisionCode();
            }
            //如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
            DivisionDict parent = map.get(parentCode);
            if (parent != null) {
                if (parent.getChildren() == null) {
                    ArrayList<DivisionDict> temp = new ArrayList<>();
                    temp.add(val);
                    parent.setChildren(temp);
                } else {
                    ArrayList<DivisionDict> children = parent.getChildren();
                    children.add(val);
                    parent.setChildren(children);
                }
            } else {
                res.add(val);
            }
        }
//        return new Result(gson.toJson(res));
        return new Result(res);
    }

    @Override
    public Result createOrg(OrgDict orgDict) {
        try {
            Object save = orgDictRepository.save(orgDict);
            return new Result(save);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result getOrgByDivision(JsonObject jsonObject) {
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String divisionCode = jsonObject.get("divisionCode").getAsString();
            List<OrgDict> orgDicts = orgDictRepository.findByDivisionCodeAndIsValid(divisionCode, Utils.VALID);
            return new Result(orgDicts);
//        return new Result(gson.toJson(orgDicts))
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getHospitalList(String orgCode) {
        try {
            List<OrgDict> list = orgDictRepository.findByParentOrgCodeAndIsValid(orgCode, Utils.VALID);
            return new Result(list);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getOrgNameByOrgCode(String orgCode) {
        try{
            return new Result(orgDictRepository.findOrgNameByOrgCodeAndIsValid(orgCode, Utils.VALID));
        } catch (Exception e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }
}
