package com.group.kcd.api.controller.wishproduct

import com.fasterxml.jackson.databind.ObjectMapper
import com.group.kcd.api.dto.wishproduct.request.WishProductCreateRequest
import com.group.kcd.domain.product.Product
import com.group.kcd.domain.product.ProductRepository
import com.group.kcd.domain.user.User
import com.group.kcd.domain.user.UserRepository
import com.group.kcd.domain.wishproduct.WishProductRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class WishProductApControllerTest @Autowired constructor(
  private val userRepository: UserRepository,
  private val productRepository: ProductRepository,
  private val wishProductRepository: WishProductRepository,
  private val mockMvc: MockMvc,
  private val objectMapper: ObjectMapper,
) {

  @Test
  fun `Header에 있는 email을 인지하여 물건에 대한 찜을 한다`() {
    // given
    val user = userRepository.save(User.fixture(email = "A"))
    val product = productRepository.save(Product.fixture())

    // when
    val result = mockMvc.perform(
      post("/api/v1/wish-product")
        .header("Authorization", "A")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(WishProductCreateRequest(product.id)))
    )

    // then
    result.andExpect(status().isOk)
    val wishProducts = wishProductRepository.findAll()[0]
    assertThat(wishProducts.productId).isEqualTo(product.id)
    assertThat(wishProducts.userId).isEqualTo(user.id)
  }

}