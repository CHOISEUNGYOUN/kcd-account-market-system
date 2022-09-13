package com.group.kcd.service.user

import com.group.kcd.domain.user.User
import com.group.kcd.domain.user.UserRepository
import com.group.kcd.dto.user.request.UserSignUpRequest
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
  private val userRepository: UserRepository,
) {

  @Transactional
  fun saveUser(request: UserSignUpRequest) {
    userRepository.save(
      User(
        email = request.email,
        password = BCrypt.hashpw(request.password, SALT)
      )
    )
  }

  companion object {
    /**
     * properties로도 뺄 수도 있고, AWS Secrets Manager 혹은 Spring Cloud Config 등으로 뺄 수도 있다.
     */
    const val SALT = "\$2a\$10\$81kF6U8mS3Bl6087K9xRG.96JClSCEsMzHKGdbCVRXZZTzgIHb5Oq"
  }

}