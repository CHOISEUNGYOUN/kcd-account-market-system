package com.group.kcd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@EnableRetry
@EnableJpaAuditing
@SpringBootApplication
class KcdAccountMarketAdminApplication

fun main(args: Array<String>) {
  runApplication<KcdAccountMarketAdminApplication>(*args)
}
