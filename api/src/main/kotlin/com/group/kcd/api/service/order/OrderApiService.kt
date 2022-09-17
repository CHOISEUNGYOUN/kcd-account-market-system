package com.group.kcd.api.service.order

import com.group.kcd.api.dto.order.request.OrderCreateRequest
import com.group.kcd.domain.order.OrderRepository
import com.group.kcd.domain.product.ProductRepository
import com.group.kcd.util.findByIdOrThrow
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderApiService(
  private val productRepository: ProductRepository,
  private val orderRepository: OrderRepository,
) {

  @Transactional
  @Retryable(value = [OptimisticLockingFailureException::class], backoff = Backoff(100), maxAttempts = 10)
  fun orderProduct(request: OrderCreateRequest, userId: Long) {
    val product = productRepository.findByIdOrThrow(request.productId)
    orderRepository.save(product.order(userId, request.count))
  }

}