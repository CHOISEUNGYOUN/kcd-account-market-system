package com.group.kcd.api.controller.ledger

import com.group.kcd.api.config.LogInUserId
import com.group.kcd.api.dto.ledger.request.LedgerCreateRequest
import com.group.kcd.api.dto.ledger.request.LedgerDeleteRequest
import com.group.kcd.api.dto.ledger.response.LedgerSummaryResponse
import com.group.kcd.api.service.ledger.LedgerApiService
import com.group.kcd.dto.ledger.LedgerDto
import org.springframework.web.bind.annotation.*

@RestController
class LedgerApiController(
  private val ledgerApiService: LedgerApiService,
) {

  @PostMapping("/api/v1/ledger")
  fun createLedger(@RequestBody request: LedgerCreateRequest, @LogInUserId userId: Long) {
    ledgerApiService.createLedger(request, userId)
  }

  @PutMapping("/api/v1/ledger")
  fun updateLedger(@RequestBody request: LedgerDto, @LogInUserId userId: Long) {
    ledgerApiService.updateLedger(request, userId)
  }

  @DeleteMapping("/api/v1/ledger")
  fun deleteLedger(request: LedgerDeleteRequest, @LogInUserId userId: Long) {
    ledgerApiService.deleteLedger(request, userId)
  }

  @GetMapping("/api/v1/ledger/sum")
  fun getLedgerSummary(year: Int, month: Int, @LogInUserId userId: Long): List<LedgerSummaryResponse> {
    return ledgerApiService.getLedgerSummaryResponses(year, month, userId)
  }

}