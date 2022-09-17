package com.group.kcd.api.service.wishproduct

import com.group.kcd.api.dto.wishproduct.request.WishProductCreateRequest
import com.group.kcd.domain.product.ProductRepository
import com.group.kcd.domain.wishproduct.WishProduct
import com.group.kcd.domain.wishproduct.WishProductRepository
import com.group.kcd.util.findByIdOrThrow
import org.springframework.stereotype.Service

@Service
class WishProductApiService(
  private val wishProductRepository: WishProductRepository,
  private val productRepository: ProductRepository,
) {

  fun saveWishProduct(request: WishProductCreateRequest, userId: Long) {
    throwIfWishProductNotExists(request.productId)
    wishProductRepository.save(WishProduct(productId = request.productId, userId = userId))
  }

  private fun throwIfWishProductNotExists(productId: Long) {
    productRepository.findByIdOrThrow(productId)
  }

}