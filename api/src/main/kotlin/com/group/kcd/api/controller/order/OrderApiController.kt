package com.group.kcd.api.controller.order

import com.group.kcd.api.config.LogInUserId
import com.group.kcd.api.dto.order.request.OrderCreateRequest
import com.group.kcd.api.service.order.OrderApiService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiController(
  private val orderApiService: OrderApiService,
) {

  @PostMapping("/api/v1/order")
  fun orderProduct(@RequestBody request: OrderCreateRequest, @LogInUserId userId: Long) {
    orderApiService.orderProduct(request, userId)
  }

}