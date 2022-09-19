package com.group.kcd.api.service.ledger

import com.group.kcd.api.dto.ledger.request.LedgerCreateRequest
import com.group.kcd.api.dto.ledger.request.LedgerDeleteRequest
import com.group.kcd.api.repository.ledger.LedgerApiRepository
import com.group.kcd.api.repository.order.OrderApiRepository
import com.group.kcd.api.service.order.OrderApiService
import com.group.kcd.domain.ledger.LedgerRepository
import com.group.kcd.domain.ledger.netIncome
import com.group.kcd.domain.order.Order.Companion.AUTO_ORDER_THRESHOLD_AMOUNT
import com.group.kcd.dto.ledger.LedgerDto
import com.group.kcd.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class LedgerApiService(
  private val ledgerRepository: LedgerRepository,
  private val ledgerApiRepository: LedgerApiRepository,
  private val orderApiRepository: OrderApiRepository,
  private val orderApiService: OrderApiService,
) {

  @Transactional
  fun createLedger(request: LedgerCreateRequest, userId: Long) {
    ledgerRepository.save(request.toEntity(userId))
    listenOnLedgerChanged(userId, request.txDate)
  }

  @Transactional
  fun updateLedger(request: LedgerDto, userId: Long) {
    val ledger = ledgerRepository.findByIdOrThrow(request.id)
    ledger.update(request, userId)
    listenOnLedgerChanged(userId, ledger.txDate)
  }

  @Transactional
  fun deleteLedger(request: LedgerDeleteRequest, userId: Long) {
    val ledger = ledgerRepository.findByIdOrThrow(request.id)
    ledger.throwIfNotOwner(userId)
    ledgerRepository.delete(ledger)

    // 테스트 필요
    listenOnLedgerChanged(userId, ledger.txDate)
  }

  /**
   * 3가지 변형기법이 가능하다.
   * - private function
   * - 새로운 Service
   * - 이벤트 핸들링 (내부 이벤트 / 외부 이벤트 + 동기 / 비동기)
   */
  private fun listenOnLedgerChanged(userId: Long, txDate: LocalDate) {
    val ledgers = ledgerApiRepository.findAll(userId, txDate)
    val orderOrNull = orderApiRepository.findAutoFromNetIncomeOrder(userId, txDate)
    if (ledgers.netIncome >= AUTO_ORDER_THRESHOLD_AMOUNT && orderOrNull == null) {
      orderApiService.createAutoOrder(userId, txDate)
    }

    if (ledgers.netIncome < AUTO_ORDER_THRESHOLD_AMOUNT && orderOrNull != null) {
      orderApiService.deleteAutoOrder(orderOrNull.id)
    }
  }

}