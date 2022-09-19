package com.group.kcd.admin.controller.ledger

import com.group.kcd.admin.dto.ledger.request.LedgerReadRequest
import com.group.kcd.admin.dto.ledger.response.LedgerAdminResponse
import com.group.kcd.admin.service.ledger.LedgerAdminService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LedgerAdminController(
  private val ledgerAdminService: LedgerAdminService,
) {

  @GetMapping("/api/v1/ledger")
  fun findLedgers(request: LedgerReadRequest, @PageableDefault pageable: Pageable): List<LedgerAdminResponse> {
    return ledgerAdminService.findLedgers(request, pageable)
  }

}