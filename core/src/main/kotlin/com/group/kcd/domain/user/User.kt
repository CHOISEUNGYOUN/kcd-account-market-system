package com.group.kcd.domain.user

import com.group.kcd.domain.BaseEntity
import javax.persistence.Entity

@Entity
class User(
  val email: String,
  val password: String, // 암호화된 비밀번호
) : BaseEntity()