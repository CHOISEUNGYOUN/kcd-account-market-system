package com.group.kcd.admin.dto.product.request

import com.group.kcd.domain.product.Product

data class ProductCreateRequest(
  val name: String,
  val price: Long,
  val remainCount: Int,
) {
  fun toEntity(): Product {
    return Product(
      name = name,
      price = price,
      remainCount = remainCount,
    )
  }
}
