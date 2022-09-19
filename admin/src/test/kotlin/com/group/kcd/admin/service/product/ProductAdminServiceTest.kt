package com.group.kcd.admin.service.product

import com.group.kcd.admin.dto.product.request.ProductCreateRequest
import com.group.kcd.admin.dto.product.request.ProductUpdateRequest
import com.group.kcd.admin.service.product.ProductAdminService
import com.group.kcd.domain.product.Product
import com.group.kcd.domain.product.ProductRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ProductAdminServiceTest @Autowired constructor(
  private val productAdminService: ProductAdminService,
  private val productRepository: ProductRepository,
) {

  @AfterEach
  fun clean() {
    productRepository.deleteAll()
  }

  @Test
  fun `물건이 정상 생성된다`() {
    // given
    val request = ProductCreateRequest("사과", 1000L, 3)

    // when
    productAdminService.createProduct(request)

    // then
    val result = productRepository.findAll()[0]
    assertProduct(result, "사과", 1000L, 3)
  }

  @Test
  fun `물건이 업데이트 된다`() {
    // given
    productRepository.save(Product.fixture("사과", 1000L, 50))
    val request = ProductUpdateRequest("사과", 2000L, 100)

    // when
    productAdminService.updateProduct(request)

    // then
    val result = productRepository.findAll()[0]
    assertProduct(result, "사과", 2000L, 100)
  }

  @Test
  fun `물건이 삭제된다`() {
    // given
    val product = productRepository.save(Product.fixture("딸기", 500L, 50))

    // when
    productAdminService.deleteProduct(product.id)

    // then
    assertThat(productRepository.findAll()).isEmpty()
  }

  private fun assertProduct(product: Product, name: String, price: Long, remainCount: Int) {
    assertThat(product.name).isEqualTo(name)
    assertThat(product.price).isEqualTo(price)
    assertThat(product.remainCount).isEqualTo(remainCount)
  }

}