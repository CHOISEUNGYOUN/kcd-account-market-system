package com.group.kcd.util

import com.querydsl.jpa.impl.JPAQuery
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

inline fun <reified T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
  return findById(id).orElseThrow { IllegalArgumentException("주어진 ${id}로 ${T::class.java}를 찾을 수 없습니다!") }
}

fun <T> JPAQuery<T>.withPageable(pageable: Pageable): JPAQuery<T> {
  return this.limit(pageable.pageSize.toLong())
    .offset(pageable.offset)
}
