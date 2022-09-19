package com.group.kcd.api.service.order

import com.group.kcd.api.dto.order.request.OrderCreateRequest
import com.group.kcd.domain.order.OrderRepository
import com.group.kcd.domain.product.Product
import com.group.kcd.domain.product.ProductRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class OrderApiServiceTest @Autowired constructor(
  private val productRepository: ProductRepository,
  private val orderRepository: OrderRepository,
  private val orderApiService: OrderApiService,
) {

  @AfterEach
  fun clean() {
    productRepository.deleteAllInBatch()
    orderRepository.deleteAllInBatch()
  }

  @Disabled
  @OptIn(DelicateCoroutinesApi::class)
  @Suppress("BlockingMethodInNonBlockingContext")
  @Test
  fun `100개의 수량에 대해 200건의 주문이 동시에 몰리면 100건만 성공한다`(): Unit = runBlocking {
    // given
    val product = productRepository.save(Product.fixture(remainCount = 100))
    val request = OrderCreateRequest(product.id, 1)

    // when
    val jobs = (1..200).map {
      launch(newFixedThreadPoolContext(10, "test-threadpool")) {
        kotlin.runCatching { orderApiService.orderProduct(request, 1L) }
      }
    }
    jobs.forEach { job -> job.join() }

    // then
    val orders = orderRepository.findAll()
    assertThat(orders).hasSize(100)
  }

}