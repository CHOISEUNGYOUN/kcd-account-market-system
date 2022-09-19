package com.group.kcd.util

import java.time.LocalDate
import java.time.ZoneId

fun kstNowDate(): LocalDate {
  return LocalDate.now(ZoneId.of("Asia/Seoul"))
}