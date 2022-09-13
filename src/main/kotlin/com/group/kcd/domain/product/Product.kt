package com.group.kcd.domain.product

import com.group.kcd.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(
  uniqueConstraints = [UniqueConstraint(name = "uni_product_1", columnNames = ["name"])]
)
class Product(
  val name: String, // 물건의 항목
  var price: Long, // 물건의 가격
  var remainCount: Int, // 물건의 남은 수량
) : BaseEntity() {

  fun update(price: Long, remainCount: Int) {
    this.price = price
    this.remainCount = remainCount
  }

  companion object {
    fun fixture(
      name: String,
      price: Long,
      remainCount: Int,
    ): Product {
      return Product(
        name = name,
        price = price,
        remainCount = remainCount,
      )
    }
  }

}