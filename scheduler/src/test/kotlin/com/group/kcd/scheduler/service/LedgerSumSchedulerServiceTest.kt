package com.group.kcd.scheduler.service

import com.group.kcd.domain.ledger.Ledger
import com.group.kcd.domain.ledger.LedgerRepository
import com.group.kcd.domain.ledger.LedgerType
import com.group.kcd.domain.ledger.sum.LedgerSum
import com.group.kcd.domain.ledger.sum.LedgerSumRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
internal class LedgerSumSchedulerServiceTest @Autowired constructor(
  private val ledgerSumSchedulerService: LedgerSumSchedulerService,
  private val ledgerRepository: LedgerRepository,
  private val ledgerSumRepository: LedgerSumRepository,
) {

  @Test
  fun `특정 날짜에 대한 유저별 타입별 집계를 생성한다`() {
    // given
    val date1 = LocalDate.of(2022, 1, 1)
    val date2 = LocalDate.of(2022, 1, 2)

    ledgerRepository.saveAll(
      listOf(
        Ledger.fixture(1L, date1, LedgerType.SALES, 1000L),
        Ledger.fixture(1L, date1, LedgerType.SALES, 2000L),
        Ledger.fixture(1L, date1, LedgerType.COST, 4000L),
        Ledger.fixture(1L, date2, LedgerType.SALES, 8000L),
        Ledger.fixture(2L, date1, LedgerType.SALES, 16000L),
      )
    )

    // when
    ledgerSumSchedulerService.createSummary(date1)

    // then
    val results = ledgerSumRepository.findAll()
    assertThat(results).hasSize(3)

    assertLedgerSum(results, 1L, LedgerType.SALES, 3000L)
    assertLedgerSum(results, 1L, LedgerType.COST, 4000L)
    assertLedgerSum(results, 2L, LedgerType.SALES, 16000L)
  }

  private fun assertLedgerSum(ledgers: List<LedgerSum>, userId: Long, type: LedgerType, amount: Long) {
    val target = ledgers.first { it.userId == userId && it.type == type }
    assertThat(target.amount).isEqualTo(amount)
  }

}