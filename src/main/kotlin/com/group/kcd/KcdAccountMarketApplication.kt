package com.group.kcd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class KcdAccountMarketApplication

fun main(args: Array<String>) {
  runApplication<KcdAccountMarketApplication>(*args)
}
