package com.group.kcd.scheduler.job

import com.group.kcd.scheduler.service.LedgerSumSchedulerService
import com.group.kcd.util.Log
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDate

/**
 * 단점 모니터링 없음 ㅠㅠㅠ.. (= 재시작/중단 불가)
 * 실패하면 그냥 지나감
 */
@Configuration
class LedgerSumScheduler(
  private val ledgerSumSchedulerService: LedgerSumSchedulerService,
) {

  companion object : Log()

  /**
   * 매 새벽 2시에 집계한다
   */
  @Scheduled(cron = "0 0 2 * * *") // 초 분 시 일 월 요일
  fun createLedgerSum() {
    log.info("[시작] createLedgerSum")
    val yesterday = LocalDate.now().minusDays(1)
    ledgerSumSchedulerService.createSummary(yesterday)
    log.info("[끝] createLedgerSum")
  }

  @Scheduled(fixedDelay = 10_000L) // 10초
  fun test1() {

  }

  @Scheduled(fixedRate = 10_000L) // 10초
  fun test2() {

  }

}