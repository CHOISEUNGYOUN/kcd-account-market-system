package com.group.kcd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
class KcdAccountMarketSchedulerApplication

fun main(args: Array<String>) {
  runApplication<KcdAccountMarketSchedulerApplication>(*args)
}