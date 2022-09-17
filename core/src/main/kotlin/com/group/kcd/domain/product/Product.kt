package com.group.kcd.domain.product

import com.group.kcd.domain.BaseEntity
import com.group.kcd.domain.order.Order
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.persistence.Version

@Entity
@Table(
  uniqueConstraints = [UniqueConstraint(name = "uni_product_1", columnNames = ["name"])]
)
class Product(
  val name: String, // 물건의 항목
  var price: Long, // 물건의 가격
  var remainCount: Int, // 물건의 남은 수량
) : BaseEntity() {

  @Version
  val version: Int = 0

  fun update(price: Long, remainCount: Int) {
    this.price = price
    this.remainCount = remainCount
  }

  fun order(userId: Long, count: Int): Order {
    require(this.remainCount >= count) { "재고수량이 부족합니다!" }
    this.remainCount -= count

    return Order(
      userId = userId,
      productId = this.id,
      count = count,
      totalPrice = this.price * count,
    )
  }

  companion object {
    fun fixture(
      name: String = "사과",
      price: Long = 1000,
      remainCount: Int = 1,
    ): Product {
      return Product(
        name = name,
        price = price,
        remainCount = remainCount,
      )
    }
  }

}