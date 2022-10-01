package com.group.kcd.api.repository.ledger

import com.group.kcd.api.dto.ledger.response.LedgerSummaryResponse
import com.group.kcd.domain.ledger.Ledger
import com.group.kcd.domain.ledger.QLedger.ledger
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class LedgerApiRepository(
  private val queryFactory: JPAQueryFactory,
) {

  fun findAll(userId: Long, txDate: LocalDate): List<Ledger> {
    return queryFactory.select(ledger)
      .from(ledger)
      .where(
        ledger.userId.eq(userId),
        ledger.txDate.eq(txDate),
      )
      .fetch()
  }

  fun getSummary(userId: Long, startDate: LocalDate, endDate: LocalDate): List<LedgerSummaryResponse> {
    return queryFactory
      .select(
        Projections.constructor(
          LedgerSummaryResponse::class.java,
          ledger.txDate,
          ledger.type,
          ledger.amount.sum()
        )
      )
      .from(ledger)
      .where(
        ledger.userId.eq(userId),
        ledger.txDate.between(startDate, endDate)
      )
      .groupBy(ledger.txDate, ledger.type)
      .fetch()
  }

}