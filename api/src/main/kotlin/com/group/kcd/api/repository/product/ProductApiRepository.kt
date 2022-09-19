package com.group.kcd.api.repository.product

import com.group.kcd.domain.order.Order.Companion.AUTO_ORDER_THRESHOLD_AMOUNT
import com.group.kcd.domain.product.Product
import com.group.kcd.domain.product.QProduct.product
import com.group.kcd.domain.wishproduct.QWishProduct.wishProduct
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class ProductApiRepository(
  private val queryFactory: JPAQueryFactory,
) {

  fun findProductCanBeOrderAutomatically(userId: Long): Product? {
    return queryFactory.select(product)
      .from(product)
      .innerJoin(wishProduct).on(
        wishProduct.productId.eq(product.id),
        wishProduct.userId.eq(userId)
      )
      .where(
        product.remainCount.gt(0),
        product.price.loe(AUTO_ORDER_THRESHOLD_AMOUNT)
      )
      .fetchFirst()
  }

}