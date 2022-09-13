package com.group.kcd.util

import org.springframework.data.repository.CrudRepository

inline fun <reified T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
  return findById(id).orElseThrow { IllegalArgumentException("주어진 ${id}로 ${T::class.java}를 찾을 수 없습니다!") }
}