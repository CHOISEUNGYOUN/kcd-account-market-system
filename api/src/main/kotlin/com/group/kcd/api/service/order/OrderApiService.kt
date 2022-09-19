package com.group.kcd.api.service.order

import com.group.kcd.api.dto.order.request.OrderCreateRequest
import com.group.kcd.api.repository.product.ProductApiRepository
import com.group.kcd.domain.order.OrderRepository
import com.group.kcd.domain.product.ProductRepository
import com.group.kcd.util.findByIdOrThrow
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class OrderApiService(
  private val productRepository: ProductRepository,
  private val productApiRepository: ProductApiRepository,
  private val orderRepository: OrderRepository,
) {

  @Transactional
  @Retryable(value = [OptimisticLockingFailureException::class], backoff = Backoff(100), maxAttempts = 10)
  fun orderProduct(request: OrderCreateRequest, userId: Long) {
    val product = productRepository.findByIdOrThrow(request.productId)
    orderRepository.save(product.order(userId, request.count))
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Retryable(value = [OptimisticLockingFailureException::class], backoff = Backoff(100), maxAttempts = 10)
  fun createAutoOrder(userId: Long, txDate: LocalDate) {
    productApiRepository.findProductCanBeOrderAutomatically(userId)?.let { product ->
      orderRepository.save(product.autoOrder(userId, txDate))
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Retryable(value = [OptimisticLockingFailureException::class], backoff = Backoff(100), maxAttempts = 10)
  fun deleteAutoOrder(orderId: Long) {
    val order = orderRepository.findByIdOrThrow(orderId)
    val product = productRepository.findByIdOrThrow(order.productId)
    product.cancelOrder(order)
    orderRepository.delete(order)
  }

}