package com.group.kcd.admin.dto.ledger.response

import com.group.kcd.domain.ledger.Ledger
import com.group.kcd.domain.ledger.LedgerType
import java.time.LocalDate

data class LedgerAdminResponse(
  val txDate: LocalDate,
  val type: LedgerType,
  val amount: Long,
) {
  companion object {
    fun of(ledger: Ledger): LedgerAdminResponse {
      return LedgerAdminResponse(
        txDate = ledger.txDate,
        type = ledger.type,
        amount = ledger.amount,
      )
    }
  }
}