package com.zjubiomedit.config.system;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
@Configuration
public class JpaQueryConfig {
    //queryDsl 使用
    @Bean
    public JPAQueryFactory jpaQuery(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
