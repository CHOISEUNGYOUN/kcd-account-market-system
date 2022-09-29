package com.group.kcd

import com.group.kcd.util.Log
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import kotlin.system.exitProcess

@EnableBatchProcessing
@EnableJpaAuditing
@SpringBootApplication
class KcdAccountMarketBatchApplication {
  companion object : Log()
}

fun main(args: Array<String>) {
  val exitCode = SpringApplication.exit(SpringApplication.run(KcdAccountMarketBatchApplication::class.java, *args))
  KcdAccountMarketBatchApplication.log.info("exitCode={}", exitCode)
  exitProcess(exitCode)
}

