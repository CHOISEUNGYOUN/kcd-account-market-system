package com.group.kcd.api.repository.ledger

import com.group.kcd.domain.ledger.Ledger
import com.group.kcd.domain.ledger.QLedger.ledger
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

}