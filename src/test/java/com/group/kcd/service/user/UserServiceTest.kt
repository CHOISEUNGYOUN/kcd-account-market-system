package com.group.kcd.service.user

import com.group.kcd.domain.user.UserRepository
import com.group.kcd.dto.user.request.UserSignUpRequest
import com.group.kcd.service.user.UserService.Companion.SALT
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class UserServiceTest @Autowired constructor(
  private val userService: UserService,
  private val userRepository: UserRepository,
) {

  @AfterEach
  fun clean() {
    userRepository.deleteAll()
  }

  @Test
  fun `유저가 저장되면 비밀번호가 암호화된다`() {
    // given
    val request = UserSignUpRequest("lannstark@naver.com", "1234")

    // when
    userService.saveUser(request)

    // then
    val result = userRepository.findAll()[0]
    assertThat(result.password).isNotEqualTo("1234")
    assertThat(result.password).isEqualTo(BCrypt.hashpw("1234", SALT))
  }

}