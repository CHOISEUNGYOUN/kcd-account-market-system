package com.group.kcd.api.service.order

import com.group.kcd.api.repository.ledger.LedgerApiRepository
import com.group.kcd.api.repository.order.OrderApiRepository
import com.group.kcd.domain.ledger.netIncome
import com.group.kcd.domain.order.Order
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.LocalDate

/**
 * 조금 복잡한(= Ledger, Order, User, Product 여러 도메인을 쓰는)
 * 로직이 있어..... 이러한 로직이 여러군데 쓰여요!
 *
 * private function으로 Service를 하나 만들어서 작성하나~
 * 어떤 차이가 있을까요?!
 * (1) 서비스에 있으면 누구나 쓸 수 있고, private 있으면 그 클래스만 쓸 수 있고 아닌가요?! (--> O)
 * ---> 여러 서비스에서 필요로 하면 무조건 새로운 Service를 써야 한다!
 *
 * (2) 어노테이션을 달 수 있게 됩니다!
 * (3) 의존성의 차이! (의존성에 따른 역할을 분리할 수 있다)
 *   --> mocking 테스트 / 이것저것 새로운 요구사항 구현...
 */
@Service
class LedgerOrderApiService(
  private val ledgerApiRepository: LedgerApiRepository,
  private val orderApiRepository: OrderApiRepository,
  private val orderApiService: OrderApiService,
) {

  fun listenOnLedgerChanged(userId: Long, txDate: LocalDate) {
    val ledgers = ledgerApiRepository.findAll(userId, txDate)
    val orderOrNull = orderApiRepository.findAutoFromNetIncomeOrder(userId, txDate)
    if (ledgers.netIncome >= Order.AUTO_ORDER_THRESHOLD_AMOUNT && orderOrNull == null) {
      orderApiService.createAutoOrder(userId, txDate)
    }

    if (ledgers.netIncome < Order.AUTO_ORDER_THRESHOLD_AMOUNT && orderOrNull != null) {
      orderApiService.deleteAutoOrder(orderOrNull.id)
    }
  }

}