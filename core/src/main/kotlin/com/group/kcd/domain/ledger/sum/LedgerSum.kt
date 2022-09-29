package com.group.kcd.domain.ledger.sum

import com.group.kcd.domain.BaseEntity
import com.group.kcd.domain.ledger.LedgerType
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class LedgerSum constructor(
  val userId: Long,
  val txDate: LocalDate,

  @Enumerated(EnumType.STRING)
  val type: LedgerType,
  val amount: Long,
) : BaseEntity()