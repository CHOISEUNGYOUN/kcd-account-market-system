package com.group.kcd.domain.user

import com.group.kcd.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(
  uniqueConstraints = [UniqueConstraint(name = "uni_user_1", columnNames = ["email"])]
)
class User(
  val email: String,
  val password: String, // 암호화된 비밀번호
) : BaseEntity() {
  companion object {
    fun fixture(
      email: String = "lannstark@github.com",
      password: String = "1234",
    ): User {
      return User(
        email = email,
        password = password,
      )
    }
  }
}