package com.group.kcd.admin.controller.product

import com.group.kcd.admin.dto.product.request.ProductCreateRequest
import com.group.kcd.admin.dto.product.request.ProductUpdateRequest
import com.group.kcd.admin.service.ProductAdminService
import org.springframework.web.bind.annotation.*

@RestController
class ProductAdminController(
  private val productAdminService: ProductAdminService,
) {

  @PostMapping("/api/v1/product")
  fun saveProduct(@RequestBody request: ProductCreateRequest) {
    productAdminService.createProduct(request)
  }

  @PutMapping("/api/v1/product")
  fun updateProduct(@RequestBody request: ProductUpdateRequest) {
    productAdminService.updateProduct(request)
  }

  @DeleteMapping("/api/v1/product")
  fun deleteProduct(@RequestParam id: Long) {
    productAdminService.deleteProduct(id)
  }

}