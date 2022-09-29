package com.group.kcd.scheduler.config

import com.group.kcd.util.Log
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

@Configuration
class SchedulerConfig : SchedulingConfigurer {

  companion object : Log()

  override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
    val scheduler = ThreadPoolTaskScheduler()
    scheduler.poolSize = 5
    scheduler.setErrorHandler { exception ->
      log.error("스케쥴러 에러 발생", exception)
    }
    scheduler.initialize()
    taskRegistrar.setScheduler(scheduler)
  }

}