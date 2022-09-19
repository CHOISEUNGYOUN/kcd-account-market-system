package com.group.kcd.admin.repository.ledger

import com.group.kcd.domain.ledger.Ledger
import com.group.kcd.domain.ledger.LedgerType
import com.group.kcd.domain.ledger.QLedger.ledger
import com.group.kcd.util.withPageable
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class LedgerAdminRepository(
  private val queryFactory: JPAQueryFactory,
) {

  fun findLedgers(
    userId: Long?,
    startTxDate: LocalDate?,
    endTxDate: LocalDate?,
    type: LedgerType?,
    pageable: Pageable,
  ): List<Ledger> {
    return queryFactory.select(ledger)
      .from(ledger)
      .where(
        userId?.let { ledger.userId.eq(it) },
        type?.let { ledger.type.eq(it) },
        conditionOnTxDate(startTxDate, endTxDate),
      )
      .withPageable(pageable)
      .fetch()
  }

  private fun conditionOnTxDate(startTxDate: LocalDate?, endTxDate: LocalDate?): BooleanExpression? {
    return when {
      startTxDate != null && endTxDate != null -> ledger.txDate.between(startTxDate, endTxDate)
      startTxDate != null -> ledger.txDate.goe(startTxDate)
      endTxDate != null -> ledger.txDate.loe(endTxDate)
      else -> null
    }
  }

}

