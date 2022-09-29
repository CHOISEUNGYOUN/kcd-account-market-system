package com.group.kcd.scheduler.repository

import com.group.kcd.domain.ledger.QLedger.ledger
import com.group.kcd.domain.ledger.sum.LedgerSum
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class LedgerSumSchedulerRepository(
  private val queryFactory: JPAQueryFactory,
) {

  fun groupLedgers(txDate: LocalDate): List<LedgerSum> {
    return queryFactory
      .select(
        Projections.constructor(
          LedgerSum::class.java,
          ledger.userId,
          ledger.txDate,
          ledger.type,
          ledger.amount.sum()
        )
      )
      .from(ledger)
      .where(
        ledger.txDate.eq(txDate)
      )
      .groupBy(ledger.userId, ledger.type)
      .fetch()
  }

}