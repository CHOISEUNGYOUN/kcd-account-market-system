package com.group.kcd

import com.group.kcd.batch.config.JobTestUtils
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean


@EnableBatchProcessing
@SpringBootTest
class BatchTestApplication {

  @Bean
  fun jobTestUtils(): JobTestUtils {
    return JobTestUtils()
  }

}