package com.group.kcd.admin.dto.product.request

data class ProductUpdateRequest(
  val name: String,
  val price: Long,
  val remainCount: Int,
)