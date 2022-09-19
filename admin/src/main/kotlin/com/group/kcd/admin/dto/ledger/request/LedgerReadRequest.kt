package com.group.kcd.admin.dto.ledger.request

import com.group.kcd.domain.ledger.LedgerType
import java.time.LocalDate

data class LedgerReadRequest(
  val userId: Long? = null,
  val startTxDate: LocalDate? = null,
  val endTxDate: LocalDate? = null,
  val type: LedgerType? = null,
)
