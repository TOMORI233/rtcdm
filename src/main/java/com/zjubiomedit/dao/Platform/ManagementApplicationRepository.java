package com.zjubiomedit.dao.Platform;

import com.zjubiomedit.entity.Platform.FollowupPlan;
import com.zjubiomedit.entity.Platform.ManagementApplicationReview;
import org.springframework.data.repository.CrudRepository;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface ManagementApplicationRepository extends CrudRepository<ManagementApplicationReview, Long> {
}
