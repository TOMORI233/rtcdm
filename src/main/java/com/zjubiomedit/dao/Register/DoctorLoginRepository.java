package com.zjubiomedit.dao.Register;

import com.zjubiomedit.domain.Register.DoctorLogin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorLoginRepository extends CrudRepository<DoctorLogin, String> {
    List<DoctorLogin> findAllByHospital(String hospital);
    Optional<DoctorLogin> findByUserId(String userId);
}
