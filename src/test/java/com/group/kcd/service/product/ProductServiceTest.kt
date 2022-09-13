package com.group.kcd.service.product

import com.group.kcd.domain.product.Product
import com.group.kcd.domain.product.ProductRepository
import com.group.kcd.dto.product.request.ProductCreateRequest
import com.group.kcd.dto.product.request.ProductUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ProductServiceTest @Autowired constructor(
  private val productService: ProductService,
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
    productService.createProduct(request)

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
    productService.updateProduct(request)

    // then
    val result = productRepository.findAll()[0]
    assertProduct(result, "사과", 2000L, 100)
  }

  @Test
  fun `물건이 삭제된다`() {
    // given
    val product = productRepository.save(Product.fixture("딸기", 500L, 50))

    // when
    productService.deleteProduct(product.id)

    // then
    assertThat(productRepository.findAll()).isEmpty()
  }

  private fun assertProduct(product: Product, name: String, price: Long, remainCount: Int) {
    assertThat(product.name).isEqualTo(name)
    assertThat(product.price).isEqualTo(price)
    assertThat(product.remainCount).isEqualTo(remainCount)
  }

}