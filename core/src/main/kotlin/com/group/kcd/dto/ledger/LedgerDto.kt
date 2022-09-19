package com.group.kcd.dto.ledger

import com.group.kcd.domain.ledger.LedgerType
import java.time.LocalDate

data class LedgerDto(
  val id: Long,
  val type: LedgerType,
  val amount: Long,
)