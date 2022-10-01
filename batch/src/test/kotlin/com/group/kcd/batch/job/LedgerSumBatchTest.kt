package com.group.kcd.batch.job

import com.group.kcd.batch.config.JobTestUtils
import com.group.kcd.batch.job.LedgerSumBatch.Companion.JOB_NAME
import com.group.kcd.domain.ledger.Ledger
import com.group.kcd.domain.ledger.LedgerRepository
import com.group.kcd.domain.ledger.LedgerType
import com.group.kcd.domain.ledger.sum.LedgerSum
import com.group.kcd.domain.ledger.sum.LedgerSumRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
internal class LedgerSumBatchTest @Autowired constructor(
  private val ledgerRepository: LedgerRepository,
  private val ledgerSumRepository: LedgerSumRepository,
  private val jobTestUtils: JobTestUtils,
) {

  @Test
  fun `특정 일자에 대한 집계가 정상 수행된다`() {
    // given
    val date1 = LocalDate.of(2022, 7, 31)
    val date2 = LocalDate.of(2022, 8, 1)

    ledgerRepository.saveAll(
      listOf(
        Ledger.fixture(1L, date1, LedgerType.SALES, 1000L),
        Ledger.fixture(1L, date1, LedgerType.SALES, 2000L),
        Ledger.fixture(1L, date1, LedgerType.COST, 4000L),
        Ledger.fixture(1L, date2, LedgerType.SALES, 8000L),
        Ledger.fixture(2L, date1, LedgerType.SALES, 16000L),
      )
    )

    val jobParameters = jobTestUtils.getUniqueJobParametersBuilder()
      .addString("txDate", "2022-07-31")
      .addString("chunkSize", "1000")
      .toJobParameters()

    // when
    val result = jobTestUtils.getJobTester(JOB_NAME).launchJob(jobParameters)

    // then
    assertThat(result.status).isEqualTo(BatchStatus.COMPLETED)

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