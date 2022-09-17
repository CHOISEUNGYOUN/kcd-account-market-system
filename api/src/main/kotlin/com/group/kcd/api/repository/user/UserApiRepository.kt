package com.group.kcd.api.repository.user

import com.group.kcd.domain.user.QUser.user
import com.group.kcd.domain.user.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class UserApiRepository(
  private val queryFactory: JPAQueryFactory,
) {

  fun findByEmail(email: String): User {
    return queryFactory.select(user)
      .from(user)
      .where(
        user.email.eq(email)
      )
      .fetchOne() ?: throw IllegalArgumentException("유저를 찾지 못했습니다. email=${email}")
  }

}