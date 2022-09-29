package com.group.kcd.batch.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class JobTestUtils {

  @Autowired
  private lateinit var applicationContext: ApplicationContext

  @Autowired
  private lateinit var jobRepository: JobRepository

  @Autowired
  private lateinit var jobLauncher: JobLauncher

  fun getJobTester(jobName: String): JobLauncherTestUtils {
    val bean = applicationContext.getBean(jobName, Job::class.java)
    val testUtils = JobLauncherTestUtils()
    testUtils.jobLauncher = jobLauncher
    testUtils.jobRepository = jobRepository
    testUtils.job = bean
    return testUtils
  }

  fun getUniqueJobParametersBuilder(): JobParametersBuilder {
    return JobParametersBuilder(JobLauncherTestUtils().uniqueJobParameters)
  }

}