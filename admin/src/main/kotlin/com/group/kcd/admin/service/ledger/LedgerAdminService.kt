package com.group.kcd.admin.service.ledger

import com.group.kcd.admin.dto.ledger.request.LedgerReadRequest
import com.group.kcd.admin.dto.ledger.response.LedgerAdminResponse
import com.group.kcd.admin.repository.ledger.LedgerAdminRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LedgerAdminService(
  private val ledgerAdminRepository: LedgerAdminRepository,
) {

  @Transactional(readOnly = true)
  fun findLedgers(request: LedgerReadRequest, pageable: Pageable): List<LedgerAdminResponse> {
    return ledgerAdminRepository.findLedgers(
      request.userId,
      request.startTxDate,
      request.endTxDate,
      request.type,
      pageable,
    ).map { ledger -> LedgerAdminResponse.of(ledger) }
  }

}