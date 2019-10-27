package com.zjubiomedit.dao.User;

import com.zjubiomedit.entity.User.DoctorUserAuths;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DoctorUserAuthsRepository extends CrudRepository<DoctorUserAuths, Long> {

    Optional<DoctorUserAuths> findByUserName(String username);
}
