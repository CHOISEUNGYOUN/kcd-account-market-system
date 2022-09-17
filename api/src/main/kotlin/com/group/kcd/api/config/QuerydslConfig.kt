package com.group.kcd.api.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class QuerydslConfig(
  private val em: EntityManager,
) {

  @Bean
  fun queryFactory(): JPAQueryFactory {
    return JPAQueryFactory(em)
  }

}