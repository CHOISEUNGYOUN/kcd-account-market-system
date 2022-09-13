package com.group.kcd.admin.controller.product

import com.group.kcd.admin.dto.product.request.ProductCreateRequest
import com.group.kcd.admin.dto.product.request.ProductUpdateRequest
import com.group.kcd.admin.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(
  private val productService: ProductService,
) {

  @PostMapping("/api/v1/product")
  fun saveProduct(@RequestBody request: ProductCreateRequest) {
    productService.createProduct(request)
  }

  @PutMapping("/api/v1/product")
  fun updateProduct(@RequestBody request: ProductUpdateRequest) {
    productService.updateProduct(request)
  }

  @DeleteMapping("/api/v1/product")
  fun deleteProduct(@RequestParam id: Long) {
    productService.deleteProduct(id)
  }

}