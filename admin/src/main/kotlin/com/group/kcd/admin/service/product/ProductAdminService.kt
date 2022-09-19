package com.group.kcd.admin.service.product

import com.group.kcd.admin.dto.product.request.ProductCreateRequest
import com.group.kcd.admin.dto.product.request.ProductUpdateRequest
import com.group.kcd.domain.product.ProductRepository
import com.group.kcd.util.failFetch
import com.group.kcd.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductAdminService(
  private val productRepository: ProductRepository,
) {

  @Transactional
  fun createProduct(request: ProductCreateRequest) {
    productRepository.save(request.toEntity())
  }

  @Transactional
  fun updateProduct(request: ProductUpdateRequest) {
    val product = productRepository.findByName(request.name) ?: failFetch()
    product.update(request.price, request.remainCount)
  }

  @Transactional
  fun deleteProduct(id: Long) {
    val product = productRepository.findByIdOrThrow(id)
    productRepository.delete(product)
  }

}