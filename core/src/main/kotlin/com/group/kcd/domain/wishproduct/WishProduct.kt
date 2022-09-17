package com.group.kcd.domain.wishproduct

import com.group.kcd.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(
  uniqueConstraints = [UniqueConstraint(name = "uni_wish_product_1", columnNames = ["userId", "productId"])]
)
class WishProduct(
  val productId: Long,
  val userId: Long,
) : BaseEntity()