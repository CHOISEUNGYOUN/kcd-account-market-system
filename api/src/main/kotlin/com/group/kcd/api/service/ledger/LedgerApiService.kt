package com.group.kcd.api.service.ledger

import com.group.kcd.api.dto.ledger.request.LedgerCreateRequest
import com.group.kcd.api.dto.ledger.request.LedgerDeleteRequest
import com.group.kcd.api.dto.ledger.response.LedgerSummaryResponse
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

/**
 * 3가지 중 하나 선택
 * - 매출 or 비용 입력 즉시 처리해준다 / 거의 즉시 처리해준다 (Messagging Queue) / 일별로 처리한다
 */
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

    listenOnLedgerChanged(userId, ledger.txDate)
  }

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

  /**
   * 정책적으로 3년치만 보관 (무조건 필요함...)
   *
   * 하루에 수백 수천건 괜찮아요~~~
   * '장부' 원장이 하루에 200만건~300만건씩 들어와! (6개월 -> 5억건 / 1년 -> 10억 -> 5000만건 확준다...)
   *
   * -> 샤딩도 방법이다~~~ (코드 짤때 힘듬. 샤딩키 기준... 눈물...)
   *
   * 기가막힙니다~~ 미리 데이터를 말아둔다!!!!! 배치성작업 / 스케쥴링 작업
   * (일별기준으로 말려 있는 데이터)
   */
  @Transactional(readOnly = true)
  fun getLedgerSummaryResponses(year: Int, month: Int, userId: Long): List<LedgerSummaryResponse> {
    val startDate = LocalDate.of(year, month, 1)
    val endDate = LocalDate.of(year, month + 1, 1).minusDays(1)
    return ledgerApiRepository.getSummary(userId, startDate, endDate)
  }

}