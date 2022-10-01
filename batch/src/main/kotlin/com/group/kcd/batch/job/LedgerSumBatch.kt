package com.group.kcd.batch.job

import com.group.kcd.domain.ledger.sum.LedgerSum
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.EntityManagerFactory

@Configuration
class LedgerSumBatch(
  private val jobBuilderFactory: JobBuilderFactory,
  private val stepBuilderFactory: StepBuilderFactory,
  private val emf: EntityManagerFactory,
) {

  companion object {
    const val JOB_NAME = "LedgerSumBatchJob"
    const val DEFAULT_CHUNK_SIZE = 1000
    const val DEFAULT_TX_DATE = "2022-01-01"
  }

  @Bean(JOB_NAME)
  fun job(): Job {
    return jobBuilderFactory.get(JOB_NAME)
      .start(step())
      .preventRestart()
      .build()
  }

  @Bean(JOB_NAME + "step")
  @JobScope
  fun step(): Step {
    return stepBuilderFactory.get(JOB_NAME + "step")
      .chunk<LedgerSum, LedgerSum>(DEFAULT_CHUNK_SIZE)
      .reader(reader("2022-01-01", DEFAULT_CHUNK_SIZE))
      .writer(writer())
      .build()
  }

  @Bean(JOB_NAME + "reader")
  @StepScope
  fun reader(
    @Value("#{jobParameters[txDate]}") txDate: String,
    @Value("#{jobParameters[chunkSize]}") chunkSize: Int,
  ): JpaPagingItemReader<LedgerSum> {
    val params = mapOf("txDate" to LocalDate.parse(txDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    return JpaPagingItemReaderBuilder<LedgerSum>()
      .queryString(
        "SELECT NEW com.group.kcd.domain.ledger.sum.LedgerSum(l.userId, l.txDate, l.type, SUM(l.amount))" +
                " FROM Ledger l WHERE l.txDate = :txDate GROUP BY l.userId, l.type"
      )
      .parameterValues(params)
      .pageSize(chunkSize)
      .entityManagerFactory(emf)
      .name(JOB_NAME + "reader")
      .build()
  }

  @Bean(JOB_NAME + "writer")
  @StepScope
  fun writer(): JpaItemWriter<LedgerSum> {
    return JpaItemWriterBuilder<LedgerSum>()
      .entityManagerFactory(emf)
      .build()
  }


}