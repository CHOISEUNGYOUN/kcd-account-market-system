package com.group.kcd.domain.product

import com.group.kcd.domain.BaseEntity
import com.group.kcd.domain.order.Order
import com.group.kcd.domain.order.OrderType
import java.time.LocalDate
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
    decreaseStock(count)
    return Order(
      userId = userId,
      productId = this.id,
      count = count,
      totalPrice = this.price * count,
    )
  }

  fun autoOrder(userId: Long, txDate: LocalDate): Order {
    decreaseStock(1)
    return Order(
      userId = userId,
      productId = this.id,
      count = 1,
      totalPrice = this.price,
      txDate = txDate,
      type = OrderType.AUTO_FROM_NET_INCOME,
    )
  }

  private fun decreaseStock(count: Int) {
    require(this.remainCount >= count) { "재고수량이 부족합니다!" }
    this.remainCount -= count
  }

  fun cancelOrder(order: Order) {
    this.remainCount += order.count
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