package com.zjubiomedit.dao.Register;

import com.zjubiomedit.domain.Register.Provinces;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProvincesRepository extends CrudRepository<Provinces, Long> {
}
