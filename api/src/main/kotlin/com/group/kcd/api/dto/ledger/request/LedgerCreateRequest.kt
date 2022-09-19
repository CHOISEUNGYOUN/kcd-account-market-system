package com.group.kcd.api.dto.ledger.request

import com.group.kcd.domain.ledger.Ledger
import com.group.kcd.domain.ledger.LedgerType
import java.time.LocalDate

data class LedgerCreateRequest(
  val txDate: LocalDate,
  val type: LedgerType,
  val amount: Long,
) {

  fun toEntity(userId: Long): Ledger {
    return Ledger(
      userId = userId,
      txDate = txDate,
      type = type,
      amount = amount,
    )
  }

}