package com.group.kcd.api.repository.order

import com.group.kcd.domain.order.Order
import com.group.kcd.domain.order.OrderType
import com.group.kcd.domain.order.QOrder.order
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class OrderApiRepository(
  private val queryFactory: JPAQueryFactory,
) {

  fun findAutoFromNetIncomeOrder(userId: Long, txDate: LocalDate): Order? {
    return queryFactory.select(order)
      .from(order)
      .where(
        order.userId.eq(userId),
        order.txDate.eq(txDate),
        order.type.eq(OrderType.AUTO_FROM_NET_INCOME)
      )
      .fetchFirst()
  }

}