package com.group.kcd.admin.controller.ledger

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.group.kcd.admin.dto.ledger.response.LedgerAdminResponse
import com.group.kcd.domain.ledger.Ledger
import com.group.kcd.domain.ledger.LedgerRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class LedgerAdminControllerTest @Autowired constructor(
  private val ledgerRepository: LedgerRepository,
  private val mockMvc: MockMvc,
  private val objectMapper: ObjectMapper,
) {

  @AfterEach
  fun clean() {
    ledgerRepository.deleteAllInBatch()
  }

  @Test
  fun `page와 size를 이용해 Ledger 목록을 가져올 수 있다`() {
    // given
    ledgerRepository.saveAll(
      listOf(
        Ledger.fixture(1L, amount = 1000L),
        Ledger.fixture(1L, amount = 2000L),
        Ledger.fixture(1L, amount = 3000L),
        Ledger.fixture(2L, amount = 4000L),
      )
    )
    val request = LinkedMultiValueMap<String, String>()
    request["userId"] = "1"
    request["page"] = "0"
    request["size"] = "2"

    // when
    val result = mockMvc.perform(
      MockMvcRequestBuilders.get("/api/v1/ledger")
        .params(request)
    )

    // then
    val stringResponse = result
      .andDo(print())
      .andExpect(status().isOk)
      .andReturn().response.contentAsString

    val jsonResponse = objectMapper.readValue<List<LedgerAdminResponse>>(stringResponse)
    assertThat(jsonResponse).hasSize(2)
    assertThat(jsonResponse).extracting("amount").containsExactly(1000L, 2000L)
  }

}