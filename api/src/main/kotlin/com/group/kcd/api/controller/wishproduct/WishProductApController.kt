package com.group.kcd.api.controller.wishproduct

import com.group.kcd.api.config.LogInUserId
import com.group.kcd.api.dto.wishproduct.request.WishProductCreateRequest
import com.group.kcd.api.service.wishproduct.WishProductApiService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class WishProductApController(
  private val wishProductApiService: WishProductApiService,
) {

  @PostMapping("/api/v1/wish-product")
  fun saveWishProduct(@RequestBody request: WishProductCreateRequest, @LogInUserId userId: Long) {
    wishProductApiService.saveWishProduct(request, userId)
  }

}