package com.group.kcd.scheduler.service

import com.group.kcd.domain.ledger.sum.LedgerSumRepository
import com.group.kcd.scheduler.repository.LedgerSumSchedulerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class LedgerSumSchedulerService(
  private val ledgerSumSchedulerRepository: LedgerSumSchedulerRepository,
  private val ledgerSumRepository: LedgerSumRepository,
) {

  @Transactional
  fun createSummary(txDate: LocalDate) {
    ledgerSumSchedulerRepository.groupLedgers(txDate)
      .let { ledgerSumRepository.saveAll(it) }
  }

}