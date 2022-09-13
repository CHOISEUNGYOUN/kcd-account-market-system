package com.group.kcd.api.controller.user

import com.group.kcd.api.dto.user.request.UserSignUpRequest
import com.group.kcd.api.service.user.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
  private val userService: UserService,
) {

  @PostMapping("/api/v1/user")
  fun saveUser(request: UserSignUpRequest) {
    userService.saveUser(request)
  }

}