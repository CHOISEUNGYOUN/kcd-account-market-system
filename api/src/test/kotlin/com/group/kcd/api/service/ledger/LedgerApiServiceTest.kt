package com.group.kcd.api.service.ledger

import com.group.kcd.api.dto.ledger.request.LedgerCreateRequest
import com.group.kcd.api.dto.ledger.request.LedgerDeleteRequest
import com.group.kcd.domain.ledger.Ledger
import com.group.kcd.domain.ledger.LedgerRepository
import com.group.kcd.domain.ledger.LedgerType
import com.group.kcd.domain.order.Order
import com.group.kcd.domain.order.OrderRepository
import com.group.kcd.domain.order.OrderType
import com.group.kcd.domain.product.Product
import com.group.kcd.domain.product.ProductRepository
import com.group.kcd.domain.user.User
import com.group.kcd.domain.user.UserRepository
import com.group.kcd.domain.wishproduct.WishProduct
import com.group.kcd.domain.wishproduct.WishProductRepository
import com.group.kcd.dto.ledger.LedgerDto
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
internal class LedgerApiServiceTest @Autowired constructor(
  private val ledgerApiService: LedgerApiService,
  private val ledgerRepository: LedgerRepository,
  private val orderRepository: OrderRepository,
  private val productRepository: ProductRepository,
  private val wishProductRepository: WishProductRepository,
  private val userRepository: UserRepository,
) {

  @AfterEach
  fun clean() {
    ledgerRepository.deleteAllInBatch()
    orderRepository.deleteAllInBatch()
    productRepository.deleteAllInBatch()
    wishProductRepository.deleteAllInBatch()
    userRepository.deleteAllInBatch()
  }

  @Test
  fun `매출을 기록해 순이익이 50만원을 넘으면 자동 주문이 들어간다`() {
    // given
    val user = userRepository.save(User.fixture())
    val request = LedgerCreateRequest(LocalDate.of(2022, 1, 1), LedgerType.SALES, 500_000L)
    val product = productRepository.save(Product.fixture("사과"))
    wishProductRepository.save(WishProduct(productId = product.id, userId = user.id))

    // when
    ledgerApiService.createLedger(request, user.id)

    // then
    val order = orderRepository.findAll()[0]
    assertThat(order.productId).isEqualTo(product.id)
    assertThat(order.txDate).isEqualTo(LocalDate.of(2022, 1, 1))
    assertThat(order.type).isEqualTo(OrderType.AUTO_FROM_NET_INCOME)

    assertLedger(ledgerRepository.findAll()[0], user.id, LocalDate.of(2022, 1, 1), type = LedgerType.SALES, 500_000L)
    assertThat(productRepository.findAll()[0].remainCount).isEqualTo(0)
  }

  private fun assertLedger(ledger: Ledger, userId: Long, txDate: LocalDate, type: LedgerType, amount: Long) {
    assertThat(ledger.userId).isEqualTo(userId)
    assertThat(ledger.txDate).isEqualTo(txDate)
    assertThat(ledger.type).isEqualTo(type)
    assertThat(ledger.amount).isEqualTo(amount)
  }

  @Test
  fun `매출을 삭제해 50만원 미만이 되면 자동 주문이 취소된다`() {
    // given
    val txDate = LocalDate.of(2022, 1, 1)

    val user = userRepository.save(User.fixture())
    val ledger = ledgerRepository.save(Ledger.fixture(user.id, txDate, amount = 500_000L))
    val product = productRepository.save(Product.fixture("사과", remainCount = 0))
    orderRepository.save(Order.fixture(user.id, product.id, txDate = txDate, type = OrderType.AUTO_FROM_NET_INCOME))

    // when
    ledgerApiService.deleteLedger(LedgerDeleteRequest(ledger.id), user.id)

    // then
    assertThat(orderRepository.findAll()).isEmpty()
    assertThat(ledgerRepository.findAll()).isEmpty()
    assertThat(productRepository.findAll()[0].remainCount).isEqualTo(1)
  }

  @Test
  fun `매출을 수정할 수 있다`() {
    // given
    val user = userRepository.save(User.fixture())
    val ledger = ledgerRepository.save(Ledger.fixture(user.id, LocalDate.of(2022, 2, 1), amount = 100_000L))

    // when
    ledgerApiService.updateLedger(LedgerDto(ledger.id, LedgerType.COST, 200_000L), user.id)

    // then
    assertThat(orderRepository.findAll()).isEmpty()
    assertLedger(ledgerRepository.findAll()[0], user.id, LocalDate.of(2022, 2, 1), LedgerType.COST, 200_000L)
  }

}