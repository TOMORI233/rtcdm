package com.zjubiomedit.service.impl;

import com.zjubiomedit.dao.Dict.OrgDictRepository;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.service.AuthService;
import com.zjubiomedit.util.Result;
import com.zjubiomedit.util.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Shiro登录
        // 获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        // 执行登录方法
        try {
            subject.login(token);
            // 登录成功
            return new Result(subject.getPrincipal());
        } catch (UnknownAccountException e) {
            return new Result(ErrorEnum.E_10001);
        } catch (IncorrectCredentialsException e) {
            return new Result(ErrorEnum.E_10002);
        }

//        if (userName == null || password == null) {
//            return new Result(ErrorEnum.E_501);
//        } else {
//            try {
//                Optional<DoctorUserAuths> userAuths = doctorUserRepository.findByUserName(userName);
//                if (userAuths.isPresent()) {
//                    DoctorUserAuths user = userAuths.get();
//                    if (user.getStatus() == Utils.USER_ACTIVE) {
//                        if (user.getPassword().equals(password)) {
//                            user.setLoginCount(user.getLoginCount() + 1);
//                            doctorUserRepository.save(user);
//                            DoctorUserLoginDto doctorUserLoginDto = new DoctorUserLoginDto();
//                            doctorUserLoginDto.setUserName(userName);
//                            doctorUserLoginDto.setAuth(user.getAuth());
//                            doctorUserLoginDto.setOrgCode(user.getOrgCode());
//                            doctorUserLoginDto.setStatus(user.getStatus());
//                            doctorUserLoginDto.setUserID(user.getUserID());
//                            Optional<OrgDict> optionalOrgDict = orgDictRepository.findByOrgCodeAndIsValid(user.getOrgCode(), Utils.VALID);
//                            optionalOrgDict.ifPresent(orgDict -> {
//                                doctorUserLoginDto.setDivisionCode(orgDict.getDivisionCode());
//                            });
//                            return new Result(doctorUserLoginDto);
//                        } else {
//                            return new Result(ErrorEnum.E_10002);
//                        }
//                    } else {
//                        return new Result(ErrorEnum.E_10004);
//                    }
//                } else {
//                    return new Result(ErrorEnum.E_10001);
//                }
//            } catch (NullPointerException e) {
//                throw new CommonJsonException(ErrorEnum.E_10007);
//            }
//        }
    }

    @Override
    public Result authLogout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new Result();
    }
}
