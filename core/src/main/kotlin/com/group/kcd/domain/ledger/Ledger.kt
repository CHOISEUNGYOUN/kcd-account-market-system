package com.group.kcd.domain.ledger

import com.group.kcd.domain.BaseEntity
import com.group.kcd.dto.ledger.LedgerDto
import com.group.kcd.util.kstNowDate
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 * 장부 사용자가 입력하는 원장
 */
@Entity
class Ledger(
  val userId: Long,
  val txDate: LocalDate,

  @Enumerated(EnumType.STRING)
  var type: LedgerType,

  var amount: Long,
) : BaseEntity() {

  init {
    require(amount >= 0)
  }

  val isSales: Boolean
    get() = this.type == LedgerType.SALES

  val isCost: Boolean
    get() = this.type == LedgerType.COST

  fun throwIfNotOwner(userId: Long) {
    require(this.userId == userId) { "주어진 ${userId}는 ${this.userId}와 다릅니다." }
  }

  fun update(dto: LedgerDto, userId: Long) {
    throwIfNotOwner(userId)
    this.type = dto.type
    this.amount = dto.amount
  }

  companion object {
    fun fixture(
      userId: Long = 0L,
      txDate: LocalDate = kstNowDate(),
      type: LedgerType = LedgerType.SALES,
      amount: Long = 10_000L
    ): Ledger {
      return Ledger(
        userId = userId,
        txDate = txDate,
        type = type,
        amount = amount
      )
    }
  }

}

val List<Ledger>.netIncome: Long
  get() = this.filter { it.isSales }.sumOf { it.amount } - this.filter { it.isCost }.sumOf { it.amount }