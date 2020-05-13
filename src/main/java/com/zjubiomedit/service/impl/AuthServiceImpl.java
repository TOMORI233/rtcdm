package com.zjubiomedit.service.impl;

import com.zjubiomedit.config.exception.CommonJsonException;
import com.zjubiomedit.dao.Dict.OrgDictRepository;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.dto.DoctorEndDto.DoctorUserLoginDto;
import com.zjubiomedit.entity.Dict.OrgDict;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import com.zjubiomedit.service.AuthService;
import com.zjubiomedit.util.Result;
import com.zjubiomedit.util.Utils;
import com.zjubiomedit.util.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    DoctorUserAuthsRepository doctorUserRepository;
    @Autowired
    OrgDictRepository orgDictRepository;

    @Override
    public Result authLogin(String userName, String password) {
//        Subject currentUser = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        try {
//            currentUser.login(token);
//            info.put("result", "success");
//        } catch (AuthenticationException e) {
//            info.put("result", "fail");
//        }
        // 未使用shiro的登陆方式，暂用，其他调试成功添加shiro
        // TODO SHIRO 认证，授权
//        System.out.println(doctorUserLoginDto);

//        if (jsonObject.has("account") && jsonObject.has("password")) {
//            String userName = jsonObject.get("account").getAsString();
//            String password = jsonObject.get("password").getAsString();
//            Optional<DoctorUserAuths> userAuths = doctorUserRepository.findByUserName(userName);
//            if (userAuths.isPresent()) {
//                DoctorUserAuths user = userAuths.get();
//                if (user.getStatus() == 0) {
//                    if (user.getPassword().equals(password)) {
//                        user.setLoginCount(user.getLoginCount() + 1);
//                        doctorUserRepository.save(user);
//                        return new Result(user);
//                    }
//                    else {
//                        return new Result(ErrorEnum.E_10002);
//                    }
//                } else {
//                    return new Result(ErrorEnum.E_10004);
//                }
//            } else {
//                return new Result(ErrorEnum.E_10001);
//            }
//        } else {
//            return new Result(ErrorEnum.E_501);
//        }

        if (userName == null || password == null) {
            return new Result(ErrorEnum.E_501);
        } else {
            try {
                Optional<DoctorUserAuths> userAuths = doctorUserRepository.findByUserName(userName);
                if (userAuths.isPresent()) {
                    DoctorUserAuths user = userAuths.get();
                    if (user.getStatus() == Utils.USER_ACTIVE) {
                        if (user.getPassword().equals(password)) {
                            user.setLoginCount(user.getLoginCount() + 1);
                            doctorUserRepository.save(user);
                            DoctorUserLoginDto doctorUserLoginDto = new DoctorUserLoginDto();
                            doctorUserLoginDto.setUserName(userName);
                            doctorUserLoginDto.setAuth(user.getAuth());
                            doctorUserLoginDto.setOrgCode(user.getOrgCode());
                            doctorUserLoginDto.setStatus(user.getStatus());
                            doctorUserLoginDto.setUserID(user.getUserID());
                            Optional<OrgDict> optionalOrgDict = orgDictRepository.findByOrgCodeAndIsValid(user.getOrgCode(), Utils.VALID);
                            optionalOrgDict.ifPresent(orgDict -> {
                                doctorUserLoginDto.setDivisionCode(orgDict.getDivisionCode());
                            });
                            return new Result(doctorUserLoginDto);
                        } else {
                            return new Result(ErrorEnum.E_10002);
                        }
                    } else {
                        return new Result(ErrorEnum.E_10004);
                    }
                } else {
                    return new Result(ErrorEnum.E_10001);
                }
            } catch (NullPointerException e) {
                throw new CommonJsonException(ErrorEnum.E_10007);
            }
        }
    }
}
