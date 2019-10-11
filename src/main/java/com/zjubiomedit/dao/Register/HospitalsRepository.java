package com.zjubiomedit.dao.Register;

import com.zjubiomedit.domain.Register.Hospitals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalsRepository extends JpaRepository<Hospitals, Integer> {
    List<Hospitals> findAllByProvinceCode(String provinceCode);
}
