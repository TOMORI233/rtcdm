package com.zjubiomedit.shiro;

import com.zjubiomedit.config.exception.CommonJsonException;
import com.zjubiomedit.dao.Dict.OrgDictRepository;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.dto.DoctorEndDto.DoctorUserLoginDto;
import com.zjubiomedit.entity.Dict.OrgDict;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import com.zjubiomedit.util.Utils;
import com.zjubiomedit.util.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    DoctorUserAuthsRepository doctorUserRepository;
    @Autowired
    OrgDictRepository orgDictRepository;

    // 执行授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("授权");
        // 进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        DoctorUserLoginDto doctorUserLoginDto = (DoctorUserLoginDto) principalCollection.getPrimaryPrincipal();
        log.info(doctorUserLoginDto.getAuth().toString());
        // 添加授权字符串
        if (doctorUserLoginDto.getAuth().equals(Utils.GROUP)) {
            info.addStringPermission("group:optional");
        } else if (doctorUserLoginDto.getAuth().equals(Utils.PERSONAL)) {
            info.addStringPermission("person:optional");
        }
        return info;
    }

    // 执行认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("认证");
        // 编写认证逻辑
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 访问数据库获取数据
        Optional<DoctorUserAuths> optional;
        try {
            optional = doctorUserRepository.findByUserName(token.getUsername());
            if (optional.isPresent()) {
                DoctorUserAuths doctorUserAuths = optional.get();
                doctorUserAuths.setLoginCount(doctorUserAuths.getLoginCount() + 1);
                doctorUserRepository.save(doctorUserAuths);
                DoctorUserLoginDto doctorUserLoginDto = new DoctorUserLoginDto();
                doctorUserLoginDto.setUserName(token.getUsername());
                doctorUserLoginDto.setAuth(doctorUserAuths.getAuth());
                doctorUserLoginDto.setOrgCode(doctorUserAuths.getOrgCode());
                doctorUserLoginDto.setStatus(doctorUserAuths.getStatus());
                doctorUserLoginDto.setUserID(doctorUserAuths.getUserID());
                Optional<OrgDict> optionalOrgDict = orgDictRepository.findByOrgCodeAndIsValid(doctorUserAuths.getOrgCode(), Utils.VALID);
                optionalOrgDict.ifPresent(orgDict -> {
                    doctorUserLoginDto.setDivisionCode(orgDict.getDivisionCode());
                });
                return new SimpleAuthenticationInfo(doctorUserLoginDto, doctorUserAuths.getPassword(), getName());
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }
}
