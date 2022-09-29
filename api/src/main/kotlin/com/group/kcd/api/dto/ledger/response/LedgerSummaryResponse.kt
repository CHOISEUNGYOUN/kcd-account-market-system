package com.group.kcd.api.dto.ledger.response

import com.group.kcd.domain.ledger.LedgerType
import java.time.LocalDate

data class LedgerSummaryResponse(
  val txDate: LocalDate,
  val type: LedgerType,
  val totalAmount: Long,
)
